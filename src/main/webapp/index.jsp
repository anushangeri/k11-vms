<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginVMSCSS.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="com.google.gdata.client.spreadsheet.SpreadsheetService"%>
<%@page import="com.google.gdata.data.spreadsheet.CustomElementCollection"%>
<%@page import="com.google.gdata.data.spreadsheet.ListEntry"%>
<%@page import="com.google.gdata.data.spreadsheet.ListFeed"%>
<%@page import="com.google.gdata.util.ServiceException"%>
<%@page import="net.javatutorial.entity.*"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
<script src="https://drvic10k.github.io/bootstrap-sortable/Scripts/bootstrap-sortable.js" type="text/javascript"></script>
<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
<script>
function validateForm() {
  var x = document.forms["checkNRIC"]["idNo"].value;
  var first = x.charAt(0);
  var isDigitFirst = (first >= '0' && first <= '9');
  var second = x.charAt(1);
  var isDigitSecond = (second >= '0' && second <= '9');
  var third = x.charAt(2);
  var isDigitThird = (third >= '0' && third <= '9');
  var forth = x.charAt(3);
  var isDigitForth = (forth >= '0' && forth <= '9');
  if (x != "K11ADMIN" && !isDigitFirst || !isDigitSecond || !isDigitThird || isDigitForth) {
    alert("PDPA Compliance: Enter only last 3 digit and letter of NRIC/FIN. E.g. 409J");
    return false;
  }
}
</script>
</head>
<body>
	<% session.removeAttribute("usertype"); %>
	<center>
		<b>*Individuals are required to self-identify should they experience any COVID-19 symptoms.</b>
		<form name="checkNRIC" action="vmsCheckNRIC" method="post" onsubmit="return validateForm()">
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="idNo">IC Number: </label> <input type="text"
						class="form-control" name="idNo" id="idNo"
						placeholder="xxxJ" minlength="4" maxlength="9" required>
				</div>
				<button type="submit" class="btn btn-primary">Check NRIC</button>
			</div>
		</form>
	</center>
</body>
</html>
