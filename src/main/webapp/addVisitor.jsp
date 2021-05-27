<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginVMSCSS.jsp"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.collections.IteratorUtils"%>
<%@page import="com.google.gdata.data.spreadsheet.CellEntry"%>
<%@page import="com.google.gdata.data.spreadsheet.Cell"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="net.javatutorial.entity.*"%>
<%@page import="java.util.*"%>
<%@page import="java.time.*"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.google.gdata.client.spreadsheet.SpreadsheetService"%>
<%@page
	import="com.google.gdata.data.spreadsheet.CustomElementCollection"%>
<%@page import="com.google.gdata.data.spreadsheet.ListEntry"%>
<%@page import="com.google.gdata.data.spreadsheet.ListFeed"%>
<%@page import="com.google.gdata.util.ServiceException"%>
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
	var tempAsStr = document.forms["addVisitor"]["temperature"].value;
	var tempAsInt = parseFloat(tempAsStr);
	if (isNaN(tempAsInt) || tempAsInt < 36 || tempAsInt > 37.5)  {
		alert("COVID Alert: Invalid temperature or temperature is high. Please go home if you are sick.");
		return false;
	}
}
function showDiv(divId, element)
{
    document.getElementById(divId).style.display = element.value == "GOVERNMENT AGENCY" ? 'block' : 'none';
}
</script>
</head>
<body>
	<%
		ArrayList<String> visitPurpose = new ArrayList<String>();
		ArrayList<String> idType = new ArrayList<String>();
		SpreadsheetService service = new SpreadsheetService("K11CLICKS: DROPDOWN EXCEL");
		try {
			//Dropdown for visitPurpose START
			String visitPurposeUrl = "https://spreadsheets.google.com/feeds/list/116L_MDacE0331uQDZLRQD4UKpKXfHgWKcMFeD0ne324/13/public/values";
			// Use this String as url
			URL visitPurposeurl = new URL(visitPurposeUrl);

			// Get Feed of Spreadsheet url
			ListFeed visitPurpoself = service.getFeed(visitPurposeurl, ListFeed.class);

			for (ListEntry le : visitPurpoself.getEntries()) {
				CustomElementCollection cec = le.getCustomElements();
				visitPurpose.add(cec.getValue("purpose").trim());
			}
			//Dropdown for visitPurpose END

			//Dropdown for idType START
			String idTypeUrl = "https://spreadsheets.google.com/feeds/list/116L_MDacE0331uQDZLRQD4UKpKXfHgWKcMFeD0ne324/3/public/values";
			// Use this String as url
			URL idTypeurl = new URL(idTypeUrl);

			// Get Feed of Spreadsheet url
			ListFeed idTypelf = service.getFeed(idTypeurl, ListFeed.class);

			for (ListEntry le : idTypelf.getEntries()) {
				CustomElementCollection cec = le.getCustomElements();
				idType.add(cec.getValue("idtype").trim());
			}
			//Dropdown for idType END

		} catch (Exception e) {
	%>
	<h1><%=e%></h1>
	<%
		}
	%>
	<div class="container body-content">
		<div class="page-header">
			<label class="heading">Visitor Management System</label> <br> <b>How
				to use:</b> Please enter Visitor Details.
			<%
 	String idNo = "SxxxxxxxJ";
	String name = "";
 	Visitor v = null;
 	ArrayList<Site> siteDropdown = null;
 	if (request.getAttribute("visitorLatRec") != null) {
 		v = (Visitor) request.getAttribute("visitorLatRec");
 	}
 	if (request.getAttribute("siteDropdown") != null) {
 		siteDropdown = (ArrayList<Site>) request.getAttribute("siteDropdown");
 	}
 	if (request.getSession(false).getAttribute("usertype") == null && request.getSession(false).getAttribute("idNo") != null) {
 		idNo = (String) request.getSession(false).getAttribute("idNo");
 		name = (String) request.getSession(false).getAttribute("name");
 	}
 %>
			<center>
				<form action="addVisitor" method="post" name="addVisitor" onsubmit="return validateForm()">
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="name">Name: </label> <input type="text"
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
						<div class="form-group col-md-6">
							<label for="siteVisiting">Site You Are Visiting: </label> 
							<% if(v == null){%>
								<select name="siteVisiting" class="form-control" required>
									<%
										for (Site eachSite: siteDropdown) {
									%>
											<option value="<%=eachSite.getSiteName()%>">
												<%=eachSite.getSiteName()%></option>
									<%
										}
									%>
								</select>
							<% } 
							else {%>
								<select name="siteVisiting" class="form-control" required>
									<%
										for (Site eachSite: siteDropdown) {
									%>
											<option value="<%=eachSite.getSiteName()%>" 
											<%=v.getSite()!= null && v.getSite().equals(eachSite.getSiteName()) ? "selected" : "" %>>
												<%=eachSite.getSiteName()%></option>
									<%
										}
									%>
								</select>
							<%} %>
						</div>
						<div class="form-group col-md-4">
							<label for="idType">ID Type: </label> 
							<% if(v == null){%>
								<select name="idType" class="form-control" required>
									<%
										for (int i = 0; i < idType.size(); i++) {
									%>
									<option value="<%=idType.get(i)%>">
										<%=idType.get(i)%></option>
									<%
										}
									%>
								</select>
							<% } 
							else {%>
								<input
								type="text" class="form-control" name="idType"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getIdType())%>" readonly>
							<%} %>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="idNo">ID Number: </label> <input type="text"
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
								<select id = "visitPurpose" onchange="showDiv('officerLogin', this)"
									name="visitPurpose" class="form-control" required>
									<%
										for (int i = 0; i < visitPurpose.size(); i++) {
									%>
									<option value="<%=visitPurpose.get(i)%>">
										<%=visitPurpose.get(i)%></option>
									<%
										}
									%>
								</select>
							<% } 
							else {%>
								<select id = "visitPurpose" onchange="showDiv('officerLogin', this)"
									name="visitPurpose" class="form-control" required>
									<%
										for (int i = 0; i < visitPurpose.size(); i++) {
									%>
									<option value="<%=visitPurpose.get(i)%>" <%=v.getVisitPurpose().equals(visitPurpose.get(i)) ? "selected" : "" %>>
										<%=visitPurpose.get(i)%></option>
									<%
										}
									%>
								</select>
							<%} %>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="vehicleNo">Vehicle Number: </label> <input
								type="text" class="form-control" name="vehicleNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getVehicleNo())%>" required>
						</div>
						<div class="form-group col-md-6">
							<label for="hostName">Host Name: </label> <input type="text"
								class="form-control" name="hostName"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getHostName())%>" required>
						</div>
						<div class="form-group col-md-6">
							<label for="hostNo">Host Number: </label> <input type="text"
								class="form-control" name="hostNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getHostNo())%>" required>
						</div>
						<div class="form-group col-md-6">
							<label for="visitorCardId">Visitor Card ID: </label> <input
								type="text" class="form-control" name="visitorCardId"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getVisitorCardId())%>" required>
						</div>
						<div class="form-group col-md-6">
							<label for="temperature">Temperature: </label> <input type="text"
								class="form-control" name="temperature" id="temperature"
								placeholder="36.6" minlength="2" maxlength="4" required>
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
					<br>
					<br>
					<!--  <div id = "officerLogin" class="form-row">
					<i>Please aproach guard house and seek approval from security officer on duty.</i>
						<div class="form-group col-md-6">
							<label for="idNo">Approving Officer ID Number: </label> <input type="text"
								class="form-control" name="idNo" id="idNo" placeholder="xxxx" oninput="this.value = this.value.toUpperCase()"
								minlength="4" maxlength="9" required>
						</div>
						<div class="form-group col-md-4">
							<label for="psw">Password</label> <input type="password" class="form-control" id="psw"
								name="psw" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
								title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
								required><input type="checkbox" onclick="showPassword()">Show Password
						</div>
					</div>-->
					<div class="form-row">
						<button type="submit" class="btn btn-primary btn-lg active">Submit
							Record</button>
						<a href="/vms" class="btn btn-warning btn-lg active" role="button"
							aria-pressed="true">Back</a>
					</div>
					<br> <br>
				</form>
			</center>
		</div>
	</div>
</body>
</html>