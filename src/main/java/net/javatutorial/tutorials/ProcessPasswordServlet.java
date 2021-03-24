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
import net.javatutorial.entity.ClientAccount;
import net.javatutorial.tutorials.PasswordUtils;

import java.util.Calendar;
import java.util.Locale;
import static java.util.Calendar.*;
import java.util.Date;

/**
 * Servlet implementation class PasswordVerifiedServlet
 */
public class ProcessPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idNo = request.getParameter("idNo");
		String password = request.getParameter("psw");
				
		//retrieving the hashed password by DB based on idNo entered by user
		ArrayList<ClientAccount> vList = ClientAccountManagerDAO.retrieveByID(idNo);
		boolean verified = false;
		String key = " ";
		String userHash = " ";
		String salt = " ";
		if(vList != null && vList.size() > 0 ) {
			ClientAccount c = vList.get(0);
			key = c.getPassword();
			salt = c.getSalt();
			userHash =  PasswordUtils.hashPassword(password, salt).get();
			verified = PasswordUtils.verifyPassword(password, key, salt);
		}
		
		String responseObj = verified +" " + password +"userHash: "+ userHash +"key: " + key;
		request.setAttribute("responseObj", responseObj);
		// Redirect to view visitor servlet to query all the visitors again.
		//response.sendRedirect("/clientLogin.jsp");
		RequestDispatcher rd = request.getRequestDispatcher("clientLogin.jsp");
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
