package disasterdecisionsupport;

import disasterdecisionsupport.similiar_postgres;
import disasterdecisionsupport.DisasterOntology;
import disasterdecisionsupport.disaster.Earthquake;
import disasterdecisionsupport.disaster.Earthquake_Handler;
import disasterdecisionsupport.sms;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import org.apache.jena.rdf.model.Model;
import org.json.simple.parser.ParseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static org.apache.jena.enhanced.BuiltinPersonalities.model;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

public class json {
    String Place,state,Date;
    double Mag,actions[];
	public void convert() throws FileNotFoundException, IOException, ParseException {
	        JsonReader reader = Json.createReader(new FileReader("C:/Users/Zonoid/Desktop/eq3.json"));
	         
	        JsonObject obj = reader.readObject();
	         
	        reader.close();
	  
	    /*    JsonObject f = obj.getJsonObject("features");
	        System.out.println("Features: ");
	        
	        JsonObject q= f.getJsonObject("properties");
	        System.out.println(q.getString("place"));
	        System.out.println(q.getString("time"));
	        System.out.println(q.getString("tz"));
	        System.out.println(q.getString("mag"));
	        System.out.println("Phone  : ");*/
	        
	         JsonArray f = obj.getJsonArray("features");
	        for (JsonValue jsonValue : f) {
	        	String s=jsonValue.toString();
	        	 JsonReader r = Json.createReader(new StringReader(s));
	             
	             JsonObject personObject = r.readObject();
	              
	             r.close();
	             
	             JsonObject addressObject = personObject.getJsonObject("properties");
                     String date[]=addressObject.getString("date").split("-");
                     Date=addressObject.getString("date");
	            Place= addressObject.getString("place");
                    String str[]=Place.split(", ");
                    state=str[1];
                    Place=str[0];
                    Mag=Double.parseDouble(addressObject.get("mag").toString());
	            actions=new similiar_postgres().similiar(Place,state,Mag,Integer.parseInt(date[2]));
                    String sm=""+actions[0]+"\n"+actions[1]+"\n"+actions[2]+"\n"+actions[3]+"\n"+actions[4];
        //            inter object=new inter(sm);
                    
                    if(actions[5]>70){
                        //Get level info
                        DisasterOntology ont=new DisasterOntology();
                        disasterdecisionsupport.DDSQuery2 q = new disasterdecisionsupport.DDSQuery2(ont);
                        int p=(int)actions[2];
                        String m=""+p;
                        List<String> needs_Recources = q.getNeedRecources1(ont.getOntModel(), m);
            
            String output="\n\nHumanitarian Assiastance:\n ";
            String title="";
            for (String action: needs_Recources){
       //         System.out.println(action);
                String arr[]=action.split(">");
                if(!title.equals(arr[0])){
                output+=arr[0]+"-\n";
                title=arr[0];
                }
                if(arr.length>=2){
                String ar1[]=arr[1].split("#");
                if(!title.substring(0,title.length()-1).equals(ar1[1]))
               output+=ar1[1]+"\t";
                String ar2[]=ar1[0].split("\"");
                if(ar2.length>2)
                output+=ar2[1]+"\n";
               }}
      //      System.out.println(output);
                  p=(int)actions[4];
                        m=""+p;
                        List<String> needs_Recources3 = q.getNeedRecources3(ont.getOntModel(), m);
            output+="\n\nSearch and Rescue:\n ";
            title="";
            for (String action: needs_Recources3){
       //         System.out.println(action);
                String arr[]=action.split(">");
                if(!title.equals(arr[0])){
                output+=arr[0]+"-\n";
                title=arr[0];
                }
                if(arr.length>=2){
                String ar1[]=arr[1].split("#");
                if(!title.substring(0,title.length()-1).equals(ar1[1]))
               output+=ar1[1]+"\t";
                String ar2[]=ar1[0].split("\"");
                if(ar2.length>2)
                output+=ar2[1]+"\n";
               }}
      //      System.out.println(output);
                      p=(int)actions[3];
                      m=""+p;
                      List<String> needs_Recources2 = q.getNeedRecources2(ont.getOntModel(), m);
            
            output+="\n\nRehabitilisation and Recovery:\n ";
            title="";
            for (String action: needs_Recources2){
       //         System.out.println(action);
                String arr[]=action.split(">");
                if(!title.equals(arr[0])){
                output+=arr[0]+"-\n";
                title=arr[0];
                }
                if(arr.length>=2){
                String ar1[]=arr[1].split("#");
                if(!title.substring(0,title.length()-1).equals(ar1[1]))
               output+=ar1[1]+"\t";
                String ar2[]=ar1[0].split("\"");
                if(ar2.length>2)
                output+=ar2[1]+"\n";
               }}
            System.out.println(output+"\n");
            for(int i=0;i<6;i++)
                System.out.print(actions[i]+"\t");
 //        new json().store();
//       new sms(output);
 /*            p=(int)actions[3];
             m=""+p;
            List<String> needs_Recources2 = q.getNeedRecources2(ont.getOntModel(), m);
            strn=needs_Recources2.get(0);
            counter=1;
            output+="\n\nRelief equipments: ";
            List<String> there1=new ArrayList<String>();
            there1.add(0,strn);
            for (String action: needs_Recources2){
//                System.out.println(action);
                
                if(action.startsWith(strn+">"))
                    continue;
               
                else{
                    if(there1.contains(action) && action!=there1.get(0))
                        continue;
                    else{
                        there1.add(counter,action);
                        strn=action;
                if(!action.startsWith(strn+">"))
                    strn=action;
                output+= "\n"+counter+". "+action;
                List<String> actionDetails = q.getActionDetail(ont.getOntModel(), action);  
                System.out.println("Action Details: "+actionDetails);
                for(int i=0; i<actionDetails.size(); i+=2){
                    output+="\n\t"+actionDetails.get(i)+"= "+actionDetails.get(i+1);
                }
                output+="\n";
                counter++;
            }}}
            
             p=(int)actions[4];
             m=""+p;
            List<String> needs_Recources3 = q.getNeedRecources3(ont.getOntModel(), m);
            strn=needs_Recources3.get(0);
            counter=1;
            output+="\n\nSearch and Rescue: ";
            List<String> there2=new ArrayList<String>();
            there2.add(0,strn);
            for (String action: needs_Recources3){
                System.out.println(action);
                
                if(action.startsWith(strn+">"))
                    continue;
               
                else{
                    if(there2.contains(action) && action!=there2.get(0))
                        continue;
                    else{
                        there2.add(counter,action);
                        strn=action;
                if(!action.startsWith(strn+">"))
                    strn=action;
                output+= "\n"+counter+". "+action;
                List<String> actionDetails = q.getActionDetail(ont.getOntModel(), action);  
                System.out.println("Action Details: "+actionDetails);
                for(int i=0; i<actionDetails.size(); i+=2){
                    output+="\n\t"+actionDetails.get(i)+"= "+actionDetails.get(i+1);
                }
                output+="\n";
                counter++;
            }}}
//            for (String action : needs_Recources){
//                output+= "\n"+counter+". "+action;
//                List<String> actionDetails = q.getActionDetail(ont.getReasonerModel(), action);
//                System.out.println("Action Details: "+actionDetails);
//                for(int i=0; i<actionDetails.size(); i+=2){
//                    output+="\n\t"+actionDetails.get(i)+"= "+actionDetails.get(i+1);
//                }
//                output+="\n";
//                counter++;
//            }
        
        System.out.println(output);*/
                    }

                      
                    else{
                        DisasterOntology ont = new DisasterOntology();
                        if(!Place.equals(""))
                            state=Place;
        Earthquake eq = new Earthquake(null, "India", state,Mag, 19.0760, 72.8777, 10, Date, "13:30:30");
        //ont.addEarthquake(eq);
        //System.out.println(ont.getOntURI());
        Earthquake_Handler eh = new Earthquake_Handler(ont);
        eh.processData(eq);
                    }
             if(actions[5]!=100){         
 try{
     
		   Class.forName("org.postgresql.Driver");
                   
	        Connection m= DriverManager.getConnection("jdbc:postgresql://localhost/casebase","postgres","abc123");
	
                Statement st= m.createStatement();
                String q= "insert into eq"+"(\"Date\", \"Location\", \"State\", \"Magnitude\", \"Death\", \"Injured\", \"H_L\", \"RR_L\", \"SR_L\")"+ "values ('"+Date+"','"+Place+"','"+state+"',"+Mag+","+(int)actions[0]+","+(int)actions[1]+","+(int)actions[2]+","+(int)actions[3]+","+(int)actions[4]+")";
	           
	             st.executeUpdate(q);
                     System.out.println("Database printed");
}
catch(Exception e){
		   e.printStackTrace();
}
             }
                    /*
                    System.out.println("Properties: ");
	             System.out.println(Place);
	             System.out.println(Time);
	             System.out.println(addressObject.get(Tz));
	             System.out.println(addressObject.get(Mag));
	     
	             System.out.println();*/
	        }
	   
	   
	}

	
public static void main(String ars[]) throws IOException, FileNotFoundException, ParseException{
    new json().convert();
    
}
}