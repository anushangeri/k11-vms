<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>QR Code Scanner</title>
<style>
  #cameraFeed {
    width: 100%;
    max-width: 400px;
    border: 1px solid #ccc;
    display: none;
  }
  #scanButton {
    margin: 10px 0;
    padding: 10px 20px;
    font-size: 16px;
  }
</style>
</head>
<body>
<h1>QR Code Scanner</h1>
<button id="scanButton">Scan QR Code</button>
<video id="cameraFeed" autoplay></video>

<script src="https://cdn.jsdelivr.net/npm/jsqr/dist/jsQR.js"></script>
<script>
const video = document.getElementById('cameraFeed');
const scanButton = document.getElementById('scanButton');
let videoStream;

async function startCamera() {
  try {
    videoStream = await navigator.mediaDevices.getUserMedia({
      video: { facingMode: "environment" }
    });
    video.srcObject = videoStream;
    video.style.display = 'block';
    scanFrames();
  } catch (err) {
    alert("Cannot access camera. iOS Chrome/Edge/Firefox do not support camera access. Use Safari.");
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
        window.location.href = code.data; // follow QR link
        return;
      }
    }
    requestAnimationFrame(scan);
  }

  scan();
}

scanButton.addEventListener('click', startCamera);
</script>
</body>
</html>
