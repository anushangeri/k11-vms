package net.javatutorial.DAO;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import net.javatutorial.tutorials.*;

public class VehMSTblDAO {

	public static String createVehmsTbl(){
		Connection connection;
		String message = "";
		try {
			connection = Main.getConnection();
			Statement stmt = connection.createStatement();
	        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS VEHMS (\r\n" + 
	        		"   VEHICLE_ID VARCHAR (100)  NOT NULL,\r\n" + 
	        		"   NAME VARCHAR (100)  NOT NULL,\r\n" + 
	        		"   COMPANY_NAME VARCHAR (100)  NULL,\r\n" + 
	        		"   ID_TYPE VARCHAR (100)   NULL, \r\n" + 
	        		"   ID_NO VARCHAR (100)   NULL, \r\n" + 
	        		"   MOBILE_NO  VARCHAR (100) NULL,   \r\n" + 
	        		"   PRIME_MOVER_NO  VARCHAR (100)  NULL,\r\n" + 
	        		"   CONTAINER_NO  VARCHAR (100) NULL, \r\n" +  
	        		"   SEAL_NO  VARCHAR (100) NULL, \r\n" +  
	        		"   CONTAINER_SIZE  VARCHAR (100) NULL, \r\n" +
	        		"   LOADED_FLAG  VARCHAR (100) NULL,\r\n" + 
	        		"   COVID_DECLARE_FLAG  VARCHAR (100) NULL,\r\n" + 
	        		"   LORRY_CHET_NO  VARCHAR (100) NULL,\r\n" + 
	        		"   DELIVERY_NOTICE_NO  VARCHAR (100) NULL,\r\n" + 
	        		"   VISIT_PURPOSE  VARCHAR (100) NULL,\r\n" + 
	        		"   TEMPERATURE  VARCHAR (100) NULL,\r\n" + 
	        		"   TIME_IN_DT TIMESTAMP  NOT NULL DEFAULT NOW(),\r\n" + 
	        		"   TIME_OUT_DT TIMESTAMP   NULL \r\n" + 
	        		");");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			message = "" + e;
			//e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			message = "" + e;
		}
		message = "Successful";
		return message;
	}
	public static String deleteVehmsTbl(){
		Connection connection;
		String message = "";
		try {
			connection = Main.getConnection();
			Statement stmt = connection.createStatement();
	        stmt.executeUpdate("DROP TABLE VEHMS;");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			message = "" + e;
			//e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			message = "" + e;
		}
		message = "Successful";
		return message;
	}
	
	public static String updateVehmsTbl(){
		Connection connection;
		String message = "";
		try {
			connection = Main.getConnection();
			Statement stmt = connection.createStatement();
	        stmt.executeUpdate("ALTER TABLE VEHMS\r\n" + 
	        		"ADD COLUMN REMARKS VARCHAR (100)  NULL;");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			message = "" + e;
			//e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			message = "" + e;
		}
		message = "Successful";
		return message;
	}
}
