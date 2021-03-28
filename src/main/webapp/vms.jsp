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
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.javatutorial.entity.*"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://drvic10k.github.io/bootstrap-sortable/Contents/bootstrap-sortable.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.19.1/moment.js" type="text/javascript"></script>
<script src="https://drvic10k.github.io/bootstrap-sortable/Scripts/bootstrap-sortable.js" type="text/javascript"></script>
<link href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" />
<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.2.1/css/buttons.dataTables.min.css" />
<script src="https://code.jquery.com/jquery-1.12.3.js" type="text/javascript"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="https://cdn.datatables.net/buttons/1.2.1/js/dataTables.buttons.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js" type="text/javascript"></script>
<script src="https://cdn.datatables.net/buttons/1.2.1/js/buttons.html5.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(document).ready(function() {
			$('table').DataTable({
				dom : 'Blfrtip',
				buttons : [ {
					text : 'Export To Excel',
					extend : 'excelHtml5',
					exportOptions : {
						modifier : {
							selected : true
						},
						columns : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
						format : {
							header : function(data, columnIdx) {
								return data;
							},
						}
					},
					footer : false,
					customize : function(xlsx) {
						var sheet = xlsx.xl.worksheets['sheet1.xml'];
					}
				} ]
			});
		});
	});
</script>
</head>
<body>
	<center>
	<%
		ArrayList<Visitor> vList = (ArrayList<Visitor>) request.getAttribute("vList");
		String message = (String) request.getAttribute("message");
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		String idNo = "SxxxxxxxJ";
		String userType = "";
	 	if (request.getSession(false).getAttribute("idNo") != null) {
	 		idNo = (String) request.getSession(false).getAttribute("idNo");
	 		userType = (String) request.getSession(false).getAttribute("usertype");
	 	}
		if (message != null && !StringUtils.isEmpty(message)) {
	%>
		<label class="heading"><%=message%> </label><br>
		<b>*Individuals are required to self-identify should they experience any COVID-19 symptoms.</b>
	</center>
		<% 
			if (vList != null && vList.size() > 0) {
		%>
			<div class="container body-content" id="tableview">
				<table id="example"
					class="table table-striped table-bordered table-sm sortable">
					<thead>
						<tr>
							<th class="th-sm">S/N</th>
							<th class="th-sm">Name</th>
							<th class="th-sm">Company Name</th>
							<th class="th-sm" style="display:none;">ID Type</th>
							<%if(userType == null) { %>
								<th class="th-sm" style="display:none;">ID Number</th>
							<% 
							} else{
							%>
								 <th class="th-sm">ID Number</th>
							<%
							}%>
							<th class="th-sm">Visitor Contact Number</th>
							<th class="th-sm">Vehicle Number</th>
							<th class="th-sm">Host Name</th>
							<th class="th-sm" style="display:none;">Host Contact Number</th>
							<th class="th-sm">Visitor Pass ID</th>
							<th class="th-sm">Covid Declaration?</th>
							<th class="th-sm">Purpose of Visit</th>
							<th class="th-sm">Temperature</th>
							<th class="th-sm">Time In</th>
							<th class="th-sm">Time Out</th>
						</tr>
					</thead>
					<tbody>
						<%
							if (!vList.isEmpty()) {
								Iterator<Visitor> vListIter = vList.iterator();
								while (vListIter.hasNext()) {
									Visitor v = vListIter.next();
						%>
								<tr>
									<td><%=v.getVmsId()%></td>
									<td><%=v.getName()%></td>
									<td><%=v.getCompanyName()%></td>
									<td style="display:none;"><%=v.getIdType()%></td>
									<!-- if session access type is admin or staff i.e. there is a access type then display idno with hyperlink -->
									<%if(userType == null) { %>
										<td style="display:none;"><%=v.getIdNo()%></td>
									<% 
									} else{
									%>
										 <td><a href="/retrieveToPopulate?idNo=<%=v.getIdNo()%>&idType=<%=v.getIdType()%>"><%=v.getIdNo()%></a></td>
									<%
									}%>
									
									<td><%=v.getMobileNo()%></td>
									<td><%=v.getVehicleNo()%></td>
									<td><%=v.getHostName()%></td>
									<td style="display:none;"><%=v.getHostNo()%></td>
									<td><%=v.getVisitorCardId()%></td>
									<td><%=((v.getCovidDeclare() == "null") ? "No" : v.getCovidDeclare())%></td>
									<td><%=v.getVisitPurpose()%></td>
									<td><%=v.getTemperature()%></td>
									<td><%=sdf.format(v.getTimeInDt())%></td>
									<!-- TO DO: if timeout is null - send to update servlet to update with system time -->
									<% if (v.getTimeOutDt() != null) { %>
										<td><%=sdf.format(v.getTimeOutDt())%></td>
									<%
										}
										else{
									%>
										<td><form method="POST" action ="/updateVisitor">
											<input type="hidden" id="vmsId" name="vmsId" value="<%=v.getVmsId()%>">
											<input type="submit" name="Submit" value="Update"></form></td>
									<%
										}
									%>
								</tr>
							<%
								}
							%>
			
						<%
							}
						%>
					</tbody>
				</table>
		<%
			}
		%>
	<%
		}
	%>
	</div>
		<div class="container body-content">
			<center>
				<a href="index.jsp" class="btn btn-warning btn-lg active"
					role="button" aria-pressed="true">Back</a>
		
				<a href="retrieveToPopulate" class="btn btn-warning btn-lg active"
				role="button" aria-pressed="true">Add Visitor Record</a>
				
				<!-- Delete all record function is for K11 Admin only -->
				<%if (request.getSession(false).getAttribute("usertype") != null) {
					String usertype = (String) request.getSession(false).getAttribute("usertype");
					if (usertype.toUpperCase().equals("ADMIN")){ %>
						<a href="deleteAllVisitor" class="btn btn-warning btn-lg active"
						role="button" aria-pressed="true">Delete Visitor Record</a>
						
						<a href="managedatabase.jsp" class="btn btn-warning btn-lg active"
						role="button" aria-pressed="true">Manage Visitor Database</a>
					<%	
					}
					
				%>
				<% 
				}
				%>
			</center>
		</div>
	<br>
	
</body>
</html>