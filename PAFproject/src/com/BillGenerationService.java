package com;

import model.BillGeneration;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;


@Path("/Bills")

public class BillGenerationService {
	BillGeneration bill = new BillGeneration();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBills()
	{
	return bill.readBills();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBill(@FormParam("username") String username,
	@FormParam("ano") String ano,
	@FormParam("address") String address,
	@FormParam("units") String units,
	@FormParam("amount") String amount)
	{
	String output = bill.insertBill(username, ano, address, units, amount);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBill(String billData)
	{
	//Convert the input string to a JSON object
	JsonObject billObject = new JsonParser().parse(billData).getAsJsonObject();
	//Read the values from the JSON object
	String billno = billObject.get("billno").getAsString();
	String username = billObject.get("username").getAsString();
	String ano = billObject.get("ano").getAsString();
	String address = billObject.get("address").getAsString();
	String units = billObject.get("units").getAsString();
	String amount = billObject.get("amount").getAsString();
	String output = bill.updateBill(billno, username, ano, address, units,  amount);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String billData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(billData, "", Parser.xmlParser());
	//Read the value from the element <billno>
	String billno = doc.select("billno").text();
	String output = bill.deleteBill(billno);
	return output;
	}

}
