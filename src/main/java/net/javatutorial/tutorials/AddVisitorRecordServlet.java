package net.javatutorial.tutorials;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import net.javatutorial.DAO.DropdownListManagerDAO;
import net.javatutorial.DAO.SiteManagerDAO;
import net.javatutorial.DAO.VMSManagerDAO;
import net.javatutorial.entity.Dropdown;
import net.javatutorial.entity.Site;
import net.javatutorial.entity.Visitor;

/**
 * Servlet implementation class AddVisitorRecordServlet
 */

public class AddVisitorRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nextVal = VMSManagerDAO.getNextVal();
		
		String message = "Something went wrong or OTP was wrong, please try again.";
		
		String vmsId = "" + nextVal;
		String name = request.getParameter("name").trim();
		String companyName = request.getParameter("companyName").trim();
		String site = request.getParameter("siteVisiting").trim();
		String idType = null;
		String idNo = request.getParameter("idNo");
		String mobileNo = request.getParameter("processedMobileNo");
		String vehicleNo = request.getParameter("vehicleNo");
		String hostName = request.getParameter("hostName");
		String hostNo = request.getParameter("processedHostNo");
		String visitorCardId = request.getParameter("visitorCardId");
		String covidDec = "";
		String visitPurpose = request.getParameter("visitPurpose");
		String temperature = "";
		String remarks = request.getParameter("remarks");
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Singapore")) ;
		Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());

		String createdBy = (String) request.getSession(false).getAttribute("idNo");
		
		//Step 1: verify officer login (if parameters not empty) and visitPurpose = GovtAgency
		String officerIdNo = request.getParameter("officerIdNo");
		
		String otpGenerated = request.getParameter("otpGenerated");
		String otpEntered = request.getParameter("otpEntered"); 
		
		// Processing Image captured in the form
		// Extract file data
		byte[] b=null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory(); 
			 
		      ServletFileUpload sfu = new ServletFileUpload(factory); 
		      List items = sfu.parseRequest(request); 
		 
		      Iterator iter = items.iterator(); 
		        
		      while (iter.hasNext()) { 
		         FileItem item = (FileItem) iter.next(); 
		         if (!item.isFormField()) { 
		              b = item.get(); 
		          } 
		      } 
		}
		catch(Exception e) {
			message = message + " - " + e;
		}
		Visitor v = null;
		
		if(otpGenerated != null && !StringUtils.isEmpty(otpGenerated) && otpEntered != null 
				&& !StringUtils.isEmpty(otpEntered) && otpGenerated.equals(otpEntered)) {
			if(officerIdNo != null && !StringUtils.isEmpty(officerIdNo) && visitPurpose.equals("GOVERNMENT AGENCY")) {
				//Step 2: add visitor
				v = new Visitor( vmsId,  name,  companyName, site, idType, idNo,  mobileNo,  vehicleNo,
						 hostName,  hostNo,  visitorCardId, covidDec, remarks, visitPurpose,  
						 temperature, officerIdNo , timestamp, createdBy, timestamp, createdBy, timestamp);
				message = VMSManagerDAO.addVisitor(v);
			}
			else if(!visitPurpose.equals("GOVERNMENT AGENCY")) {
				//Step 2: add visitor if not GOVT AGENCY
				v = new Visitor( vmsId,  name,  companyName, site, idType, idNo,  mobileNo,  vehicleNo,
						 hostName,  hostNo,  visitorCardId, covidDec, remarks, visitPurpose,  
						 temperature, null , timestamp, createdBy, timestamp, createdBy, timestamp);
				message = VMSManagerDAO.addVisitor(v);
			}
			request.setAttribute("message", message);
			// Redirect to view visitor servlet to query all the visitors again.
			response.sendRedirect("/vms");
		}
		else {
			//if OTP verify fail, return to add page, populate parameters
			ArrayList<Site> siteDropdown = SiteManagerDAO.retrieveAll();
			ArrayList<Dropdown> visitPurposes = DropdownListManagerDAO.retrieveByDropdownKey("VISIT_PURPOSE");
			request.setAttribute("responseObj", message);
			request.setAttribute("visitorLatRec", v);
			request.setAttribute("siteDropdown", siteDropdown);
			request.setAttribute("visitPurpose", visitPurposes);
			RequestDispatcher rd = request.getRequestDispatcher("addVisitor.jsp");
			rd.forward(request, response);
		}
		
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
