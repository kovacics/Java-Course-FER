package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class represents fully functional http server.
 * As an argument class must get path to the server configuration file.
 * Smart http server provides variety of functions to the user, such as
 * cookies implementation. Server uses memory management methods for
 * safer memory usage.
 *
 * @author Stjepan Kovačić
 */
public class SmartHttpServer {

    /** Server address. */
    private String address;

    /** Server domain name. */
    private String domainName;

    /** Port that server uses. */
    private int port;

    /** Number of threads server uses. */
    private int workerThreads;

    /** Session timeout used for cookies. */
    private int sessionTimeout;

    /** Thread for whole server work. */
    private ServerThread serverThread;

    /** Pool of threads. */
    private ExecutorService threadPool;

    /** Server root path. */
    private Path documentRoot;

    /** Map of all mime types. */
    private Map<String, String> mimeTypes = new HashMap<>();

    /** Map of all workers. */
    private Map<String, IWebWorker> workersMap = new HashMap<>();

    /** Map of all cached sessions. */
    private Map<String, SessionMapEntry> sessions = new HashMap<>();

    /** Random number generator. */
    private Random sessionRandom = new Random();

    /** Cleaning thread time. */
    private static final int CLEANING_THREAD_TIME = 5 * 60 * 1000;

    /**
     * Constructs smart http server from the given server configuration file.
     *
     * @param configFileName server configuration file
     */
    public SmartHttpServer(String configFileName) {
        Properties server = new Properties();
        try {
            server.load(Files.newBufferedReader(Paths.get(configFileName)));
        } catch (IOException e) {
            throw new RuntimeException("Given file cannot be loaded");
        }

        initServer(server);
    }

    /**
     * Starts the server.
     */
    protected synchronized void start() {
        serverThread = new ServerThread();
        serverThread.start();
        threadPool = Executors.newFixedThreadPool(workerThreads);

        addCleaningThread();
    }

    /**
     * Method adds session cleaning thread.
     */
    private void addCleaningThread() {

        Thread sessionCleaner = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(CLEANING_THREAD_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                sessions.forEach((sid, entry) -> {
                    if (entry.validUntil < System.currentTimeMillis()) {
                        sessions.remove(sid);
                    }
                });
            }
        });

        sessionCleaner.setDaemon(true);
        sessionCleaner.start();
    }

    /**
     * Stops server and shutdowns thread pool.
     */
    protected synchronized void stop() {
        serverThread.interrupt();
        threadPool.shutdown();
    }

    /**
     * Thread specific for the server.
     * Thread waits for the connection and then process the request.
     */
    protected class ServerThread extends Thread {

        @Override
        public void run() {

            try {
                @SuppressWarnings("resource")
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress(address, port));

                while (true) {
                    Socket client = serverSocket.accept();
                    ClientWorker cw = new ClientWorker(client);
                    threadPool.submit(cw);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //**********************
    //    INIT METHODS
    //**********************

    /**
     * Initializes the all server properties.
     *
     * @param server server properties class
     */
    private void initServer(Properties server) {
        address = server.getProperty("server.address");
        domainName = server.getProperty("server.domainName");
        documentRoot = Paths.get(server.getProperty("server.documentRoot")).normalize().toAbsolutePath();

        try {
            port = Integer.parseInt(server.getProperty("server.port"));
            workerThreads = Integer.parseInt(server.getProperty("server.workerThreads"));
            sessionTimeout = Integer.parseInt(server.getProperty("session.timeout"));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Server config file is invalid.");
        }

        initMimeTypes(server);
        initWorkers(server);
    }

    /**
     * Private method for initializing mime types map.
     *
     * @param server server properties class
     */
    private void initMimeTypes(Properties server) {
        Properties mime = new Properties();
        try {
            mime.load(Files.newBufferedReader(Paths.get(server.getProperty("server.mimeConfig")).toAbsolutePath()));
        } catch (IOException e) {
            throw new RuntimeException("Given file cannot be loaded");
        }

        mime.forEach((key, value) -> mimeTypes.put((String) key, (String) value));
    }

    /**
     * Private method for initializing workers map.
     *
     * @param server server properties class
     */
    private void initWorkers(Properties server) {
        Properties workers = new Properties();
        try {
            workers.load(Files.newBufferedReader(Paths.get(server.getProperty("server.workers")).toAbsolutePath()));
        } catch (IOException e) {
            throw new RuntimeException("Given file cannot be loaded");
        }

        workers.forEach((path, fqcn) -> {
            try {
                Class<?> referenceToClass = this.getClass().getClassLoader().loadClass((String) fqcn);
                Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
                IWebWorker iww = (IWebWorker) newObject;
                workersMap.put((String) path, iww);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //**************************
    //    CLIENT WORKER CLASS
    //**************************


    /**
     * Private class that represents client serving task.
     */
    private class ClientWorker implements Runnable, IDispatcher {

        /** Client socket. */
        private Socket csocket;

        /** Input stream. */
        private PushbackInputStream istream;

        /** Output stream. */
        private OutputStream ostream;

        /** (Http) version. */
        private String version;

        /** Method. */
        private String method;

        /** Host. */
        private String host;

        /** Parameters map. */
        private Map<String, String> params = new HashMap<>();

        /** Temporary parameters map. */
        private Map<String, String> tempParams = new HashMap<>();

        /** Permanent parameters map. */
        private Map<String, String> permParams = new HashMap<>();

        /** Output cookies list. */
        private List<RequestContext.RCCookie> outputCookies = new ArrayList<>();

        /** Session id. */
        private String sid;

        /** Request context. */
        private RequestContext context = null;

        /**
         * Constructs client worker for given client socket.
         *
         * @param csocket client socket
         */
        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
        }

        @Override
        public void run() {

            try {
                istream = new PushbackInputStream(csocket.getInputStream());
                ostream = new BufferedOutputStream(csocket.getOutputStream());
                List<String> request = readRequest();

                if (request == null) {
                    sendError(ostream, 400, "Bad request");
                    csocket.close();
                    return;
                }

                String[] firstLineParts = request.get(0).split(" ");
                method = firstLineParts[0];
                String requestedPath = firstLineParts[1];
                version = firstLineParts[2];

                if (!method.equals("GET") || !version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
                    sendError(ostream, 400, "Bad request");
                    csocket.close();
                    return;
                }

                setHost(request);
                checkSession(request);

                int splitIndex = requestedPath.lastIndexOf("?");
                if (splitIndex != -1) {
                    String paramString = requestedPath.substring(splitIndex + 1);
                    requestedPath = requestedPath.substring(0, splitIndex);
                    parseParameters(paramString);
                }
                internalDispatchRequest(requestedPath, true);
                ostream.flush();
                csocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Method process the request.
         *
         * @param urlPath    url path user requested
         * @param directCall flag if direct call
         * @throws Exception if error happens while processing request
         */
        public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
            if (notAllowedRequest(urlPath, directCall)) {
                sendError(ostream, 404, "File not found.");
                return;
            }
            initContextIfNull();
            IWebWorker webWorker = getWebWorker(urlPath);

            // worker request
            if (webWorker != null) {
                webWorker.processRequest(context);
                return;
            }

            // file request
            Path fullPath = documentRoot.resolve(urlPath.substring(1));

            if (outOfTheServerRoot(fullPath)) {
                sendError(ostream, 403, "Forbidden.");
                return;
            }
            if (cannotAccessFile(fullPath.toFile())) {
                sendError(ostream, 404, "File not found.");
                return;
            }

            processByExtension(fullPath);
            ostream.flush();
        }

        /**
         * Dispatches request to given url path.
         *
         * @param urlPath url path
         * @throws Exception if error happens while processing request
         */
        public void dispatchRequest(String urlPath) throws Exception {
            internalDispatchRequest(urlPath, false);
        }

        //***********************
        //    HELPING METHODS
        //***********************

        /**
         * Method checks current session which means it checks if
         * current session has some cookies cached. If there are
         * some cookies web browser send to us, we will use them.
         * If the browser didn't send any cookies, then we will
         * create new map for storing cookies.
         *
         * @param request list of request lines
         */
        private synchronized void checkSession(List<String> request) {
            String sidCandidate = null;

            // -- TRY TO FIND SAVED SESSION --
            // find session id in the cookies
            for (String line : request) {
                if (line.startsWith("Cookie:")) {
                    String cookieLine = line.substring(7).trim();
                    String[] cookies = cookieLine.split(";");
                    for (String cookie : cookies) {
                        if (cookie.trim().startsWith("sid")) {
                            sidCandidate = cookie.split("=")[1].trim();
                            sidCandidate = sidCandidate.replace("\"", "");
                        }
                    }
                }
            }
            // check if session with that sid is not too old
            // and specified for this host
            if (sidCandidate != null) {
                var entry = sessions.get(sidCandidate);
                if (entry != null) {
                    if (entry.host.equals(host)) {
                        if (entry.validUntil < System.currentTimeMillis()) {
                            sessions.remove(sidCandidate);
                        } else {
                            entry.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
                            permParams = entry.map;
                            return;
                        }
                    }
                }
            }

            // -- SAVE THIS SESSION --
            // if no good cookies then get some session id
            // and create session id as well as cookies map
            sid = generateSID(20);
            SessionMapEntry entry = new SessionMapEntry(sid, host,
                    System.currentTimeMillis() + sessionTimeout * 1000,
                    new ConcurrentHashMap<>());

            sessions.put(entry.sid, entry);
            permParams = entry.map;
            outputCookies.add(new RequestContext.RCCookie("sid", sid, null, host, "/", true));
        }


        /**
         * Method initialize context if it is null.
         */
        private void initContextIfNull() {
            if (context == null) {
                context = new RequestContext(ostream, params, permParams,
                        outputCookies, tempParams, this, sid);
            }
        }

        /**
         * Method returns web worker which should process request.
         *
         * @param path full path
         * @return web worker which can process request
         * @throws Exception if error happens while loading web worker
         */
        private IWebWorker getWebWorker(String path) throws Exception {
            int splitIndex = path.lastIndexOf("/");
            String worker = path.substring(splitIndex);
            IWebWorker webWorker = workersMap.get(worker);

            if (webWorker != null) {
                return webWorker;
            }

            // second possibility, name of workers class in the path
            if (path.matches("/ext/\\w+")) {
                worker = path.substring(5);
                String fqcn = "hr.fer.zemris.java.webserver.workers." + worker;
                Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
                Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
                webWorker = (IWebWorker) newObject;
                return webWorker;
            }

            return null;
        }

        /**
         * Process request by the extension of the requested file.
         *
         * @param path requested path
         * @throws IOException if i/o error happens
         */
        private void processByExtension(Path path) throws IOException {
            String extension = getExtension(path);

            if (extension.equals("smscr")) {
                String documentBody = Files.readString(path);
                new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), context).execute();
            } else {
                String mime = mimeTypes.get(extension);

                if (mime == null) {
                    mime = "application/octet-stream";
                }

                context.setMimeType(mime);
                context.write(Files.readAllBytes(path));
            }
        }

        /**
         * Method returns extension of the file.
         *
         * @param file file from which to get extension
         * @return extension of the given file
         */
        private String getExtension(Path file) {
            int splitIndex = file.toString().lastIndexOf(".");
            return file.toString().substring(splitIndex + 1);
        }

        /**
         * Private method that generates session id of given length.
         * Sid contains only upper-case letters.
         *
         * @param length length of the sid
         * @return generated sid
         * @throws IllegalArgumentException if given length is less than 1
         */
        private String generateSID(int length) {
            if (length < 1) {
                throw new IllegalArgumentException("Length must be at least one.");
            }
            String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder sidBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sidBuilder.append(CHARS.charAt((int) (sessionRandom.nextFloat() * CHARS.length())));
            }
            return sidBuilder.toString();
        }

        /**
         * Private method that parses request parameters.
         *
         * @param paramString string of parameters
         */
        private void parseParameters(String paramString) {
            String[] parts = paramString.split("[=,&]");

            if (paramString.matches("=\\w+=") || paramString.matches("&\\w+&") || parts.length % 2 != 0) {
                throw new RuntimeException("Input parameters line is invalid");
            }

            for (int i = 0; i < parts.length; i += 2) {
                params.put(parts[i], parts[i + 1]);
            }
        }

        /**
         * Method sets host from the request header.
         *
         * @param request list of all request lines
         */
        private void setHost(List<String> request) {
            host = domainName;
            request.forEach(line -> {
                if (line.startsWith("Host:")) {
                    line = line.trim();
                    if (line.length() > 5) {
                        host = line.substring(5);
                        if (host.matches(".+:\\d+")) {
                            host = host.split(":")[0].trim();
                        }
                    }
                }
            });
        }

        /**
         * Helping method for sending error message to the output stream.
         *
         * @param ostream    output stream
         * @param statusCode error status code
         * @param statusText error status text
         * @throws IOException if i/o error happens
         */
        private void sendError(OutputStream ostream, int statusCode, String statusText) throws IOException {
            ostream.write(
                    ("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                            "Server: Smart Http Server\r\n" +
                            "Content-Type: text/plain;charset=UTF-8\r\n" +
                            "Content-Length: 0\r\n" +
                            "Connection: close\r\n" + "\r\n").getBytes(StandardCharsets.US_ASCII));
        }

        //***************************************
        //    METHODS FOR VALIDATING REQUESTS
        //***************************************

        /**
         * Method checks if given path is out of the server root path.
         *
         * @param path path to check
         * @return true if path out of the server root path
         */
        private boolean outOfTheServerRoot(Path path) {
            return !path.toString().startsWith(documentRoot.toString());
        }

        /**
         * Method checks if given request is not allowed.
         *
         * @param requestedPath requested file
         * @param directCall    direct call flag
         * @return true if request is not allowed, false otherwise
         */
        private boolean notAllowedRequest(String requestedPath, boolean directCall) {
            return directCall && (requestedPath.startsWith("/private/") || requestedPath.equals("/private"));
        }

        /**
         * Method checks if requested file cannot be accessed.
         *
         * @param requestedFile requested file
         * @return true if cannot access, false otherwise
         */
        private boolean cannotAccessFile(File requestedFile) {
            return !requestedFile.exists() || !requestedFile.isFile() || !requestedFile.canRead();
        }

        //******************************
        //    REQUEST READING METHODS
        //******************************

        /**
         * Helping method for reading a request.
         *
         * @return list of request lines
         * @throws IOException if i/o error happens
         */
        private List<String> readRequest() throws IOException {
            byte[] request = getBytesHeader();

            if (request == null) {
                sendError(ostream, 400, "Bad request");
                return null;
            }
            String requestHeader = new String(request, StandardCharsets.US_ASCII);
            List<String> headers = new ArrayList<>();
            String currentLine = null;

            //construct lines
            for (String s : requestHeader.split("\n")) {
                if (s.isEmpty()) break;
                char c = s.charAt(0);
                if (c == 9 || c == 32) {
                    currentLine += s;
                } else {
                    if (currentLine != null) {
                        headers.add(currentLine);
                    }
                    currentLine = s;
                }
            }
            if (!currentLine.isEmpty()) {
                headers.add(currentLine);
            }
            return headers;
        }


        /**
         * Helping method that reads bytes from input streams
         * and creates byte array of the header.
         *
         * @return byte array of the header
         * @throws IOException if i/o error happens
         */
        private byte[] getBytesHeader() throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int state = 0;
            l:
            while (true) {
                int b = istream.read();
                if (b == -1) return null;
                if (b != 13) {
                    bos.write(b);
                }
                switch (state) {
                case 0:
                    if (b == 13) {
                        state = 1;
                    } else if (b == 10) state = 4;
                    break;
                case 1:
                    if (b == 10) {
                        state = 2;
                    } else state = 0;
                    break;
                case 2:
                    if (b == 13) {
                        state = 3;
                    } else state = 0;
                    break;
                case 3:
                case 4:
                    if (b == 10) {
                        break l;
                    } else state = 0;
                    break;
                }
            }
            return bos.toByteArray();
        }
    }

    //****************************
    //    HELPING SESSION CLASS
    //****************************

    /**
     * Helping static class that represents one session map entry.
     */
    private static class SessionMapEntry {

        /**
         * Session id.
         */
        String sid;

        /**
         * Session host.
         */
        String host;

        /**
         * Time in ms until session is valid.
         */
        long validUntil;

        /**
         * Map of session data.
         */
        Map<String, String> map;

        /**
         * Constructs session map entry with specified parameters.
         *
         * @param sid        session id
         * @param host       entry host
         * @param validUntil time until session is valid
         * @param map        map for storing session data
         */
        public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
            this.sid = sid;
            this.host = host;
            this.validUntil = validUntil;
            this.map = map;
        }
    }

    //**********************
    //    MAIN METHOD
    //**********************

    /**
     * Main method of the server.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Expected 1 argument, config file.");
            System.exit(1);
        }
        SmartHttpServer server = new SmartHttpServer(args[0]);
        server.start();
    }
}

