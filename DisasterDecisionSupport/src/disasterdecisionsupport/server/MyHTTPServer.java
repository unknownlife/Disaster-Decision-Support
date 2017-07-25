/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import disasterdecisionsupport.DisasterDecisionSupport;
import disasterdecisionsupport.DisasterOntology;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;

/**
 *
 * @author Classified
 */
public class MyHTTPServer {
    DisasterOntology ont;
    
    public MyHTTPServer(DisasterOntology ont){
        this.ont=ont;
        
        //starting the server
        int serverPort = 8000;
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(serverPort), 0);
            server.createContext("/test", new MyTestingHandler());
            server.createContext("/earthquake/autolocation/in", new HTTPHandler_EarthquakeAutolocation());
            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("Server started: port "+serverPort);
        } catch (IOException ex) {
            Logger.getLogger(MyHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    class MyTestingHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

