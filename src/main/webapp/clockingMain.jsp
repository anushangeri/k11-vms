<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="net.javatutorial.entity.Clocking"%>
<%
/*
Error handling: Instead of a plain sendRedirect, show an error popup that disappears automatically.

Button/field enablement flow:
Scan QR Code + Done Clocking start disabled.
Once Save Officer Info is clicked and valid data is stored in session, disable inputs + Save button, and enable Scan QR Code.
After QR is scanned at least once, enable Done Clocking.
*/
//Handle session clearing
String action = request.getParameter("action");
if ("clear".equals(action)) {
	session.invalidate();
	response.sendRedirect("clockingMain.jsp");
	return;
}

// Save officer info to session if submitted
String officerNameParam = request.getParameter("officerName");
String officerNricParam = request.getParameter("officerNric");

if (officerNameParam != null && officerNricParam != null) {
	if (officerNameParam.trim().isEmpty() || officerNricParam.trim().isEmpty()) {
		response.sendRedirect("clockingMain.jsp?error=Missing+officer+details");
		return;
	}
	session.setAttribute("officerName", officerNameParam.toUpperCase());
	session.setAttribute("officerNric", officerNricParam.toUpperCase());
}

// Retrieve session data
String officerName = (String) session.getAttribute("officerName");
String officerNric = (String) session.getAttribute("officerNric");

List<Clocking> records = null;
Object obj = session.getAttribute("clockingRecords");
if (obj instanceof List<?>) {
	records = (List<Clocking>) obj;
}

session.setMaxInactiveInterval(4 * 60 * 60);

String errorMessage = request.getParameter("error");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Officer QR Check-In</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jsqr/dist/jsQR.js"
	type="text/javascript"></script>
<style type="text/css">
#cameraFeed {
	width: 100%;
	max-width: 400px;
	border: 1px solid #ccc;
	display: none;
	margin-top: 20px;
}

#scanButton, #doneButton, #saveButton {
	margin: 10px 0;
	padding: 10px 20px;
	font-size: 16px;
}

.disabled-btn {
	pointer-events: none;
	opacity: 0.5;
}

#errorPopup {
	position: fixed;
	top: 20px;
	right: 20px;
	background: #f44336;
	color: #fff;
	padding: 15px 20px;
	border-radius: 8px;
	z-index: 9999;
	display: none;
}
</style>
</head>
<body>
	<div class="container">
		<h2 class="text-center">Officer Check-In</h2>
		<div class="row justify-content-center">
			<div class="col-md-6">

				<!-- Officer Info Form -->
				<form id="officerForm" method="post" action="">
					<div class="form-group">
						<label for="officerName">Officer Name:</label> <input type="text"
							class="form-control" id="officerName" name="officerName"
							value='<%=officerName != null ? officerName : ""%>'
							oninput="this.value = this.value.toUpperCase()"
							<%=officerName != null ? "disabled" : ""%> required>
					</div>
					<div class="form-group">
						<label for="officerNric">Officer NRIC:</label> <input type="text"
							class="form-control" id="officerNric" name="officerNric"
							value='<%=officerNric != null ? officerNric : ""%>' maxlength="9"
							minlength="9" oninput="this.value = this.value.toUpperCase()"
							<%=officerNric != null ? "disabled" : ""%> required>
					</div>
					<button type="submit" id="saveButton"
						class='btn btn-primary btn-lg btn-block <%=officerName != null && officerNric != null ? "disabled-btn" : ""%>'>
						Save Officer Info</button>
					<button type="button" id="scanButton"
						class='btn btn-warning btn-lg btn-block <%=officerName == null || officerNric == null ? "disabled-btn" : ""%>'>
						Scan QR Code</button>
				</form>

				<video id="cameraFeed" autoplay src=""></video>

				<br>
				<button type="button" id="doneButton"
					class="btn btn-danger btn-block disabled-btn"
					onclick="window.location.href='clockingMain.jsp?action=clear'">
					Done Clocking</button>
			</div>
		</div>

		<%
		if (records != null && !records.isEmpty()) {
		%>
		<h3 class="text-center mt-4">Clocking Records This Session</h3>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>Clocking ID</th>
					<th>Clocking Point</th>
					<th>Site</th>
					<th>Time</th>
					<th>Created By</th>
				</tr>
			</thead>
			<tbody>
				<%
				for (Clocking c : records) {
				%>
				<tr>
					<td><%=c.getClockingId()%></td>
					<td><%=c.getClockingPointName()%></td>
					<td><%=c.getSiteName()%></td>
					<td><%=c.getCreatedDt()%></td>
					<td><%=c.getCreatedBy()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
		<%
		}
		%>
	</div>

	<!-- Error Popup -->
	<div id="errorPopup"><%=errorMessage != null ? errorMessage : ""%></div>

	<script type="text/javascript">
const video = document.getElementById('cameraFeed');
let videoStream = null;
let hasScannedOnce = false;

//Error popup auto-hide
window.onload = function() {
const errorPopup = document.getElementById("errorPopup");
if (errorPopup.innerText.trim() !== "") {
    errorPopup.style.display = "block";
    setTimeout(() => { errorPopup.style.display = "none"; }, 3000);
}
};

//Save officer info
document.getElementById('officerForm').addEventListener('submit', function(e) {
	e.preventDefault();
	this.submit();
});

//Enable Done Clocking after at least one scan
function markScanDone() {
	hasScannedOnce = true;
	document.getElementById("doneButton").classList.remove("disabled-btn");
}

//Start scanning only when Scan QR Code is active
document.getElementById('scanButton').addEventListener('click', function() {
if (this.classList.contains("disabled-btn")) return;
	startCamera();
});

async function startCamera() {
try {
    videoStream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } });
    video.srcObject = videoStream;
    video.style.display = 'block';
    scanFrames();
} catch (err) {
    alert("Cannot access camera. Use HTTPS or Safari on iOS.");
    console.error(err);
}
}

function stopCamera() {
if (videoStream) {
    videoStream.getTracks().forEach(track => track.stop());
    video.style.display = 'none';
}
}

function scanFrames() {
const canvas = document.createElement('canvas');
const context = canvas.getContext('2d');

function scan() {
    if (video.readyState === video.HAVE_ENOUGH_DATA) {
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        context.drawImage(video, 0, 0, canvas.width, canvas.height);
        const imageData = context.getImageData(0, 0, canvas.width, canvas.height);
        const code = jsQR(imageData.data, imageData.width, imageData.height);
        if (code && code.data) {
            stopCamera();
            markScanDone();
            window.location.href = code.data;
            return;
        }
    }
    requestAnimationFrame(scan);
}

scan();
}
</script>
</body>
</html>