package com.ty.visitor.dao;
import static com.ty.visitor.util.AppConstants.SECRET_KEY;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ty.visitor.dto.Visitor;
import com.ty.visitor.util.ConnectionObject;
import com.ty.visitor.util.TestAES;
public class VisitorDao {
	int resultCount;

	public int saveVisitor(Visitor visitor)
	{
		Connection connection = ConnectionObject.getConnection();
		String sql = "INSERT INTO visitor VALUES(?,?,?,?,?,?,?,?)";
		
		try {
			String encryption1 = TestAES.encrypt(visitor.getName(), SECRET_KEY);
			String encryption2 = TestAES.encrypt(visitor.getEmail(), SECRET_KEY);
			String encryption3 = TestAES.encrypt(visitor.getPhone() + "", SECRET_KEY);
			PreparedStatement preparedStatement =  connection.prepareStatement(sql);
			preparedStatement.setInt(1, visitor.getId());
			preparedStatement.setString(2, encryption1);
			preparedStatement.setString(3, encryption2);
			preparedStatement.setString(4, encryption3);
			preparedStatement.setInt(5, visitor.getAge());
			preparedStatement.setString(5, visitor.getGender());
			preparedStatement.setString(5, visitor.getDateTime());
			
			resultCount = preparedStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				connection.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultCount;
	}
	

}
