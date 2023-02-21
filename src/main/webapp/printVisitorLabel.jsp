<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="loginVMSCSS.jsp"%>

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
	String timeInDt = "";
	String timeOutDt = "";
	
	if (request.getParameter("visitorName") != null) {
		visitorName = (String) request.getParameter("visitorName");
		hostName = (String) request.getParameter("hostName");
		companyName = (String) request.getParameter("companyName");
		timeInDt = (String) request.getParameter("timeInDt");
		
		timeOutDt = timeInDt.split(" ")[0];
	}
	
    
	%>
	<div class="container body-content">
		<div class="page-header">
			<center>
			<div class="div-label">
				<label class="heading header-label">VISITOR</label> <br>
				<label class="main-label"><strong><%=visitorName %></strong></label> <br>
				<label class="content-label">Company: <%=companyName %></label> <br>
				<label class="content-label">Host: <%=hostName %></label> <br>
				<label class="content-label">Time In: <%=timeInDt %></label> <br>
				<label class="content-label">Time Out: <%=timeOutDt %> 11:59:59 PM</label> <br>
			</div>
			</center>
		</div>
		
	</div>
</body>
<footer class="no-print">
	<div class="form-row">
		<a href="/vms" class="btn btn-warning btn-lg active" role="button"
			aria-pressed="true">Done Printing</a> <br>
	</div>
</footer>
</html>