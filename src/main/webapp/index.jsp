<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginVMSCSS.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="com.google.gdata.client.spreadsheet.SpreadsheetService"%>
<%@page
	import="com.google.gdata.data.spreadsheet.CustomElementCollection"%>
<%@page import="com.google.gdata.data.spreadsheet.ListEntry"%>
<%@page import="com.google.gdata.data.spreadsheet.ListFeed"%>
<%@page import="com.google.gdata.util.ServiceException"%>
<%@page import="net.javatutorial.entity.*"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="https://drvic10k.github.io/bootstrap-sortable/Scripts/bootstrap-sortable.js"
	type="text/javascript"></script>
<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
	rel="stylesheet">
</head>
<body>
	<%
		session.removeAttribute("usertype");
	%>
	<center>
		<b>*Individuals are required to self-identify should they
			experience any COVID-19 symptoms.</b>
		<br>
		<br>
		<div class="container">
			<div class="card bg-dark text-white">
				<div class="card-body font-size-percent">
					<form method="POST" action ="vmsCheckNRIC.jsp">
						<label
							for="coviddeclaration"> Are you one of the following? <br> 
							• visitor <br> 
							• staff <br> 
							• government agency <br>
						</label>
						<input type="hidden" id="recordType" name="recordType" value="visitorRecord">
						<input id="removeBackground" type="submit" name="Submit" value="Select this."></form>
				</div>
			</div>
			<br>
			<div class="card bg-warning text-white">
				<div class="card-body font-size-percent">
					<form method="POST" action ="vmsCheckNRIC.jsp">
						<label
							for="coviddeclaration"> Are you one of the following? <br> 
							• commercial vehicle <br> 
							• container <br> 
						</label>
						<input type="hidden" id="recordType" name="recordType" value="vehicleRecord">
						<input id="removeBackground" type="submit" name="Submit" value="Select this."></form>
				</div>
			</div>
			<br>
		</div>
	</center>
</body>
</html>
