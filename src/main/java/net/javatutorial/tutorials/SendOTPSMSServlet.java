package net.javatutorial.tutorials;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.javatutorial.DAO.DropdownListManagerDAO;
import net.javatutorial.DAO.SiteManagerDAO;
import net.javatutorial.DAO.VMSManagerDAO;
import net.javatutorial.entity.Dropdown;
import net.javatutorial.entity.Site;
import net.javatutorial.entity.Visitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Servlet implementation class SendOTPSMSServlet
 */
public class SendOTPSMSServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		int nextVal = 1; //this value does not matter since we are not adding visitor yet
		
		String vmsId = "" + nextVal;
		String name = request.getParameter("name").trim();
		String companyName = request.getParameter("companyName").trim();
		String site = request.getParameter("siteVisiting").trim();
		String idType = null;
		String idNo = request.getParameter("idNo");
		String mobileNo = request.getParameter("mobileNo");
		String vehicleNo = request.getParameter("vehicleNo");
		String hostName = request.getParameter("hostName");
		String hostNo = request.getParameter("hostNo");
		String visitorCardId = request.getParameter("visitorCardId");
		String covidDec = "";
		String visitPurpose = request.getParameter("visitPurpose");
		String temperature = "";
		String remarks = request.getParameter("remarks");
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Singapore")) ;
		Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());

		String officerIdNo = request.getParameter("officerIdNo");
		
		String otpGenerated = sendSms(mobileNo);
		
		Visitor v = new Visitor( vmsId,  name,  companyName, site, idType, idNo,  mobileNo,  vehicleNo,
				 hostName,  hostNo,  visitorCardId, covidDec, remarks, visitPurpose,  
				 temperature, officerIdNo , timestamp);
		
		ArrayList<Site> siteDropdown = SiteManagerDAO.retrieveAll();
		ArrayList<Dropdown> visitPurposes = DropdownListManagerDAO.retrieveByDropdownKey("VISIT_PURPOSE");
		
		request.setAttribute("visitorLatRec", v);
		request.setAttribute("siteDropdown", siteDropdown);
		request.setAttribute("visitPurpose", visitPurposes);
		request.setAttribute("otpGenerated", otpGenerated);
		RequestDispatcher rd = request.getRequestDispatcher("addVisitor.jsp");
        rd.forward(request, response);
	}
	@Override
	public void init() throws ServletException {
		System.out.println("Servlet " + this.getServletName() + " has started");
	}

	@Override
	public void destroy() {
		System.out.println("Servlet " + this.getServletName() + " has stopped");
	}
	public String sendSms(String mobileNo) {
//		try {
//			// Construct data
//			String apiKey = "apikey=" + "NzY0ZDQ5NjczODU3NTI3NjRmNmI2MzY3NTY2MzZiNzI=";
//			Random rand = new Random();
//			int otpGenerated = rand.nextInt(999999);
//			String message = "&message=" + "Hi visitor, your OTP is " + otpGenerated;
//			String sender = "&sender=" + "K11 VMS";
//			String numbers = "&numbers=" + mobileNo;
//			
//			// Send data
//			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
//			String data = apiKey + numbers + message + sender;
//			conn.setDoOutput(true);
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
//			conn.getOutputStream().write(data.getBytes("UTF-8"));
//			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			final StringBuffer stringBuffer = new StringBuffer();
//			String line;
//			while ((line = rd.readLine()) != null) {
//				stringBuffer.append(line);
//			}
//			rd.close();
//			System.out.println(stringBuffer.toString());
//			return otpGenerated + "";
//		} catch (Exception e) {
//			System.out.println("Error SMS "+e);
//			return "Error "+e;
//		}
		
		try {
			Scanner sc = new Scanner(System.in);

			
			String host = "localhost";
			int port = 9500;
			String username = "admin";
			String password = "abc123";
			
			/**
			 * Connect to Ozeki NG SMS Gateway and logging in.
			 */
			
			MyOzSmsClient osc = new MyOzSmsClient(host, port);
			osc.login(username, password);		
			System.out.println("SMS message:");
			
			/**
			 * If logged in send "Text message" to number "+00112233"
			 */
			
			if(osc.isLoggedIn()) {
					
				osc.sendMessage("+16476093381", "Test Message");				
				
			}
			
			sc.nextLine();
			osc.logout();
			
			/**
			 * Receiving message:
			 * 
			 * If you want to receive messages you can use doOnMessageReceived in MyOzSmsClient.java
			 * That's an event, which runs automatically when a message is received. 
			 */
			
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return mobileNo;
	}
}
