package com.allianz.Main;

import com.allianz.Bean.Filter;
import com.allianz.ServiceImpl.FilterBySubject;
import com.allianz.ServiceImpl.FilterImpl;

public class Test
{ 

	public static void main(String[] args) 
	{
		FilterImpl filterImpl = new FilterImpl();
		Filter filter = filterImpl.addFilter(new FilterBySubject(), "Allianz");
		filterImpl.removeFilter(filter);
		
	}

}
