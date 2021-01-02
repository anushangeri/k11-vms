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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormatter;

import net.javatutorial.DAO.VMSManagerDAO;
import net.javatutorial.entity.Visitor;

/**
 * Servlet implementation class AddEmployeeServlet
 */
public class ViewVisitorRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idNo = (String) request.getSession(false).getAttribute("usertype");
		String message = "No visitor records available";
		ArrayList<Visitor> vList = null;
		if(!StringUtils.isEmpty(idNo)) {
			if(idNo.toUpperCase().equals("K11ADMIN")) {
				vList = VMSManagerDAO.retrieveAll();
				message = "List of visitor records";
				request.setAttribute("vList", vList);
				if(vList == null || vList.size() == 0) {
					message = "No visitor records available";
				}
			}
			else{
				vList = VMSManagerDAO.retrieveByNRIC(idNo);
				message = "List of visitor records for " + idNo;
				request.setAttribute("vList", vList);
				if(vList == null || vList.size() == 0) {
					message = "No visitor records available for " + idNo;
				}
			}
		}
		
		request.setAttribute("message", message);
        RequestDispatcher rd = request.getRequestDispatcher("vms.jsp");
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
