<!DOCTYPE html>
<html>
<head>
    <title>QR Code Scanner</title>
    <script src="https://unpkg.com/html5-qrcode@2.3.7/minified/html5-qrcode.min.js"></script>
</head>
<body>
    <h2>QR Code Scanner</h2>

    <!-- Button to start scanning -->
    <button id="start-scan-btn">Scan QR Code</button>

    <!-- Camera feed will appear here -->
    <div id="qr-reader" style="width:320px;height:240px;margin-top:10px;"></div>

    <!-- Optional: file input fallback -->
    <input type="file" id="qr-input-file" accept="image/*" capture>

    <script>
        let html5QrScanner;

        document.getElementById("start-scan-btn").addEventListener("click", () => {
            if (!html5QrScanner) {
                html5QrScanner = new Html5Qrcode("qr-reader");
            }

            Html5Qrcode.getCameras().then(cameras => {
                if (cameras && cameras.length) {
                    // Use the back camera if available
                    const cameraId = cameras.find(cam => cam.label.toLowerCase().includes("back"))?.id || cameras[0].id;

                    html5QrScanner.start(
                        cameraId,
                        { fps: 10, qrbox: 250 },
                        qrMessage => {
                            console.log("QR Code detected:", qrMessage);
                            window.location.href = qrMessage; // follow the link
                            html5QrScanner.stop(); // stop scanning after success
                        },
                        errorMessage => {
                            console.warn("QR scan error:", errorMessage);
                        }
                    ).catch(err => console.error("Camera start failed:", err));
                }
            }).catch(err => console.error("Camera not found:", err));
        });

        // File input fallback
        const fileInput = document.getElementById('qr-input-file');
        fileInput.addEventListener('change', e => {
            if (e.target.files.length === 0) return;
            const imageFile = e.target.files[0];
            if (!html5QrScanner) {
                html5QrScanner = new Html5Qrcode("qr-reader");
            }
            html5QrScanner.scanFile(imageFile, true)
                .then(qrCodeMessage => {
                    console.log("QR Code from file:", qrCodeMessage);
                    window.location.href = qrCodeMessage;
                })
                .catch(err => console.error("File scan error:", err));
        });
    </script>
</body>
</html>
