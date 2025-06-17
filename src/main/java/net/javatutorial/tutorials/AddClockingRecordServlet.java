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
		try {
			int nextVal = ClockingManagerDAO.getNextVal();

			String clockingId = String.valueOf(nextVal);
			String clockingPointName = request.getParameter("clockingPointName");
			String siteName = request.getParameter("siteName");

			if (clockingPointName == null || siteName == null || clockingPointName.isEmpty() || siteName.isEmpty()) {
				showPopupAndClose(response, "Missing parameters: clockingPointName or siteName", true);
				return;
			}

			ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Singapore"));
			Timestamp timestamp = Timestamp.valueOf(zdt.toLocalDateTime());

			Clocking clocking = new Clocking(clockingId, clockingPointName, siteName, timestamp, timestamp);

			String message = ClockingManagerDAO.addClocking(clocking);

			System.out.println("The message is this: " + message);
			
			if (message.toLowerCase().contains("error") || message.toLowerCase().contains("exception")) {
				showPopupAndClose(response, "Failed to add clocking record: " + message, true);
			} else {
				showPopupAndClose(response, "Clocking record added successfully!", false);
			}

		} catch (Exception e) {
			e.printStackTrace(); // log for server
			showPopupAndClose(response, "Error occurred: " + e.getMessage(), true);
		}
	}

	private void showPopupAndClose(HttpServletResponse response, String message, boolean isError) throws IOException {
		
		// Escape single quotes and newlines to avoid breaking JS
	    String escapedMessage = message.replace("'", "\\'").replace("\n", "\\n").replace("\r", "");

		response.setContentType("text/html");
		response.getWriter().println(
			"<html>" +
	            "<head><title>" + (isError ? "Error" : "Success") + "</title></head>" +
	            "<body>" +
	                "<script type='text/javascript'>" +
	                    "alert('" + escapedMessage + "');" +
	                    "setTimeout(function() { window.close(); }, 300);" +
	                "</script>" +
	            "</body>" +
	        "</html>"
		);
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
