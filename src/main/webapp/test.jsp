<!DOCTYPE html>
<html>
<head>
  <title>QR Code Scanner</title>
  <script type="text/javascript" src="instascan.min.js"></script>
  <style>
    video { width: 100%; max-width: 400px; border: 1px solid #ccc; }
  </style>
</head>
<body>
  <h2>Scan QR Code</h2>
  <video id="preview"></video>

  <script>
    async function startScanner() {
      const videoElem = document.getElementById('preview');
      const scanner = new Instascan.Scanner({ video: videoElem, mirror: false });

      scanner.addListener('scan', function(content) {
        alert("Scanned QR Code: " + content);
        scanner.stop(); // Stop camera immediately after scan
      });

      try {
        const cameras = await Instascan.Camera.getCameras();
        if (cameras.length > 0) {
          const backCamera = cameras.find(cam => cam.name.toLowerCase().includes('back')) || cameras[0];
          scanner.start(backCamera);
        } else {
          alert("No camera found on this device.");
        }
      } catch (e) {
        alert("Camera access error: " + e);
        console.error(e);
      }
    }

    // Start scanner when page loads
    window.addEventListener('DOMContentLoaded', startScanner);
  </script>
</body>
</html>
