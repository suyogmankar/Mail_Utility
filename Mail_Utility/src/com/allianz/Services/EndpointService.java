package com.allianz.Services;

import java.io.File;
import java.sql.Connection;
import com.allianz.Bean.Endpoint;


public interface EndpointService
{
	public Endpoint addEndpoint(Connection connection);
	public Endpoint addEndpoint(File file);
	public void removeEndpoint(Endpoint endpoint);
}//class ends here
