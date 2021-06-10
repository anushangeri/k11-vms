package net.javatutorial.tutorials;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormatter;

import net.javatutorial.DAO.ClientAccountManagerDAO;
import net.javatutorial.DAO.VMSManagerDAO;
import net.javatutorial.entity.ClientAccount;
import net.javatutorial.entity.Visitor;

import java.util.Calendar;
import java.util.Locale;
import static java.util.Calendar.*;
import java.util.Date;

/**
 * Servlet implementation class AddEmployeeServlet
 */
public class AddVisitorRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nextVal = VMSManagerDAO.getNextVal();
		
		String vmsId = "" + nextVal;
		String name = request.getParameter("name").trim();
		String companyName = request.getParameter("companyName").trim();
		String site = request.getParameter("siteVisiting").trim();
		String idType = request.getParameter("idType");
		String idNo = request.getParameter("idNo");
		String mobileNo = request.getParameter("mobileNo");
		String vehicleNo = request.getParameter("vehicleNo");
		String hostName = request.getParameter("hostName");
		String hostNo = request.getParameter("hostNo");
		String visitorCardId = request.getParameter("visitorCardId");
		String covidDec = request.getParameter("coviddeclaration");
		String visitPurpose = request.getParameter("visitPurpose");
		String temperature = request.getParameter("temperature");
		String remarks = request.getParameter("remarks");
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Singapore")) ;
		Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());

		//Step 1: verify officer login (if parameters not empty) and visitPurpose = GovtAgency
		String officerIdNo = request.getParameter("officerIdNo");
		String officerpsw = request.getParameter("officerpsw");
		
		//retrieving the hashed password by DB based on idNo entered by user
		ArrayList<ClientAccount> vList = ClientAccountManagerDAO.retrieveByID(officerIdNo);
		boolean verified = false;
		String key = " ";
		String salt = " ";
		ClientAccount c = null;
		if(vList != null && vList.size() > 0 ) {
			c = vList.get(0);
			if(c != null) {
				key = c.getPassword();
				salt = c.getSalt();
				verified = PasswordUtils.verifyPassword(officerpsw, key, salt);
			}
		}
		Visitor v = new Visitor( vmsId,  name,  companyName, site, idType, idNo,  mobileNo,  vehicleNo,
				 hostName,  hostNo,  visitorCardId, covidDec, remarks, visitPurpose,  temperature,  timestamp);
		String message = "Something went wrong, please try again.";
		if(verified && visitPurpose.equals("GOVERNMENT AGENCY")) {
			//Step 2: add visitor
			message = VMSManagerDAO.addVisitor(v);
		}
		else {
			//Step 1a: if verify fail, return to add page, populate parameters
			request.setAttribute("responseObj", message);
			request.setAttribute("visitorLatRec", v);
			RequestDispatcher rd = request.getRequestDispatcher("addVisitor.jsp");
			rd.forward(request, response);
		}
		
		ArrayList<String> responseObj = new ArrayList<String>();
		responseObj.add(message + " " + name);
		request.setAttribute("responseObj", responseObj);
		// Redirect to view visitor servlet to query all the visitors again.
		response.sendRedirect("/vms");
	}
	@Override
	public void init() throws ServletException {
		System.out.println("Servlet " + this.getServletName() + " has started");
	}

	@Override
	public void destroy() {
		System.out.println("Servlet " + this.getServletName() + " has stopped");
	}

}
