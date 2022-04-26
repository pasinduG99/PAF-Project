package model;

import java.sql.*;

public class Employee {
	
	private Connection connect() {
		Connection con = null;
		try	{
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supplymanagement",
					"root", "rusiru");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public String insertemployee(String EmployeeID, String Employeename, String Salary, String Adress, String Nic, String contact) { 
        String output = ""; 
        try { 
            Connection con = connect(); 
            if (con == null) {
                return "Error while connecting to the database for inserting."; 
            } 

            // create a prepared statement
            String query = " insert into employee " +
                            "(`Employee_ID`,`Employee_name`,`Salary`,`Contact`,`Adress`,`Nic`)"
                            + " values (?, ?, ?, ?, ?, ?)"; 

            PreparedStatement preparedStmt = con.prepareStatement(query); 

            // binding values
            preparedStmt.setInt(1, Integer.parseInt(EmployeeID)); 
            preparedStmt.setString(2, Employeename); 
            preparedStmt.setDouble(3, Double.parseDouble(Salary));
            preparedStmt.setString(4, contact);  
            preparedStmt.setString(5, Adress); 
            preparedStmt.setString(6, Nic); 

            // execute the statement
            preparedStmt.execute(); 
            con.close(); 
            output = "Inserted successfully"; 

        } catch (Exception e) { 
            output = "Error while inserting the employee details.  " + e.getMessage(); 
            System.err.println(e.getMessage()); 
        } 
        
        return output; 
    }
	
	public String viewemployee() {
		String output = ""; 

		try { 

			Connection con = connect(); 
			if (con == null) {
				return "Error while connecting to the database for reading."; 
			} 

			// Prepare the html table to be displayed
			output = "<table border='1'><tr>"+
			"<th>employee ID</th>"+
			"<th>employee Name</th>" +
			"<th>salary</th>" + 
			"<th>contatact number</th>" +
			"<th>adress</th>" + 
			"<th>nic</th>" +
			"<th>Update</th><th>Remove</th></tr>"; 
	 
			String query = "select * from employee"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 

			// iterate through the rows in the result set
			while (rs.next()) { 

				String Employee_ID = rs.getString("Employee_ID"); 
				String EmployeeName = rs.getString("Employee_name"); 
				String Salary = rs.getString("Salary"); 
				String Contactnumber = rs.getString("Contact"); 
				String Adress = rs.getString("Adress"); 
				String Nic = rs.getString("Nic"); 

				// Add into the html table
				output += "<tr><td>" +Employee_ID  + "</td>"; 
				output += "<td>" + EmployeeName + "</td>"; 
				output += "<td>" + Salary + "</td>"; 
				output += "<td>" + Contactnumber + "</td>";
				output += "<td>" + Adress + "</td>"; 
				output += "<td>" + Nic + "</td>";				

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'"+
				"class='btn btn-secondary'></td>"
				+ "<td><form method='post' action='items.jsp'>"
				+ "<input name='btnRemove' type='submit' value='Remove'"+ 
				"class='btn btn-danger'>"
				+ "<input name='itemID' type='hidden' value='"  
				+ "'>" + "</form></td></tr>"; 
			} 
			con.close(); 

			// Complete the html table
			output += "</table>"; 

		} catch (Exception e) { 
			output = "Error while reading the employee details."; 
			System.err.println(e.getMessage()); 
		} 

		return output; 
	}
	
	public String updateemployee(String EmployeeID, String Employeename, String Salary, String contact, String Adress, String Nic ) { 

        String output = ""; 

        try { 

            Connection con = connect(); 

            if (con == null) {
                return "Error while connecting to the database for updating."; 
            } 

            // create a prepared statement
            String query = "UPDATE employee SET Employee_ID=?, Employee_name=?, Salary=?, Contact=?, Adress=?, Nic=? WHERE Employee_ID=?"; 
            PreparedStatement preparedStmt = con.prepareStatement(query); 

            // binding values
            preparedStmt.setInt(1, Integer.parseInt(EmployeeID)); 
            preparedStmt.setString(2, Employeename); 
            preparedStmt.setDouble(3, Double.parseDouble(Salary));
            preparedStmt.setString(4, contact);  
            preparedStmt.setString(5, Adress); 
            preparedStmt.setString(6, Nic); 
            preparedStmt.setInt(7, Integer.parseInt(EmployeeID));
            
            // execute the statement
            preparedStmt.execute(); 
            con.close(); 
            output = "Updated successfully"; 

        } catch (Exception e) { 
            output = "Error while updating the employee details.  " + e.getMessage(); 
            System.err.println(e.getMessage()); 
        } 

        return output; 
    }
	
	public String deleteemployee(String empID) { 

        String output = ""; 

        try { 

            Connection con = connect(); 

            if (con == null) {
                return "Error while connecting to the database for deleting."; 
            }

            // create a prepared statement
            String query = "delete from employee where Employee_ID=?"; 
            PreparedStatement preparedStmt = con.prepareStatement(query); 

            // binding values
            preparedStmt.setInt(1, Integer.parseInt(empID)); 

            // execute the statement
            preparedStmt.execute(); 
            con.close(); 
            output = "Deleted successfully"; 

        } catch (Exception e) { 
            output = "Error while deleting the employee details."; 
            System.err.println(e.getMessage()); 
        } 

        return output; 
    }
}