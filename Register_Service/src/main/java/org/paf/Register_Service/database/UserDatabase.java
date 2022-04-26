package org.paf.Register_Service.database;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import org.paf.Register_Service.Model.User;

public class UserDatabase {
	
	public Connection connect() {
		
			Connection con = null;
			try
			{
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample_db",
					"root", "root");
			}
			catch (Exception e)
			{
				System.out.println("not connect");
				e.printStackTrace();
			
			}
			return con;
	}
	
	public List<User> allUsers() {
		
		List<User> users = new ArrayList<User>();
		
		try {
			Connection con = connect();
			if (con == null) {
				users=null;
				return users;
			}
		
			String query = "select * from new_table";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				
				
				int id = rs.getInt("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");
				int phone = rs.getInt("phone");
				String address = rs.getString("address");
				
				User user = new User(id,firstName,lastName,email,phone,address);
				users.add(user);
				
			}
			con.close();
			
			
		} catch (Exception e) {
			users=null;
			System.err.println(e.getMessage());
		}
		return users;
	}
	public User getUser(int id) {

		User user =null;	
		try {
			Connection con = connect();
			if (con == null) {
				user=null;
				return user;
			}
		
			String query = "select * from new_table";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				
				if(rs.getInt("id")==id) {
					id = rs.getInt("id");
					String firstName = rs.getString("firstName");
					String lastName = rs.getString("lastName");
					String email = rs.getString("email");
					int phone = rs.getInt("phone");
					String address = rs.getString("address");
					
					user = new User(id,firstName,lastName,email,phone,address);
					
				}else {
					user = new User();
				}
				
				
				
				
			}
			con.close();
			
			
		} catch (Exception e) {
			user=null;
			System.err.println(e.getMessage());
		}
		return user;
	}
	
	public String addUser(User user){
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = "insert into new_table(`id`,`firtName`,`lastName`,`email`,`phone`,`address`)"
					+ " values (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, user.getId());
			preparedStmt.setString(2, user.getFirstName());
			preparedStmt.setString(3, user.getLastName());
			preparedStmt.setString(4, user.getEmail());
			preparedStmt.setInt(5, user.getPhone());
			preparedStmt.setString(6, user.getAddress());

			// execute the statement
			preparedStmt.execute();
			con.close();
			
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			return "Inserted successfully";
		}
		return "Inserted successfully";
		
	}

	public String updateUser(int id) {
		User user = new User();
		user.setId(id);
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = "UPDATE new_table SET firtName=?,lastName=?,email=?,phone=?,address=? WHERE id=?";
					

			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			
			preparedStmt.setString(1, user.getFirstName());
			preparedStmt.setString(2, user.getLastName());
			preparedStmt.setString(3, user.getEmail());
			preparedStmt.setInt(4, user.getPhone());
			preparedStmt.setString(5, user.getAddress());

			// execute the statement
			preparedStmt.execute();
			con.close();
			
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			return "update successfully";
		}
		return "update successfully";
		
	}
	public String deleteUser(int id) {
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from new_table where id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, id);
			// execute the statement
			preparedStmt.execute();
			con.close();
			
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			return "Error while deleting the payment.";
		}
		return "Deleted successfully";
		
	}
}
