/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.InfModel;

/**
 *
 * @author Classified
 */
public class DDSQuery {
    static String prefix;
    
    public DDSQuery(DisasterOntology ont){
        prefix = "PREFIX es: <"+ ont.ontURI + "#> " +
                 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
                 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
                 "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
                 "PREFIX owl: <http://www.w3.org/2002/07/owl#>";
    }
    
    public List <String> getTripletObject (InfModel base, String IndividualName, String type, String Property, Boolean DataPropertyShow){   //if DataPropertyShow==true will show whole URL, otherwise only the object annotation
        String queryString = "SELECT ?y " +
                             "WHERE {es:" + IndividualName +" "+type+":" + Property + "?y}";
        return rawQuery(base, queryString, DataPropertyShow);
    }
    
    public List<String> getNeedRecources(InfModel base, String level){
        String queryString = "SELECT ?x ?y ?z"+
                             "WHERE {es:"+level+" es:needs_Recources ?x. ?x ?y ?z.}";
     //   System.out.println(rawQuery(base, queryString, false)+"\n\n");
        return rawQuery(base, queryString, false);
    }
    
    public List<String> getActionDetail(InfModel base, String IndividualName){
        String queryString = prefix + "SELECT ?x ?y ?z" +
                             "WHERE {es:" + IndividualName +" ?x ?y." +
                                    "?x rdfs:subPropertyOf ?z}";
    //    System.out.println(IndividualName);
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, base);
        ResultSet results = ResultSetFactory.copyResults( qe.execSelect() );
                
        List<String> output = new ArrayList();
        String temp1;
        String temp2;
        HelperFunctions I = new HelperFunctions();
        while (results.hasNext()){
            temp1 = results.next().toString();
            temp2 = temp1;
            temp1 = I.cutFront(temp1, "#", 1);
            temp1 = I.cutBack(temp1, ">", 1);
            temp2 = I.cutFront(temp2, "=", 1);
            temp2 = I.cutBack(temp2, ")", 2);
            temp1=temp1.trim();
            temp2=temp2.trim();
            output.add(temp1);
            output.add(temp2);

        }
        return output;
    }
    
    public List <String> rawQuery(InfModel base, String queryWord, Boolean DataPropertyShow){
        String queryString = prefix + queryWord;
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, base);
        ResultSet results = ResultSetFactory.copyResults( qe.execSelect() );
        //ResultSetFormatter.out(System.out, results, query);
        
        List <String> result = new ArrayList();
        String temp;
        HelperFunctions helper = new HelperFunctions();
        if (results.hasNext()) {
            while (results.hasNext()) {
                temp = results.next().toString();
                if (DataPropertyShow){
                    int crit = temp.indexOf("^");
                    temp = helper.cutFront(temp, "=", 1);
                    if (crit == -1){
                        temp = helper.cutBack(temp, ")", 1);
                    }
                    else {
                        temp = helper.cutBack(temp, "^", 2);
                    }

                }
                else {
                    temp = helper.cutFront(temp, "#", 1);
                    temp = helper.cutBack(temp, ">", 1);
                }
                temp.trim();
                result.add(temp);
            }
        }

        qe.close();  

    return result;  
    }
    
}
