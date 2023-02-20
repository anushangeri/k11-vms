<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.12/js/intlTelInput.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.12/css/intlTelInput.css"
	rel="stylesheet" />

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/intlTelInput.min.js"></script>
</head>
<body>
	<% 
	String visitorName = "";
	String hostName = "";
	String companyName = "";
	if (request.getAttribute("visitorName") != null) {
		visitorName = (String) request.getAttribute("visitorName");
		hostName = (String) request.getAttribute("hostName");
		companyName = (String) request.getAttribute("companyName");
	}%>
	<div class="container body-content">
		<div class="page-header">
			<center>
				<label class="heading">VISITOR</label> <br>
				<label class="heading" style="color:red; font-size:50px;"><strong><%=visitorName %></strong></label> <br>
				<label class="heading">Host: <%=hostName %></label> <br>
			</center>
		</div>
	</div>
</body>
</html>