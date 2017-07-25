/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport;

/**
 *
 * @author Zonoid
 */
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class sms {
    sms(String s){
         String authkey = "161318AeiTtm6yM594be588";
            String mobiles = "+918447278554";
            String senderId = "earthQ";
            String message = s;
            String route="4";

            URLConnection myURLConnection=null;
            URL myURL=null;
            BufferedReader reader=null;

            String encoded_message=URLEncoder.encode(message);

            String mainUrl="http://api.msg91.com/api/sendhttp.php?";

            StringBuilder sbPostData= new StringBuilder(mainUrl);
            sbPostData.append("authkey="+authkey);
            sbPostData.append("&mobiles="+mobiles);
            sbPostData.append("&message="+encoded_message);
            sbPostData.append("&route="+route);
            sbPostData.append("&sender="+senderId);

            mainUrl = sbPostData.toString();
            try
            {
                myURL = new URL(mainUrl);
                myURLConnection = myURL.openConnection();
                myURLConnection.connect();
                reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
                String response;
                while ((response = reader.readLine()) != null)
                System.out.println(response);

                reader.close();
            }
            catch (IOException e)
            {
                    e.printStackTrace();
            }
    }
}
