package com.allianz.ServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;

import com.allianz.Bean.Filter;
import com.allianz.Bean.ReceivedMessage;
import com.allianz.Services.FilterService;


public class FilterImpl implements FilterService
{
	public static final Logger logger = Logger.getLogger(FilterImpl.class);
	
	/**
	 * This method simply serialize the receivedMessage object and write it file
	 * @param receivedMessage
	 * @param file
	 * @throws IOException:will occur,if it will not find the file where the contents have to be written down. 
	 */
	@SuppressWarnings("resource")
	public static void writeToFile(ReceivedMessage receivedMessage, File file) throws IOException
	{
		FileOutputStream fout=new FileOutputStream(file);  
		ObjectOutputStream out=new ObjectOutputStream(fout);  
		  
		out.writeObject(receivedMessage);  
		out.flush();  
		logger.info("Object Written successfully");
	}//method ends here


	/**
	 * This method accepts FilterByDomain object and 
	 * add filter and returns Filter object which tells 
	 * currently which filter is applied
	 */
	@Override
	public Filter addFilter(FilterByDomainName filterByDomainName)
	{
		Filter filter = new Filter();
		filter.setDomainName(filterByDomainName);
		logger.info("Filter added successfully");
		filterByDomainName.filter();
		return filter;
	}//method ends here


	/**
	 * This method accepts FilterBySubject object and 
	 * add filter and returns Filter object which tells 
	 * currently which filter is applied
	 */
	@Override
	public Filter addFilter(FilterBySubject filterBySubject,String search)
	{
		Filter filter = new Filter();
		filter.setSubject(filterBySubject);
		logger.info("Filter added successfully");
		filterBySubject.filter(search);
		return filter;		
	}//method ends here
	
	/**
	 * This method accepts FilterByUnread object and 
	 * add filter and returns Filter object which tells 
	 * currently which filter is applied
	 */
	@Override
	public Filter addFilter(FilterByUnread filterByUnread) 
	{
		Filter filter = new Filter();
		filter.setStatus(filterByUnread);
		logger.info("Filter added successfully");
        
        try 
        {
        	Properties properties = System.getProperties();
    		Properties AuthProp = new Properties();
    		AuthProp.load(new FileReader("Authentication.properties"));
            properties.setProperty("mail.store.protocol", "imaps");
        	
        	Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            
            store.connect("imap.gmail.com", AuthProp.getProperty("username"), AuthProp.getProperty("password"));

            Folder inbox = store.getFolder("INBOX");
    		FilterByUnread.filterUnread(inbox);
    		FilterByUnread.filterRead(inbox);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	logger.debug(e);
        }
        return filter;		
	}

	
	/**
	 * This method accepts filter object which tells 
	 * which filter is currently applied and removes 
	 * applied filter.
	 */
	@Override
	public void removeFilter(Filter filter)
	{
		if(filter.getDomainName() != null)
		{
			filter.setDomainName(null);
			logger.info("Filter removed successfully");
		}
		else if(filter.getSubject() != null)
		{
			filter.setSubject(null);
			logger.info("Filter removed successfully");
		}
		else if(filter.getStatus() != null)
		{
			filter.setStatus(null);
			logger.info("Filter removed successfully");
		}
		
		
	}//method ends here
	
}//class ends here
