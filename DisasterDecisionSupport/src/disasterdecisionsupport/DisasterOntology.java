/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport;

import disasterdecisionsupport.disaster.Earthquake;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.*;

/**
 *
 * @author Classified
 */
public class DisasterOntology {
    Model baseModel;
    OntModel ontModel;
    InfModel reasonerModel;
    String ontURI;
    
    final String EARTHQUAKE = "Earthquake";
    
    public DisasterOntology(){
        baseModel = ModelFactory.createDefaultModel();
        ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, baseModel);        
        loadOnt();
        loadReasonerRules();
        loadOntologyURI();
    }
    
    private void loadOnt()
    {
        //String name = "MainOntology";
        
        InputStream in = FileManager.get().open(Constants.ONTOLOGY_PATH);
        if (in == null){ 
            System.out.println("Ontology not found");
            return;
        }
        else{
            ontModel.read(in,"");
            System.out.println("Ontology: ok");
        }
    }
    
    public void loadReasonerRules(){
        List rules = Rule.rulesFromURL(Constants.RULE_PATH);
        if (rules == null){
            System.out.println("Rules not found");
            return;
        }
        else{
            System.out.println("Rules: ok");
        }
        Reasoner reasoner = new GenericRuleReasoner(rules);
        reasonerModel = ModelFactory.createInfModel(reasoner, baseModel);
    }
    
    private void loadOntologyURI(){
        Iterator iter = ontModel.listOntologies();
        if (iter.hasNext()){
            Ontology onto = (Ontology) iter.next();
            ontURI = (onto).getURI();
        }
        else{
            ontURI = null;
        }
    }
    
    public void ontSave(){
        File file = new File(Constants.ONTOLOGY_PATH);
        try{
            StringWriter sw = new StringWriter();
            ontModel.write(sw,"RDF/XML-ABBREV");
            FileWriter fw = new FileWriter(file);
            fw.write(sw.toString());
            fw.close();
            System.out.println("<<SAVED>>");
        }
        catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    public Individual newInstance(String className, String instanceName){
        OntClass ontClass = ontModel.getOntClass(ontURI+"#"+className);
        Individual indv = ontClass.createIndividual(ontURI+"#"+instanceName);
         return indv;
    }
    
    public void addDisaster(String disasterName, String disasterType, String disasterLocation){
        /*adding disaster data to the knowledge base*/
                
        //adding the triples of the disaster happened ontModel.add(individualSubject,propertyPredicate,classObject)
        Individual disaster = newInstance(disasterType,disasterName);
        Individual location = ontModel.getIndividual(ontURI+"#"+disasterLocation);
        Property has_location = ontModel.getObjectProperty(ontURI+"#"+"has_location");
        ontModel.add(disaster, has_location, location);
        
        
        //adding other object triples based on the type of the disaster
        switch(disasterType){
            case EARTHQUAKE:
                break;
        }
        
    }
    
    public void addEarthquake(Earthquake earthquake){
        String instanceName = earthquake.getCity();
        System.out.println(instanceName);
        Individual e = newInstance("Ground_Shaking", instanceName);
        //adding intensity
        Property has_magnitude = ontModel.getProperty(ontURI+"#"+"has_magnitude");
        ontModel.addLiteral(e,has_magnitude,earthquake.getmagnitude());
        //adding location
        Property has_location = ontModel.getProperty(ontURI+"#"+"has_location");
        Individual city = ontModel.getIndividual(ontURI+"#"+earthquake.getCity());
                ontModel.add(e,has_location,city);
        
        ontSave();
        earthquake.setName(instanceName);
    }
    
    
    public Model getBaseModel(){
        return this.baseModel;
    }
    
    public OntModel getOntModel(){
        return this.ontModel;
    }
    
    public InfModel getReasonerModel(){
        return this.reasonerModel;
    }
    
    public String getOntURI(){
        return this.ontURI;
    }
}
