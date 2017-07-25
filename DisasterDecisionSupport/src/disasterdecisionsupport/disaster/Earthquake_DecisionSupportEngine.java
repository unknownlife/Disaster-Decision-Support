/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport.disaster;

import disasterdecisionsupport.DDSQuery;
import disasterdecisionsupport.DisasterOntology;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.Individual;

/**
 *
 * @author Classified
 */
public class Earthquake_DecisionSupportEngine {
    //should it inherit from parent class DecisionSupportEngine?
    DisasterOntology ont;
    public Earthquake_DecisionSupportEngine(){
        this.ont = new DisasterOntology();
    }
    
    public Earthquake_DecisionSupportEngine(DisasterOntology ont){
        this.ont = ont;
    }
    
    
    public void run(Earthquake e){
        String output="\nOutput: "+"\nlocation: "+e.getCity()+"\nmagnitude: "+e.getmagnitude();
        //ont.loadReasonerRules();
        DDSQuery q = new DDSQuery(ont);
        List<String> eq = q.getTripletObject(ont.getReasonerModel(), e.getName(),"owl", "sameAs", false);
        System.out.println(e.getName());
        if (eq.isEmpty()){
            output+="\nNo matching action level";
        }
        else{
            System.out.println(eq.toString());
            
            String earthquakeScheme="__Level_1";
            for (String str: eq){
                //for getting the highest of the earthquake level matched, bec eq might have more than 1 result
                if (Integer.parseInt(str.substring(str.length()-1))>=Integer.parseInt(earthquakeScheme.substring(earthquakeScheme.length()-1)))
                    earthquakeScheme=str;
            }
            output+="\nMatching Scheme: "+earthquakeScheme;

            //getting possible victim and actions needed
//            double possibleVictimPercentage = Double.parseDouble((q.getTripletObject(ont.getReasonerModel(), earthquakeScheme, "es", "hasPercentagePossibleVictim", true)).get(0));
//            double population=Double.parseDouble((q.getTripletObject(ont.getReasonerModel(), e.getCity(), "es", "hasPopulation", true)).get(0).trim());
//            double possibleVictim = population/100*possibleVictimPercentage;
//            BigInteger victims = new BigDecimal(possibleVictim).toBigInteger();
//            output+= "\nPossible Victims: "+victims;

            List<String> needs_Recources = q.getNeedRecources(ont.getReasonerModel(), earthquakeScheme);
            String s=needs_Recources.get(0);
            int counter=1;
            output+="\n\nActions to be performed: ";
            List<String> there=new ArrayList<String>();
            there.add(0,s);
            for (String action: needs_Recources){
//                System.out.println(action);
                
                if(action.startsWith(s+">"))
                    continue;
               
                else{
                    if(there.contains(action) && action!=there.get(0))
                        continue;
                    else{
                        there.add(counter,action);
                        s=action;
                if(!action.startsWith(s+">"))
                    s=action;
                output+= "\n"+counter+". "+action;
                List<String> actionDetails = q.getActionDetail(ont.getReasonerModel(), action);  
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
        }
        System.out.println(output);
        
        
        
        
    }
}
