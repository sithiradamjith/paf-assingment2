$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validateHospitalForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidAppIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "hospitalApi",
		type : t,
		data : $("#formhospital").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onHospitalSaveComplete(response.responseText, status);
		}
	});
}); 

function onHospitalSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidAppIDSave").val("");
	$("#formhospital")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidAppIDSave").val($(this).closest("tr").find('#hidAppIDUpdate').val());     
	$("#hosp_name").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#phn_no").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#hosp_address").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#hosp_type").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#description").val($(this).closest("tr").find('td:eq(4)').text()); 

});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "hospitalApi",
		type : "DELETE",
		data : "hosp_id=" + $(this).data("host"),
		dataType : "text",
		complete : function(response, status)
		{
			onHospitalDeletedComplete(response.responseText, status);
		}
	});
});

function onHospitalDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validateHospitalForm() {  
	// NAME  
	if ($("#hosp_name").val().trim() == "")  {   
		return "Insert fullName.";  
		
	} 
	
	 // MOBILE  
	if ($("#phn_no").val().trim() == "")  {   
		return "Insert Phone No.";  
		
	} 
	 
	 // is numerical value  
	var tmpMobile = $("#phn_no").val().trim();  
	if (!$.isNumeric(tmpMobile))  {   
		return "Insert a numerical value for Phone No.";  
		
	}
	 
	 // hospital address 
	if ($("#hosp_address").val().trim() == "")  {   
		return "Insert hospital address.";  
		
	} 
	
	// hospital type  
	if ($("#hosp_type").val().trim() == "")  {   
		return "Insert hospital type.";  
		
	} 
	 
	 
	
	// description  
	if ($("#description").val().trim() == "")  {   
		return "Insert description.";  
		
	} 
	
	 
	 return true; 
	 
}
