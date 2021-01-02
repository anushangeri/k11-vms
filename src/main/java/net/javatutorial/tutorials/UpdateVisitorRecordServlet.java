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

import net.javatutorial.DAO.VMSManagerDAO;
import net.javatutorial.entity.Visitor;

/**
 * Servlet implementation class AddEmployeeServlet
 */
public class UpdateVisitorRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String vmsId = (String) request.getParameter("vmsId");
		Visitor v = null;
		String message = "VMS ID of visitor is unavailable, please add visitor record.";
		if(vmsId != null && !StringUtils.isEmpty(vmsId)) {
			//retrieve Visitor object
			v = VMSManagerDAO.retrieveByVmsId(vmsId);
			//update Visitor object with current system time as time out
			message = VMSManagerDAO.updateVisitorTimeOut(v);
			
		}
		request.setAttribute("message", message);
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
