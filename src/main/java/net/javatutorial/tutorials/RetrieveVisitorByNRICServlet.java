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

import net.javatutorial.DAO.DropdownListManagerDAO;
import net.javatutorial.DAO.SiteManagerDAO;
import net.javatutorial.DAO.VMSManagerDAO;
import net.javatutorial.entity.Visitor;
import net.javatutorial.entity.Dropdown;
import net.javatutorial.entity.Site;

/**
 * Servlet implementation class RetrieveVisitorByNRICServlet
 * To retrieve latest visitor record to populate in addVisitor.jsp
 * If the visitor object is empty, then look at the google sheets
 */
public class RetrieveVisitorByNRICServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usertype = (String) request.getSession(false).getAttribute("usertype");
		String idNo = (String) request.getSession(false).getAttribute("idNo");
		
		//from client login view
		String vmsId = request.getParameter("vmsId");
		String status = request.getParameter("status");
		
		ArrayList<Visitor> vList = null;
		Visitor v = null;
		
		if(usertype == null) {
			if(!StringUtils.isEmpty(idNo)) {
				vList = VMSManagerDAO.retrieveByNameIDPopulate(idNo);
				if(vList != null && vList.size() > 0) {
					v = vList.get(0);
				}
			}
		}
		else {
			v = VMSManagerDAO.retrieveByVmsId(vmsId);
			
		}
		ArrayList<Site> siteDropdown = SiteManagerDAO.retrieveAll();
		ArrayList<Dropdown> visitPurpose = DropdownListManagerDAO.retrieveByDropdownKey("VISIT_PURPOSE");
		
		request.setAttribute("visitorLatRec", v);
		request.setAttribute("siteDropdown", siteDropdown);
		request.setAttribute("visitPurpose", visitPurpose);
		request.setAttribute("status", status);
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
