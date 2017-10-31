package com.allianz.ServiceImpl;

import java.io.File;
import java.sql.Connection;
import org.apache.log4j.Logger;
import com.allianz.Bean.Endpoint;
import com.allianz.Services.EndpointService;

public class EndpointImpl implements EndpointService
{
	public static final Logger logger = Logger.getLogger(EndpointImpl.class);
	
	Endpoint endpoint = null;

	/**
	 * This is an overridden method from endpointService interface
	 * and it sets endpoint as database from which email id's to be taken
	 * in order to send mail
	 */
	@Override
	public Endpoint addEndpoint(Connection connection)
	{
		endpoint = new Endpoint();
		endpoint.setConnection(connection);
		logger.info("Database endpoint set successfully");
		return endpoint;
	}//method ends here

	
	/**
	 * This is an overridden method from endpointService interface
	 * and it sets Endpoint as file from which email id's to be taken
	 * in order to send mail
	 */
	@Override
	public Endpoint addEndpoint(File file)
	{
		endpoint = new Endpoint();
		endpoint.setFile(file);
		logger.info("File endpoint is set successfully");
		return endpoint;
	}//method ends here

	
	/**
	 * This method removes the endpoint which is previously set
	 */
	@Override
	public void removeEndpoint(Endpoint endpoint)
	{
		if( (endpoint.getConnection()) != null)
		{
			endpoint.setConnection(null);
			logger.info("Endpoint removed successfully");
		}
		else
		{
			endpoint.setFile(null);
			logger.info("Endpoint removed successfully");
		}//if-else ends here
		
	}//method ends here
	
}//class ends here
