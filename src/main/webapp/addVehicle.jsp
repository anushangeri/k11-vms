<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginVMSCSS.jsp"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.collections.IteratorUtils"%>
<%@page import="com.google.gdata.data.spreadsheet.CellEntry"%>
<%@page import="com.google.gdata.data.spreadsheet.Cell"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="net.javatutorial.entity.*"%>
<%@page import="net.javatutorial.DAO.*"%>
<%@page import="java.util.*"%>
<%@page import="java.time.*"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
<style type="text/css"></style>
<script type="text/javascript"
	src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
	
<script>
	function validateForm() {
		var tempAsStr = document.forms["addVehicle"]["temperature"].value;
		var tempAsInt = parseFloat(tempAsStr);
		if (isNaN(tempAsInt) || tempAsInt < 36 || tempAsInt > 37.5)  {
			alert("COVID Alert: Invalid temperature or temperature is high. Please go home if you are sick.");
			return false;
		}
	}
</script>	
</head>
<body>
	<%
		ArrayList<Dropdown> visitPurpose = new ArrayList<Dropdown>();
		ArrayList<Dropdown> idType = new ArrayList<Dropdown>();
		ArrayList<Dropdown> containerSize = new ArrayList<Dropdown>();
		try {
			//Dropdown for visitPurpose START
			visitPurpose = DropdownListManagerDAO.retrieveByDropdownKey("IDTYPE");
			//Dropdown for visitPurpose END

			//Dropdown for idType START
			idType = DropdownListManagerDAO.retrieveByDropdownKey("IDTYPE");
			//Dropdown for idType END
			
			//Dropdown for containerSize START
			containerSize = DropdownListManagerDAO.retrieveByDropdownKey("IDTYPE");
			//Dropdown for containerSize END

		} catch (Exception e) {
	%>
	<h1><%=e%></h1>
	<%
		}
	%>
	<div class="container body-content">
		<div class="page-header">
			<label class="heading">Vehicle Management System</label> <br> <b>How
				to use:</b> Please enter Visitor Details.
			<%
 	String idNo = "SxxxxxxxJ";
    String name = "";			
	Vehicle v = null;
 	if (request.getAttribute("vehicleLatRec") != null) {
 		v = (Vehicle) request.getAttribute("vehicleLatRec");
 	}
 	if (request.getSession(false).getAttribute("usertype") == null && request.getSession(false).getAttribute("idNo") != null) {
 		idNo = (String) request.getSession(false).getAttribute("idNo");
 		name = (String) request.getSession(false).getAttribute("name");
 	}
 %>
			<center>
				<form action="addVehicle" method="post" name="addVehicle" onsubmit="return validateForm()">
					<div class="form-row"> 
						<div class="form-group col-md-6">
							<label for="name">Vehicle Driver Name: </label> <input type="text"
								class="form-control" name="name"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? name : v.getName())%>" required>
						</div>
						<div class="form-group col-md-6">
							<label for="companyName">Company Name: </label> <input
								type="text" class="form-control" name="companyName"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getCompanyName())%>" required>
						</div>
<!-- 						<div class="form-group col-md-4"> -->
<!-- 							<label for="idType">ID Type: </label>  -->
<%-- 							<% if(v == null){%> --%>
<!-- 								<select name="idType" class="form-control" required> -->
<%-- 									<% 
// 										for (Dropdown d: idType) {
<%-- 									%> --%>
<%-- 									<option value="<%=d.getDropdownValue()%>"> --%>
<%-- 										<%=d.getDropdownValue()%></option> --%>
<%-- 									<% 
// 										}
<%-- 									%> --%>
<!-- 								</select> -->
<%-- 							<% }  --%>
<%-- 							else {%> --%>
<!-- 								<input -->
<!-- 								type="text" class="form-control" name="idType" -->
<!-- 								oninput="this.value = this.value.toUpperCase()" -->
<%-- 								value="<%=((v == null) ? "" : v.getIdType())%>" readonly> --%>
<%-- 							<%} %> --%>
<!-- 						</div> -->
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="idNo">Vehicle Driver ID Number: </label> <input type="text"
								class="form-control" name="idNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? idNo : v.getIdNo())%>"
								minlength="4" maxlength="9" readonly>
						</div>
						<div class="form-group col-md-6">
							<label for="mobileNo">Mobile: </label> <input type="text"
								class="form-control" name="mobileNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getMobileNo())%>" required>
						</div>
						<div class="form-group col-md-4">
							<label for="visitPurpose">Visit Purpose: </label> 
							<% if(v == null){%>
								<select
									name="visitPurpose" class="form-control" required>
									<%
										for (Dropdown d: visitPurpose) {
									%>
									<option value="<%=d.getDropdownValue()%>">
										<%=d.getDropdownValue()%></option>
									<%
										}
									%>
								</select>
							<% } 
							else {%>
								<select
									name="visitPurpose" class="form-control" required>
									<%
										for (Dropdown d: visitPurpose) {
									%>
									<option value="<%=d.getDropdownValue()%>" 
										<%=v.getVisitPurpose().equals(d.getDropdownValue()) ? "selected" : "" %>>
										<%=d.getDropdownValue()%></option>
									<%
										}
									%>
								</select>
							<%} %>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="primeMoverNo">Vehicle/Primemover Number: </label> <input
								type="text" class="form-control" name="primeMoverNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getPrimeMoverNo())%>">
						</div>
						<div class="form-group col-md-6">
							<label for="containerNo">Container Number: </label> <input type="text"
								class="form-control" name="containerNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getContainerNo())%>">
						</div>
						<div class="form-group col-md-4">
							<input type="checkbox" id="loadedNoLoaded"
								name="loadedNoLoaded" value="Yes" > <label
								for="loadedNoLoaded"> Select if container is loaded.
						</label>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="sealNo">Seal No: </label> <input type="text"
								class="form-control" name="sealNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getSealNo())%>">
						</div>
						<div class="form-group col-md-4">
							<label for="containerSize">Container Size: </label> 
							<% if(v == null || v.getContainerSize() == null || StringUtils.isEmpty(v.getContainerSize())){%>
								<select
									name="containerSize" class="form-control">
									<%
										for (Dropdown d: containerSize) {
									%>
									<option value="<%=d.getDropdownValue()%>" >
										<%=d.getDropdownValue()%></option>
									<%
										}
									%>
								</select>
							<% } 
							else {%>
								<select
									name="containerSize" class="form-control">
									<%
										for (Dropdown d: containerSize) {
									%>
									<option value="<%=d.getDropdownValue()%>" 
										<%=v.getContainerSize().equals(d.getDropdownValue()) ? "selected" : "" %>>
										<%=d.getDropdownValue()%></option>
									<%
										}
									%>
								</select>
							<%} %>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="lorryChetNumber">Lorry Chet Number: </label> <input
								type="text" class="form-control" name="lorryChetNumber"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getLorryChetNumber())%>">
						</div>
						<div class="form-group col-md-6">
							<label for="deliveryNoticeNumber">Delivery Notice Number: </label> <input type="text"
								class="form-control" name="deliveryNoticeNumber"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getDeliveryNoticeNumber())%>">
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="temperature">Temperature: </label> <input type="text"
								class="form-control" name="temperature" id="temperature"
								placeholder="36.6" minlength="2" maxlength="4" required>
						</div>
						<div class="form-group col-md-6">
							<label for="remarks">Remarks: </label> <input type="text"
								class="form-control" name="remarks" id="remarks">
						</div>
					</div>
					<div class="form-row checkbox">
						<input type="checkbox" id="coviddeclaration"
							name="coviddeclaration" value="Yes" required> <label
							for="coviddeclaration"> I confirm that I am NOT
							experiencing any of the following symptoms: <br> • fever
							(feeling hot to the touch, a temperature of 37.8 degrees Celsius
							or higher)<br> • new onset of cough (continuous, more than
							usual)<br> • difficulty breathing<br> <b>*Individuals
								are required to self-identify should they experience any
								COVID-19 symptoms.</b>
						</label>
					</div>
					<div class="form-row">
						<button type="submit" class="btn btn-primary btn-lg active">Submit
							Record</button>
						<a href="/vehms" class="btn btn-warning btn-lg active" role="button"
							aria-pressed="true">Back</a>
					</div>
					<br> <br>
				</form>
			</center>
		</div>
	</div>
</body>
</html>