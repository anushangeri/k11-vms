<!DOCTYPE html>
<html>
<head>
    <title>QR Code Scanner</title>
    <script src="https://cdn.jsdelivr.net/npm/jsqr@1.4.0/dist/jsQR.js"></script>
    <style>
        video {
            width: 320px;
            height: 240px;
            border: 1px solid #ccc;
        }
    </style>
</head>
<body>
    <h2>QR Code Scanner</h2>
    <video id="cameraFeed" autoplay></video>

    <script>
        async function initCamera() {
            const video = document.getElementById("cameraFeed");
            let stream;

            try {
                // Try to use back camera first
                stream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } });
            } catch (err) {
                console.error("Error accessing camera, falling back to any camera:", err);
                stream = await navigator.mediaDevices.getUserMedia({ video: true });
            }

            video.srcObject = stream;

            window.addEventListener("beforeunload", () => {
                stream.getTracks().forEach(track => track.stop());
            });

            video.onloadedmetadata = () => {
                video.play();
                scanQRCode(video, stream);
            };
        }

        function scanQRCode(video, stream) {
            const canvas = document.createElement("canvas");
            const ctx = canvas.getContext("2d");

            function scan() {
                if (video.readyState === video.HAVE_ENOUGH_DATA) {
                    canvas.width = video.videoWidth;
                    canvas.height = video.videoHeight;
                    ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
                    const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
                    const code = jsQR(imageData.data, imageData.width, imageData.height);
                    if (code) {
                        console.log("QR Code detected:", code.data);
                        stream.getTracks().forEach(track => track.stop());
                        window.location.href = code.data; // Follow the link
                        return;
                    }
                }
                requestAnimationFrame(scan);
            }

            scan();
        }

        window.addEventListener("DOMContentLoaded", initCamera);
    </script>
</body>
</html>
