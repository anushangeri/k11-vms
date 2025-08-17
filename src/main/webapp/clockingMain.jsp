<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="net.javatutorial.entity.Clocking"%>
<%
    // Handle session clearing
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
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Officer QR Check-In</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jsqr/dist/jsQR.js"></script>
<style>
    #cameraFeed {
        width: 100%;
        max-width: 400px;
        border: 1px solid #ccc;
        display: none;
        margin-top: 20px;
    }
    #scanButton {
        margin: 10px 0;
        padding: 10px 20px;
        font-size: 16px;
    }
</style>
</head>
<body>
<div class="container">
    <h2 class="text-center">Officer Check-In</h2>
    <div class="row justify-content-center">
        <div class="col-md-6">

            <!-- Officer Info Form -->
            <form id="officerForm" method="post">
                <div class="form-group">
                    <label for="officerName">Officer Name:</label>
                    <input type="text" class="form-control" id="officerName" name="officerName"
                           value="<%= officerName != null ? officerName : "" %>"
                           oninput="this.value = this.value.toUpperCase()" required>
                </div>
                <div class="form-group">
                    <label for="officerNric">Officer NRIC:</label>
                    <input type="text" class="form-control" id="officerNric" name="officerNric"
                           value="<%= officerNric != null ? officerNric : "" %>"
                           maxlength="9" minlength="9"
                           oninput="this.value = this.value.toUpperCase()" required>
                </div>
                <button type="submit" id="saveButton" class="btn btn-primary btn-lg btn-block">Save Officer Info</button>
                <button type="button" id="scanButton" class="btn btn-warning btn-lg btn-block">Scan QR Code</button>
            </form>

            <video id="cameraFeed" autoplay></video>

            <br>
            <button type="button" class="btn btn-danger btn-block"
                    onclick="window.location.href='clockingMain.jsp?action=clear'">
                Done Clocking
            </button>
        </div>
    </div>

    <% if (records != null && !records.isEmpty()) { %>
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
            <% for (Clocking c : records) { %>
                <tr>
                    <td><%= c.getClockingId() %></td>
                    <td><%= c.getClockingPointName() %></td>
                    <td><%= c.getSiteName() %></td>
                    <td><%= c.getCreatedDt() %></td>
                    <td><%= c.getCreatedBy() %></td>
                </tr>
            <% } %>
            </tbody>
        </table>
    <% } %>
</div>

<script>
const video = document.getElementById('cameraFeed');
let videoStream = null;

// Save officer info
document.getElementById('officerForm').addEventListener('submit', function(e) {
    e.preventDefault(); // Prevent normal submit
    this.submit();      // Submit to save officer info in session
});

// Start scanning only when user clicks "Scan QR Code"
document.getElementById('scanButton').addEventListener('click', function() {
    const officerName = document.getElementById('officerName').value.trim();
    const officerNric = document.getElementById('officerNric').value.trim();
    if (!officerName || !officerNric) {
        alert("Please enter officer name and NRIC first.");
        return;
    }
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
                window.location.href = code.data; // Follow QR link
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
