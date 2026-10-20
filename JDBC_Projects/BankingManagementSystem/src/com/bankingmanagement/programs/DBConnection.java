package com.bankingmanagement.programs;

import java.sql.*;

public class DBConnection 
{
	static final String url = "jdbc:mysql://localhost:3307/Banking_Management_Jdbc";
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
