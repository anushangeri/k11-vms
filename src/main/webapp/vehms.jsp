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
<script src="https://cdn.datatables.net/plug-ins/1.10.24/sorting/datetime-moment.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(document).ready(function() {
			$.fn.dataTable.moment('DD/MM/YYYY hh:mm:ss A');
			$('table').DataTable({
				dom : 'Blfrtip',
				buttons : [ {
					text : 'Export To Excel',
					extend : 'excelHtml5',
					exportOptions : {
						modifier : {
							selected : true
						},
						columns : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18],
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
				} ],
				"order": [[17, 'desc']]
			});
		});
	});
	function showDiv(divId, element)
	{
	    document.getElementById(divId).style.display = element.value == "Y" ? 'block' : 'none';
	}
	function goBack() {
	  window.history.back();
	}
</script>
</head>
<body>
	<center>
	<%
		ArrayList<Vehicle> vList = (ArrayList<Vehicle>) request.getAttribute("vList");
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
					class="table table-striped table-bordered table-sm sortable" style="width: 80%;">
					<thead>
						<tr>
							<th class="th-sm">S/N</th>
							<th class="th-sm">Name</th>
							<th class="th-sm">Company Name</th>
							<th class="th-sm"  style="display:none;">ID Type</th>
							<!-- if session access type is admin or staff i.e. there is a access type then display idno with hyperlink -->
							<%if(userType == null) { %>
								<th class="th-sm" style="display:none;">ID Number</th>
							<% 
							} else{
							%>
								 <th class="th-sm">ID Number</th>
							<%
							}%>
							<th class="th-sm">Visitor Contact Number</th>
							<th class="th-sm">Vehicle No./Primemover No.</th>
							<th class="th-sm">Loaded/Not Loaded</th>
							<th class="th-sm">Container No.</th>
							<th class="th-sm">Container Size</th>
							<th class="th-sm">Seal No.</th>
							<th class="th-sm" style="display:none;">Covid Declaration?</th>
							<th class="th-sm">Lorry Chet No.</th>
							<th class="th-sm">Delivery Notice No.</th>
							<th class="th-sm">Remarks</th>
							<th class="th-sm">Purpose of Visit</th>
							<th class="th-sm">Temperature</th>
							<th class="th-sm">Time In</th>
							<th class="th-sm">Time Out</th>
						</tr>
					</thead>
					<tbody>
						<%
							if (!vList.isEmpty()) {
								Iterator<Vehicle> vListIter = vList.iterator();
								while (vListIter.hasNext()) {
									Vehicle v = vListIter.next();
						%>
								<tr>
									<td><%=v.getVehicleId()%></td>
									<td><%=v.getName()%></td>
									<td><%=v.getCompanyName()%></td>
									<td style="display:none;" ><%=v.getIdType()%></td>
									<!-- if session access type is admin or staff i.e. there is a access type then display idno with hyperlink -->
									<%if(userType == null) { %>
										<td style="display:none;"><%=v.getIdNo()%></td>
									<% 
									} else{
									%>
										 <td><a href="/retrieveVehToPopulate?idNo=<%=v.getIdNo()%>&idType=<%=v.getIdType()%>"><%=v.getIdNo()%></a></td>
									<%
									}%>
									<td><%=(v.getMobileNo() != null ? v.getMobileNo() : "")%></td>
									<td><%=(v.getPrimeMoverNo() != null ? v.getPrimeMoverNo() : "")%></td>
									<td><%=(v.getLoadedNoLoaded().equals("null") ? "Not Loaded" : "Loaded")%></td>
									<td><%=(v.getContainerNo() != null ? v.getContainerNo() : "")%></td>
									<td><%=(v.getContainerSize() != null ? v.getContainerSize() : "")%></td>
									<td><%=(v.getSealNo() != null ? v.getSealNo() : "")%></td>
									<td style="display:none;" ><%=((v.getCovidDeclare().equals("null")) ? "No" : v.getCovidDeclare())%></td>
									<% if (v.getLorryChetNumber() != null && !StringUtils.isEmpty(v.getLorryChetNumber())) { %>
										<td><%=v.getLorryChetNumber()%></td>
									<%
										}
										else{
									%>
										<td>
											<select id = "ddlLorryChet" onchange="showDiv('dvLorryChet<%=v.getVehicleId()%>', this)">
										        <option value="N">No</option>
										        <option value="Y">Yes</option>            
										    </select>
										    <hr />
											<div id="dvLorryChet<%=v.getVehicleId()%>" style="display: none">
												<form method="POST" action ="/updateVehLorryChet">
													<input type="hidden" id="vehicleId" name="vehicleId" value="<%=v.getVehicleId()%>">
													<input type="text" class="form-control" name="lorryChetNumber"
													oninput="this.value = this.value.toUpperCase()">
													<input type="submit" name="Submit" value="Update">
												</form>
											</div>
										</td>
									<%
										}
									%>
									<% if (v.getDeliveryNoticeNumber() != null && !StringUtils.isEmpty(v.getDeliveryNoticeNumber())) { %>
										<td><%=v.getDeliveryNoticeNumber()%></td>
									<%
										}
										else{
									%>
										<td>
											<select id = "ddlDelNotice" onchange="showDiv('dvDelNotice<%=v.getVehicleId()%>', this)">
										        <option value="N">No</option>
										        <option value="Y">Yes</option>            
										    </select>
										    <hr />
											<div id="dvDelNotice<%=v.getVehicleId()%>" style="display: none">
												<form method="POST" action ="/updateVehDeliveryNotice">
													<input type="hidden" id="vehicleId" name="vehicleId" value="<%=v.getVehicleId()%>">
													<input type="text" class="form-control" name="deliveryNoticeNumber"
													oninput="this.value = this.value.toUpperCase()">
													<input type="submit" name="Submit" value="Update">
												</form>
											</div>
										</td>
									<%
										}
									%>
									<td>
										<p><%=(v.getRemarks() != null ? v.getRemarks() : "No Remarks Yet")%></p>
										<select id = "ddlRemarks" onchange="showDiv('dvRemarks<%=v.getVehicleId()%>', this)">
									        <option value="N">No Edit Remarks</option>
									        <option value="Y">Yes Edit Remarks</option>            
									    </select>
									    <hr />
										<div id="dvRemarks<%=v.getVehicleId()%>" style="display: none">
											<form method="POST" action ="/updateVehRemarks">
												<input type="hidden" id="vehicleId" name="vehicleId" value="<%=v.getVehicleId()%>">
												<input type="text" class="form-control" name="remarks"
												oninput="this.value = this.value.toUpperCase()">
												<input type="submit" name="Submit" value="Update">
											</form>
										</div>
									</td>
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
										<td><form method="POST" action ="/updateVehTimeOut">
											<input type="hidden" id="vehicleId" name="vehicleId" value="<%=v.getVehicleId()%>">
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
				<a href="/index.jsp" class="btn btn-warning btn-lg active"
				role="button" aria-pressed="true">Back</a>
		
				<a href="retrieveVehToPopulate" class="btn btn-warning btn-lg active"
				role="button" aria-pressed="true">Add Vehicle Record</a>
				
				<!-- Delete all record function is for K11 Admin only -->
				<%if (request.getSession(false).getAttribute("usertype") != null) {
					String userInput = (String) request.getSession(false).getAttribute("usertype");
					if (userInput.toUpperCase().equals("ADMIN")){ %>
						<a href="deleteAllVehicle" class="btn btn-warning btn-lg active"
						role="button" aria-pressed="true">Delete Vehicle Record</a>
						
						<a href="managedatabase.jsp" class="btn btn-warning btn-lg active"
						role="button" aria-pressed="true">Manage Vehicle Database</a>
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