package net.javatutorial.tutorials;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.javatutorial.DAO.ClockingManagerDAO;
import net.javatutorial.entity.Clocking;

public class AddClockingRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nextVal = ClockingManagerDAO.getNextVal();

		String clockingId = String.valueOf(nextVal);
		String clockingPointName = request.getParameter("clockingPointName");
		String siteName = request.getParameter("siteName");

		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Singapore"));
		Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());

		Clocking clocking = new Clocking(clockingId, clockingPointName, siteName, timestamp, timestamp);

		String message = ClockingManagerDAO.addClocking(clocking);

		request.setAttribute("responseObj", message);

		// Redirect to view all clocking records - may not need this at this time
		//response.sendRedirect("/retrieveAllClockingRecords");
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
