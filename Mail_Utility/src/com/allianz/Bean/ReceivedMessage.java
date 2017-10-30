package com.allianz.Bean;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ReceivedMessage implements Serializable
{
	private String from;  //from whom mail is received
	private String to;   //to whom mail is to be send
	private String subject;  //subject of mail
	private String content; //body of mail
	
	public ReceivedMessage() 
	{
		super();
	}//constructor ends here

	public ReceivedMessage(String from, String to, String subject, String content) 
	{
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.content = content;
	}//constructor ends here

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString()
	{
		return "Message [from=" + from + ", to=" + to + ", subject=" + subject + ", content=" + content + "]";
	}
	
	
}//class ends here
