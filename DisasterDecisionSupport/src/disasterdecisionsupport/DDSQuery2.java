/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport;

import com.sun.org.apache.xml.internal.security.Init;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.OntModel;
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
public class DDSQuery2 {
    static String prefix;
    
    public DDSQuery2(DisasterOntology ont){
        prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
"PREFIX css: <"+ont.ontURI+"#> " +
"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
    }
    
    public List <String> getTripletObject (OntModel base, String IndividualName, String type, String Property, Boolean DataPropertyShow){   //if DataPropertyShow==true will show whole URL, otherwise only the object annotation
        String queryString = "SELECT ?y " +
                             "WHERE {es:" + IndividualName +" "+type+":" + Property + "?y}";
        return rawQuery(base, queryString, DataPropertyShow);
    }
    
    public List<String> getNeedRecources1(OntModel base, String level){
    String queryString = "SELECT DISTINCT ?x ?y " +
" ?z " +
"WHERE { " +
"  css:H_L-"+level+" css:needs_Recources ?x. " +
"?x ?y ?z. " +
"}"; //   System.out.println(rawQuery(base, queryString, false)+"\n\n");
        return rawQuery(base, queryString, false);
    }
    public List<String> getNeedRecources2(OntModel base, String level){
        String queryString = "SELECT DISTINCT ?x ?y " +
" ?z " +
"WHERE { " +
"  css:RR_L-"+level+" css:needs_Recources ?x. " +
"?x ?y ?z. " +
"}";//   System.out.println(rawQuery(base, queryString, false)+"\n\n");
        return rawQuery(base, queryString, false);
    }
    public List<String> getNeedRecources3(OntModel base, String level){
        String queryString = "SELECT DISTINCT ?x ?y " +
" ?z " +
"WHERE { " +
"  css:R_L-"+level+" css:needs_Recources ?x. " +
"?x ?y ?z. " +
"}";
     //   System.out.println(rawQuery(base, queryString, false)+"\n\n");
        return rawQuery(base, queryString, false);
    }
    public List<String> getActionDetail(OntModel base, String IndividualName){
        String queryString = prefix +  IndividualName ;
       // System.out.println(IndividualName);
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
    
    public List <String> rawQuery(OntModel base, String queryWord, Boolean DataPropertyShow){
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
