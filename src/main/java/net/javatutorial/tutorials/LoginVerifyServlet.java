package net.javatutorial.tutorials;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
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

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;


import java.util.Calendar;
import java.util.Locale;
import static java.util.Calendar.*;
import java.util.Date;

/**
 * Servlet implementation class TodHodSearchServlet
 */
public class LoginVerifyServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ArrayList<String> responseObj = new ArrayList<String>();
		RequestDispatcher rd = null;
		
		Date dtOfBirthLogin = null;
		Date dtOfBirthExcel = null;
		Date dtOfBirthNantha = null;
		
		String idNo = request.getParameter("idNo").trim();
		String dtOfBirthStr = request.getParameter("dob").trim();
		//covert the string to date to compare:
		try {
			//make idNo uppercase
        	if(idNo != null && !idNo.isEmpty() ){
        		idNo = idNo.toUpperCase();
        	}
        	if(dtOfBirthStr.length() != 0 && !StringUtils.isEmpty(dtOfBirthStr)){
        		dtOfBirthLogin = dateFormat.parse(dtOfBirthStr);
        	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		boolean loginsuccessful = false;
		
		SpreadsheetService service = new SpreadsheetService("Form Responses 3");
		if(!StringUtils.isEmpty(idNo) && !StringUtils.isEmpty(dtOfBirthStr)) {
			if(idNo.toUpperCase().equals("K11ADMIN")) {
				loginsuccessful = true;
				session.setAttribute("usertype", "K11ADMIN");
			}
			else {
				try {
		            String sheetUrl
		                    = "https://spreadsheets.google.com/feeds/list/1aGjQLWkAWP2Oa9H5vzTZaddduVoHLTbJn0J7XgUH_1I/1/public/values";

		            // Use this String as url
		            URL url = new URL(sheetUrl);

		            // Get Feed of Spreadsheet url
		            ListFeed lf = service.getFeed(url, ListFeed.class);
		            
		            //Iterate over feed to get cell value
		            for (ListEntry le : lf.getEntries()) {
		                
		                CustomElementCollection cec = le.getCustomElements();
		                
		                if (cec != null){
		                    String nricfin = cec.getValue("nricfin").trim();
		                    //make idNo uppercase
		                	if(nricfin != null && !nricfin.isEmpty() ){
		                		nricfin = nricfin.toUpperCase();
		                	}
		                    //System.out.println("THE PROBLEM IS HERE: " + nricfin);
		                    String dateofbirth = cec.getValue("dateofbirth");
		                    String accessright = cec.getValue("accessright");
		                    //System.out.println("THE PROBLEM IS HERE: " + dateofbirth);
		                    if(!StringUtils.isEmpty(nricfin) && !StringUtils.isEmpty(dateofbirth) &&
		                    		!StringUtils.isEmpty(accessright) && !StringUtils.isEmpty(accessright)) {
		                    	//covert the string to date to compare:
		                		try {
		                			dtOfBirthExcel = dateFormat.parse(dateofbirth);
		                        	
		                		} catch (Exception e) {
		                			// TODO Auto-generated catch block
		                			e.printStackTrace();
		                		}
		                    	if(nricfin.equals(idNo) && dtOfBirthExcel.equals(dtOfBirthLogin)) {
		                    		loginsuccessful = true;
		                    		//if admin, don't set NRIC because admin can see everything
		                    		if(accessright.toUpperCase().equals("K11ADMIN")) {
		                    			session.setAttribute("usertype", accessright);
		                    		}
		                    		else {
		                    			session.setAttribute("usertype", accessright);
			            				session.setAttribute("nricfin", nricfin);
		                    		}
		            				
	                    		
		                    	}
		                    }
				
		                }
		            }
		            
		        }catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		}
		
		if(loginsuccessful) {
			responseObj.add("Login successful");
			request.setAttribute("responseObj", responseObj);
			rd = request.getRequestDispatcher("dashboard.jsp");
		}
		else{
			responseObj.add("NRIC/FIN or Date of Birth is empty." + dtOfBirthStr + idNo);
			request.setAttribute("responseObj", responseObj);
			rd = request.getRequestDispatcher("index.jsp");
		}
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