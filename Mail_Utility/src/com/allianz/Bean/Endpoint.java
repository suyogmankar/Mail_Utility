package com.allianz.Bean;

import java.io.File;
import java.sql.Connection;

public class Endpoint
{
	private Connection connection;  //connection object of any database
	private File file;   //File as endpoint
	
	public Endpoint(Connection connection, File file)
	{
		super();
		this.connection = connection;
		this.file = file;
	}//constructor ends here

	public Endpoint()
	{
		super();
	}//constructor ends here

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public String toString()
	{
		return "Endpoint [connection=" + connection + ", file=" + file + "]";
	}
	

}// class ends here
