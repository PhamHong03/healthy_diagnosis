# 🌐 API Server Configuration (BASE_URL)

This project uses Retrofit to connect to a backend API server. By default, the server URL is set as:

private const val BASE_URL = "http://192.168.70.24:5000/"

🔧 How to Update BASE_URL

👉 You need to update the IP address (192.168.70.24) to match your own server's IP address or domain. This ensures your app communicates with the correct server.

📍 Files to Update (5 locations):
No.	                File	                                      Description
1️⃣	core/utils/exportDiagnosisToPdf.kt	                Exports diagnosis result to PDF
2️⃣	di/NetworkModule.kt	Global                          Retrofit and networking setup
3️⃣	presentation/screen/doctors/PatientDetail.kt	      Screen displaying patient details
4️⃣	presentation/screen/doctors/ResultScreen.kt	        Screen displaying diagnosis results
5️⃣	res/xml/network-security-config.xml	                Network security policy for cleartext IP

// Before
private const val BASE_URL = "http://192.168.70.24:5000/"

// After (example IP: 192.168.1.100)
private const val BASE_URL = "http://192.168.1.100:5000/"
⚠️ Note: If you're using a local IP like 192.168.x.x, make sure your Android device is on the same network as the server.


🌍 Using a Public Domain
If you are deploying your backend to a live server (e.g. https://api.yourdomain.com/), make sure:

You enable HTTPS

CORS is properly configured

Your domain is whitelisted in your network security config

Example network-security-config.xml for public domain:

<domain-config cleartextTrafficPermitted="false">
    <domain includeSubdomains="true">yourdomain.com</domain>
</domain-config>

Let me know if you'd like this content saved to a real file or packaged with the rest of your documentation.
