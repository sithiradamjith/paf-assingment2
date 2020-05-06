<%@page import="modal.hospital"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>hospital Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="components/jquery-3.2.1.min.js"></script>
<script src="components/main.js"></script>
</head>
<body>
<div class="container"> 
		<div class="row">  
		
			<div class="col-8">       
				<h1 class="m-3">hospital Management</h1>        
				
				<form id="formhospital" name="formhospital" method="post" action="hospital.jsp">  
					Hospital Name:  
					<input id="hosp_name" name="hosp_name" type="text" class="form-control form-control-sm">  
					
					<br> 
					Phone Number:  
					<input id="phn_no" name="phn_no" type="text" class="form-control form-control-sm">  
					
					<br>
					 Hospital Address:  
					 <input id="hosp_address" name="hosp_address" type="text" class="form-control form-control-sm">  
					 
					 <br> 
					 Hospital Type:  
					 <input id="hosp_type" name="hosp_type" type="text" class="form-control form-control-sm">  
					 
					 <br> 
					 Description:  
					 <input id="description" name="description" type="text" class="form-control form-control-sm">  
					 
					 <br> 
					 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">  
					 <input type="hidden" id="hidAppIDSave" name="hidAppIDSave" value=""> 
					 
				</form> 
				
				<div id="alertSuccess" class="alert alert-success"></div>  
				<div id="alertError" class="alert alert-danger"></div> 
				
				<br>  
				<div id="divItemsGrid">   
					<%    
						hospital appObj = new hospital();
						out.print(appObj.readhospital());   
					%>  
					
				</div> 
				  
 			</div>
 		 
 		</div>    
 		
 
	</div> 

</body>

</html>