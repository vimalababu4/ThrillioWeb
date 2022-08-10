package thrillio.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import thrillio.Managers.UserManager;
import thrillio.a.DataStore;
import thrillio.constants.BookGenre;
import thrillio.constants.Gender;
import thrillio.entities.User;

public class UserDao {
	public List<User> getUsers() {
		return DataStore.getUsers();
	}

	public User getUser(long userId) {
		User user=null;
		try {
		
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?useSSL=false","root", "password");
				Statement stmt = conn.createStatement();){
			String query= "Select * from User where id=" + userId;
			ResultSet rs= stmt.executeQuery(query);
			while(rs.next()) {
				long id=rs.getLong("id");
				String email= rs.getString("email");
				String password= rs.getString("password");
				String firstName=rs.getString("first_name");
				String  lastName=rs.getString("last_name");
				int genderId=rs.getInt("gender_id");
				Gender gender= Gender.values()[genderId];
				String userType=rs.getString("user_type_id");
				Date createdDate = rs.getDate("created_date");
	    		
	    		user= UserManager.getInstance().createUser(id, email, password, firstName, lastName, gender, userType);
			}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return user;

}


	public long authenticate(String email, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?useSSL=false", "root", "abhIruchI*123$");
				Statement stmt = conn.createStatement();) {	
			String query = "Select id from User where email = '" + email + "' and password = '" + password + "'";
			System.out.println("query: " + query);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				return rs.getLong("id");				
	    	}			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return -1;
	}

}
