<!DOCTYPE html>
<html>
<head>
    <title>QR Code Scanner</title>
    <script src="https://unpkg.com/html5-qrcode@2.3.7/minified/html5-qrcode.min.js"></script>
</head>
<body>
    <h2>QR Code Scanner</h2>
    <div id="reader" style="width:320px;height:240px;border:1px solid #ccc;"></div>
    <button id="start-scan">Scan QR Code</button>
    <input type="file" id="qr-input-file" accept="image/*" capture>

    <script>
        const html5QrCode = new Html5Qrcode("reader");
        const startBtn = document.getElementById("start-scan");

        startBtn.addEventListener("click", () => {
            Html5Qrcode.getCameras().then(cameras => {
                if (cameras && cameras.length) {
                    const backCamera = cameras.find(cam => cam.label.toLowerCase().includes("back")) || cameras[0];
                    html5QrCode.start(
                        backCamera.id,
                        { fps: 10, qrbox: 250 },
                        qrCodeMessage => {
                            console.log("QR Code detected:", qrCodeMessage);
                            html5QrCode.stop();
                            window.location.href = qrCodeMessage; // follow link
                        },
                        errorMessage => {
                            console.log("Scanning error:", errorMessage);
                        }
                    ).catch(err => console.error("Camera start failed:", err));
                }
            }).catch(err => console.error("Camera access failed:", err));
        });

        // File input fallback
        const fileInput = document.getElementById('qr-input-file');
        fileInput.addEventListener('change', e => {
            if (e.target.files.length === 0) return;
            const imageFile = e.target.files[0];
            html5QrCode.scanFile(imageFile, true)
                .then(qrCodeMessage => {
                    console.log("QR Code from file:", qrCodeMessage);
                    window.location.href = qrCodeMessage;
                })
                .catch(err => console.error("File scan error:", err));
        });
    </script>
</body>
</html>
