package com.allianz.DAO;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection 
{
	private static Connection connection;  //connection object 
	
	private DBConnection()
	{
		super();
	}//constructor ends here
	
	
	/**
	 * This method checks whether connection is already present
	 * If connection is present then it will return that connection otherwise 
	 * it will create new connection and returns connection object  
	 * @exception :SQLException
	 * @exception:ClassNotFoundException
	 * @exception :IOException
	 * Either of the three exception could occur if class is not found or file not found or query wrongly written.
	 */
	public static Connection getConnection()
	{
		try
		{
			if(connection != null)
			{
				if(!connection.isClosed())
				{
					return connection;
				}
			}
			else
			{
				Properties properties = new Properties();
				properties.load(new FileReader("db.properties"));
				
				String driver = properties.getProperty("db.driver");
				String host = properties.getProperty("db.host");
				String port = properties.getProperty("db.port");
				String dbName = properties.getProperty("db.dbName");
				String user = properties.getProperty("db.user");
				String password = properties.getProperty("db.password");
				String protocol = properties.getProperty("db.protocol");
				
				
				Class.forName(driver);
				String ConnectionString = protocol+"://"+host+":"+port+"/"+dbName;
				connection = DriverManager.getConnection(ConnectionString,user,password);
				
			}//if-else ends here
			
		}
		catch(SQLException | ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		}//try catch ends here

		return connection;
	}// getConnection() method ends here
	
}//class ends here
