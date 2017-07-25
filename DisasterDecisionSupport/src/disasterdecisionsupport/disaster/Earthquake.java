/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport.disaster;

/**
 *
 * @author Classified
 */
public class Earthquake {
    String country, city, place, id,date,time;
    double  magnitude,lat, lng, depth;
    String name;
    
    public Earthquake(String id, String place, double magnitude, double lat, double lng, double depth){
        this.id = id;
        this.country = country;
        this.place = place;
        this.magnitude = magnitude;
        this.lat=lat;
        this.lng=lng;
        this.depth=depth;
            }
    
    public Earthquake(String id, String country, String city, double magnitude, double lat, double lng, double depth, String date, String time){
        this.id = id;
        this.country = country;
        this.city = city;
        this.magnitude = magnitude;
        this.lat=lat;
        this.lng=lng;
        this.depth=depth;
        this.date=date;
        this.time=time;
        
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPlace() {
        return place;
    }

    public String getId() {
        return id;
    }
      public double getLat(){
          return lat;
      }
      public double getlng()
      {
          return lng;
      }
      public double getDepth()
      {
          return depth;
      }
          public double getmagnitude() {
        return magnitude;
    }
          public String getDate()
          {
              return date;
          }
     public String getTime()
     {
         return time;
     }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
