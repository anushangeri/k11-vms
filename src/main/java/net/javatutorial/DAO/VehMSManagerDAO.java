package net.javatutorial.DAO;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.javatutorial.entity.Vehicle;
import net.javatutorial.tutorials.Main;

public class VehMSManagerDAO {
	
	public static String addVisitor(Vehicle v){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		String message = "";
		try {
			connection = Main.getConnection();
			stmt = connection.createStatement();

	        stmt.executeUpdate("INSERT INTO VEHMS "
	        		+ "(VEHICLE_ID, NAME, COMPANY_NAME, ID_TYPE, ID_NO, MOBILE_NO, PRIME_MOVER_NO, CONTAINER_NO, "
	        		+ " LOADED_FLAG, COVID_DECLARE_FLAG, LORRY_CHET_NO, DELIVERY_NOTICE_NO,"
	        		+ " VISIT_PURPOSE, TEMPERATURE, SEAL_NO, CONTAINER_SIZE, REMARKS, TIME_IN_DT)" + 
	        		"  VALUES ('" +v.getVehicleId()+ "','" +v.getName()+ "','" +v.getCompanyName()+ "','" +v.getIdType()+ "','" 
	        		+v.getIdNo()+ "','" +v.getMobileNo()+ "','" +v.getPrimeMoverNo()+ "','" +v.getContainerNo()+ "','" 
	        		+v.getLoadedNoLoaded()+ "','" +v.getCovidDeclare()+ "','" +v.getLorryChetNumber()+ "','" 
	        		+v.getDeliveryNoticeNumber()+ "','" +v.getVisitPurpose()+ "','" 
	        		+v.getTemperature()+ "','" +v.getSealNo()+ "','" +v.getContainerSize()+ "','"
	        		+v.getRemarks()+ "','" +v.getTimeInDt()+ "');");
	        rs = stmt.executeQuery("SELECT LAST(NAME) FROM VEHMS;");
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
		message = "successfull" ;
		return message;
	}
	
	public static String updateVehicleTimeOut(Vehicle v){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		String message = "";
		try {
			connection = Main.getConnection();
			stmt = connection.createStatement();

	        stmt.executeUpdate("SET TIMEZONE = 'Singapore'; "
	        		+ "UPDATE VEHMS "
	        		+  "SET TIME_OUT_DT = NOW()" +
	        		"   WHERE VEHICLE_ID = '" + v.getVehicleId() + "';");
	        rs = stmt.executeQuery("SELECT NAME FROM VEHMS WHERE VEHICLE_ID ='" + v.getVehicleId() +"';");
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
	
	public static String updateVehicleCustomTimeOut(Vehicle v){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		String message = "";
		try {
			connection = Main.getConnection();
			stmt = connection.createStatement();

	        stmt.executeUpdate("SET TIMEZONE = 'Singapore'; "
	        		+ "UPDATE VEHMS "
	        		+ "SET TIME_OUT_DT = '" + v.getTimeOutDt() + "' "
	        		+ "WHERE VEHICLE_ID = '" + v.getVehicleId() + "';");
	        rs = stmt.executeQuery("SELECT NAME FROM VEHMS WHERE VEHICLE_ID ='" + v.getVehicleId() +"';");
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
	
	public static String updateVehicleCustomTimeIn(Vehicle v){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		String message = "";
		try {
			connection = Main.getConnection();
			stmt = connection.createStatement();

	        stmt.executeUpdate("SET TIMEZONE = 'Singapore'; "
	        		+ "UPDATE VEHMS "
	        		+ "SET TIME_OUT_DT = '" + v.getTimeInDt() + "' "
	        		+ "WHERE VEHICLE_ID = '" + v.getVehicleId() + "';");
	        rs = stmt.executeQuery("SELECT NAME FROM VEHMS WHERE VEHICLE_ID ='" + v.getVehicleId() +"';");
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
	
	public static String updateVehicleLorryChet(Vehicle v){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		String message = "";
		try {
			connection = Main.getConnection();
			stmt = connection.createStatement();

	        stmt.executeUpdate("UPDATE VEHMS "
	        		+  "SET LORRY_CHET_NO = '" + v.getLorryChetNumber() + "'" 
	        		+ "   WHERE VEHICLE_ID = '" + v.getVehicleId() + "';");
	        rs = stmt.executeQuery("SELECT NAME FROM VEHMS WHERE VEHICLE_ID ='" + v.getVehicleId() +"';");
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
	
	public static String updateVehicleDeliveryNotice(Vehicle v){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		String message = "";
		try {
			connection = Main.getConnection();
			stmt = connection.createStatement();

	        stmt.executeUpdate("UPDATE VEHMS "
	        		+  "SET DELIVERY_NOTICE_NO = '" + v.getDeliveryNoticeNumber() + "'" 
	        		+ "   WHERE VEHICLE_ID = '" + v.getVehicleId() + "';");
	        rs = stmt.executeQuery("SELECT NAME FROM VEHMS WHERE VEHICLE_ID ='" + v.getVehicleId() +"';");
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
	
	public static String updateVehicleRemarks(Vehicle v){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		String message = "";
		try {
			connection = Main.getConnection();
			stmt = connection.createStatement();

	        stmt.executeUpdate("UPDATE VEHMS "
	        		+  "SET REMARKS = '" + v.getRemarks() + "'" 
	        		+ "   WHERE VEHICLE_ID = '" + v.getVehicleId() + "';");
	        rs = stmt.executeQuery("SELECT NAME FROM VEHMS WHERE VEHICLE_ID ='" + v.getVehicleId() +"';");
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
	        rs = stmt.executeQuery("SELECT MAX(CAST(VEHICLE_ID AS INTEGER)) FROM VEHMS;");
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
	
	public static ArrayList<Vehicle> retrieveAll() {
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        Vehicle v = null;
        ArrayList<Vehicle> vList = new ArrayList<Vehicle>();
        try {
        	connection = Main.getConnection();
            String sql = "SELECT VEHICLE_ID, NAME, COMPANY_NAME, ID_TYPE, ID_NO, MOBILE_NO, PRIME_MOVER_NO, "
            		+ "CONTAINER_NO, LOADED_FLAG, COVID_DECLARE_FLAG, LORRY_CHET_NO, DELIVERY_NOTICE_NO, \r\n" 
            		+ "VISIT_PURPOSE, TEMPERATURE, SEAL_NO, CONTAINER_SIZE, REMARKS, TIME_IN_DT, TIME_OUT_DT \r\n"
            		+ "FROM VEHMS ORDER BY TIME_IN_DT DESC; ";
            pstmt = connection.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	v = new Vehicle(rs.getString(1), 
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
            			rs.getString(14),
            			rs.getString(15),
            			rs.getString(16),
            			rs.getString(17),
            			rs.getTimestamp(18),
            			rs.getTimestamp(19));
                vList.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Main.close(connection, pstmt, rs);
        }
        return vList;
    }
	
	public static ArrayList<Vehicle> retrieveByNRIC(String idNo) {
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        Vehicle v = null;
        ArrayList<Vehicle> vList = new ArrayList<Vehicle>();
        try {
        	connection = Main.getConnection();
            String sql = "SELECT VEHICLE_ID, NAME, COMPANY_NAME, ID_TYPE, ID_NO, MOBILE_NO, PRIME_MOVER_NO, \r\n" + 
            		" CONTAINER_NO, LOADED_FLAG, COVID_DECLARE_FLAG, LORRY_CHET_NO, DELIVERY_NOTICE_NO, \r\n" + 
            		" VISIT_PURPOSE, TEMPERATURE, SEAL_NO, CONTAINER_SIZE, REMARKS, TIME_IN_DT, TIME_OUT_DT \r\n" + 
            		" FROM VEHMS WHERE ID_NO ='" + idNo + "' ORDER BY TIME_IN_DT DESC LIMIT 5;";
            pstmt = connection.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	v = new Vehicle(rs.getString(1), 
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
            			rs.getString(14),
            			rs.getString(15),
            			rs.getString(16),
            			rs.getString(17),
            			rs.getTimestamp(18),
            			rs.getTimestamp(19));
                vList.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Main.close(connection, pstmt, rs);
        }
        return vList;
    }
	
	// to be used in populate	
	public static ArrayList<Vehicle> retrieveByNameIDandType(String idType, String idNo) {
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        Vehicle v = null;
        ArrayList<Vehicle> vList = new ArrayList<Vehicle>();
        try {
        	connection = Main.getConnection();
            String sql = "SELECT VEHICLE_ID, NAME, COMPANY_NAME, ID_TYPE, ID_NO, MOBILE_NO, PRIME_MOVER_NO, \r\n" + 
            		"CONTAINER_NO, LOADED_FLAG, COVID_DECLARE_FLAG, LORRY_CHET_NO, DELIVERY_NOTICE_NO, \r\n" + 
            		"VISIT_PURPOSE, TEMPERATURE, SEAL_NO, CONTAINER_SIZE, REMARKS, TIME_IN_DT, TIME_OUT_DT \r\n" + 
            		" FROM VEHMS"
            		+ " WHERE ID_NO ='" + idNo + "' AND ID_TYPE = '" + idType + "' "
    				+ " ORDER BY TIME_IN_DT DESC";
            pstmt = connection.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	v = new Vehicle(rs.getString(1), 
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
            			rs.getString(14),
            			rs.getString(15),
            			rs.getString(16),
            			rs.getString(17),
            			rs.getTimestamp(18),
            			rs.getTimestamp(19));
                vList.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Main.close(connection, pstmt, rs);
        }
        return vList;
    }
	
	public static Vehicle retrieveByVehicleId(String vehicleId) {
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        Vehicle v = null;
        ArrayList<Vehicle> vList = new ArrayList<Vehicle>();
        try {
        	connection = Main.getConnection();
            String sql = "SELECT VEHICLE_ID, NAME, COMPANY_NAME, ID_TYPE, ID_NO, MOBILE_NO, PRIME_MOVER_NO, \r\n" + 
            		"CONTAINER_NO, LOADED_FLAG, COVID_DECLARE_FLAG, LORRY_CHET_NO, DELIVERY_NOTICE_NO, \r\n" + 
            		"VISIT_PURPOSE, TEMPERATURE, SEAL_NO, CONTAINER_SIZE, REMARKS, TIME_IN_DT, TIME_OUT_DT \r\n" + 
            		"FROM VEHMS \r\n"
            		+ " WHERE VEHICLE_ID ='" + vehicleId + "' ORDER BY TIME_IN_DT DESC;";
            pstmt = connection.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
            	v = new Vehicle(rs.getString(1), 
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
            			rs.getString(14),
            			rs.getString(15),
            			rs.getString(16),
            			rs.getString(17),
            			rs.getTimestamp(18),
            			rs.getTimestamp(19));
                vList.add(v);
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
        String message = "All records deleted - No vehicle records available";
        try {
        	connection = Main.getConnection();
            String sql = "DELETE FROM VEHMS WHERE TIME_IN_DT <= GETDATE() - 30;";
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
