package modal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class hospital {

	//A common method to connect to the DB 
		private Connection connect() {
			Connection con = null;
			
			try {
				 Class.forName("com.mysql.cj.jdbc.Driver");
				 //Provide the correct details: DBServer/DBName, username, password 
				 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcare?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

				//For testing          
				 System.out.print("Successfully connected");
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return con; 
		}
		
		public String readhospital() {  
			String output = "";  
			
			try {  
				Connection con = connect();  
				if (con == null)  {   
					return "Error while connecting to the database for reading.";  
				} 

				// Prepare the html table to be displayed   
				output = "<table border='1'><tr><th>Hospital_Name</th>"
						+ "<th>Phone_No</th>"
						+ "<th>Hospital_Address</th>"
						+ "<th>Hospital_Type</th>"
						+"<th>description</th>"
						+ "<th>Update</th><th>Remove</th></tr>";


				  String query = "select * from hospital";   
				  Statement stmt = con.createStatement();   
				  ResultSet rs = stmt.executeQuery(query); 

				  // iterate through the rows in the result set   
				  while (rs.next())   {  

					  	String hosp_id = Integer.toString(rs.getInt("hosp_id"));
						String hosp_name = rs.getString("hosp_name");
						String phn_no = Integer.toString(rs.getInt("phn_no"));
						String hosp_address = rs.getString("hosp_address");
						String hosp_type = rs.getString("hosp_type");
						String description = rs.getString("description");
					  // Add into the html table    

					  output += "<tr><td><input id='hidAppIDUpdate' name='hidAppIDUpdate' type='hidden' value='" + hosp_id + "'>" + hosp_name + "</td>"; 

					  output += "<td>" + phn_no + "</td>";
						output += "<td>" + hosp_address + "</td>";
						output += "<td>" + hosp_type + "</td>";
						output += "<td>" + description + "</td>";
					  
					// buttons     
					  output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
					  		+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-host='"+ hosp_id +"'>"+"</td></tr>";

					} 
				  
				  con.close(); 

				  // Complete the html table   
				  output += "</table>"; 
				}
				catch (Exception e) {  
					output = "Error while reading the hospital.";  
					System.err.println(e.getMessage()); 
				}

				return output;
			}
		
		//Insert hospital
		public String inserthospital(String hosp_name, String phn_no, String hosp_address, String hosp_type, String description) {
			String output = "";

			try {
				Connection con = connect();  

				if (con == null) {
					return "Error while connecting to the database";
				}

				// create a prepared statement   
				String query = " insert into hospital (`hosp_id`,`hosp_name`,`phn_no`,`hosp_address`,`hosp_type`, `description`)"+" values (?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values 
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, hosp_name);
				preparedStmt.setString(3, phn_no);
				preparedStmt.setString(4, hosp_address);
				preparedStmt.setString(5,hosp_type);
				preparedStmt.setString(6, description);
				
				//execute the statement   
				preparedStmt.execute();   
				con.close(); 

				//Create JSON Object to show successful msg.
				String newhospital = readhospital();
				output = "{\"status\":\"success\", \"data\": \"" + newhospital + "\"}";
			}
			catch (Exception e) {  
				//Create JSON Object to show Error msg.
				output = "{\"status\":\"error\", \"data\": \"Error while Inserting hospital.\"}";   
				System.err.println(e.getMessage());  
			} 

			 return output; 
		}
		
		//Update hospital
		public String updatehospital(String hosp_id, String hosp_name, String phn_no, String hosp_address, String hosp_type, String description )  {   
			String output = ""; 
		 
		  try   {   
			  Connection con = connect();
		 
			  if (con == null)    {
				  return "Error while connecting to the database for updating."; 
			  } 
		 
		   // create a prepared statement    
			   String query = "UPDATE hospital SET hosp_name=?,phn_no=?,hosp_address=?,hosp_type=?,description=?WHERE hosp_id=?";
				 
		   PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		   // binding values    
		   preparedStmt.setString(1, hosp_name);
			preparedStmt.setInt(2,Integer.parseInt (phn_no));
			preparedStmt.setString(3, hosp_address);
			preparedStmt.setString(4,hosp_type);
			preparedStmt.setString(5, description);
			preparedStmt.setInt(6, Integer.parseInt(hosp_id));
		   
		 
		   // execute the statement    
		   preparedStmt.execute();    
		   con.close(); 
		 
		   //create JSON object to show successful msg
		   String newhospital = readhospital();
		   output = "{\"status\":\"success\", \"data\": \"" + newhospital + "\"}";
		   }   catch (Exception e)   {    
			   output = "{\"status\":\"error\", \"data\": \"Error while Updating hospital Details.\"}";      
			   System.err.println(e.getMessage());   
		   } 
		 
		  return output;  
		  }
		
		public String deletehospital(String hosp_id) {  
			String output = ""; 
		 
		 try  {   
			 Connection con = connect();
		 
		  if (con == null)   {    
			  return "Error while connecting to the database for deleting.";   
		  } 
		 
		  // create a prepared statement   
		  String query = "DELETE FROM hospital WHERE hosp_id=?"; 
		 
		  PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		  // binding values   
		  preparedStmt.setInt(1, Integer.parseInt(hosp_id));       
		  // execute the statement   
		  preparedStmt.execute();   
		  con.close(); 
		 
		  //create JSON Object
		  String newhospital = readhospital();
		  output = "{\"status\":\"success\", \"data\": \"" + newhospital + "\"}";
		  }  catch (Exception e)  {  
			  //Create JSON object 
			  output = "{\"status\":\"error\", \"data\": \"Error while Deleting hospital.\"}";
			  System.err.println(e.getMessage());  
			  
		 } 
		 
		 return output; 
		 }
}
