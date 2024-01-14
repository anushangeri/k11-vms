package net.javatutorial.tutorials;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		System.out.println("nextVal: " + nextVal);
		String message = null;
		
		String vmsId = "" + nextVal;
		String name = "";
		String companyName = "";
		String site ="";
		String idType = null;
		String idNo = "";
		String mobileNo = "";
		String vehicleNo ="";
		String hostName = "";
		String hostNo = "";
		String visitorCardId = "";
		String covidDec = "";
		String visitPurpose ="";
		String temperature = "";
		String remarks = "";
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Singapore")) ;
		Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());

		String createdBy = (String) request.getSession(false).getAttribute("idNo");
		
		//Step 1: verify officer login (if parameters not empty) and visitPurpose = GovtAgency
		String officerIdNo = "";
		
		String otpGenerated = "";
		String otpEntered = "";
		
		// Processing Image captured in the form
		Visitor v = null;
		
		// Check if the request contains multipart content (file upload)
	    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	    if (isMultipart) {
	    	// Create a factory for disk-based file items
	        DiskFileItemFactory factory = new DiskFileItemFactory();

	        // Set factory constraints
	        factory.setSizeThreshold(1024 * 1024); // 1MB threshold
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

	        // Create a new file upload handler
	        ServletFileUpload upload = new ServletFileUpload(factory);

	        try {
	            // Parse the request to get file items
	            List<FileItem> items = upload.parseRequest(request);

	            // Process the uploaded items
	            for (FileItem item : items) {
	            	 String fieldName = item.getFieldName();
	            	 System.out.println("fieldName: " + fieldName);
	                    // Use switch-case to handle different form fields
	                    switch (fieldName) {
	                        case "name":
	                        	name = item.getString();
	                        case "companyName":
	                        	companyName = item.getString();
	                        case "site":
	                        	site = item.getString();
	                        case "idNo":
	                        	idNo = item.getString();
	                        case "mobileNo":
	                        	mobileNo = item.getString();
	                        case "vehicleNo":
	                        	vehicleNo = item.getString();
	                        case "hostName":
	                        	hostName = item.getString();
	                        case "hostNo":
	                        	hostNo = item.getString();
	                        case "visitorCardId":
	                        	visitorCardId = item.getString();
	                        case "visitPurpose":
	                        	visitPurpose = item.getString();
	                        case "remarks":
	                        	remarks = item.getString();
	                        case "officerIdNo":
	                        	officerIdNo = item.getString();
	                        case "otpGenerated":
	                        	otpGenerated = item.getString();
	                        case "otpEntered":
	                        	otpEntered = item.getString();
	                        case "visitorImage":
	                            // Process file upload
	                            String visitorImage = new File(item.getName()).getName();
	                            InputStream fileContent = item.getInputStream();
	                            // You can save the file or perform other actions here
	                            System.out.println("File Field Name: " + fieldName + ", File Name: " + visitorImage + "<br>");
	                            break;
	                        default:
	                            response.getWriter().println("Unknown Field: " + fieldName + "<br>");
	                            break;
	                    }
	            }
	        } catch (Exception e) {
	            message = message + " "+ e.getMessage();
	        }
	    }
	    else {
			message = "there is an issue with the upload, visitor record not added. Approach guardhouse. - " + message;
			ArrayList<Site> siteDropdown = SiteManagerDAO.retrieveAll();
			ArrayList<Dropdown> visitPurposes = DropdownListManagerDAO.retrieveByDropdownKey("VISIT_PURPOSE");
			request.setAttribute("responseObj", message);
			request.setAttribute("visitorLatRec", v);
			request.setAttribute("siteDropdown", siteDropdown);
			request.setAttribute("visitPurpose", visitPurposes);
			RequestDispatcher rd = request.getRequestDispatcher("addVisitor.jsp");
			rd.forward(request, response);
		}
		if(otpGenerated != null && !StringUtils.isEmpty(otpGenerated) && otpEntered != null 
				&& !StringUtils.isEmpty(otpEntered) && otpGenerated.equals(otpEntered)
				&& message != null) {
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
			message = message + " - there is an issue with OTP, visitor record not added. Approach guardhouse.";
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
