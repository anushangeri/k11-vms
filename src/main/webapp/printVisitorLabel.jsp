<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="loginVMSCSS.jsp"%>
<%@page import="java.io.IOException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.time.ZonedDateTime"%>

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
	Timestamp timestamp = null;
	
	

	
	if (request.getParameter("visitorName") != null) {
		visitorName = (String) request.getParameter("visitorName");
		hostName = (String) request.getParameter("hostName");
		companyName = (String) request.getParameter("companyName");
		timeInDt = (String) request.getParameter("timeInDt");
	}
	try {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
	    Date parsedDate = dateFormat.parse(timeInDt);
	    timestamp = new java.sql.Timestamp(parsedDate.getTime());
	} catch(Exception e) { //this generic but you can control another types of exception
	    // look the origin of excption 
	}
	// Convert Timestamp object to ZonedDateTime object
    LocalDateTime localDateTime = timestamp.toLocalDateTime();
    ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("Singapore"));
    
	ZonedDateTime eod = zdt.with(LocalTime.of(23, 59, 59));
	Timestamp timestampEOD = Timestamp.valueOf(eod.toLocalDateTime());
	%>
	<div class="container body-content">
		<div class="page-header">
			<center>
			<div class="div-label">
				<label class="heading header-label">VISITOR</label> <br>
				<label class="main-label"><strong><%=visitorName %></strong></label> <br>
				<label class="content-label">Host: <%=hostName %></label> <br>
				<label class="content-label">Time In: <%=timeInDt %></label> <br>
				<label class="content-label">Time Out: <%=timestampEOD %></label> <br>
			</div>
			</center>
		</div>
		<div class="form-row">
			<a href="/vms" class="btn btn-warning btn-lg active" role="button"
				aria-pressed="true">Done Printing</a> <br>
		</div>
	</div>
</body>
</html>