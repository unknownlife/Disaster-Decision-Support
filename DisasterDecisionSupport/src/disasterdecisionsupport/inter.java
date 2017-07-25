/*+918950777202
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisasterDecisionSupport;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.AbstractAction;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Zonoid
 */
public class inter implements ActionListener{

	static JTextField Place,Magn, Time, Tz;
	static  JLabel l5,l1,l2,l3,l4;
	static JFrame f;
	JButton Submit,go1,go2,go3;
	Object source;
	public static void main(String[] args) {
            new inter("12\n15\n1\n1\n1"
                    );
	      //new inter();
	       
	    }
	public inter(String s){
            String actions[]=s.split("\n");
		 f= new JFrame("Temporary Interface"); 
		  final String a="Deaths:   "+actions[0]+"\nInjured:  "+actions[1]+"\n";
		    l1=new JLabel("Deaths:   "+actions[0]);  
		    l1.setBounds(50,50, 100,30);  
		    l2=new JLabel("Injured:  "+actions[1]);  
		    l2.setBounds(200,50, 100,30);
		    l3=new JLabel("Humanitarian Assistance:  "+actions[2]);  
		    l3.setBounds(50,150, 200,30);  
		    f.add(l1); f.add(l2);
		    l4=new JLabel("Rescue and Rehabitalization:  "+actions[3]);
		    l5=new JLabel("Search and Rescue:  "+actions[4]);
		    l4.setBounds(50,200, 200,30);
                    l5.setBounds(50,250,200,30);
                    f.add(l5);
		    f.add(l3); f.add(l4);  
	    go1=new JButton(new AbstractAction("SEND SMS"){
                @Override
                public void actionPerformed(ActionEvent e){
                    
                    sms obj=new sms(a+"Humanitarian Assistance:  "+actions[2]);
                }
            });
            go2=new JButton(new AbstractAction("SEND SMS"){
                @Override
                public void actionPerformed(ActionEvent e){
                    
                    sms obj=new sms(a+"Rescue and Rehabitalization:  "+actions[3]);
                }
            });
            go3=new JButton(new AbstractAction("SEND SMS"){
                @Override
                public void actionPerformed(ActionEvent e){
                    
                    sms obj=new sms(a+"Search and Rescue:\n"+actions[4]);
                }
            });
            go1.setBounds(250,150,100,30);
            go2.setBounds(250,200,100,30);
            go3.setBounds(250,250,100,30);
            
	    f.add(go1);
            f.add(go2);
            f.add(go3);
	    f.setSize(400,400);  
	    f.setLayout(null);  
	    f.setVisible(true); 
	    
	}
	/*public void actionPerformed(ActionEvent ae) {

        source = ae.getSource();
        sms obj =new sms();
        JOptionPane.showMessageDialog(null, "Message Sent", "InfoBox: " + "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
    }
	/*private static void store(){
	go1.addActionListener(this);
            go2.addActionListener(this);
            go3.addActionListener(this);
        try{
			String place=Place.getText();
			String magn=Magn.getText();
			String time=Time.getText();
			String tz=Tz.getText();
			Place.setText("");
			Magn.setText("");
			Time.setText("");
			Tz.setText("");
			   Class.forName("org.postgresql.Driver");
		        Connection m= DriverManager.getConnection("jdbc:postgresql://localhost/test","postgres","abc123");
		        Statement st= m.createStatement();
		        String q= "insert into earthquake"+"(place,time,tz,magnitude)"+ "values ('"+place+"',"+time+","+tz+","+magn+")";
		        st.executeUpdate(q);
		        l1=new JLabel("");
		}
		catch(Exception e){
			   e.printStackTrace();
		}
        } */   

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


