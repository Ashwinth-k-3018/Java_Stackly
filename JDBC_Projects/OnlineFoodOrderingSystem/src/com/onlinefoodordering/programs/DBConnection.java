package com.onlinefoodordering.programs;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection 
{
	static final String url = "jdbc:mysql://localhost:3307/food_ordering_db";
	static final String user = "root";
	static final String pass = "Ashwin2312@";
	
	public static Connection getConnection()
	{
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			return DriverManager.getConnection(url,user,pass);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}

}
