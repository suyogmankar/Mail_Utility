package com.allianz.Bean;

public class User
{
	private int id;  //id of user
	private String Username;  //username should be email id
	private String password; //password
	private int contact;   //contact no of user
	
	public User(int id, String username, String password, int contact) 
	{
		super();
		this.id = id;
		Username = username;
		this.password = password;
		this.contact = contact;
	}//constructor ends here

	public User()
	{
		super();
	}//constructor ends here

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getContact() {
		return contact;
	}

	public void setContact(int contact) {
		this.contact = contact;
	}//getter-setter ends here

	@Override
	public String toString() {
		return "User [id=" + id + ", Username=" + Username + ", password=" + password + ", contact=" + contact + "]";
	}
	
}//class ends here
