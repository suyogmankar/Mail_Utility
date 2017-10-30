package com.allianz.ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.allianz.Bean.ReceivedMessage;

public class FilterBySubject
{
	public static final Logger logger = Logger.getLogger(FilterImpl.class);
	
	ReceivedMessage receivedMessage = null;
	Mail mail = null;
	
	/**
	 * This method receives all fetched mails from Mail class
	 * and filters out mails depending upon subject of mails
	 * It returns nothing
	 */
	public void filter(String search)
	{
		File directory = null;
		try
		{
			mail = new Mail();
			List<MimeMessage> listOfMails = mail.fetchMail();
			
			for(int i = 0; i < listOfMails.size(); i++)
			{
				MimeMessage message = listOfMails.get(i);
				
				//create receivedMessage object
				receivedMessage = new ReceivedMessage();
				receivedMessage.setFrom(message.getFrom()[0].toString());
				receivedMessage.setSubject(message.getSubject().toString());
				receivedMessage.setContent(mail.getText(message));
				
				String subject = receivedMessage.getSubject();
				directory = new File("C:/Filter/"+search);
				
				if(directory.exists() && subject.contains(search))
				{
					logger.info("Directory already present");
					FilterImpl.writeToFile(receivedMessage, new File("C:/Filter/"+search+"/"+message.getMessageNumber()+".txt"));
				}
				else if(subject.contains(search))
				{
					directory.mkdir();
					logger.info("Directory created");
					FilterImpl.writeToFile(receivedMessage, new File("C:/Filter/"+search+"/"+message.getMessageNumber()+".txt"));
				}//if-else ends here
				
			}//for ends here
		} 
		catch (IOException | MessagingException e) 
		{
			e.printStackTrace();
		}//try catch ends here
	}//constructor ends here
}//class ends here
