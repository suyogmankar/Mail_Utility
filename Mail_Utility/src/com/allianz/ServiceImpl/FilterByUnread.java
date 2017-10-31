package com.allianz.ServiceImpl;

import java.io.File;
import java.io.IOException;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import org.apache.log4j.Logger;

import com.allianz.Bean.ReceivedMessage;

public class FilterByUnread
{
	public static final Logger logger = Logger.getLogger(FilterImpl.class);
	
	static ReceivedMessage receivedMessage = null;
	static Mail mail = null;
	
	
	/**
	 * This method fetches all mails and 
	 * and filter those mails which are unread.
	 * @param inbox(Folder name)
	 * @return nothing
	 * @throws Exception- If occured,it may throw IOException when it will not find the content from mail from where it is fetching and
	 *  in which the message has to be written or Message Exception when it would fail to fetch the message content via message object.
	 */
	public static void filterUnread(Folder inbox)
	{        
		try
		{
			inbox.open(Folder.READ_ONLY);
			Message allmails[] = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            
            for(int i = 0; i < allmails.length; i++)
            {
            	MimeMessage message =  (MimeMessage)allmails[i];
            	mail = new Mail();
            	receivedMessage = new ReceivedMessage();
            	receivedMessage.setFrom(message.getFrom()[0].toString());
				receivedMessage.setSubject(message.getSubject().toString());
				receivedMessage.setContent(mail.getText(message));
				
				File directory = new File("C:/Filter/Unread");
				
				if(directory.exists())
				{
					logger.info("Directory already present");
					FilterImpl.writeToFile(receivedMessage, new File("C:/Filter/Unread/"+message.getMessageNumber()+".txt"));
				}
				else
				{
					directory.mkdir();
					logger.info("Directory created");
					FilterImpl.writeToFile(receivedMessage, new File("C:/Filter/Unread/"+message.getMessageNumber()+".txt"));
				}//if-else ends here
            	
            }//for ends here
		}
		catch (MessagingException | IOException e)
		{
			e.printStackTrace();
		}//try-catch ends here
	}//method ends here
	    
	
	/**
	 * This method fetches all mails and 
	 * and filter those mails which are already read.
	 * @param inbox(Folder name)
	 * @return nothing.
	 * @throws Exception- If occured,it may throw IOException when it will not find the content from mail from where it is fetching and
	 * in which the message has to be written or Message Exception when it would fail to fetch the message content via message object.
	 */
	public static void filterRead(Folder inbox)
	{        
		try
		{
			
			Message allmails[] = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), true));
            
            for(int i = 0; i < allmails.length; i++)
            {
            	MimeMessage message =  (MimeMessage)allmails[i];
            	mail = new Mail();
            	receivedMessage = new ReceivedMessage();
            	receivedMessage.setFrom(message.getFrom()[0].toString());
				receivedMessage.setSubject(message.getSubject().toString());
				receivedMessage.setContent(mail.getText(message));
				
				File directory = new File("C:/Filter/Read");
				
				if(directory.exists())
				{
					logger.info("Directory already present");
					FilterImpl.writeToFile(receivedMessage, new File("C:/Filter/Read/"+message.getMessageNumber()+".txt"));
				}
				else
				{
					directory.mkdir();
					logger.info("Directory created");
					FilterImpl.writeToFile(receivedMessage, new File("C:/Filter/Read/"+message.getMessageNumber()+".txt"));
				}//if-else ends here
            	
            }//for ends here
		}
		catch (MessagingException | IOException e)
		{
			e.printStackTrace();
		}//try-catch ends here
	}//method ends here
	
}//class ends here