<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginCSS.jsp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet"
	href="https://formden.com/static/cdn/bootstrap-iso.css" />
<!-- Bootstrap Date-Picker Plugin -->
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css" />
<script type="text/javascript">
	$(document).ready(
			function() {
				var date_input = $('input[name="dob"]'); //our date input has the name "date"
				var container = $('.bootstrap-iso form').length > 0 ? $(
						'.bootstrap-iso form').parent() : "body";
				var options = {
					format : 'mm/dd/yyyy',
					container : container,
					todayHighlight : true,
					autoclose : true,
				};
				date_input.datepicker(options);
			})
</script>
</head>
<body>
	<%
		ArrayList<String> responseObj = (ArrayList<String>) request.getAttribute("responseObj");
		if (responseObj != null) {
	%>
			<label class="heading"><%=responseObj.toString() %></label>
	<%
		}
	%>
	<center>
	<form action="loginVerify" method="post">
	<h2><center>K11 Attendance Record</center></h2>
		<div class="form-row">
			<div class="form-group col-md-6">
				<label for="idNo"> FULL NRIC/FIN: </label> <input type="text"
					class="form-control" name="idNo" placeholder="Enter FULL NRIC/FIN">
			</div>
			<div class="form-group col-md-6">
				<label for="dob">Date of Birth: </label> <input class="form-control"
					id="dob" name="dob" placeholder="MM/DD/YYYY" type="text" required />
			</div>
			<button type="submit" class="btn btn-primary">Login</button>
		</div>
	</form>
	</center>
	<br>
	
</body>
</html>