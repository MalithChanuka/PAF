package com;

import model.Order; 

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON
import com.google.gson.*; 

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/Orders") 
public class OrderService 
{ 
	Order orderObj = new Order(); 
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readOrders() 
	{ 
		return orderObj.readOrders(); 
	}
	
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertOrder(@FormParam("orderCode") String orderCode, 
							 @FormParam("customerID") String customerID,
							 @FormParam("customerName") String customerName, 
							 @FormParam("customerEmail") String customerEmail, 
							 @FormParam("customerAddress") String customerAddress,
							 @FormParam("orderTotalAmount") String orderTotalAmount) 
	{ 
			 String output = orderObj.insertOrder(orderCode, customerID, customerName, customerEmail, customerAddress, orderTotalAmount); 
			 return output; 
	}
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateOrder(String orderData) 
	{ 
			//Convert the input string to a JSON object 
			 JsonObject orderObject = new JsonParser().parse(orderData).getAsJsonObject(); 
			
			 //Read the values from the JSON object
			 String orderID = orderObject.get("orderID").getAsString(); 
			 String orderCode = orderObject.get("orderCode").getAsString(); 
			 String customerID = orderObject.get("customerID").getAsString(); 
			 String customerName = orderObject.get("customerName").getAsString(); 
			 String customerEmail = orderObject.get("customerEmail").getAsString();
			 String customerAddress = orderObject.get("customerAddress").getAsString();
			 String orderTotalAmount = orderObject.get("orderTotalAmount").getAsString();
			 
			 String output = orderObj.updateOrder(orderID, orderCode, customerID, customerName, customerEmail, customerAddress, orderTotalAmount); 
			 
			 return output; 
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteOrder(String orderData) 
	{ 
			//Convert the input string to an XML document
			 Document doc = Jsoup.parse(orderData, "", Parser.xmlParser()); 
			 
			//Read the value from the element <itemID>
			 String orderID = doc.select("orderID").text(); 
			 
			 String output = orderObj.deleteOrder(orderID); 
			 
			 return output; 
	}

}