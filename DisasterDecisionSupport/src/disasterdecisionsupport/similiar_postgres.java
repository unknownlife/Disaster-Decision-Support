package disasterdecisionsupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class similiar_postgres {
	static String a,pl,c;
	static double m,max,mag;
	static int y;
        static double actions[]=new double[6];
public static double[] similiar(String a, String c, double m, int year) {
	  similiar_postgres.a= a;
          similiar_postgres.c=c;
      similiar_postgres.m=m;
     similiar_postgres.y=year;
     max=0;
	 String SQL = "SELECT * FROM eq";
	 
     try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/casebase","postgres","abc123");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
         // display actor information
         display(rs);
     } catch (SQLException ex) {
         System.out.println(ex.getMessage());
     }
     
     return actions;
 }
private static void display(ResultSet rs) throws SQLException {
    while (rs.next()) {
        double simi=0;int c=0;
        String year[]=rs.getString("Date").split("-");
        double magnitude=rs.getDouble("magnitude");
        if(rs.getString("State").equalsIgnoreCase(a))
        {  
       	 simi+=1;
       	 c++;
        }
        simi+=((1-Math.sqrt((Math.abs(y-Integer.parseInt(year[2])))/1000.0))*0.7+(1-Math.sqrt((Math.abs(magnitude-m))/10.0)));
        c+=2;
        simi/=c;
        if(simi>max){
       	 max=simi;
       	 mag=rs.getDouble("magnitude");
       	 pl=rs.getString("State");
         actions[0]=rs.getInt("Death");
         actions[1]=rs.getInt("Injured");
         actions[2]=rs.getInt("H_L");
         actions[3]=rs.getInt("RR_L");
         actions[4]=rs.getInt("SR_L");
        }
        
        System.out.println("\nSimilarity:"+simi*100+"\n");
   }
    actions[5]=max*100;
    }

}

