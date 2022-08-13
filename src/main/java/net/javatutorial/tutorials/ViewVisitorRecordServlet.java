package net.javatutorial.tutorials;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import net.javatutorial.DAO.VMSManagerDAO;
import net.javatutorial.entity.Visitor;

/**
 * Servlet implementation class ViewVisitorRecordServlet - to view visitor
 * records if Usertype is client or officer, then client or officer can only view visitor record for
 * that site if management or admin, they can view all records
 */
public class ViewVisitorRecordServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String usertype = (String) request.getSession(false).getAttribute("usertype");
		String idNo = (String) request.getSession(false).getAttribute("idNo");
		String name = (String) request.getSession(false).getAttribute("name");
		String siteInCharge = (String) request.getSession(false).getAttribute("siteInCharge");

		String message = request.getAttribute("message") != null || !StringUtils.isEmpty((String) request.getAttribute("message")) ? (String) request.getAttribute("message") : "" ;
		
		message = message + " No visitor records available for: " + name;
		ArrayList<Visitor> vList = null;
		if (!StringUtils.isEmpty(idNo)) {
			if (!StringUtils.isEmpty(usertype) && usertype != null
					&& !(usertype.equals("CLIENT") || usertype.equals("OFFICER"))) {
				vList = VMSManagerDAO.retrieveAll();
				message =  message + " List of visitor records";
				request.setAttribute("vList", vList);
				if (vList == null && vList.size() == 0) {
					message = message + " No visitor records available";
				}
			} else if (!StringUtils.isEmpty(usertype) && usertype != null
					&& (usertype.equals("CLIENT") || usertype.equals("OFFICER"))
					&& !StringUtils.isEmpty(siteInCharge)) {
				vList = VMSManagerDAO.retrieveBySite(siteInCharge);
				message =  message + " List of visitor records";
				request.setAttribute("vList", vList);
				if (vList == null && vList.size() == 0) {
					message =  message + " No visitor records available";
				}
			} else {
				vList = VMSManagerDAO.retrieveByNRIC(idNo);
				message =  message + " List of visitor records for " + name;
				request.setAttribute("vList", vList);
				if (vList == null && vList.size() == 0) {
					message =  message + " No visitor records available for " + name;
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