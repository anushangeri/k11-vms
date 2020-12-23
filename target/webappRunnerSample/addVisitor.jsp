<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginCSS.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="com.google.gdata.client.spreadsheet.SpreadsheetService"%>
<%@page
	import="com.google.gdata.data.spreadsheet.CustomElementCollection"%>
<%@page import="com.google.gdata.data.spreadsheet.ListEntry"%>
<%@page import="com.google.gdata.data.spreadsheet.ListFeed"%>
<%@page import="com.google.gdata.util.ServiceException"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
<style type="text/css"></style>
<script type="text/javascript"
	src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#escalation').DataTable();
	});
</script>
</head>
<body>
	<div class="container body-content">
		<div class="page-header">
			<label class="heading">Visitor Management System</label> <br> <b>How
				to use:</b> Please enter Visitor Details. <br> <br>
			<center>
				<form action="addVisitor" method="post">
					<div class="form-row">
						<div class="form-row">
							<div class="form-group col-md-6">
								<label for="firstName">First Name: </label> <input type="text"
									class="form-control" name="firstName"
									placeholder="Enter First Name" required>
							</div>
							<div class="form-group col-md-6">
								<label for="lastName">Last Name: </label> <input type="text"
									class="form-control" name="lastName"
									placeholder="Enter Last Name" required>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label for="idNo">IC Number: </label> <input type="text"
								class="form-control" name="idNo" placeholder="SxxxxxxxJ"
								minlength="9" maxlength="9" required>
						</div>
						<div class="form-group col-md-6">
							<label for="mobileNo">MOBILE: </label> <input type="text"
								class="form-control" name="mobileNo" placeholder="Enter MOBILE"
								required>
						</div>

						<div class="form-group col-md-6">
							<label for="vehicleNo">vehicleNo: </label> <input type="text"
								class="form-control" name="vehicleNo"
								placeholder="Enter vehicleNo" required>
						</div>
						<div class="form-group col-md-6">
							<label for="hostName">hostName: </label> <input type="text"
								class="form-control" name="hostName"
								placeholder="Enter hostName" required>
						</div>
						<div class="form-group col-md-6">
							<label for="hostNo">hostNo: </label> <input type="text"
								class="form-control" name="hostNo" placeholder="Enter hostNo"
								required>
						</div>

						<div class="form-group col-md-6">
							<label for="visitorCardId">visitorCardId: </label> <input
								type="text" class="form-control" name="visitorCardId"
								placeholder="Enter visitorCardId" required>
						</div>
						<button type="submit" class="btn btn-primary">Submit
							record</button>
					</div>
				</form>
			</center>
		</div>
	</div>

</body>
</html>