/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatpoint.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("/hello")
public class Hello {
    // This method is called if HTML and XML is not requested  
/*
    @GET
    @Path("/plain")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello Jersey Plain";
    }
    // This method is called if XML is requested  
*/
	    // This method is called if HTML and XML is not requested  
	   public static String Email = "";
	    @POST
	    @Path("/login")
	    @Produces(MediaType.TEXT_PLAIN)
	    public Response login(@FormParam("email") String email, @FormParam("password") String password) throws URISyntaxException {
	    	Email = email;
	    	try{  
	    		Class.forName("com.mysql.jdbc.Driver");  
	    		Connection con=DriverManager.getConnection(  
	    		"jdbc:mysql://localhost:3306/Information","root","prashantyadav");  
	    		//here sonoo is database name, root is username and password  
	    		Statement stmt=con.createStatement();  
	    		
	    		ResultSet rs=stmt.executeQuery("select Email,Password from Adduser");  
	    		while(rs.next())  
	    		{
	    			if(email.equals(rs.getString(1)) && password.equals(rs.getString(2)))
	    			{
	    				URI location = new URI("http://localhost:8080/RestJersey/Homepage.html");
	    		    	return Response.seeOther(location).build(); 	
	    			}
	    			
	    			
	    			
	    		}
	    		con.close();  
	    		}catch(Exception e){ System.out.println(e);}  
	    	
			URI location = new URI("http://localhost:8080/RestJersey/index.html");
	    	return Response.seeOther(location).build();		
	    	
	        
	    }
	    
	    
	    @POST
	    @Path("/signup")
	    @Produces(MediaType.TEXT_PLAIN)
	    public Response signup(@FormParam("name") String name,@FormParam("email") String email, @FormParam("password") String password ) throws URISyntaxException {
	    	try{  
	    		Class.forName("com.mysql.jdbc.Driver");  
	    		Connection con=DriverManager.getConnection(  
	    		"jdbc:mysql://localhost:3306/Information","root","prashantyadav");  
	    		//here sonoo is database name, root is username and password  
	    	    
	    		PreparedStatement ps=con.prepareStatement("insert into Adduser values(?,?,?)");  
	    	
	    		ps.setString(1,name);  
	    		ps.setString(2,email);  
	    		ps.setString(3,password);  
	    		ps.executeUpdate();  	
	    		
	    		con.close();  
	    		}catch(Exception e){ System.out.println(e);}  
	    		  
	    	URI location = new URI("http://localhost:8080/RestJersey/index.html");
	    	return Response.seeOther(location).build(); 
	    	
	        
	    }
	    
	    @GET
	    @Path("/viewall")
	    @Produces(MediaType.TEXT_HTML)
	    public String viewall() throws URISyntaxException {
	    	String output = "";
	    	try{  
	    		Class.forName("com.mysql.jdbc.Driver");  
	    		Connection con=DriverManager.getConnection(  
	    		"jdbc:mysql://localhost:3306/Information","root","prashantyadav");  
	    		//here sonoo is database name, root is username and password  
	    		Statement stmt=con.createStatement();  
	    		ResultSet rs=stmt.executeQuery("select * from Addcomments"); 
	    		output="<html><body>";
	    		while(rs.next())  
	    		{
	    			output += "<p>"+rs.getString(1) + " : " + rs.getString(2) + "</p>";
	    		}
	    		output+="</body></html>";
	    		con.close();
	    		return output;
	    		}catch(Exception e){ System.out.println(e);}  
	    		  
//	    	URI location = new URI("http://localhost:8080/RestJersey/index.html");
//	    	return Response.seeOther(location).build(); 
	    	
	    	return null;
	        
	    }
	    @GET
	    @Path("/YourComments")
	    @Produces(MediaType.TEXT_PLAIN)
	    public Response viewYourComments() throws URISyntaxException {
	    	String output = "";
	    	String name="";
	    	
	    	
	    	
	    	try{  
	    		Class.forName("com.mysql.jdbc.Driver");  
	    		Connection con=DriverManager.getConnection(  
	    		"jdbc:mysql://localhost:3306/Information","root","prashantyadav");  
	    	
	    		Statement stmt=con.createStatement();
	    		
//	    		String query = "select name from User where email ="+Email;
//	    		ResultSet rs=stmt.executeQuery(query); 
	    			
	    		String query = "select name from Adduser where email =?";
	    		PreparedStatement ps = con.prepareStatement(query);
	    		ps.setString(1,Email);
	    		ResultSet rs = ps.executeQuery();
	            
	    		while(rs.next())  
	    		{
	    			
	    			name = rs.getString(1);
	    		}
	    	
	    		ResultSet rs1=stmt.executeQuery("select * from Addcomments where Name='"+name+"'");
	    		output="<html><body>";
	    		while(rs1.next())  
	    		{
	    			output += "<p>"+rs1.getString(1) + " : " + rs1.getString(2) + "</p>";
	    		}
	    		output+="</body></html>";
	    		con.close();
	    		return Response.status(200).entity(output).build();	
	    		}catch(Exception e){ System.out.println(e);}  

	    	return null;
	    }
	    @POST
	    @Path("/AddComment")
	    @Produces(MediaType.TEXT_PLAIN)
	    public Response AddComment(@FormParam("comments") String comments ) throws URISyntaxException {
	    	String name ="";
	    	try{  
	    		Class.forName("com.mysql.jdbc.Driver");  
	    		Connection con=DriverManager.getConnection(  
	    		"jdbc:mysql://localhost:3306/Information","root","prashantyadav");  
	    		String query = "select name from Adduser where email =?";
	    		PreparedStatement ps = con.prepareStatement(query);
	    		ps.setString(1,Email);
	    		ResultSet rs = ps.executeQuery();
	    	 		while(rs.next())  
	    		{
	    			name = rs.getString(1);
	    		}
	    		//System.out.println(name);
	    		PreparedStatement ps1=con.prepareStatement("insert into Addcomments values(?,?)");  
	    		
	    		ps1.setString(1,name);  
	    		ps1.setString(2,comments);  
	    		  
	    		ps1.executeUpdate();  	
	    		
	    		con.close();  
	    		}catch(Exception e){ System.out.println(e);}  
	    		  
	    	URI location = new URI("http://localhost:8080/RestJersey/Homepage.html");
	    	return Response.seeOther(location).build(); 
	    	
	        
	    }    

}

	    
	   /* 
    @GET
    @Path("/xml")
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }

    // This method is called if HTML is requested  
    @GET
    @Path("/html")
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
                + "<body><h1>" + "Hello Jersey HTML" + "</h1></body>" + "</html> ";
    }
}
*/