<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginCSS.jsp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
	<%
		ArrayList<String> responseObj = (ArrayList<String>) request.getAttribute("responseObj");
		if (responseObj != null) {
	%>
	<label class="heading"><%=responseObj.toString()%></label>
	<%} %>
	<center>
	<%if ( !(session.getAttribute("usertype") == null)) {
			String usertype = (String) session.getAttribute("usertype");
			
		%>
				<!-- access control for K11 Admin -->
				<%if (usertype.equals("K11ADMIN")) {%>
				<div class="card">
					<a href="vms.jsp">
						<div class="eachCard crop">
							<img class="center-block" src="VMS_icon.png"
								alt="VMS_icon.png">
							<h4 class="fontheader">
								<b>VISITOR MANAGEMENT SYSTEM</b>
							</h4>
						</div>
					</a>
				</div>	
				<br>
				<div class="card">
					<a href="managedatabase.jsp">
						<div class="eachCard crop">
							<img class="center-block" src="elearning.png" alt="elearning.png">
							<h4 class="fontheader">
								<b>MANAGE DATABASE</b>
							</h4>
						</div>
					</a>
				</div>
			<%} %>
<%} %>
</center>
</body>
</html>
