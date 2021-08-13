<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="loginVMSCSS.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="com.google.gdata.client.spreadsheet.SpreadsheetService"%>
<%@page
	import="com.google.gdata.data.spreadsheet.CustomElementCollection"%>
<%@page import="com.google.gdata.data.spreadsheet.ListEntry"%>
<%@page import="com.google.gdata.data.spreadsheet.ListFeed"%>
<%@page import="com.google.gdata.util.ServiceException"%>
<%@page import="net.javatutorial.entity.*"%>
<%@page import="net.javatutorial.DAO.*"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="https://drvic10k.github.io/bootstrap-sortable/Scripts/bootstrap-sortable.js"
	type="text/javascript"></script>
<link
	href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
	rel="stylesheet">
<script>
	function validateForm() {
		var idNo = document.forms["checkNRIC"]["idNo"].value;
		var idType = document.forms["checkNRIC"]["idType"].value;
		var first = idNo.charAt(0);
		var isDigitFirst = (first >= '0' && first <= '9');
		var second = idNo.charAt(1);
		var isDigitSecond = (second >= '0' && second <= '9');
		var third = idNo.charAt(2);
		var isDigitThird = (third >= '0' && third <= '9');
		var forth = idNo.charAt(3);
		var isDigitForth = (forth >= '0' && forth <= '9');
		var n = idNo.length;
		if (idNo != "K11ADMIN" && (idType == "NRIC" || idType == "FIN") && (!(n >= 4) ||
				!isDigitFirst || !isDigitSecond || !isDigitThird || isDigitForth))  {
			alert("PDPA Compliance: Enter ONLY last 3 digit and letter of ID Number. E.g. 409J ");
			return false;
		}
		if (idNo != "K11ADMIN" && (idType == "PASSPORT NO.") && (!(n >= 4) ||
				!isDigitFirst || !isDigitSecond || !isDigitThird || !isDigitForth))  {
			alert("PDPA Compliance: Enter ONLY last 4 digit of Passport No. E.g. 4456");
			return false;
		}
	}
</script>
</head>
<body>
	<%
		ArrayList<Dropdown> idType = new ArrayList<Dropdown>();
		SpreadsheetService service = new SpreadsheetService("K11CLICKS: DROPDOWN EXCEL");
		try {
			//Dropdown for idType START
// 			String idTypeUrl = "https://spreadsheets.google.com/feeds/list/116L_MDacE0331uQDZLRQD4UKpKXfHgWKcMFeD0ne324/3/public/values";
// 			// Use this String as url
// 			URL idTypeurl = new URL(idTypeUrl);

// 			// Get Feed of Spreadsheet url
// 			ListFeed idTypelf = service.getFeed(idTypeurl, ListFeed.class);
			
			idType = DropdownListManagerDAO.retrieveByDropdownKey("IDTYPE");
			//Dropdown for idType END

		} catch (Exception e) {
	%>
			<h1><%=e%></h1>
	<%
		}
		session.removeAttribute("usertype");
		session.removeAttribute("name");
		session.removeAttribute("idType");
	%>
	<center>
		<b>*Individuals are required to self-identify should they
			experience any COVID-19 symptoms.</b>

		<form name="checkNRIC" action="vmsCheckNRIC" method="post"
			onsubmit="return validateForm()">
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="name">Visitor or Vehicle Driver Name: </label> <input type="text"
						class="form-control" name="name"
						oninput="this.value = this.value.toUpperCase()" required>
				</div>
				<div class="form-group col-md-4">
					<label for="idType">ID Type: </label> <select name="idType"
						class="form-control" required>
						<%
						for (Dropdown d: idType) {
						%>
						<option value="<%=d.getDropdownValue()%>">
							<%=d.getDropdownValue()%></option>
						<%
						}
						%>
					</select>
				</div>
				<div class="form-group col-md-6">
					<label for="idNo">ID Number: </label> <input type="text"
						class="form-control" name="idNo" id="idNo" placeholder="xxxx" oninput="this.value = this.value.toUpperCase()"
						minlength="4" maxlength="9" required>
				</div>
				<input type="hidden" id="recordType" name="recordType" value=<%=request.getParameter("recordType")%>>
				<button type="submit" class="btn btn-primary">Check NRIC</button>
			</div>
		</form>
	</center>
</body>
</html>