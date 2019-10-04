package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Class represents request of some operation. It contains all needed
 * parameters for request to be processed.
 *
 * @author Stjepan Kovačić
 */
@SuppressWarnings("unused")
public class RequestContext {

    /** Output stream. */
    private OutputStream outputStream;

    /** Charset used in construction of string from bytes. */
    private Charset charset;

    /** Encoding used in construction of string from bytes. */
    private String encoding = "UTF-8";

    /** Status code of the operation. */
    private int statusCode = 200;

    /** Status text of the operation. */
    private String statusText = "OK";

    /** Mime type used in html header. */
    private String mimeType = "text/html";

    /** Length of the content that comes after generated header. */
    private Long contentLength = null;

    /** Output cookies. */
    private List<RCCookie> outputCookies;

    /** Context parameters. */
    private Map<String, String> parameters;

    /** Temporary context parameters. */
    private Map<String, String> temporaryParameters;

    /** Persistent context parameters. */
    private Map<String, String> persistentParameters;

    /** Flag that tells if header is already generated. */
    private boolean headerGenerated = false;

    /** Dispatcher used to process request. */
    private IDispatcher dispatcher;

    /** Session id. */
    private String sid;

    //************************
    //      CONSTRUCTORS
    //************************

    /**
     * Constructs request context with specified parameters.
     *
     * @param outputStream         output stream
     * @param parameters           parameters map
     * @param persistentParameters persistent parameters map
     * @param outputCookies        output cookies list
     * @param temporaryParameters  temporary parameters
     * @param dispatcher           dispatcher
     * @param sid                  session id
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
                          Map<String, String> persistentParameters, List<RCCookie> outputCookies,
                          Map<String, String> temporaryParameters, IDispatcher dispatcher, String sid) {
        Objects.requireNonNull(outputStream);

        this.outputStream = outputStream;
        this.parameters = parameters != null ? parameters : new HashMap<>();
        this.persistentParameters = persistentParameters != null ? persistentParameters : new HashMap<>();
        this.outputCookies = outputCookies != null ? outputCookies : new ArrayList<>();
        this.sid = sid;

        this.temporaryParameters = Objects.requireNonNullElseGet(temporaryParameters, HashMap::new);
        this.dispatcher = dispatcher;
    }

    /**
     * Constructs request context with specified parameters.
     *
     * @param outputStream         output stream
     * @param parameters           parameters map
     * @param persistentParameters persistent parameters map
     * @param outputCookies        output cookies list
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
                          Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
        this(outputStream, parameters, persistentParameters, outputCookies, null, null, null);
    }

    //************************
    //     WRITE METHODS
    //************************

    /**
     * Writes bytes on the output stream.
     *
     * @param data bytes to write
     * @return this context
     * @throws IOException if io error happens
     */
    public RequestContext write(byte[] data) throws IOException {
        if (!headerGenerated) {
            outputStream.write(generateHeader());
            headerGenerated = true;
        }
        outputStream.write(data);
        return this;
    }

    /**
     * Writes to output stream bytes of given length from given position in the given array.
     *
     * @param data   bytes array
     * @param offset offset of the bytes
     * @param len    number of bytes to write
     * @return this context
     * @throws IOException if io error happens
     */
    public RequestContext write(byte[] data, int offset, int len) throws IOException {
        if (!headerGenerated) {
            outputStream.write(generateHeader());
            headerGenerated = true;
        }
        outputStream.write(data, offset, len);
        return this;
    }

    /**
     * Writes given text to the output stream.
     *
     * @param text text to write
     * @return this context
     * @throws IOException if io error happens
     */
    public RequestContext write(String text) throws IOException {
        if (!headerGenerated) {
            outputStream.write(generateHeader());
            headerGenerated = true;
        }
        outputStream.write(text.getBytes(charset));
        return this;
    }

    //************************
    //     PRIVATE METHODS
    //************************

    /**
     * Helping method which generates header of the output file.
     *
     * @return bytes representing header
     */
    private byte[] generateHeader() {
        StringBuilder header = new StringBuilder();
        header.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n")
                .append("Content-Type: ").append(mimeType)
                .append(mimeType.startsWith("text/") ? "; charset=" + encoding + "\r\n" : "\r\n")
                .append(contentLength != null ? "Content-Length:" + contentLength + "\r\n" : "");

        if (outputCookies != null) {
            outputCookies.forEach(cookie -> {
                header.append("Set-Cookie: ").append(cookie.name).append("=")
                        .append("\"").append(cookie.value).append("\";");
                header.append(cookie.domain != null ? " Domain=" + cookie.domain + ";" : "");
                header.append(cookie.path != null ? " Path=" + cookie.path + ";" : "");
                header.append(cookie.maxAge != null ? " Max-Age=" + cookie.maxAge + ";" : "");
                header.append(" HttpOnly\r\n");
            });
        }
        header.append("\r\n");

        charset = Charset.forName(encoding);
        return header.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    //***************************
    //    GETTERS AND SETTERS
    //***************************

    /**
     * Adds one cookie to the cookie collection.
     *
     * @param cookie cookie to add
     */
    public void addRCCookie(RCCookie cookie) {
        outputCookies.add(cookie);
    }

    /**
     * Method that retrieves value from parameters map (or null if no association exists):
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Method that retrieves names of all parameters in parameters map (note, this set must be read-only):
     */
    public Set<String> getParameterNames() {
        return Collections.unmodifiableSet(parameters.keySet());
    }

    /**
     * Method that retrieves value from persistentParameters map (or null if no association exists):
     */
    public String getPersistentParameter(String name) {
        return persistentParameters.get(name);
    }

    /**
     * Method that retrieves names of all parameters in persistent parameters map (note, this set must be readonly):
     */
    public Set<String> getPersistentParameterNames() {
        return Collections.unmodifiableSet(persistentParameters.keySet());
    }

    /**
     * Method that stores a value to persistentParameters map:
     */
    public void setPersistentParameter(String name, String value) {
        persistentParameters.put(name, value);
    }

    /**
     * Method that removes a value from persistentParameters map:
     */
    public void removePersistentParameter(String name) {
        persistentParameters.remove(name);
    }

    /**
     * Method that retrieves value from temporaryParameters map (or null if no association exists):
     */
    public String getTemporaryParameter(String name) {
        return temporaryParameters.get(name);
    }

    /**
     * Method that retrieves names of all parameters in temporary parameters map (note, this set must be readonly):
     */
    public Set<String> getTemporaryParameterNames() {
        return Collections.unmodifiableSet(temporaryParameters.keySet());
    }

    /**
     * Method that stores a value to temporaryParameters map:
     */
    public void setTemporaryParameter(String name, String value) {
        temporaryParameters.put(name, value);
    }

    /**
     * Method that removes a value from temporaryParameters map:
     */
    public void removeTemporaryParameter(String name) {
        temporaryParameters.remove(name);
    }

    /**
     * Method that retrieves an identifier which is unique for current user session.
     */
    public String getSessionID() {
        return sid;
    }


    /**
     * Getter for the parameters map.
     *
     * @return parameters map
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Setter for the parameters map.
     *
     * @param parameters parameters to set
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     * Getter for the temporary parameters map.
     *
     * @return temporary parameters map
     */
    public Map<String, String> getTemporaryParameters() {
        return temporaryParameters;
    }

    /**
     * Setter for the temporary parameters map.
     *
     * @param temporaryParameters temporary parameters to set
     */
    public void setTemporaryParameters(Map<String, String> temporaryParameters) {
        this.temporaryParameters = temporaryParameters;
    }

    /**
     * Getter for the persistent parameters map.
     *
     * @return persistent parameters map
     */
    public Map<String, String> getPersistentParameters() {
        return persistentParameters;
    }

    /**
     * Setter for the persistent parameters map.
     *
     * @param persistentParameters persistent parameters to set
     */
    public void setPersistentParameters(Map<String, String> persistentParameters) {
        this.persistentParameters = persistentParameters;
    }


    /**
     * Setter for the encoding.
     *
     * @param encoding encoding to set
     */
    public void setEncoding(String encoding) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated.");
        }
        this.encoding = encoding;
    }

    /**
     * Sets the status code.
     *
     * @param statusCode status code to set
     */
    public void setStatusCode(int statusCode) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated.");
        }
        this.statusCode = statusCode;
    }

    /**
     * Sets status text.
     *
     * @param statusText status text to set
     */
    public void setStatusText(String statusText) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated.");
        }
        this.statusText = statusText;
    }

    /**
     * Sets mime type.
     *
     * @param mimeType mime type to set
     */
    public void setMimeType(String mimeType) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated.");
        }
        this.mimeType = mimeType;
    }

    /**
     * Sets content length.
     *
     * @param contentLength content length to set
     */
    public void setContentLength(Long contentLength) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated.");
        }
        this.contentLength = contentLength;
    }

    /**
     * Sets output cookies.
     *
     * @param outputCookies output cookies to set
     */
    public void setOutputCookies(List<RCCookie> outputCookies) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated.");
        }
        this.outputCookies = outputCookies;
    }

    /**
     * Getter of the dispatcher.
     *
     * @return dispatcher
     */
    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    //****************************
    //    HELPING STATIC CLASS
    //****************************


    /**
     * Static class which represents one cookie.
     */
    public static class RCCookie {

        /**
         * Cookie name.
         */
        private String name;

        /**
         * Cookie value.
         */
        private String value;

        /**
         * Cookie domain.
         */
        private String domain;

        /**
         * Cookie path.
         */
        private String path;

        /**
         * Cookie maxAge
         */
        private Integer maxAge;

        /**
         * Http-only flag.
         */
        private boolean httpOnly;

        /**
         * Constructs cookie with specified parameters.
         *
         * @param name     cookie name
         * @param value    cookie value
         * @param maxAge   cookie maxAge
         * @param domain   cookie domain
         * @param path     cookie path
         * @param httpOnly if cookie is http-only
         */
        public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
            this.name = name;
            this.value = value;
            this.domain = domain;
            this.path = path;
            this.maxAge = maxAge;
            this.httpOnly = httpOnly;
        }

        /**
         * Constructs cookie with specified parameters.
         *
         * @param name   cookie name
         * @param value  cookie value
         * @param maxAge cookie maxAge
         * @param domain cookie domain
         * @param path   cookie path
         */
        public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
            this(name, value, maxAge, domain, path, false);
        }

        /**
         * Getter for the cookie name.
         *
         * @return cookie name
         */
        public String getName() {
            return name;
        }

        /**
         * Getter for the cookie value.
         *
         * @return cookie value
         */
        public String getValue() {
            return value;
        }

        /**
         * Getter for the cookie domain.
         *
         * @return cookie domain
         */
        public String getDomain() {
            return domain;
        }

        /**
         * Getter for the cookie path.
         *
         * @return cookie path
         */
        public String getPath() {
            return path;
        }

        /**
         * Getter for the cookie maxAge.
         *
         * @return cookie maxAge
         */
        public Integer getMaxAge() {
            return maxAge;
        }

        /**
         * Getter for the http only flag.
         *
         * @return http-only flag
         */
        public boolean isHttpOnly() {
            return httpOnly;
        }

        /**
         * Sets http-only flag.
         *
         * @param httpOnly flag value to set
         */
        public void setHttpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
        }
    }
}
