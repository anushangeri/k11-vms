package net.javatutorial.tutorials;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;

/**
 * Servlet implementation class TodHodSearchServlet
 */
public class VMSCheckNRICServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ArrayList<String> responseObj = new ArrayList<String>();
		RequestDispatcher rd = null;
		
		String idNo = request.getParameter("idNo").trim();
		try {
			//make idNo uppercase
        	if(idNo != null && !idNo.isEmpty() ){
        		idNo = idNo.toUpperCase();
        	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		boolean loginsuccessful = false;
		
		//CHECK K11CLICKS:DROPDOWN EXCEL VMS_ADMIN_CRED_12
		SpreadsheetService service = new SpreadsheetService("K11CLICKS: DROPDOWN EXCEL");
		if(!StringUtils.isEmpty(idNo)) {
			loginsuccessful = true;
			if(idNo.toUpperCase().equals("K11ADMIN")) {
				session.setAttribute("usertype", "K11ADMIN");
			}
			else{
				try {
		            String sheetUrl
		                    = "https://spreadsheets.google.com/feeds/list/116L_MDacE0331uQDZLRQD4UKpKXfHgWKcMFeD0ne324/12/public/values";

		            // Use this String as url
		            URL url = new URL(sheetUrl);

		            // Get Feed of Spreadsheet url
		            ListFeed lf = service.getFeed(url, ListFeed.class);
		            //Iterate over feed to get cell value
		            for (ListEntry le : lf.getEntries()) {
		                CustomElementCollection cec = le.getCustomElements();
		                if (cec != null){
		                    String nricfin = cec.getValue("nricfin").trim();
		                    String usertype = cec.getValue("usertype").trim();
		                	if(nricfin.equals(idNo)) {
	                    		//if admin, don't set NRIC because admin can see everything
	                    		session.setAttribute("usertype", usertype);
	                    	}
		                }
		            }
		            //if loop through google sheets and cannot find match means it is a public user not K11 STAFF
		            if(session.getAttribute("usertype") == null) {
		            	session.setAttribute("usertype", idNo);
		            }
		            
		        }catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		if(loginsuccessful) {
			responseObj.add("Login successful.");
			request.setAttribute("responseObj", responseObj);
			// Redirect to view visitor servlet to query all the visitors again.
			response.sendRedirect("/vms");
		}
		else{
			responseObj.add("NRIC/FIN is invalid. Please try again: " + idNo);
			request.setAttribute("responseObj", responseObj);
			rd = request.getRequestDispatcher("vmsCheckNRIC.jsp");
			rd.forward(request, response);
		}
        
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

