package com;


import model.Supply;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;


@Path("/Supply")

public class SupplyService {
	
	Supply pay = new Supply();
	
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
	
	 public String insertItem(
		        @FormParam("supID") String supID, 
		        @FormParam("itemName") String itemName, 
		        @FormParam("qty") String qty, 
		        @FormParam("price") String price,
		        @FormParam("total") String total, 
		        @FormParam("date") String date) { 

		            String output = pay.insertItem(supID, itemName, qty, price, total, date); 
		            return output; 
		        }

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	 public String updateItem(String supDet) { 

        //Convert the input string to a JSON object 
        JsonObject cardObject = new JsonParser().parse(supDet).getAsJsonObject(); 

        //Read the values from the JSON object
        String ID = cardObject.get("ID").getAsString(); 
        String supID = cardObject.get("supID").getAsString(); 
        String itemName = cardObject.get("itemName").getAsString(); 
        String qty = cardObject.get("qty").getAsString(); 
        String price = cardObject.get("price").getAsString();  
		String total = cardObject.get("total").getAsString(); 
        String date = cardObject.get("date").getAsString();  

        String output = pay.updateItem(ID, supID, itemName, qty, price, total, date); 

        return output; 
    }
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String deleteItem(String supDet) { 

        //Convert the input string to an XML document
        Document doc = Jsoup.parse(supDet, "", Parser.xmlParser()); 
    
        //Read the value from the element <itemID>
        String itemID = doc.select("itemID").text(); 
        String output = pay.deleteItem(itemID); 

        return output; 
    }	

}
