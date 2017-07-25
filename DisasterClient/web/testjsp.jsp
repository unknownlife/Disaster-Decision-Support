<%-- 
    Document   : testjsp
    Created on : 9 Jul, 2017, 12:10:05 PM
    Author     : Shikhar Jain
--%>

<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.ObjectInputStream"%>
<%@page contentType="text/html" pageEncoding="UTF-8"  %>

<%@page import="java.util.Date" 
       import="java.io.DataOutputStream"
import="java.io.File"
import="java.io.FileInputStream"
import="java.io.IOException"
import="java.net.Socket" 
        %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Decision Support</title>
    </head>
    <body>
        
        
        <%!
            private static Socket s;

        //Starts a socket
	public String FileClient(String host, int port, String file) {
		String x= null;
                try {
			s = new Socket(host, port);
			x = sendFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
return x;
	}
	//Reads a file and send it through the socket
	public String sendFile(String file) throws IOException {
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                
                FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[4096];
		File f=new File(file);

                dos.writeInt((int)f.length());  //Sends file size
		while (fis.read(buffer) > 0) {
			dos.write(buffer);
		}
                DataInputStream oin = new DataInputStream(s.getInputStream());
		String msg=oin.readUTF();
                
		fis.close();
		dos.close();
                //System.out.println("hello");
return msg;
        }
    /*static String client() throws IOException, ClassNotFoundException {
        //String s123 = null;
        //Socket socket = s;
        Socket socket = new Socket("localhost",3000);
        //InputStream in = socket.getInputStream();
        DataInputStream oin = new DataInputStream(socket.getInputStream());
        //String stringFromServer = (String) oin.readObject();
        //FileWriter writer = new FileWriter(s123);
        //writer.write(stringFromServer);
        //in.close();
        //writer.close();
        String msg=oin.readUTF();
        return msg;
            }*/
        %>
        
        <%

         String q = FileClient("localhost", 1989, "D:\\earthquakeSampleData.json"); 
         //String x =client();
         //System.out.println(x);
         %>
         <%= q %>
         
            
            
        
    </body>
</html>
