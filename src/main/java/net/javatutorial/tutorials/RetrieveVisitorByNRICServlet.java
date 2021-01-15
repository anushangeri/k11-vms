package net.javatutorial.tutorials;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;

import net.javatutorial.DAO.VMSManagerDAO;
import net.javatutorial.entity.Visitor;

/**
 * Servlet implementation class RetrieveVisitorByNRICServlet
 * To retrieve latest visitor record to populate in addVisitor.jsp
 * If the visitor object is empty, then look at the google sheets
 */
public class RetrieveVisitorByNRICServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idNo = (String) request.getSession(false).getAttribute("usertype");
		ArrayList<Visitor> vList = null;
		Visitor v = null;
		
		if(!StringUtils.isEmpty(idNo)) {
			if(!idNo.toUpperCase().equals("K11ADMIN")) {
				vList = VMSManagerDAO.retrieveByNRIC(idNo);
				if(vList != null && vList.size() > 0) {
					v = vList.get(0);
				}
				if(v == null) {
					SpreadsheetService k11VMS = new SpreadsheetService("K11 VMS");
					try {
						String sheetUrl = "https://spreadsheets.google.com/feeds/list/1vJBvGXxyOUCz5ZYZtQCIet5qmxamihTYLCjv51os070/1/public/values";
	
						// Use this String as url
						URL url = new URL(sheetUrl);
	
						// Get Feed of Spreadsheet url
						ListFeed lf = k11VMS.getFeed(url, ListFeed.class);
						
						String icNumber = "";
						String firstName = "";
						String lastName = "";
						String mobileNo = "";
						String vehicleNo = "";
						String hostName = "";
						String hostNo = "";
						String visitorCardId = "";
	
						// Iterate over feed to get cell value
						for (ListEntry le : lf.getEntries()) {
							CustomElementCollection cec = le.getCustomElements();
	
							if (cec != null) {
								icNumber = cec.getValue("icnumber");
								if (icNumber != null && !StringUtils.isEmpty(icNumber)) {
									icNumber = icNumber.toUpperCase();
									icNumber = icNumber.trim();
									// Compare with user input - to populate the rest
									if (icNumber.equals(idNo)) {
										firstName = cec.getValue("firstname");
										lastName = cec.getValue("lastname");
										mobileNo = cec.getValue("mobile");
										vehicleNo = cec.getValue("vehiclenumber");
										hostName = cec.getValue("hostname");
										hostNo = cec.getValue("hostnumber");
										visitorCardId = cec.getValue("visitorcardid");
										
										v = new Visitor( null,  firstName,  lastName,  idNo,  mobileNo,  vehicleNo,
												 hostName,  hostNo,  visitorCardId,  null, null);
									}
								}
	
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		request.setAttribute("visitorLatRec", v);
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

}
