package com.allianz.Services;

import com.allianz.Bean.Endpoint;


public interface MailService
{
	public void sendMail(String sendTo);
	public void sendMails(Endpoint endpoint);

}//class ends here
