package model;

import java.sql.*;

public class Order 
{ //A common method to connect to the DB
	private Connection connect() 
	{ 
			Connection con = null; 
			
			try
			{ 
				Class.forName("com.mysql.jdbc.Driver"); 
 
				//Provide the correct details: DBServer/DBName, username, password 
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db_connect", "root", ""); 
			} 
			catch (Exception e) 
			{e.printStackTrace();}
			
			return con; 
	} 
	
	public String insertOrder(String code, String id, String name, String email, String address, String total) 
	{ 
		String output = ""; 
		
		try
		{ 
			Connection con = connect();
			
			if (con == null) 
			{return "Error while connecting to the database for inserting."; }
			
			// create a prepared statement
			String query = " insert into orders (`orderID`,`orderCode`,`customerID`,`customerName`,`customerEmail`,`customerAddress`,`orderTotalAmount`)"
							+ " values (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, code); 
			 preparedStmt.setString(3, id);
			 preparedStmt.setString(4, name);  
			 preparedStmt.setString(5, email);
			 preparedStmt.setString(6, address);
			 preparedStmt.setDouble(7, Double.parseDouble(total));
			 
			 // execute the statement3
			 preparedStmt.execute(); 
			 con.close();
			 
			 output = "Inserted successfully"; 
		} 
		catch (Exception e) 
		{ 
			 output = "Error while inserting the order."; 
			 System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
	
	public String readOrders() 
	{ 
		String output = ""; 
		
		try
		{ 
			Connection con = connect();
			
			if (con == null) 
			{return "Error while connecting to the database for reading."; }
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Order Code</th><th>Customer ID</th>" +
					"<th>Customer Name</th>" + "<th>Customer Email</th>" +
					"<th>Customer Address</th>" + "<th>Order Total Amount</th>" +
					"<th>Update</th><th>Remove</th></tr>"; 
 
			String query = "select * from order"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) 
			{ 
				 String orderID = Integer.toString(rs.getInt("orderID")); 
				 String orderCode = rs.getString("orderCode"); 
				 String customerID = rs.getString("customerID");
				 String customerName = rs.getString("customerName");
				 String customerEmail = rs.getString("customerEmail");
				 String customerAddress = rs.getString("customerAddress");
				 String orderTotalAmount = Double.toString(rs.getDouble("orderTotalAmount")); 
				 
				 
				 // Add into the html table
				 output += "<tr><td>" + orderCode + "</td>"; 
				 output += "<td>" + customerID + "</td>"; 
				 output += "<td>" + customerName + "</td>"; 
				 output += "<td>" + customerEmail + "</td>";
				 output += "<td>" + customerAddress + "</td>"; 
				 output += "<td>" + orderTotalAmount + "</td>"; 
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						 	+ "<td><form method='post' action='orders.jsp'>"
						 	+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						 	+ "<input name='orderID' type='hidden' value='" + orderID 
						 	+ "'>" + "</form></td></tr>"; 
			}
			
			con.close();
			
			// Complete the html table
			output += "</table>"; 
		} 
		catch (Exception e) 
		{ 
			output = "Error while reading the orders."; 
			System.err.println(e.getMessage()); 
		}
		
		return output; 
	} 
	
	public String updateOrder(String ID, String code, String id, String name, String email, String address, String total)
	{ 
		String output = ""; 
		
		try
		{ 
			Connection con = connect(); 
			
			 if (con == null) 
			 {return "Error while connecting to the database for updating."; }
			 
			 // create a prepared statement
			 String query = "UPDATE orders SET orderCode=?,customerID=?,customerName=?,customerEmail=?,customerAddress=?,orderTotalAmount=? WHERE orderID=?";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setString(1, code); 
			 preparedStmt.setString(2, id);
			 preparedStmt.setString(3, name);
			 preparedStmt.setString(4, email);
			 preparedStmt.setString(5, address);
			 preparedStmt.setString(6, total); 
			 preparedStmt.setInt(5, Integer.parseInt(ID));
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 
			 output = "Updated successfully"; 
		} 
		catch (Exception e) 
		{ 
			output = "Error while updating the order."; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
	
	public String deleteOrder(String orderID) 
	{ 
		String output = ""; 
		
		try
		{ 
			Connection con = connect(); 
			
			 if (con == null) 
			 {return "Error while connecting to the database for deleting."; } 
			 
			 // create a prepared statement
			 String query = "delete from orders where orderID=?";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(orderID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 
			 output = "Deleted successfully"; 
		} 
		catch (Exception e) 
		{ 
			output = "Error while deleting the order."; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	} 
}