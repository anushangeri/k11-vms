package net.javatutorial.tutorials;

import java.io.IOException;
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

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormatter;

import net.javatutorial.DAO.VehMSManagerDAO;
import net.javatutorial.entity.Vehicle;

/**
 * Servlet implementation class UpdateVehicleTimeRecordServlet
 * updates the lorryChetNumber for vehicle record 
 */
public class UpdateVehicleLorryChetRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String vehicleId = (String) request.getParameter("vehicleId");
		String lorryChetNumber = request.getParameter("lorryChetNumber");
		Vehicle v = null;
		String message = "Vehicle ID of visitor is unavailable, please add vehicle record.";
		if(vehicleId != null && !StringUtils.isEmpty(vehicleId)) {
			//retrieve Vehicle object
			v = VehMSManagerDAO.retrieveByVehicleId(vehicleId);
			v.setLorryChetNumber(lorryChetNumber);
			//update Vehicle object with current system time as time out
			message = VehMSManagerDAO.updateVehicleLorryChet(v);
			
		}
		request.setAttribute("message", message);
		// Redirect to view Vehicle servlet to query all the visitors again.
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
