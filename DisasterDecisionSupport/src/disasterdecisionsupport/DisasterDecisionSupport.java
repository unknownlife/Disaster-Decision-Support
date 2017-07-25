/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport;

//import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;

import disasterdecisionsupport.disaster.Earthquake;
import disasterdecisionsupport.disaster.Earthquake_Handler;
import disasterdecisionsupport.server.MyIMAPServer;
import java.util.Iterator;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.InetSocketAddress;

//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//import com.sun.net.httpserver.HttpServer;
//import disasterdecisionsupport.disaster.Earthquake_Handler;
//import disasterdecisionsupport.server.MyHTTPServer;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.InputStream;
//import java.io.StringWriter;
//import java.util.Date;
//import java.util.Iterator;
//import sun.misc.IOUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.jena.datatypes.xsd.XSDDateTime;
//import org.apache.jena.ontology.Individual;
//import org.apache.jena.ontology.OntClass;
//import org.apache.jena.ontology.OntModel;
//import org.apache.jena.rdf.model.Model;
//
//import org.json.*;

/**
 *
 * @author Classified
 */
public class DisasterDecisionSupport extends Thread {

    /**
     * @param args the command line arguments
     */
    private ServerSocket ss;
	
	public DisasterDecisionSupport(int port) {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				Socket clientSock = ss.accept();
				saveFile(clientSock);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException ex) {
                        Logger.getLogger(DisasterDecisionSupport.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}

	private void saveFile(Socket clientSock) throws IOException, FileNotFoundException, ParseException {
		DataInputStream dis = new DataInputStream(clientSock.getInputStream());
		FileOutputStream fos = new FileOutputStream("C:\\Users\\Zonoid\\Desktop\\eq3.json");
		byte[] buffer = new byte[4096];
		
		int filesize = dis.readInt();
              //  System.out.println(filesize);// Send file size in separate msg
		int read = 0;
		int totalRead = 0;
		int remaining = filesize;
		while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			totalRead += read;
			remaining -= read;
			
			fos.write(buffer, 0, read);
		}
		
		fos.close();
		dis.close();
                json j=new json();
                j.convert();
	}
    public static void main(String[] args) throws Exception {
        //MyIMAPServer email = new MyIMAPServer();
        //System.out.println(HelperFunctions.getCity(18.9220, 72.8347));
        //email.startEmailListener();
        //email.listEmails();
        DisasterDecisionSupport obj=new DisasterDecisionSupport(1988);
        obj.start();
  /*      DisasterOntology ont = new DisasterOntology();
        Earthquake eq = new Earthquake(null, "India", "Koynanagar",9, 19.0760, 72.8777, 10, "07/04/2017", "13:30:30");
        //ont.addEarthquake(eq);
        //System.out.println(ont.getOntURI());
        Earthquake_Handler eh = new Earthquake_Handler(ont);
        eh.processData(eq);
        
*/

        
        
        
//        Earthquake_Handler eh =  new Earthquake_Handler(ont);
//        eh.accessAPI("us1000876f");
//        MyHTTPServer server = new MyHTTPServer(ont);
 
//        Model baseModel = ont.getBaseModel();
//        OntModel ontModel = ont.getOntModel();
//        OntClass country = ontModel.getOntClass(ont.getOntURI()+"#"+"Country");
//
//        Iterator iter = country.listInstances();
//        while(iter.hasNext()){
//            System.out.println(iter.next());
//        }
        
    }
}
