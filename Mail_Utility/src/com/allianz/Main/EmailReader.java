package com.allianz.Main;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class EmailReader
{

    public static void main(String args[])
    {
        Properties properties = System.getProperties();
        properties.setProperty("mail.store.protocol", "imaps");
        try
        {
                Session session = Session.getDefaultInstance(properties);
                Store store = session.getStore("imaps");
                
                store.connect("imap.gmail.com", "suyogmankar9@gmail.com", "born2paint");

                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_ONLY);
                
                Message messages[] = inbox.getMessages(); 
                
                for(int i = messages.length - 4; i < messages.length;i++)
                {
                	Message message  = messages[i];
                	//System.out.println("EMAIL NUMBER: "+(i+1));
                	System.out.println("DATE: "+message.getSentDate().toString());
                    System.out.println("FROM: "+message.getFrom()[0].toString());            
                    System.out.println("SUBJECT: "+message.getSubject().toString());
                    //System.out.println("CONTENT: "+message.getContent().toString());
                    System.out.println(message.getContentType());
                 
                }
                inbox.close(false);
                store.close();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
     }

}
 