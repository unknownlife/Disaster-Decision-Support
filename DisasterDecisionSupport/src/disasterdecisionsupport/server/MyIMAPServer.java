/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disasterdecisionsupport.server;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import disasterdecisionsupport.Constants;
import disasterdecisionsupport.disaster.Earthquake_Handler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;


/**
 *
 * @author Classified
 */
public class MyIMAPServer {
    Properties props = new Properties();
    Session session;
    Store store = null;
    IMAPFolder inbox;
    Thread idleThread;
    boolean isStopping=false;
    
    public MyIMAPServer(){
        try {
            props.load(new FileInputStream(new File(Constants.SMTP_PROPERTIES_PATH)));
            props.setProperty("mail.store.protocol", "imaps");
            session = Session.getDefaultInstance(props, null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void connect(){ 
        try {
            System.out.print("Connecting to IMAP Server...");
            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", Constants.EMAIL_USERNAME, Constants.EMAIL_PASSWORD);  
            inbox = (IMAPFolder)store.getFolder("inbox");
            inbox.open(Folder.READ_WRITE);
            
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MessagingException ex) {
            Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void reconnect(){
        System.out.print("Reconnecting to IMAP Server...");
        try {
            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", Constants.EMAIL_USERNAME, Constants.EMAIL_PASSWORD);  
            inbox = (IMAPFolder)store.getFolder("inbox");
            inbox.open(Folder.READ_WRITE);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void disconnect(){
        try {
            //false = for marking as read, and true to leave it like that
            inbox.close(false);
            store.close();
        } catch (MessagingException ex) {
            Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handleMessages(Message[] messages){
        int i=0;
        for (Message m : messages){
            Address[] froms;
            try {
                froms = m.getFrom();
                String sender = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
                
                System.out.println( messages[i].getSubject()+ "(" +sender+")" + " \n"+i);
                
                switch(sender){
                    case Constants.EARTHQUAKE_NOTIFICATION_EMAIL:
                        //handle eathquake
                        //get Earthquake ID
                        String eID="";
                        
                        if (eID != null && !eID.isEmpty()){
                            Earthquake_Handler eh= new Earthquake_Handler();
                            eh.accessAPI(eID);
                        }
                        break;
                }
            } catch (MessagingException ex) {
                Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                //mark the message as seen only when operation has been performed properly
                inbox.setFlags(new Message[] {messages[i]}, new Flags(Flags.Flag.SEEN), true);
            } catch (MessagingException ex) {
                Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            i++;
        }
    }
    
    public void startEmailListener(){
        connect();
        try {
            if (!((IMAPStore)inbox.getStore()).hasCapability("IDLE")) {
                throw new UnsupportedOperationException("The imap server does not support IDLE command");
            }
            
            MessageCountListener messageCountListener = new MessageCountListener(){
                @Override
                public void messagesAdded(MessageCountEvent mce) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    Message[] messages = mce.getMessages();
                    System.out.println("Got " + messages.length + " new message");
                    handleMessages(messages);
                }

                @Override
                public void messagesRemoved(MessageCountEvent mce) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

            };

            // Add the handler for messages to be added in the future.
            inbox.addMessageCountListener(messageCountListener);

            //will this run forever??
            idleThread = new Thread() {
                @Override
                public void run() {
                    System.out.print("\nEmail Receiving Thread is running...");
                    while (true) {
                        if (isStopping) break;
                        try {
                            // Notify the message count listener if the value of EXISTS response is
                            // larger than realTotal.
                            inbox.idle();
                        } catch (FolderClosedException e) {
                            if (isStopping) break;
                            // reconnect
                            System.out.println("Reopen the imap folder");
                            reconnect();
                        } catch (Exception e) {
                            System.out.println("Failed to run IDLE command; abort" + e);
                            break;
                        }
                    }
                    System.out.println("Stop the Email Receiving Thread");
                }
            };
            idleThread.start();
        } catch (MessagingException ex) {
            Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listUnread(){
        this.connect();
        try {
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message messages[] = inbox.search(ft);
            System.out.println("\nTotal Unread Messages:- " + messages.length);

            for(int i=messages.length-1; i>messages.length-11; i--) {

//                     Multipart mp = (Multipart)messages[i].getContent();  
//                     Object p = mp.getBodyPart(i).getContent();  
//                     String q = p.toString();//object has the body content  
//                     System.out.println(q);//prints the body  
                inbox.setFlags(new Message[] {messages[i]}, new Flags(Flags.Flag.SEEN), true);
                System.out.println( messages[i].getSubject()+ " \n"+i);
                //System.out.println((String)messages[i].getContent() );
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.exit(2);
        }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        this.disconnect();
    }
    
    public void listEmails(){
        this.connect();
        int messageCount;
        try {
            messageCount = inbox.getMessageCount();
            System.out.println("Total Messages:- " + messageCount);

            Message[] messages = inbox.getMessages();
            System.out.println("------------------------------");

            for (int i = messageCount-1; i > messageCount-11; i--) {
                System.out.println("Mail Subject:- " + messages[i].getSubject());
//                System.out.println(messages[i].getContent());
                Multipart mp = (Multipart)messages[i].getContent();  
                Object p = mp.getBodyPart(0).getContent();  
                String q = p.toString();//object has the body content  
                
                System.out.println(q);//prints the body 
            }
        } catch (MessagingException ex) {
            Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyIMAPServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.disconnect();

    }
}
