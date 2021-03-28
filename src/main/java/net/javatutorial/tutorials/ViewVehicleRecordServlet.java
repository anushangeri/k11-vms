package net.javatutorial.tutorials;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import net.javatutorial.DAO.VehMSManagerDAO;
import net.javatutorial.entity.Vehicle;

/**
 * Servlet implementation class ViewVehicleRecordServlet
 */
public class ViewVehicleRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usertype = (String) request.getSession(false).getAttribute("usertype");
		String idNo = (String) request.getSession(false).getAttribute("idNo");
		String name = (String) request.getSession(false).getAttribute("name");
		String message = "No vehicle records available for: " + name;
		ArrayList<Vehicle> vList = null;
		if(!StringUtils.isEmpty(idNo)) {
			if(usertype != null) {
				vList = VehMSManagerDAO.retrieveAll();
				message = "List of vehicle records";
				request.setAttribute("vList", vList);
				if(vList == null && vList.size() == 0) {
					message = "No vehicle records available";
				}
			}
			else{
				vList = VehMSManagerDAO.retrieveByNRIC(idNo);
				message = "List of vehicle records for " + name;
				request.setAttribute("vList", vList);
				if(vList == null && vList.size() == 0) {
					message = "No vehicle records available for " + name;
				}
			}
		}
		
		request.setAttribute("message", message);
        RequestDispatcher rd = request.getRequestDispatcher("vehms.jsp");
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
