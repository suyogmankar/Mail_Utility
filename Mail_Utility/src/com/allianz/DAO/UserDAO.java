package com.allianz.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.allianz.Bean.User;

public class UserDAO
{
	private static PreparedStatement preparedStatement;  //preparedStatement for query
	private static Connection connection = DBConnection.getConnection(); //connection object for getting connection
	private static ResultSet resultSet;  //to display updated result
	private static Statement statement;  //to execute query
	
	private static String addQuery = "insert into user (id,username,password) values(?,?,?)";
	private static String deleteQuery = "delete from user where id = ?";
	private static String selectQuery = "select * from user";
	private static String updateQuery = "update user set username = ?,password = ? where id = ?";
	
	
	/**
	 * This method extracts updated resulSet or table from
	 * database.
	 * @return resultSet
	 * @throws SQLException
	 */
	public static ResultSet getResultSet() throws SQLException
	{
		statement = connection.createStatement();
		resultSet = statement.executeQuery(selectQuery);
		
		return resultSet;
	}//method ends here
	
	
	/**
	 * This method add one user to table
	 * @param user object
	 * @throws SQLException
	 */
	public static void addUser(User user) throws SQLException
	{
		preparedStatement = connection.prepareStatement(addQuery);
		preparedStatement.setInt(1, user.getId());
		preparedStatement.setString(2, user.getUsername());
		preparedStatement.setString(3, user.getPassword());
				
		preparedStatement.executeUpdate();
	}//method ends here
	
	
	/**
	 * This method accepts userid as argument and returns one user object
	 * @param userid
	 * @return Users object
	 * @throws SQLException
	 */
	public static User getUser(int userid) throws SQLException
	{
		User user = null;
		resultSet = UserDAO.getResultSet();
		
		while(resultSet.next())
		{
			if(resultSet.getInt("id") == userid)
			{
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
			}//if ends here
			
		}//while ends here
		
		return user;
	}//method ends here
	
	
	/**
	 * This method is returns List of all users 
	 * @return List of Users
	 * @throws SQLException
	 */
	public static List<User> getUsers() throws SQLException
	{
		List<User> listOfUsers = new ArrayList<User>();
		resultSet = getResultSet();
		User user = null;
		
		while(resultSet.next())
		{
			user = new User();
			user.setId(resultSet.getInt("id"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			
			listOfUsers.add(user);
		}//while ends here
		return listOfUsers;
	}//method ends here
	
	
	/**
	 * This method accepts Users object and update
	 * User and returns nothing 
	 * @param users object
	 * @throws SQLException
	 */
	public static void updateUser(User user) throws SQLException
	{
		preparedStatement = connection.prepareStatement(updateQuery);
	
		preparedStatement.setString(1, user.getUsername());
		preparedStatement.setString(2, user.getPassword());
		preparedStatement.setInt(4, user.getId());
		
		preparedStatement.executeUpdate();
		
	}//method ends here
	
	/**
	 * This method accepts userid as argument and delete one user
	 * @param userid
	 * @throws SQLException
	 */
	public static void deleteUser(int userid) throws SQLException
	{
		preparedStatement = connection.prepareStatement(deleteQuery);
		preparedStatement.setInt(1, userid);
		
		preparedStatement.executeUpdate();
	}//method ends here
}//class ends here
