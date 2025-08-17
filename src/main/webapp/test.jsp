<!DOCTYPE html>
<html>
<head>
    <title>QR Code Scanner</title>
    <script src="https://unpkg.com/html5-qrcode@2.3.7/minified/html5-qrcode.min.js"></script>
</head>
<body>
    <h2>QR Code Scanner</h2>
    <div id="reader" style="width:320px;height:240px;"></div>
    <input type="file" id="qr-input-file" accept="image/*" capture>

    <script>
        const html5QrCode = new Html5Qrcode("reader");

        // Start inline camera scanning
        Html5Qrcode.getCameras().then(cameras => {
            if (cameras && cameras.length) {
                // Use back camera if available
                const backCamera = cameras.find(cam => cam.label.toLowerCase().includes("back")) || cameras[0];
                html5QrCode.start(
                    backCamera.id,
                    { fps: 10, qrbox: 250 },
                    qrCodeMessage => {
                        console.log("QR Code detected:", qrCodeMessage);
                        html5QrCode.stop();
                        window.location.href = qrCodeMessage;
                    },
                    errorMessage => {
                        console.log("QR scanning error:", errorMessage);
                    }
                ).catch(err => {
                    console.error("Camera start failed:", err);
                });
            }
        }).catch(err => {
            console.error("Camera access failed:", err);
        });

        // File input scanning fallback
        const fileInput = document.getElementById('qr-input-file');
        fileInput.addEventListener('change', e => {
            if (e.target.files.length === 0) return;
            const imageFile = e.target.files[0];
            html5QrCode.scanFile(imageFile, true).then(qrCodeMessage => {
                console.log("QR Code from file:", qrCodeMessage);
                window.location.href = qrCodeMessage;
            }).catch(err => {
                console.error("File scan error:", err);
            });
        });
    </script>
</body>
</html>
