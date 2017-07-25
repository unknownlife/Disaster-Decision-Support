/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport;

import disasterdecisionsupport.disaster.Earthquake_Handler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Classified
 */
public class HelperFunctions {
    public static String getCity(double lat, double lng){
        String url = Constants.GOOGLE_REVERSE_GEOCODING_URL+"latlng="+lat+","+lng;
        try {
            InputStream response = new URL(url).openStream();
            JSONObject res = HelperFunctions.parseJSON(response);
            
            //processing data aqcuired from API
            System.out.println(res.toString());
            JSONArray results = res.optJSONArray("results");
            JSONObject address = results.optJSONObject(0);
            JSONArray addressComponent = address.optJSONArray("address_components");
            
            for (Object component : addressComponent){
                JSONArray componentTypes = ((JSONObject) component).optJSONArray("types");
                for (Object type : componentTypes){
                    if ((type.toString()).equals("locality"))
                        return ((JSONObject) component).optString("long_name");
                }
            }        
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Earthquake_Handler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Earthquake_Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public String getDate(String s){
        Date date = new Date(s);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        return format.format(date);
    }
    
    public String getTime(String s){
        Date date = new Date(s);
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        return format.format(date);
    }
    
    public static JSONObject parseJSON(InputStream is){
        BufferedReader streamReader;
        try {
            streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            try {
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
            } catch (IOException ex) {
                Logger.getLogger(HelperFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }

            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
            return jsonObject;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HelperFunctions.class.getName()).log(Level.SEVERE, null, ex);
            return new JSONObject();
        }
    }
    
    public String cutBack(String txt, String teil, int number) {
        for (int i = 0; i < number; i++) {
            txt = txt.substring(0, txt.lastIndexOf(teil));
        }
        return txt;
    } 
    
    public String cutFront(String txt, String teil, int number) {
        for (int i = 0; i < number; i++) {
            txt = txt.substring(txt.indexOf(teil) + 1, txt.length());
        }
        return txt;
    } 
    
}
