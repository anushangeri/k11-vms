package net.javatutorial.DAO;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.javatutorial.entity.Visitor;
import net.javatutorial.tutorials.Main;

public class VMSManagerDAO {
	
	public static String addVisitor(Visitor v){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		String message = "";
		try {
			connection = Main.getConnection();
			stmt = connection.createStatement();

	        stmt.executeUpdate("INSERT INTO VMS "
	        		+  "(VMS_ID, NAME, COMPANY_NAME, ID_TYPE, ID_NO, MOBILE_NO, VEHICLE_NO, HOST_NAME, HOST_CONTACT, VISTOR_CARD_ID, COVID_DECLARE, "
	        		+ "VISIT_PURPOSE, TEMPERATURE, TIME_IN_DT)" + 
	        		"   VALUES ('" +v.getVmsId()+ "','" +v.getName()+ "','" +v.getCompanyName()+ "','" +v.getIdType()+ "','" 
	        		+v.getIdNo()+ "','" +v.getMobileNo()+ "','" +v.getVehicleNo()+ "','" +v.getHostName()+ "','" 
	        		+v.getHostNo()+ "','" +v.getVisitorCardId()+ "','" +v.getCovidDeclare()+ "','" 
	        		+v.getVisitPurpose()+ "','" +v.getTemperature()+ "','" +v.getTimeInDt()+ "')");
	        rs = stmt.executeQuery("SELECT LAST(FIRST_NAME) FROM VMS;");
	        while (rs.next()) {
	        	message = "Read from DB: " + rs.getTimestamp("tick");
	        }
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			message = "" + e;
			//e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			message = "" + e;
		}
		finally {
        	Main.close(connection, stmt, rs);
        }
		message = "Successful";
		return message;
	}
	public static String updateVisitorTimeOut(Visitor v){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		String message = "";
		try {
			connection = Main.getConnection();
			stmt = connection.createStatement();

	        stmt.executeUpdate("SET TIMEZONE = 'Singapore'; "
	        		+ "UPDATE VMS "
	        		+  "SET TIME_OUT_DT = NOW()" +
	        		"   WHERE VMS_ID = '" + v.getVmsId() + "';");
	        rs = stmt.executeQuery("SELECT LAST(FIRST_NAME) FROM VMS WHERE VMS_ID ='" + v.getVmsId() +"';");
	        while (rs.next()) {
	        	message = "Read from DB: " + rs.getTimestamp("tick");
	        }
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			message = "" + e;
			//e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			message = "" + e;
		}
		finally {
        	Main.close(connection, stmt, rs);
        }
		message = "Successful";
		return message;
	}
	public static int getNextVal(){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		int result = 1;
		try {
			connection = Main.getConnection();
			stmt = connection.createStatement();
//	        stmt.executeUpdate("SELECT count(*) FROM EMPLOYEES;");
	        rs = stmt.executeQuery("SELECT MAX(VMS_ID) FROM VMS;");
	        if(rs != null) {
	        	while (rs.next()) {
		        	if(rs.getString(1) != null && !rs.getString(1).isEmpty()) {
		        		result = Integer.parseInt(rs.getString(1)) + 1;
		        	}
	                
	            }
	        }
	        
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
        	Main.close(connection, stmt, rs);
        }
		return result;
	}
	
	public static ArrayList<Visitor> retrieveAll() {
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        Visitor v = null;
        ArrayList<Visitor> vList = new ArrayList<Visitor>();
        try {
        	connection = Main.getConnection();
            String sql = "SELECT VMS_ID, NAME,\r\n" + 
            		"              COMPANY_NAME, ID_TYPE, ID_NO, MOBILE_NO, \r\n" + 
            		"              VEHICLE_NO, HOST_NAME,\r\n" + 
            		"              HOST_CONTACT, VISTOR_CARD_ID, COVID_DECLARE, VISIT_PURPOSE, TEMPERATURE, \r\n" + 
            		"              TIME_IN_DT, TIME_OUT_DT FROM VMS ORDER BY TIME_IN_DT DESC; ";
            pstmt = connection.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	v = new Visitor(rs.getString(1), 
            			rs.getString(2),
            			rs.getString(3),
            			rs.getString(4),
            			rs.getString(5),
            			rs.getString(6),
            			rs.getString(7),
            			rs.getString(8),
            			rs.getString(9),
            			rs.getString(10),
            			rs.getString(11),
            			rs.getString(12),
            			rs.getString(13),
            			rs.getTimestamp(14),
            			rs.getTimestamp(15));
                vList.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Main.close(connection, pstmt, rs);
        }
        return vList;
    }
	
	public static ArrayList<Visitor> retrieveByNRIC(String idNo) {
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        Visitor v = null;
        ArrayList<Visitor> vList = new ArrayList<Visitor>();
        try {
        	connection = Main.getConnection();
            String sql = "SELECT VMS_ID, NAME,\r\n" + 
            		"              COMPANY_NAME, ID_TYPE, ID_NO, MOBILE_NO, \r\n" + 
            		"              VEHICLE_NO, HOST_NAME,\r\n" + 
            		"              HOST_CONTACT, VISTOR_CARD_ID, COVID_DECLARE, VISIT_PURPOSE, TEMPERATURE, \r\n" + 
            		"              TIME_IN_DT, TIME_OUT_DT FROM VMS WHERE ID_NO ='" + idNo + "' ORDER BY TIME_IN_DT DESC LIMIT 1;";
            pstmt = connection.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	v = new Visitor(rs.getString(1), 
            			rs.getString(2),
            			rs.getString(3),
            			rs.getString(4),
            			rs.getString(5),
            			rs.getString(6),
            			rs.getString(7),
            			rs.getString(8),
            			rs.getString(9),
            			rs.getString(10),
            			rs.getString(11),
            			rs.getString(12),
            			rs.getString(13),
            			rs.getTimestamp(14),
            			rs.getTimestamp(15));
                vList.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Main.close(connection, pstmt, rs);
        }
        return vList;
    }
	
	public static ArrayList<Visitor> retrieveByNameIDandType(String name, String idType, String idNo) {
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        Visitor v = null;
        ArrayList<Visitor> vList = new ArrayList<Visitor>();
        try {
        	connection = Main.getConnection();
            String sql = "SELECT VMS_ID, NAME,\r\n" + 
            		"              COMPANY_NAME, ID_TYPE, ID_NO, MOBILE_NO, \r\n" + 
            		"              VEHICLE_NO, HOST_NAME,\r\n" + 
            		"              HOST_CONTACT, VISTOR_CARD_ID, COVID_DECLARE, VISIT_PURPOSE, TEMPERATURE, \r\n" + 
            		"              TIME_IN_DT, TIME_OUT_DT "
            		+ "FROM VMS "
            		+ "WHERE NAME = '" + name + "' AND ID_NO ='" + idNo + "' AND ID_TYPE = '" + idType + "' "
    				+ "ORDER BY TIME_IN_DT DESC LIMIT 1; ";
            pstmt = connection.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	v = new Visitor(rs.getString(1), 
            			rs.getString(2),
            			rs.getString(3),
            			rs.getString(4),
            			rs.getString(5),
            			rs.getString(6),
            			rs.getString(7),
            			rs.getString(8),
            			rs.getString(9),
            			rs.getString(10),
            			rs.getString(11),
            			rs.getString(12),
            			rs.getString(13),
            			rs.getTimestamp(14),
            			rs.getTimestamp(15));
                vList.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Main.close(connection, pstmt, rs);
        }
        return vList;
    }
	
	public static Visitor retrieveByVmsId(String vmsId) {
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        Visitor v = null;
        try {
        	connection = Main.getConnection();
            String sql = "SELECT VMS_ID, NAME,\r\n" + 
            		"              COMPANY_NAME, ID_TYPE, ID_NO, MOBILE_NO, \r\n" + 
            		"              VEHICLE_NO, HOST_NAME,\r\n" + 
            		"              HOST_CONTACT, VISTOR_CARD_ID, COVID_DECLARE, VISIT_PURPOSE, TEMPERATURE, \r\n" + 
            		"              TIME_IN_DT, TIME_OUT_DT FROM VMS WHERE VMS_ID ='" + vmsId + "' ORDER BY TIME_IN_DT DESC;";
            pstmt = connection.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	v = new Visitor(rs.getString(1), 
            			rs.getString(2),
            			rs.getString(3),
            			rs.getString(4),
            			rs.getString(5),
            			rs.getString(6),
            			rs.getString(7),
            			rs.getString(8),
            			rs.getString(9),
            			rs.getString(10),
            			rs.getString(11),
            			rs.getString(12),
            			rs.getString(13),
            			rs.getTimestamp(14),
            			rs.getTimestamp(15));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Main.close(connection, pstmt, rs);
        }
        return v;
    }
	
	public static String deleteAll() {
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        String message = "All records deleted - No visitor records available";
        try {
        	connection = Main.getConnection();
            String sql = "DELETE FROM VMS WHERE TIME_IN_DT <= GETDATE() - 30;";
            pstmt = connection.prepareStatement(sql);

            rs = pstmt.executeQuery();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Main.close(connection, pstmt, rs);
        }
        return message;
    }
}
