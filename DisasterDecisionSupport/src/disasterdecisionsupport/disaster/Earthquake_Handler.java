/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
===================================================
API JSON RESPONSE FORMAT
===================================================
{
  type: "FeatureCollection",
  metadata: {
    generated: Long Integer,
    url: String,
    title: String,
    api: String,
    count: Integer,
    status: Integer
  },
  bbox: [
    minimum longitude,
    minimum latitude,
    minimum depth,
    maximum longitude,
    maximum latitude,
    maximum depth
  ],
  features: [
    {
      type: "Feature",
      properties: {
        mag: Decimal,
        place: String,
        time: Long Integer,
        updated: Long Integer,
        tz: Integer,
        url: String,
        detail: String,
        felt:Integer,
        cdi: Decimal,
        mmi: Decimal,
        alert: String,
        status: String,
        tsunami: Integer,
        sig:Integer,
        net: String,
        code: String,
        ids: String,
        sources: String,
        types: String,
        nst: Integer,
        dmin: Decimal,
        rms: Decimal,
        gap: Decimal,
        magType: String,
        type: String
      },
      geometry: {
        type: "Point",
        coordinates: [
          longitude,
          latitude,
          depth
        ]
      },
      id: String
    },
    …
  ]
}
*/


package disasterdecisionsupport.disaster;

import disasterdecisionsupport.Constants;
import disasterdecisionsupport.DisasterOntology;
import disasterdecisionsupport.HelperFunctions;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.atlas.json.io.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Classified
 */
public class Earthquake_Handler {
    DisasterOntology ont;
    
    public Earthquake_Handler(DisasterOntology ont){
        this.ont = ont;
    }
    
    public Earthquake_Handler(){
        this.ont = new DisasterOntology();
    }
    
    public void accessAPI(String id){
        //accessing API
        String url = Constants.EARTHQUAKE_API_URL + id + ".geojson";
        try {
            InputStream response = new URL(url).openStream();
            JSONObject res = HelperFunctions.parseJSON(response);
            
            //processing data aqcuired from API
            System.out.print(res.toString());
            processData(parseJSON(res));
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Earthquake_Handler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Earthquake_Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void processData(Earthquake e){
        
        ont.addEarthquake(e);
        
        //apply decision support on the data
        Earthquake_DecisionSupportEngine engine = new Earthquake_DecisionSupportEngine();
        engine.run(e);
        
    }
    
    private Earthquake parseJSON(JSONObject o){
        //extract necessary information from the JSON data
        String country, place, id;
        double  magnitude, lat, lng, depth;
        long time_long, updated_long;
        id = o.optString("id");
        JSONObject prop = o.getJSONObject("properties");
        place = prop.optString("place");
        country = place.substring(place.lastIndexOf(","), (place.length()-1)); 
       magnitude = prop.optDouble("mag");
       time_long=o.optLong("time");
       updated_long=o.optLong("updated");
       
        
        JSONObject geometry = o.getJSONObject("geometry");
        JSONArray coor = geometry.getJSONArray("coordinates");
        lng=coor.optDouble(0);
        lat=coor.optDouble(0);
        depth=coor.optDouble(0);
               
        //storing the earthquake data to the ontology
       return new Earthquake(id, place, magnitude, lng, lat, depth);
    }
}
