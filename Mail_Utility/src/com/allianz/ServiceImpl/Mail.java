package com.allianz.ServiceImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

import com.allianz.Bean.Endpoint;
import com.allianz.Bean.User;
import com.allianz.DAO.UserDAO;
import com.allianz.Services.MailService;

/*
 * This Class send e-mail to specific  
 * recipient. And takes parameters from AuthProp file
 */
public class Mail implements MailService
{
	public static final Logger logger = Logger.getLogger(Mail.class);
	
	private String host;  //host name
	private String port;  //SMTP port
	private String username;  //username for e-mail id for authentication 
	private String password; //password for e-mail id for authentication
	private String from;  //e-mail id of sender
	private String subject;  //subject of mail to be sent
	private String msg;  // message or body of mail
	Properties AuthProp;  //properties file for authentication
	Properties ComposeProp;  //properties file to compose mail
	
	/**
	 * This is constructor which sends initializes
	 * all the values and variables which are 
	 * required to send mail.
	 * 
	 * It takes values from properties file
	 * 
	 * @throws FileNotFoundException or IOException: if it will not find Authentication and Compose properties files.
	 * 	 
	 */
	public Mail()
	{
		AuthProp = new Properties();
		ComposeProp = new Properties();
		try 
		{
			AuthProp.load(new FileReader("Authentication.properties"));
			ComposeProp.load(new FileReader("Compose.properties"));
		}
		catch (IOException e)
		{
			logger.debug(e);
			e.printStackTrace();
		}//try-catch ends here
		
		host = AuthProp.getProperty("host");
		port = AuthProp.getProperty("port");
		username = AuthProp.getProperty("username");
		password = AuthProp.getProperty("password");
		from = ComposeProp.getProperty("from");
		msg = ComposeProp.getProperty("message");
		subject = ComposeProp.getProperty("subject");
		
	}//constructor ends here
	
	
	/**
	 * This method actually sends the mail.
	 * It takes one String argument which represents email address of recipient
	 * @return nothing.
	 */
	private void send(String sendTo)
	{
		AuthProp.put("mail.smtp.auth", "true");
		AuthProp.put("mail.smtp.starttls.enable", "true");
		AuthProp.put("mail.smtp.host", host);
		AuthProp.put("mail.smtp.port", port);

		Session session = Session.getInstance(AuthProp,new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(username, password);
			}
		});

		try
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sendTo));
			message.setSubject(subject);
			message.setText(msg);

			Transport.send(message);
			logger.info("Mail Sent Successfully");
		 }
		 catch(MessagingException e)
		 {
			throw new RuntimeException(e);
		 }//try-catch ends here
		
	}//method ends here
	
	
	/**
	 * This method fetches all mails and adds it to list.
	 * It users IMAP protocol to fetch mails.
	 * @return List of all mails in selected folder
	 */
	public List<MimeMessage> fetchMail()
	{
		List<MimeMessage> listOfMails = new ArrayList<MimeMessage>();
		
		Properties properties = System.getProperties();
	        properties.setProperty("mail.store.protocol", "imaps");
	        try
	        {
	                Session session = Session.getDefaultInstance(properties);
	                Store store = session.getStore("imaps");
	                
	                store.connect("imap.gmail.com", username, password);

	                Folder inbox = store.getFolder("INBOX");
	                inbox.open(Folder.READ_ONLY);
	                
	                Message allmails[] = inbox.getMessages(); 
	                logger.info("Got all emails");
	                
	                for(int i = 0; i < allmails.length; i++)
	                {
	                	MimeMessage msg =  (MimeMessage)allmails[i];
	                	listOfMails.add(msg);
	                	
	                }//for ends here
	                logger.info("Mails Added to successfully to list");
	                //inbox.close(false);
	                //store.close();
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
			return listOfMails;
	}//method ends here
	
	/**
	 * This method sends mail to single user.
	 * It accepts String argument which is email id and 
	 * it returns nothing
	 */
	@Override
	public void sendMail(String sendTo)
	{
		send(sendTo);
		
	}//method ends here

	
	/**
	 * This method accepts endpoint object as argument
	 * and depending upon selected endpoint it fetches 
	 * email id's and send mail to those mail ids.
	 */
	@Override
	public void sendMails(Endpoint endpoint)
	{
		if(endpoint == null)
		{
			logger.info("Endpoint has nothing");
		}
		else if( (endpoint.getConnection()) != null)
		{
			try
			{
				List<User> listOfUsers = UserDAO.getUsers();
				for(User user : listOfUsers)
				{
					String mailId = user.getUsername();
					send(mailId);
				}//for ends here
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}//try-catch ends here
		}
		else if(endpoint.getFile() != null)
		{
			try 
			{
				BufferedReader br = new BufferedReader(new FileReader(endpoint.getFile()));
				String mailId; 
				while( (mailId = br.readLine()) != null)
				{
					send(mailId);
				}//while ends here
				br.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}//if-else ends here
		
	}//method ends here
	
	
	
	public String getText(Part p) throws MessagingException, IOException 
	{

	    if (p.isMimeType("multipart/alternative"))
	    {
	        // prefer html text over plain text
	        Multipart mp = (Multipart)p.getContent();
	        String text = null;
	        for (int i = 0; i < mp.getCount(); i++) 
	        {
	            Part bp = mp.getBodyPart(i);
	            if (bp.isMimeType("text/plain")) 
	            {
	                if (text == null)
	                    text = getText(bp);
	                continue;
	            }
	            else if (bp.isMimeType("text/html")) 
	            {
	                String s = getText(bp);
	                if (s != null)
	                    return s;
	            }
	            else
	            {
	                return getText(bp);
	            }
	        }
	        return text;
	    } 
	    else if (p.isMimeType("multipart/*")) 
	    {
	        Multipart mp = (Multipart)p.getContent();
	        for (int i = 0; i < mp.getCount(); i++) 
	        {
	            String s = getText(mp.getBodyPart(i));
	            if (s != null)
	                return s;
	        }
	    }
	    else
	    { 
	    	return p.getContent().toString();
	    }  
	    return null; 
	}//method ends here
	
}//class ends here
