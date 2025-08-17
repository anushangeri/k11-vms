<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="net.javatutorial.entity.Clocking"%>
<%
    String officerName = (String) session.getAttribute("officerName");
    String officerNric = (String) session.getAttribute("officerNric");

    List<Clocking> records = null;
    Object obj = session.getAttribute("clockingRecords");
    if (obj instanceof List<?>) {
        records = (List<Clocking>) obj;
    }

    // Set session timeout to 4 hours
    session.setMaxInactiveInterval(4 * 60 * 60);

    // Clear session if requested
    String action = request.getParameter("action");
    if ("clear".equals(action)) {
        session.invalidate();
        response.sendRedirect("clockingMain.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Officer QR Check-In</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="https://rawgit.com/schmich/instascan-builds/master/instascan.min.js"></script>
    <style>
        video { width: 100%; max-width: 400px; border: 1px solid #ccc; margin-top: 20px; display: none; }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">Officer Check-In</h2>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <form id="officerForm">
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
            </form>

            <button id="startScannerBtn" class="btn btn-warning btn-lg btn-block">Scan QR Code</button>
            <video id="preview"></video>

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
document.getElementById('startScannerBtn').addEventListener('click', async () => {
    const videoElem = document.getElementById('preview');
    videoElem.style.display = 'block'; // show video

    const scanner = new Instascan.Scanner({ video: videoElem, mirror: false });

    scanner.addListener('scan', function(content) {
        console.log("Scanned QR Code:", content); // just log or handle backend
        alert("QR code scanned!");
        scanner.stop();               // stop camera
        videoElem.style.display = 'none'; // hide video
    });

    try {
        const cameras = await Instascan.Camera.getCameras();
        if (cameras.length > 0) {
            // Try to find environment/back camera
            let backCamera = cameras.find(cam => cam.name.toLowerCase().includes('back'));
            if (!backCamera) backCamera = cameras.find(cam => cam.facing === 'environment');
            if (!backCamera) backCamera = cameras[0]; // fallback

            await scanner.start(backCamera);
        } else {
            alert("No camera found on this device.");
        }
    } catch (e) {
        alert("Camera access error: " + e);
        console.error(e);
    }
});
</script>
</body>
</html>
