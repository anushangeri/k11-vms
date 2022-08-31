package net.javatutorial.tutorials;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.javatutorial.DAO.VehMSManagerDAO;
import net.javatutorial.entity.Vehicle;

/**
 * Servlet implementation class UpdateVehicleApprovalRecordServlet
 * updates the warehouse approver for vehicle record with approver login ID
 */
public class UpdateVehicleApprovalRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String vehicleId = (String) request.getParameter("vehicleId");
		String approverId = (String) request.getParameter("approverId");
		Vehicle v = null;
		String message = "Vehicle ID of vehicle record / gate pass is unavailable, please add vehicle / gate pass record.";
		if(vehicleId != null && !StringUtils.isEmpty(vehicleId)) {
			//retrieve Vehicle object
			v = VehMSManagerDAO.retrieveByVehicleId(vehicleId);
			//update Vehicle object with approver id
			v.setWarehouseApprover(approverId);
			message = VehMSManagerDAO.updateVehicleApprover(v);
			
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
