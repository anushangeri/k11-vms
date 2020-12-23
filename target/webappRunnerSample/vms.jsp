<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginCSS.jsp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="css/styles.css">
</head>
<body>
	<%
		ArrayList<String> responseObj = (ArrayList<String>) request.getAttribute("responseObj");
		if (responseObj != null) {
	%>
	<label class="heading"><%=responseObj.toString()%></label>
	<%
		}
	%>
	<center>
		<br>
		<div class="card">
			<a href="addVisitor.jsp">
				<div class="eachCard crop">
					<img class="center-block" src="addVisitor.png" alt="addVisitor.png">
					<h4 class="fontheader">
						<b>ADD VISITOR RECORD</b>
					</h4>
				</div>
			</a>
		</div>
		<br>
		<!-- using form tag here because need to send button click straight to servlet -->
		<form action="viewVisitor" method="post">
			<div class="card">
				<button type="submit" class="btn btn-link">
					<div class="eachCard crop">
						<img class="center-block" src="viewallvisitors.png"
							alt="viewallvisitors.png">
						<h4 class="fontheader">
							<b>VIEW ALL VISITOR RECORDS</b>
						</h4>
					</div>
				</button>
			</div>
		</form>
	</center>
</body>
</html>
