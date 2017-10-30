package com.allianz.Services;

import com.allianz.Bean.Filter;
import com.allianz.ServiceImpl.FilterByDomainName;
import com.allianz.ServiceImpl.FilterByUnread;
import com.allianz.ServiceImpl.FilterBySubject;

public interface FilterService
{
	public Filter addFilter(FilterByDomainName filterByDomainName);
	public Filter addFilter(FilterBySubject filterBySubject,String search);
	public Filter addFilter(FilterByUnread filterBySeen);
	public void removeFilter(Filter filter);
	
}//class ends here
