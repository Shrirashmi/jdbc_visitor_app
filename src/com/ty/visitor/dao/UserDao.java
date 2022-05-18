package com.ty.visitor.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ty.visitor.dto.User;
import com.ty.visitor.util.ConnectionObject;
import com.ty.visitor.util.TestAES;

import static com.ty.visitor.util.AppConstants.SECRET_KEY;
public class UserDao {
	
	//private String secretKey = "tyapp";
	public int saveUser(User user)
	{
		String sql = "INSERT INTO user VALUES(?,?,?,?,?,?)";
		Connection connection = ConnectionObject.getConnection();
		
		try 
		{
			String encryption = TestAES.encrypt(user.getPassword(), SECRET_KEY);
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, encryption);
			preparedStatement.setLong(5, user.getPhone());
			
			return preparedStatement.executeUpdate();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		finally {
			if(connection!=null) {
				try {
					connection.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return 0;
	}


public int updateUser(int id, User user) {
	String sql = "UPDATE user SET name=?, email=?, password=?, mobile=? WHERE id=?";
	Connection connection = ConnectionObject.getConnection();
	try {
		//establish statement         used PreparedStatement for dynamic query
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1, user.getName());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getPassword());
		preparedStatement.setLong(4, user.getPhone());
		preparedStatement.setInt(5, user.getId());
		//User user = new user(id, name, email, password, mobile);
		if(user.getId()==id) 
		{
		//execute query
		return preparedStatement.executeUpdate();
		}

	}
	catch (SQLException e)
	{
		e.printStackTrace();
	}
	finally 
	{
	//close connection
	try 
	{
		connection.close();
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
	System.out.println("Data updated using Prepared Statement");
	}
	return 0;
	}


public User validateUser(String email, String password) {
	String sql = "SELECT * FROM user WHERE email=? and password=?";
	Connection connection = ConnectionObject.getConnection();
	
	try 
	{
		String encryption = TestAES.encrypt(password, SECRET_KEY);
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, email);
		preparedStatement.setString(2, encryption);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			User user = new User();
			user.setId(resultSet.getInt(1));
			user.setName(resultSet.getString(2));
			user.setEmail(resultSet.getString(3));
			user.setPhone(resultSet.getLong(5));
			return user;
		}
	} 
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
	finally {
		try 
		{
			connection.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	return null;
}


public int deleteUserById(int id) {
	String sql = "DELETE FROM user WHERE Id=?";
	Connection connection = ConnectionObject.getConnection();
	try
	{
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		
		return preparedStatement.executeUpdate();
	}
	catch(SQLException e) 
	{
		e.printStackTrace();
	}	
	finally {
		try 
		{
			if(connection!=null)
			{
			connection.close();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	return 0;
	}
}

	
	
	
	

	

	