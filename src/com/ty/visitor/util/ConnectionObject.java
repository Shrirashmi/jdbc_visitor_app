package com.ty.visitor.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static com.ty.visitor.util.AppConstants.*;
public class ConnectionObject {
	
	static {
		//bcz Driver class needs to be loaded only once. 
		try {
			Class.forName(DRIVER);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		try 
		{
			//Connection connection = DriverManager.getConnection(Driver);
			//variable used only once in pgm, dont declare the variable.   rightclick variable , refactor --> inline
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public String getDriver() {
		return DRIVER;
	}


	public String getUrl() {
		return URL;
	}


	public String getUser() {
		return USER;
	}


	public String getPassword() {
		return PASSWORD;
	}
	
}

