package com;


import model.Payment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;


@Path("/Payments")

public class PaymentService {
	
	Payment pay = new Payment();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayments()
	{
	return pay.readPayments();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("name") String name,
	@FormParam("nic") String nic,
	@FormParam("description") String description,
	@FormParam("amount") String amount)
	{
	String output = pay.insertPayment(name, nic, description, amount);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String paymentData)
	{
	//Convert the input string to a JSON object
	JsonObject payObject = new JsonParser().parse(paymentData).getAsJsonObject();
	//Read the values from the JSON object
	String invoiceNo = payObject.get("invoiceNo").getAsString();
	String name = payObject.get("name").getAsString();
	String nic = payObject.get("nic").getAsString();
	String description = payObject.get("description").getAsString();
	String amount = payObject.get("amount").getAsString();
	String output = pay.updatePayment(invoiceNo, name, nic, description, amount);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String paymentData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());
	//Read the value from the element <itemID>
	String invoiceNo = doc.select("invoiceNo").text();
	String output = pay.deletePayment(invoiceNo);
	return output;
	}
	
	
	
	
	

}
