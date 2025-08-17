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

    session.setMaxInactiveInterval(4 * 60 * 60);

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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsqr/1.4.0/jsQR.min.js"></script>
    <style>
        video { width: 100%; max-width: 400px; border: 1px solid #ccc; margin-top: 20px; }
        canvas { display: none; }
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
            <canvas id="qrCanvas"></canvas>

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
    const video = document.getElementById('preview');
    const canvas = document.getElementById('qrCanvas');
    const ctx = canvas.getContext('2d');
    let stream;

    try {
        // Prefer back camera
        stream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: { ideal: "environment" } } });
    } catch (e) {
        // fallback to any camera
        stream = await navigator.mediaDevices.getUserMedia({ video: true });
    }

    video.srcObject = stream;
    await video.play();

    const scanQR = () => {
        if (video.readyState === video.HAVE_ENOUGH_DATA) {
            canvas.width = video.videoWidth;
            canvas.height = video.videoHeight;
            ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
            const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
            const code = jsQR(imageData.data, imageData.width, imageData.height);
            if (code) {
                stream.getTracks().forEach(track => track.stop());
                window.location.href = code.data; // follow QR link
                return;
            }
        }
        requestAnimationFrame(scanQR);
    };

    scanQR();
});
</script>
</body>
</html>
