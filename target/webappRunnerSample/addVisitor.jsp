<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.collections.IteratorUtils"%>
<%@page import="com.google.gdata.data.spreadsheet.CellEntry"%>
<%@page import="com.google.gdata.data.spreadsheet.Cell"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="net.javatutorial.entity.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginCSS.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.time.*"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.google.gdata.client.spreadsheet.SpreadsheetService"%>
<%@page import="com.google.gdata.data.spreadsheet.CustomElementCollection"%>
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

<script type="text/javascript">
	var userInput = "SxxxxxxxJ"
	$(document).ready(function() {
		$('#escalation').DataTable();
	});
	function othername() {
		userInput = document.getElementById("userInput").value;
		window.location.replace("addVisitor.jsp?userInput=" + userInput);
	}

	var dt = new Date();
	document.getElementById("currTime").innerHTML = dt.toLocaleTimeString();
</script>
</head>
<body>
	<div class="container body-content">
		<div class="page-header">
			<label class="heading">Visitor Management System</label> <br> <b>How
				to use:</b> Please enter Visitor Details. <br> <br>
			<center>
				<h3>Step 1: Enter NRIC</h3>
				<form id="form" onsubmit="return false;">
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="idNo">IC Number: </label> <input type="text"
								class="form-control" name="idNo" id="userInput"
								placeholder="SxxxxxxxJ" minlength="9" maxlength="9" required>
						</div>
						<button type="submit" class="btn btn-primary"
							onclick="othername();">Check NRIC</button>
					</div>
				</form>
				<%
					String userInput = "SxxxxxxxJ";

					String icNumber = "";
					String firstName = "";
					String lastName = "";
					String mobileNo = "";
					String vehicleNo = "";
					String hostName = "";
					String hostNo = "";
					String visitorCardId = "";

					if (request.getParameter("userInput") != null) {
						userInput = (String) request.getParameter("userInput").trim().toUpperCase();
					}
					SpreadsheetService k11VMS = new SpreadsheetService("K11 VMS");
					try {
						String sheetUrl = "https://spreadsheets.google.com/feeds/list/1vJBvGXxyOUCz5ZYZtQCIet5qmxamihTYLCjv51os070/1/public/values";

						// Use this String as url
						URL url = new URL(sheetUrl);

						// Get Feed of Spreadsheet url
						ListFeed lf = k11VMS.getFeed(url, ListFeed.class);

						// Iterate over feed to get cell value
						for (ListEntry le : lf.getEntries()) {
							CustomElementCollection cec = le.getCustomElements();

							if (cec != null) {
								icNumber = cec.getValue("icnumber");
								if (icNumber != null && !StringUtils.isEmpty(icNumber)) {
									icNumber = icNumber.toUpperCase();
									icNumber = icNumber.trim();
									// Compare with user input - to populate the rest
									if (icNumber.equals(userInput)) {
										firstName = cec.getValue("firstname");
										lastName = cec.getValue("lastname");
										mobileNo = cec.getValue("mobile");
										vehicleNo = cec.getValue("vehiclenumber");
										hostName = cec.getValue("hostname");
										hostNo = cec.getValue("hostnumber");
										visitorCardId = cec.getValue("visitorcardid");
									}
								}

							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				%>
				<br>
				<h3>Step 2: Enter visitor details</h3>
				<form action="addVisitor" method="post">
					<div class="form-row">
						<div class="form-row">
							<div class="form-group col-md-6">
								<label for="firstName">First Name: </label> <input type="text"
									class="form-control" name="firstName"
									oninput="this.value = this.value.toUpperCase()"
									value=<%=firstName%> required>
							</div>
							<div class="form-group col-md-6">
								<label for="lastName">Last Name: </label> <input type="text"
									class="form-control" name="lastName"
									oninput="this.value = this.value.toUpperCase()"
									value=<%=lastName%> required>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label for="idNo">IC Number: </label> <input type="text"
								class="form-control" name="idNo"
								oninput="this.value = this.value.toUpperCase()"
								value=<%=userInput%> minlength="9" maxlength="9" required>
						</div>
						<div class="form-group col-md-6">
							<label for="mobileNo">Mobile: </label> <input type="text"
								class="form-control" name="mobileNo"
								oninput="this.value = this.value.toUpperCase()"
								value=<%=mobileNo%> required>
						</div>
						<div class="form-group col-md-6">
							<label for="vehicleNo">Vehicle Number: </label> <input
								type="text" class="form-control" name="vehicleNo"
								oninput="this.value = this.value.toUpperCase()"
								value=<%=vehicleNo%> required>
						</div>
						<div class="form-group col-md-6">
							<label for="hostName">Host Name: </label> <input type="text"
								class="form-control" name="hostName"
								oninput="this.value = this.value.toUpperCase()"
								value=<%=hostName%> required>
						</div>
						<div class="form-group col-md-6">
							<label for="hostNo">Host Number: </label> <input type="text"
								class="form-control" name="hostNo"
								oninput="this.value = this.value.toUpperCase()"
								value=<%=hostNo%> required>
						</div>
						<div class="form-group col-md-6">
							<label for="timein">Time In: </label> <input
								type="datetime-local" class="form-control" name="timeInDt"
								required>
						</div>
						<div class="form-group col-md-6">
							<label for="visitorCardId">Visitor Card ID: </label> <input
								type="text" class="form-control" name="visitorCardId"
								oninput="this.value = this.value.toUpperCase()"
								value=<%=visitorCardId%> required>
						</div>
						<button type="submit" class="btn btn-primary">Submit
							Record</button>
					</div>
				</form>
			</center>
		</div>
	</div>

</body>
</html>