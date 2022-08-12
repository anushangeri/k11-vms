<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginVMSCSS.jsp"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.collections.IteratorUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="net.javatutorial.entity.*"%>
<%@page import="java.util.*"%>
<%@page import="java.time.*"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/8.4.6/js/utils.js"></script>
<script>
function validateForm() {
	var tempAsStr = document.forms["addVisitor"]["temperature"].value;
	var tempAsInt = parseFloat(tempAsStr);
	if (isNaN(tempAsInt) || tempAsInt < 36 || tempAsInt > 37.5)  {
		alert("COVID Alert: Invalid temperature or temperature is high. Please go home if you are sick.");
		return false;
	}
	//remember to add  onsubmit="return validateForm()" in the form to use this function
}
function showDiv(divId, element)
{
    document.getElementById(divId).style.display = element.value == "GOVERNMENT AGENCY" ? 'block' : 'none';
    document.getElementById(divId).style.display == 'block' ? document.getElementById("officerIdNo").setAttribute("required", "") : document.getElementById("officerIdNo").removeAttribute("required");
    
}
function showOfficeDivOnLoad(officerLogin,visitPurpose)
{
    document.getElementById(officerLogin).style.display = document.getElementById(visitPurpose).value == "GOVERNMENT AGENCY" ? 'block' : 'none';
    document.getElementById(officerLogin).style.display == 'block' ? document.getElementById("officerIdNo").setAttribute("required", "") : document.getElementById("officerIdNo").removeAttribute("required");
    
}
function getSMSOTP()
{
    var mobileNo = document.getElementById(mobileNo).value
    document.location.href="/getSMSOTP?mobileNo="mobileNo;
    $.ajax({
        type: "POST",
        url: "../getSMSOTP",
        data: "mobileNo="+mobileNo,
        success: function(result){
        	document.getElementById(otpGenerated).value = result
        }
    });
    
}
function showPassword() {
	  var x = document.getElementById("officerpsw");
	  if (x.type === "password") {
	    x.type = "text";
	  } else {
	    x.type = "password";
	  }
}
$("input").intlTelInput({
	  utilsScript: "https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/8.4.6/js/utils.js"
});

</script>
</head>
<body onload="showOfficeDivOnLoad('officerLogin','visitPurpose')">
	<div class="container body-content">
		<div class="page-header">
			<label class="heading"><%=((request.getAttribute("responseObj") != null) ? request.getAttribute("responseObj") : "")%></label>
			<br>
			<label class="heading">Visitor Management System</label> <br> <b>How
				to use:</b> Please enter Visitor Details.
			<%
 	String idNo = "SxxxxxxxJ";
	String name = "";
	String otpGenerated = "";
 	Visitor v = null;
 	ArrayList<Site> siteDropdown = new ArrayList<Site>();
 	ArrayList<Dropdown> visitPurpose = new ArrayList<Dropdown>();
 	if (request.getAttribute("visitorLatRec") != null) {
 		v = (Visitor) request.getAttribute("visitorLatRec");
 		otpGenerated = (String) request.getAttribute("otpGenerated");
 	}
 	if (request.getAttribute("siteDropdown") != null) {
 		siteDropdown = (ArrayList<Site>) request.getAttribute("siteDropdown");
 	}
 	if (request.getAttribute("visitPurpose") != null) {
 		visitPurpose = (ArrayList<Dropdown>) request.getAttribute("visitPurpose");
 	}
 	if (request.getSession(false).getAttribute("usertype") == null && request.getSession(false).getAttribute("idNo") != null) {
 		idNo = (String) request.getSession(false).getAttribute("idNo");
 		name = (String) request.getSession(false).getAttribute("name");
 	}
 %>
			<center>
				<form action="addVisitor" method="post" name="addVisitor">
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="name">Name: </label> <input type="text"
								class="form-control" name="name"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? name : v.getName())%>" required>
						</div>
						<div class="form-group col-md-6">
							<label for="companyName">Company Name: </label> <input
								type="text" class="form-control" name="companyName"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getCompanyName())%>" required>
						</div>
						<div class="form-group col-md-6">
							<label for="siteVisiting">Site You Are Visiting: </label> 
							<% if(v == null){%>
								<select name="siteVisiting" class="form-control" required>
									<%
										for (Site eachSite: siteDropdown) {
									%>
											<option value="<%=eachSite.getSiteName()%>">
												<%=eachSite.getSiteName()%></option>
									<%
										}
									%>
								</select>
							<% } 
							else {%>
								<select name="siteVisiting" class="form-control" required>
									<%
										for (Site eachSite: siteDropdown) {
									%>
											<option value="<%=eachSite.getSiteName()%>" 
											<%=v.getSite()!= null && v.getSite().equals(eachSite.getSiteName()) ? "selected" : "" %>>
												<%=eachSite.getSiteName()%></option>
									<%
										}
									%>
								</select>
							<%} %>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="idNo">ID Number: </label> <input type="text"
								class="form-control" name="idNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getIdNo())%>"
								minlength="4" maxlength="9"  <%=((v == null) ? "" : "readonly")%>>
						</div>
						<div class="form-group col-md-6">
							<label for="mobileNo">Mobile: </label> <input type="text"
								class="form-control" name="mobileNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getMobileNo())%>" required>
						</div>
						<div class="input-group">
						    <label for="tel">telephone: </label> <input type="tel" class="form-control">
						    <span class="input-group-addon"></span>
						</div>
						<div class="form-group col-md-4">
							<label for="visitPurpose">Visit Purpose: </label> 
							<% if(v == null){%>
								<select id = "visitPurpose" onchange="showDiv('officerLogin', this)"
									name="visitPurpose" class="form-control" required>
									<%
										for (Dropdown d: visitPurpose) {
									%>
									<option value="<%=d.getDropdownValue()%>">
										<%=d.getDropdownValue()%></option>
									<%
										}
									%>
								</select>
							<% } 
							else {%>
								<select id = "visitPurpose" onchange="showDiv('officerLogin', this)"
									name="visitPurpose" class="form-control" required>
									<%
										for (Dropdown d: visitPurpose) {
									%>
									<option value="<%=d.getDropdownValue()%>" <%=v.getVisitPurpose().equals(d.getDropdownValue()) ? "selected" : "" %>>
										<%=d.getDropdownValue()%></option>
									<%
										}
									%>
								</select>
							<%} %>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="vehicleNo">Vehicle Number: </label> <input
								type="text" class="form-control" name="vehicleNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getVehicleNo())%>" required>
						</div>
						<div class="form-group col-md-6">
							<label for="hostName">Host Name: </label> <input type="text"
								class="form-control" name="hostName"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getHostName())%>" required>
						</div>
						<div class="form-group col-md-6">
							<label for="hostNo">Host Number: </label> <input type="text"
								class="form-control" name="hostNo"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getHostNo())%>" required>
						</div>
						<div class="form-group col-md-6">
							<label for="visitorCardId">Visitor Card ID: </label> <input
								type="text" class="form-control" name="visitorCardId"
								oninput="this.value = this.value.toUpperCase()"
								value="<%=((v == null) ? "" : v.getVisitorCardId())%>" required>
						</div>
<!-- 						<div class="form-group col-md-6"> -->
<!-- 							<label for="temperature">Temperature: </label> <input type="text" -->
<!-- 								class="form-control" name="temperature" id="temperature" -->
<!-- 								placeholder="36.6" minlength="2" maxlength="4" required> -->
<!-- 						</div> -->
						<div class="form-group col-md-6">
							<label for="remarks">Remarks: </label> <input type="text"
								class="form-control" name="remarks" id="remarks">
						</div>
					</div>
<!-- 					<div class="form-row checkbox"> -->
<!-- 						<input type="checkbox" id="coviddeclaration" -->
<!-- 							name="coviddeclaration" value="Yes" required> <label -->
<!-- 							for="coviddeclaration"> I confirm that I am NOT -->
<!-- 							experiencing any of the following symptoms: <br> • fever -->
<!-- 							(feeling hot to the touch, a temperature of 37.8 degrees Celsius -->
<!-- 							or higher)<br> • new onset of cough (continuous, more than -->
<!-- 							usual)<br> • difficulty breathing<br> <b>*Individuals -->
<!-- 								are required to self-identify should they experience any -->
<!-- 								COVID-19 symptoms.</b> -->
<!-- 						</label> -->
<!-- 					</div> -->
					<br>
					<br>
					<div id = "officerLogin" class="form-row">
					<i>Please aproach guard house and seek approval from security officer on duty.</i>
						<div class="form-group col-md-6">
							<label for="officerIdNo">Approving Officer ID Number: </label> <input type="text"
								class="form-control" name="officerIdNo" id="officerIdNo" placeholder="xxxx" oninput="this.value = this.value.toUpperCase()"
								minlength="4" maxlength="9">
						</div>
						<div class="form-group col-md-4">
							<label for="officerpsw">Password</label> <input type="password" class="form-control" id="officerpsw"
								name="officerpsw" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
								title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
								><input type="checkbox" onclick="showPassword()">Show Password
						</div>
					</div>
					
					<br> <br>
					<div class="form-row">
						<button type="submit" class="btn btn-primary btn-lg active" onclick=getSMSOTP()>Get OTP</button>
					</div>
					<br> <br>
					
					<label for="otp">Test OTP 1: </label> <input type=text id="otpGenerated" name="otpGenerated">
					<label for="otp">Test OTP 2: </label>  <input type=text id="testotp" name="testotp">${requestScope.otpGenerated}
					<div class="form-group col-md-6">
							<label for="otp">Enter SMS OTP received: </label> <input
								type="text" class="form-control" name="otp">
					</div>
					<div class="form-row">
						<button type="submit" class="btn btn-primary btn-lg active">Submit</button>
						<a href="/vms" class="btn btn-warning btn-lg active" role="button"
							aria-pressed="true">Back</a>
					</div>
				</form>
				
			</center>
		</div>
	</div>
</body>
</html>