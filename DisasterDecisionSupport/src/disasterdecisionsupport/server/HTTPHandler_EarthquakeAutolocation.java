/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




package disasterdecisionsupport.server;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import disasterdecisionsupport.DisasterOntology;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.json.JSONObject;

/**
 *
 * @author Classified
 */
public class HTTPHandler_EarthquakeAutolocation implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "Autolocation system handler";
        InputStream is = t.getRequestBody();

        //further process of disaster data
        process(is);

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void process(InputStream is){
        try
            {
                DisasterOntology ont = new DisasterOntology();
                Model baseModel = ont.getBaseModel();
                OntModel ontModel = ont.getOntModel();

                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer,"UTF-8");
                String postRequestContent = writer.toString();
                System.out.println(postRequestContent);

                //parsing data from earthquake-autolocation
                /*
                {
                    time: ,
                    intensity: ,
                    lat: ,
                    lng: ,
                    country: ,
                    city: ,
                }
                */
                JSONObject req = new JSONObject(is);
                long time = asTimestamp(req.getString("time"));
                int intensity = Integer.parseInt(req.getString("intensity"));
                double lat = req.getDouble("lat");
                double lng = req.getDouble("lng");
                String country = req.getString("country");
                String city = req.getString("city");




                //adding the disaster data to the ontology for further process
                //further process: give decision support -> decision made -> save as history 
                //and the previous decision suggested/made before become considertaion for the next ones
                //ont.addEarthquake();                    


            } catch(Exception ex) { System.out.println(ex.toString()); }
    }

    public long asTimestamp(String time) {
        try {
            XSDDateTime dateTime = (XSDDateTime)XSDDatatype.XSDdateTime.parse(time);
            return dateTime.asCalendar().getTimeInMillis();
        } catch (Exception e) {}
        try {
            XSDDateTime dateTime = (XSDDateTime)XSDDatatype.XSDdate.parse(time);
            return dateTime.asCalendar().getTimeInMillis();
        } catch (Exception e) {}
        try {
            return Long.parseLong(time);

        } catch (Exception e) {}
        return -1;
    }
}
    
