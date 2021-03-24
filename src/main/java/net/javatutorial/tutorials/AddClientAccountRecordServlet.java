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
 * Servlet implementation class AddClientAccountServlet
 */
public class AddClientAccountRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nextVal = ClientAccountManagerDAO.getNextVal();
		
		String accountId = "" + nextVal;
		String name = request.getParameter("name").trim();
		String idType = request.getParameter("idType");
		String idNo = request.getParameter("idNo");
		String password= request.getParameter("psw");
		String accessType= request.getParameter("accessType");
		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Singapore")) ;
		Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());

		//hashing the password
		String salt = PasswordUtils.generateSalt(512).get();
		String hashedPassword = PasswordUtils.hashPassword(password, salt).get();
				
		ClientAccount v = new ClientAccount( accountId,  name, idType, idNo,  hashedPassword, salt, accessType, timestamp, timestamp);
		
		String message = ClientAccountManagerDAO.addClientAccount(v);
		
		ArrayList<String> responseObj = new ArrayList<String>();
		responseObj.add(message + " " + name);
		request.setAttribute("responseObj", v.toString());
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
