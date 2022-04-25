package model;

import java.sql.*;

public class Supply {
	
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
	
	public String insertItem(String supID, String itemName, String qty, String price, String total, String date) { 
        String output = ""; 
        try { 
            Connection con = connect(); 
            if (con == null) {
                return "Error while connecting to the database for inserting."; 
            } 

            // create a prepared statement
            String query = " insert into supplyitems " +
                            "(`ID`,`sup_id`,`item_name`,`qty`,`price`,`total`,`date`)"
                            + " values (?, ?, ?, ?, ?, ?, ?)"; 

            PreparedStatement preparedStmt = con.prepareStatement(query); 

            // binding values
            preparedStmt.setInt(1, 0); 
            preparedStmt.setString(2, supID); 
            preparedStmt.setString(3, itemName); 
            preparedStmt.setInt(4, Integer.parseInt(qty)); 
            preparedStmt.setDouble(5, Double.parseDouble(price)); 
			preparedStmt.setDouble(6, Double.parseDouble(total)); 
            preparedStmt.setString(7, date); 

            // execute the statement
            preparedStmt.execute(); 
            con.close(); 
            output = "Inserted successfully"; 

        } catch (Exception e) { 
            output = "Error while inserting the supply details.  " + e.getMessage(); 
            System.err.println(e.getMessage()); 
        } 
        
        return output; 
    }
	
	public String readPayments() {
		String output = ""; 

		try { 

			Connection con = connect(); 
			if (con == null) {
				return "Error while connecting to the database for reading."; 
			} 

			// Prepare the html table to be displayed
			output = "<table border='1'><tr>"+
			"<th>supplierID</th>"+
			"<th>Item Name</th>" +
			"<th>Quantity</th>" + 
			"<th>Price</th>" +
			"<th>Total</th>" + 
			"<th>Date</th>" +
			"<th>Update</th><th>Remove</th></tr>"; 
	 
			String query = "select * from supplyitems"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 

			// iterate through the rows in the result set
			while (rs.next()) { 

				String supID = rs.getString("sup_id"); 
				String itemName = rs.getString("item_name"); 
				String qty = rs.getString("qty"); 
				String price = rs.getString("price"); 
				String total = rs.getString("total"); 
				String date = rs.getString("date"); 

				// Add into the html table
				output += "<tr><td>" + supID + "</td>"; 
				output += "<td>" + itemName + "</td>"; 
				output += "<td>" + qty + "</td>"; 
				output += "<td>" + price + "</td>";
				output += "<td>" + total + "</td>"; 
				output += "<td>" + date + "</td>";				

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
			output = "Error while reading the supply details."; 
			System.err.println(e.getMessage()); 
		} 

		return output; 
	}
	
	public String updateItem(String ID, String supID, String itemName, String qty, String price, String total, String date) { 

        String output = ""; 

        try { 

            Connection con = connect(); 

            if (con == null) {
                return "Error while connecting to the database for updating."; 
            } 

            // create a prepared statement
            String query = "UPDATE supplyitems SET sup_id=?, item_name=?, qty=?, price=?, total=?, date=? WHERE ID=?"; 
            PreparedStatement preparedStmt = con.prepareStatement(query); 

            // binding values
            preparedStmt.setString(1, supID); 
            preparedStmt.setString(2, itemName); 
            preparedStmt.setInt(3, Integer.parseInt(qty)); 
            preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setDouble(5, Double.parseDouble(total)); 
            preparedStmt.setString(6, date);
            preparedStmt.setString(7, ID);

            // execute the statement
            preparedStmt.execute(); 
            con.close(); 
            output = "Updated successfully"; 

        } catch (Exception e) { 
            output = "Error while updating the supply details."; 
            System.err.println(e.getMessage()); 
        } 

        return output; 
    }
	
	public String deleteItem(String itemID) { 

        String output = ""; 

        try { 

            Connection con = connect(); 

            if (con == null) {
                return "Error while connecting to the database for deleting."; 
            }

            // create a prepared statement
            String query = "delete from supplyitems where ID=?"; 
            PreparedStatement preparedStmt = con.prepareStatement(query); 

            // binding values
            preparedStmt.setInt(1, Integer.parseInt(itemID)); 

            // execute the statement
            preparedStmt.execute(); 
            con.close(); 
            output = "Deleted successfully"; 

        } catch (Exception e) { 
            output = "Error while deleting the supply details."; 
            System.err.println(e.getMessage()); 
        } 

        return output; 
    } 
		
}
