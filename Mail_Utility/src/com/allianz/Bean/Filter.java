package com.allianz.Bean;

import com.allianz.ServiceImpl.FilterByDomainName;
import com.allianz.ServiceImpl.FilterByUnread;
import com.allianz.ServiceImpl.FilterBySubject;

public class Filter
{
	FilterByDomainName domainName; //FilterBydoaminName object
	FilterBySubject subject; //FilterBySubject object
	FilterByUnread status;  //status of mail whether mail is seen or unseen
	
	public Filter() 
	{
		super();
	}//constructor ends here

	public Filter(FilterByDomainName domainName, FilterBySubject subject, FilterByUnread status) 
	{
		super();
		this.domainName = domainName;
		this.subject = subject;
		this.status = status;
	}//constructor ends here

	public FilterByDomainName getDomainName() {
		return domainName;
	}

	public void setDomainName(FilterByDomainName domainName) {
		this.domainName = domainName;
	}

	public FilterBySubject getSubject() {
		return subject;
	}

	public void setSubject(FilterBySubject subject) {
		this.subject = subject;
	}

	public FilterByUnread getStatus() {
		return status;
	}

	public void setStatus(FilterByUnread status) {
		this.status = status;
	}//getter setter ends here
	
}//class ends here
