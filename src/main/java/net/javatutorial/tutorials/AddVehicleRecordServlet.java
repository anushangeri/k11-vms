package net.javatutorial.tutorials;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.javatutorial.DAO.VehMSManagerDAO;
import net.javatutorial.entity.Vehicle;

/**
 * Servlet implementation class AddEmployeeServlet
 */
public class AddVehicleRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nextVal = VehMSManagerDAO.getNextVal();
		
		String vehicleId = "" + nextVal;
		String name = request.getParameter("name").trim();
		String companyName = request.getParameter("companyName").trim();
		String idType = request.getParameter("idType");
		String idNo = request.getParameter("idNo");
		String mobileNo = request.getParameter("mobileNo").length() > 0 ? request.getParameter("mobileNo") : null;
		String primeMoverNo = request.getParameter("primeMoverNo").length() > 0 ? request.getParameter("primeMoverNo") : null;
		String containerNo = request.getParameter("containerNo").length() > 0 ? request.getParameter("containerNo") : null;
		String loadedNoLoaded = request.getParameter("loadedNoLoaded");
		String covidDec = request.getParameter("coviddeclaration");
		String lorryChetNumber = request.getParameter("lorryChetNumber").length() > 0 ? request.getParameter("lorryChetNumber") : null;
		String deliveryNoticeNumber = request.getParameter("deliveryNoticeNumber").length() > 0 ? request.getParameter("deliveryNoticeNumber") : null;
		String visitPurpose = request.getParameter("visitPurpose");
		String temperature = request.getParameter("temperature");
		String sealNo = request.getParameter("sealNo").length() > 0 ? request.getParameter("sealNo") : null;
		String containerSize = request.getParameter("containerSize").length() > 0 ? request.getParameter("containerSize") : null;
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Singapore")) ;
		Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());

		Vehicle v = new Vehicle( vehicleId,  name,  companyName, idType, idNo,  mobileNo,  primeMoverNo,
				containerNo,  loadedNoLoaded, covidDec, lorryChetNumber, deliveryNoticeNumber,  
				visitPurpose, temperature, sealNo, containerSize,  timestamp);
		
		String message = VehMSManagerDAO.addVisitor(v);
		
		ArrayList<String> responseObj = new ArrayList<String>();
		responseObj.add(message + " " + name);
		request.setAttribute("responseObj", responseObj);
		// Redirect to view vehicle servlet to query all the vehicle again.
		response.sendRedirect("/vehms");
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
