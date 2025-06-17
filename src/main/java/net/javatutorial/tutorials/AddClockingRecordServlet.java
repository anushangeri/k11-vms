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
		processRequest(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int nextVal = ClockingManagerDAO.getNextVal();

		String clockingId = String.valueOf(nextVal);
		String clockingPointName = request.getParameter("clockingPointName");
		String siteName = request.getParameter("siteName");

		// Basic input check (optional)
		if (clockingPointName == null || siteName == null || clockingPointName.isEmpty() || siteName.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters: clockingPointName or siteName");
			return;
		}

		ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Singapore"));
		Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());

		Clocking clocking = new Clocking(clockingId, clockingPointName, siteName, timestamp, timestamp);

		String message = ClockingManagerDAO.addClocking(clocking);

		// Optional: output message in browser (for GET use)
		response.setContentType("text/plain");
		response.getWriter().println("Clocking record added: " + message);
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
