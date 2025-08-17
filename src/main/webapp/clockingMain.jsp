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

    // Set session timeout to 4 hours (in seconds)
    session.setMaxInactiveInterval(4 * 60 * 60);

    // Clear session if ?action=clear is in URL
    String action = request.getParameter("action");
    if ("clear".equals(action)) {
        session.invalidate();
        response.sendRedirect("clockingMain.jsp");
        return; // stop further rendering
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Officer Check-In</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
    <script src="https://unpkg.com/html5-qrcode/minified/html5-qrcode.min.js"></script>

    <script>
        function validateNRIC(nric) {
            const regex = /^[A-Za-z]\d{7}[A-Za-z]$/;
            return regex.test(nric);
        }

        function startQrScanner() {
            const name = document.getElementById("officerName").value.trim();
            const nric = document.getElementById("officerNric").value.trim();

            if (!name) {
                alert("Please enter Officer Name.");
                return;
            }
            if (!validateNRIC(nric)) {
                alert("Invalid NRIC. Format: Letter + 7 digits + Letter (e.g. S1234567D)");
                return;
            }

            // Show QR reader
            const qrReader = document.getElementById("qr-reader");
            qrReader.style.display = "block";

            // Initialize QR scanner
            const html5QrCode = new Html5Qrcode("qr-reader");
            html5QrCode.start(
                { facingMode: "environment" },
                { fps: 10, qrbox: 250 },
                (decodedText) => {
                    alert("Scanned: " + decodedText);
                    html5QrCode.stop();
                },
                (errorMessage) => {
                    console.log("QR scan error: " + errorMessage);
                }
            ).catch(err => alert("Cannot start camera: " + err));
        }
    </script>

    <style>
        /* Remove form border */
        form {
            border: none;
            padding: 0;
            margin: 0;
        }
        #qr-reader { width: 300px; margin-top: 20px; display: none; }
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

                <button type="button" class="btn btn-warning btn-lg btn-block" onclick="startQrScanner()">
                    Scan QR Code
                </button>

                <div id="qr-reader"></div>

                <br><br>
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
</body>
</html>