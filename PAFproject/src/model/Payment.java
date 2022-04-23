package model;

import java.sql.*;

public class Payment {
	
	private Connection connect()
	{
	Connection con = null;
	try
	{
	Class.forName("com.mysql.jdbc.Driver");
	
	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paymentdb",
			"root", "Casio123");
	}
	catch (Exception e)
	{e.printStackTrace();}
	return con;
	}
	public String insertPayment(String name, String nic, String description, String amount)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for inserting."; }
	
	String query = " insert into payments(`invoiceNo`,`name`,`nic`,`description`,`amount`)" 
	+ " values (?, ?, ?, ?, ?)";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	preparedStmt.setInt(1, 0);
	preparedStmt.setString(2, name);
	preparedStmt.setString(3, nic);
	preparedStmt.setString(4, description);
	preparedStmt.setDouble(5, Double.parseDouble(amount));
	
	
	// execute the statement
	
	preparedStmt.execute();
	con.close();
	output = "Inserted successfully";
	}
	catch (Exception e)
	{
	output = "Error while inserting the payment.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	public String readPayments()
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for reading."; }
	// Prepare the html table to be displayed
	output = "<table border='1'><tr><th>Name</th><th>NIC</th>" +
	"<th>Description</th>" +
	"<th>Amount</th>" +
	"<th>Update</th><th>Remove</th></tr>";
	String query = "select * from payments";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(query);
	// iterate through the rows in the result set
	while (rs.next())
	{
	String invoiceNo = Integer.toString(rs.getInt("invoiceNo"));
	String name = rs.getString("name");
	String nic= rs.getString("nic");
	String description = rs.getString("description");
	String amount = Double.toString(rs.getDouble("amount"));
	
	// Add into the html table
	
	output += "<td>" + name + "</td>";
	output += "<td>" + nic + "</td>";
	output += "<td>" + description + "</td>";
	output += "<td>" + amount + "</td>";
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	+ "<td><form method='post' action='payment.jsp'>"
	+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
	+ "<input name='invoiceNo' type='hidden' value='" + invoiceNo
	+ "'>" + "</form></td></tr>";
	}
	con.close();
	// Complete the html table
	output += "</table>";
	}
	catch (Exception e)
	{
	output = "Error while reading the payments.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	public String updatePayment(String invoiceNo ,String name, String nic, String description, String amount)
	
	{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for updating."; }
		// create a prepared statement
		String query = "UPDATE payments SET name=?,nic=?,description=?,amount=? WHERE invoiceNo=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setString(1, name);
		preparedStmt.setString(2, nic);
		preparedStmt.setString(3, description);
		preparedStmt.setDouble(4, Double.parseDouble(amount));
		preparedStmt.setInt(5, Integer.parseInt(invoiceNo));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Updated successfully";
		}
		catch (Exception e)
		{
		output = "Error while updating the payment.";
		System.err.println(e.getMessage());
		}
		return output;
		}
		public String deletePayment(String invoiceNo)
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for deleting."; }
		// create a prepared statement
		String query = "delete from payments where invoiceNo=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		preparedStmt.setInt(1, Integer.parseInt(invoiceNo));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Deleted successfully";
		}
		catch (Exception e)
		{
		output = "Error while deleting the payment.";
		System.err.println(e.getMessage());
		}
		return output;
		}
		
}
