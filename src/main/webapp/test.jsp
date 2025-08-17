<!DOCTYPE html>
<html>
<head>
    <title>QR Code Scanner</title>
    <script src="https://unpkg.com/html5-qrcode@2.3.7/minified/html5-qrcode.min.js"></script>
</head>
<body>
    <h2>QR Code Scanner</h2>

    <!-- This div will show the live camera feed -->
    <div id="qr-reader" style="width:320px;height:240px;"></div>

    <!-- Optional: fallback for choosing a file -->
    <input type="file" id="qr-input-file" accept="image/*" capture>

    <script>
        // Use the Html5QrcodeScanner which creates a start button automatically
        const html5QrScanner = new Html5QrcodeScanner(
            "qr-reader",
            { fps: 10, qrbox: 250, rememberLastUsedCamera: true },
            /* verbose= */ false
        );

        function onScanSuccess(qrMessage) {
            console.log("QR Code detected:", qrMessage);
            window.location.href = qrMessage; // automatically follow link
        }

        html5QrScanner.render(onScanSuccess);

        // File input fallback
        const fileInput = document.getElementById('qr-input-file');
        fileInput.addEventListener('change', e => {
            if (e.target.files.length === 0) return;
            const imageFile = e.target.files[0];
            Html5Qrcode.getCameras().then(cameras => {
                const html5QrCode = new Html5Qrcode("qr-reader");
                html5QrCode.scanFile(imageFile, true)
                    .then(qrCodeMessage => {
                        console.log("QR Code from file:", qrCodeMessage);
                        window.location.href = qrCodeMessage;
                    })
                    .catch(err => console.error("File scan error:", err));
            });
        });
    </script>
</body>
</html>
