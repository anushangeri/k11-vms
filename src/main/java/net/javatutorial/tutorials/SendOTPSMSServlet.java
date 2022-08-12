package net.javatutorial.tutorials;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.javatutorial.DAO.DropdownListManagerDAO;
import net.javatutorial.DAO.SiteManagerDAO;
import net.javatutorial.entity.Dropdown;
import net.javatutorial.entity.Site;
import net.javatutorial.entity.Visitor;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Servlet implementation class SendOTPSMSServlet
 */
public class SendOTPSMSServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


//		int nextVal = 1; // this value does not matter since we are not adding visitor yet
//
//		String vmsId = "" + nextVal;
//		String name = request.getParameter("name").trim();
//		String companyName = request.getParameter("companyName").trim();
//		String site = request.getParameter("siteVisiting").trim();
//		String idType = null;
//		String idNo = request.getParameter("idNo");
		String mobileNo = request.getParameter("mobileNo");
//		String vehicleNo = request.getParameter("vehicleNo");
//		String hostName = request.getParameter("hostName");
//		String hostNo = request.getParameter("hostNo");
//		String visitorCardId = request.getParameter("visitorCardId");
//		String covidDec = "";
//		String visitPurpose = request.getParameter("visitPurpose");
//		String temperature = "";
//		String remarks = request.getParameter("remarks");
//		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Singapore"));
//		Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());
//
//		String officerIdNo = request.getParameter("officerIdNo");
		System.out.println("MobileNo from javascript: " +mobileNo);
		Random random = new Random();
        int num = random.nextInt(100000);
        String otp = String.format("%05d", num);
		
		
		sendOTP(mobileNo, otp);

//		Visitor v = new Visitor(vmsId, name, companyName, site, idType, idNo, mobileNo, vehicleNo, hostName, hostNo,
//				visitorCardId, covidDec, remarks, visitPurpose, temperature, officerIdNo, timestamp);

//		ArrayList<Site> siteDropdown = SiteManagerDAO.retrieveAll();
//		ArrayList<Dropdown> visitPurposes = DropdownListManagerDAO.retrieveByDropdownKey("VISIT_PURPOSE");

//		request.setAttribute("visitorLatRec", v);
//		request.setAttribute("siteDropdown", siteDropdown);
//		request.setAttribute("visitPurpose", visitPurposes);
		request.setAttribute("otpGenerated", otp);
		//RequestDispatcher rd = request.getRequestDispatcher("addVisitor.jsp");
		//rd.forward(request, response);
		request.getRequestDispatcher("addVisitor.jsp").forward(request, response);
	}

	@Override
	public void init() throws ServletException {
		System.out.println("Servlet " + this.getServletName() + " has started");
	}

	@Override
	public void destroy() {
		System.out.println("Servlet " + this.getServletName() + " has stopped");
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public static void sendOTP(String mobileNo, String otp) {
		try {
			
			OkHttpClient client = new OkHttpClient();
			RequestBody formBody = new FormBody.Builder()
				      .add("to", "+16476093381")
				      .add("message", "Sahngeri Test")
				      .build();
		    Request request = new Request.Builder()
		            .url("https://d5f0629a-0abd-400f-9059-7a996b7da98a:QKnJYGZLd7Rrx2UQyzrqvg@api.blower.io/messages")
		            .addHeader("Authorization", String.format("Bearer %s", "b98d1f54-768f-4907-af63-9bb610effe0d"))
		            .post(formBody)
		            .build();

		    Response response = client.newCall(request).execute();
		    System.out.println(response.body().string());
			
			
//			URL url = new URL("https://d5f0629a-0abd-400f-9059-7a996b7da98a:QKnJYGZLd7Rrx2UQyzrqvg@api.blower.io/messages");
//			HttpURLConnection http = (HttpURLConnection)url.openConnection();
//			http.setRequestProperty("Authorization","Bearer b98d1f54-768f-4907-af63-9bb610effe0d");
//			http.setRequestMethod("POST");
//			http.setDoOutput(true);
//			http.setRequestProperty("Content-Type", "application/json");
//			http.setRequestProperty("Accept", "application/json");
//
//			String data = "{to: +16476093381, message: Shangeri test SMS}";
//
//			byte[] out = data.getBytes(StandardCharsets.UTF_8);
//
//			OutputStream stream = http.getOutputStream();
//			stream.write(out);
//
//			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
//			http.disconnect();
			System.out.println("OTP send");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
