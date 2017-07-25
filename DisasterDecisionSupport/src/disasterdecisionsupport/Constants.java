/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport;

/**
 *
 * @author Classified
 */
public class Constants {
    //for Ontology
    //public static final String ONTOLOGY_PATH = System.getProperty("user.dir")+ "\\mainFiles\\MainOnt.owl";
    public static final String ONTOLOGY_PATH = System.getProperty("user.dir")+ "\\mainFiles\\finalont.owl";
    public static final String RULE_PATH = System.getProperty("user.dir")+ "\\mainFiles\\tryRules.txt";            //SWRL rules
    
    //for email server
    public static final String SMTP_PROPERTIES_PATH = System.getProperty("user.dir")+"\\mainFiles\\smtp.properties";
    public static final String EMAIL_USERNAME = "username";
    public static final String EMAIL_PASSWORD = "password";
    
    //Disaster Notification Email addresses
    public static final String EARTHQUAKE_NOTIFICATION_EMAIL = "ens@ens.usgs.gov";
    
    public static final String EARTHQUAKE_API_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/";
    public static final String GOOGLE_REVERSE_GEOCODING_URL = "http://maps.googleapis.com/maps/api/geocode/json?sensor=true&";
}
