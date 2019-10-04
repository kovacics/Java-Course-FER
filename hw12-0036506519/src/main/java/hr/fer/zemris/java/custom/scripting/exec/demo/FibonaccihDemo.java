package hr.fer.zemris.java.custom.scripting.exec.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo program #5.
 *
 * @author Stjepan Kovačić
 */
public class FibonaccihDemo {

    public static void main(String[] args) throws IOException {
        String documentBody = Files.readString(Paths.get("fibonaccih.smscr"));
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

        try (var os = Files.newOutputStream(Paths.get("fibonaccih.html"))) {

            // create engine and execute it
            new SmartScriptEngine(
                    new SmartScriptParser(documentBody).getDocumentNode(),
                    new RequestContext(os, parameters, persistentParameters, cookies)
            ).execute();
        }
    }
}
