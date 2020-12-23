<%-- 
    Document   : loginCSS
    Created on : Sep 19, 2014, 5:58:46 PM
    Author     : Wei Fa
--%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" type="image/png" sizes="96x96" href="favicon-96x96.png">
<!--Method #1 of Initializing Bootstrap-->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="css/bootstrap.min.css" media="all">
<!-- Optional theme -->
<link rel="stylesheet" href="css/bootstrap-theme.min.css" media="all">
<link rel="stylesheet" href="css/styles.css" media="all">
<link
	href=' http://fonts.googleapis.com/css?family=Inconsolata|Droid+Sans'
	rel='stylesheet' type='text/css'>
<style type="text/css">
.navbar-default {
	background-color: #9b59b6;
	border-color: #8e44ad;
}

.navbar-default .navbar-brand {
	color: #ff6600;
}

.navbar-default .navbar-brand:hover, .navbar-default .navbar-brand:focus
	{
	color: #ecdbff;
}

.navbar-default .navbar-text {
	color: #ff6600;
}

.navbar-default .navbar-nav>li>a {
	color: #ff6600;
}

.navbar-default .navbar-nav>li>a:hover, .navbar-default .navbar-nav>li>a:focus
	{
	color: #ecdbff;
}

.navbar-default .navbar-nav>.active>a, .navbar-default .navbar-nav>.active>a:hover,
	.navbar-default .navbar-nav>.active>a:focus {
	color: #ecdbff;
	background-color: #8e44ad;
}

.navbar-default .navbar-nav>.open>a, .navbar-default .navbar-nav>.open>a:hover,
	.navbar-default .navbar-nav>.open>a:focus {
	color: #ecdbff;
	background-color: #8e44ad;
}

.navbar-default .navbar-toggle {
	border-color: #8e44ad;
}

.navbar-default .navbar-toggle:hover, .navbar-default .navbar-toggle:focus
	{
	background-color: #8e44ad;
}

.navbar-default .navbar-toggle .icon-bar {
	background-color: #ecf0f1;
}

.navbar-default .navbar-collapse, .navbar-default .navbar-form {
	border-color: #ecf0f1;
}

.navbar-default .navbar-link {
	color: #ff6600;
}

.navbar-default .navbar-link:hover {
	color: #ecdbff;
}

.list{
	background-color: #FFA8A4 !important;
}


body {
	background-color: #F3F3F3;
}
</style>
<nav class="navbar list" role="navigation">
	<div class="container-fluid">
	<%if ( !(session.getAttribute("usertype") == null)) {
			String usertype = (String) session.getAttribute("usertype");
			if (usertype.equals("K11SECURITY")) {
	%>
				<nav class="nav">
				  <a class="nav-link active" href="todhodsearch.jsp">TOD/HOD Details</a>
				  <a class="nav-link" href="/logout">Logout</a>
				</nav>
			<%} %>
			<%if (usertype.equals("K11ADMIN")) {%>
				<nav class="nav">
				  <a class="nav-link" href="todhodsearch.jsp">TOD/HOD Details</a>
				  <a class="nav-link" href="/logout">Logout</a>
				</nav>
			<%} %>
	<%} %>
	
	<%if ( !(session.getAttribute("siteUser") == null)) {
		String usertype = (String) session.getAttribute("siteUser");
	%>
		<nav class="nav">
		  <a class="nav-link" href="viewVehRegRec.jsp">Vehicle Registration Records</a>
		  <a class="nav-link" href="/logout">Logout</a>
		</nav>
	<%} %>
	</div>
	<!-- /.container-fluid -->
</nav>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"
	type="text/javascript"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<title>K11 eSERVICE</title>
</head>
<body>
	<h1 id="k11title">K11 SECURITY ENGINEERING eSERVICE PLATFORM</h1>
</body>


</html>