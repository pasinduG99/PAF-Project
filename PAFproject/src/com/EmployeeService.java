package com;


import model.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;


@Path("/Employee")

public class EmployeeService {
	
	Employee pay = new Employee();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayments()
	{
	return pay.viewemployee();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	
	 public String insertItem(
		        @FormParam("Employee_ID") String EmployeeID, 
		        @FormParam("Employee_name") String Employeename, 
		        @FormParam("Salary") String Salary, 
		        @FormParam("Contact_number")String Contactnumber,
		        @FormParam("Adress") String Adress, 
		        @FormParam("Nic") String Nic) { 

		            String output = pay.insertemployee(EmployeeID,Employeename, Salary, Contactnumber, Adress, Nic); 
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
        String emp_id = cardObject.get("emp_id").getAsString(); 
        String emp_name = cardObject.get("emp_name").getAsString(); 
        String salary = cardObject.get("salary").getAsString(); 
        String contact = cardObject.get("contact").getAsString(); 
        String adress = cardObject.get("adress").getAsString();  
		String nic = cardObject.get("nic").getAsString(); 
          

        String output = pay.updateemployee(emp_id, emp_name, salary, contact, adress, nic); 

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
        String output = pay.deleteemployee(itemID); 

        return output; 
    }	

}