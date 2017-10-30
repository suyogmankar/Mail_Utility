package com.allianz.ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import com.allianz.Bean.ReceivedMessage;

public class FilterByDomainName
{
	public static final Logger logger = Logger.getLogger(FilterImpl.class);
	
	ReceivedMessage receivedMessage = null;
	Mail mail = null;
	
	/**
	 * This method receives all fetched mails from Mail class
	 * and filters out mails using domain name
	 * It returns nothing
	 */
	public void filter()
	{
		File directory = null;
		try
		{
			mail = new Mail();
			List<MimeMessage> list  = mail.fetchMail();
			
			for(int i = 0; i < list.size(); i++)
			{
				MimeMessage message =  list.get(i);
				
				//create receivedMessage object
				receivedMessage = new ReceivedMessage();
				receivedMessage.setFrom(message.getFrom()[0].toString());
				receivedMessage.setSubject(message.getSubject().toString());
				receivedMessage.setContent(mail.getText(message));
				
				String from = receivedMessage.getFrom();
				
				//extract domain name
				int index = from.indexOf('@')+1;
				String domainName = from.substring(index);
				
				if(domainName.contains(">"))
				{
					domainName = domainName.replace(">", "");
				}//if ends here
				
				directory = new File("C:/Filter/"+domainName);
				if(directory.exists())
				{
					logger.info("Directory already present");
					FilterImpl.writeToFile(receivedMessage, new File("C:/Filter/"+domainName+"/"+message.getMessageNumber()+".txt"));
				}
				else
				{
					directory.mkdir();
					logger.info("Directory created");
					FilterImpl.writeToFile(receivedMessage, new File("C:/Filter/"+domainName+"/"+message.getMessageNumber()+".txt"));
				}//if-else ends here
				
			}//for ends here
		}
		catch (IOException | MessagingException e) 
		{
			logger.debug(e);
			e.printStackTrace();
		}//try-catch ends here
	}//methods ends here
	
}//class ends here
