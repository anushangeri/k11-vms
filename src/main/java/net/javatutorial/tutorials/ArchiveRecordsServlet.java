package net.javatutorial.tutorials;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.javatutorial.DAO.VMSArchiveManagerDAO;
import net.javatutorial.DAO.VehMSArchiveManagerDAO;
import net.javatutorial.entity.Vehicle;
import net.javatutorial.entity.Visitor;

/**
 * Servlet implementation class ArchiveRecordsServlet
 * 
 * Archiving Logic: daily batch job will move data from main table to archive table, 
 * so main table will have only 30 days worth of data.
 * 
 * Monthly batch job will run to retrieve all data in archive table and email to client 
 * and delete data from archive table leaving only 30 days
 *
 */
public class ArchiveRecordsServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//archiving visitor records
		String visitorMessage = VMSArchiveManagerDAO.moveVisitor();
		System.out.println(visitorMessage);
		//archiving vehicle records
		String vehicleMessage = VehMSArchiveManagerDAO.moveVehicle();
		System.out.println(vehicleMessage);
		
		// get the current date
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		// get the calendar last day of this month
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// check to see if the given date really is the last day of this month
		System.out.println( "check to see if the given date really is the last day of this month");
		System.out.println( cal.get(Calendar.DAY_OF_MONTH) == lastDay);
		if(cal.get(Calendar.DAY_OF_MONTH) != lastDay) {
			System.out.println( "it is the last day of the month, so batch job will do archive db clean up and email results");
			
			try 
			{ 
				
				//start making email
				//retrieving visitor records from archive table
				ArrayList<Visitor> visitorList = VMSArchiveManagerDAO.retrieveAll();
				System.out.println("retrieved visitors successful");
				
				//retrieving visitor records from archive table
				ArrayList<Vehicle> vehicleList = VehMSArchiveManagerDAO.retrieveAll();
				System.out.println("retrieved vehicles successful");
				
				LocalDate localDate = LocalDate.now(ZoneId.of("GMT+08:00"));
				String fileName = "vms_records_dev"+ localDate +".xls";
				
				// workbook object
				XSSFWorkbook workbook = new XSSFWorkbook();
				// spreadsheet object
				XSSFSheet visitorspreadsheet = workbook.createSheet(" Visitor Records ");
				// creating a row object

				XSSFRow visitorheader = visitorspreadsheet.createRow(0);
				visitorheader.createCell(0).setCellValue("Record ID");
				visitorheader.createCell(1).setCellValue("Visitor Name");
				visitorheader.createCell(2).setCellValue("Visitor Company Name");
				visitorheader.createCell(3).setCellValue("Visited Site");
				visitorheader.createCell(4).setCellValue("Visitor ID Type");
				visitorheader.createCell(5).setCellValue("Visitor ID Number");
				visitorheader.createCell(6).setCellValue("Visitor Mobile No.");
				visitorheader.createCell(7).setCellValue("Visitor Vehicle No.");
				visitorheader.createCell(8).setCellValue("Host Name");
				visitorheader.createCell(9).setCellValue("Host Number");
				visitorheader.createCell(10).setCellValue("Visitor Card ID");
				visitorheader.createCell(11).setCellValue("Remarks");
				visitorheader.createCell(12).setCellValue("Visit Purpose");
				visitorheader.createCell(13).setCellValue("Approving Officer");
				visitorheader.createCell(14).setCellValue("Time In Date");
				visitorheader.createCell(15).setCellValue("Time Out Date");
				visitorheader.createCell(16).setCellValue("Archived Date");

				XSSFRow row;
				int rowid = 1;

				System.out.println("writing the data into the sheets...");
				System.out.println("writing visitor records..");
				// writing the data into the sheets...
				for (Visitor a : visitorList) {

					row = visitorspreadsheet.createRow(rowid++);

					row.createCell(0).setCellValue(a.getVmsId());
					row.createCell(1).setCellValue(a.getName());
					row.createCell(2).setCellValue(a.getCompanyName());
					row.createCell(3).setCellValue(a.getSite());
					row.createCell(4).setCellValue(a.getIdType());
					row.createCell(5).setCellValue(a.getIdNo());
					row.createCell(6).setCellValue(a.getMobileNo());
					row.createCell(7).setCellValue(a.getVehicleNo());
					row.createCell(8).setCellValue(a.getHostName());
					row.createCell(9).setCellValue(a.getHostNo());
					row.createCell(10).setCellValue(a.getVisitorCardId());
					row.createCell(11).setCellValue(a.getRemarks());
					row.createCell(12).setCellValue(a.getVisitPurpose()); 
					row.createCell(13).setCellValue(a.getApprovingOfficer());
					row.createCell(14).setCellValue(a.getTimeInDt().toString());
					if (a.getTimeOutDt() != null) {
						row.createCell(15).setCellValue(a.getTimeOutDt().toString());
					}
					row.createCell(16).setCellValue(a.getArchivedDt().toString());
				}
				
				XSSFSheet vehiclespreadsheet = workbook.createSheet(" Vehicle Records ");
				// creating a row object

				XSSFRow vehicleheader = vehiclespreadsheet.createRow(0);
				vehicleheader.createCell(0).setCellValue("Record ID");
				vehicleheader.createCell(1).setCellValue("Visitor Name");
				vehicleheader.createCell(2).setCellValue("Visitor Company Name");
				vehicleheader.createCell(3).setCellValue("Visitor ID Type");
				vehicleheader.createCell(4).setCellValue("Visitor ID Number");
				vehicleheader.createCell(5).setCellValue("Visitor Mobile No.");
				vehicleheader.createCell(6).setCellValue("Vehicle PrimeMover No.");
				vehicleheader.createCell(7).setCellValue("Vehicle Container No.");
				vehicleheader.createCell(8).setCellValue("Vehicle Loaded?");
				vehicleheader.createCell(9).setCellValue("Lorry Chet Number");
				vehicleheader.createCell(10).setCellValue("Delivery Notice No.");
				vehicleheader.createCell(11).setCellValue("Visit Purpose");
				vehicleheader.createCell(12).setCellValue("Seal No.");
				vehicleheader.createCell(13).setCellValue("Container Size");
				vehicleheader.createCell(14).setCellValue("Remarks");
				vehicleheader.createCell(15).setCellValue("Time In Date");
				vehicleheader.createCell(16).setCellValue("Time Out Date");
				vehicleheader.createCell(17).setCellValue("Archived Date");
				
				rowid = 1;

				System.out.println("writing vehicle records...");
				// writing the data into the sheets...
				for (Vehicle a : vehicleList) {

					row = vehiclespreadsheet.createRow(rowid++);

					row.createCell(0).setCellValue(a.getVehicleId());
					row.createCell(1).setCellValue(a.getName());
					row.createCell(2).setCellValue(a.getCompanyName());
					row.createCell(3).setCellValue(a.getIdType());
					row.createCell(4).setCellValue(a.getIdNo());
					row.createCell(5).setCellValue(a.getMobileNo());
					row.createCell(6).setCellValue(a.getPrimeMoverNo());
					row.createCell(7).setCellValue(a.getContainerNo());
					row.createCell(8).setCellValue(a.getLoadedNoLoaded());
					row.createCell(9).setCellValue(a.getLorryChetNumber());
					row.createCell(10).setCellValue(a.getDeliveryNoticeNumber());
					row.createCell(11).setCellValue(a.getVisitPurpose());
					row.createCell(12).setCellValue(a.getSealNo());
					row.createCell(13).setCellValue(a.getContainerNo());
					row.createCell(14).setCellValue(a.getRemarks());
					row.createCell(15).setCellValue(a.getTimeInDt().toString());
					if (a.getTimeOutDt() != null) {
						row.createCell(16).setCellValue(a.getTimeOutDt().toString());
					}
					row.createCell(17).setCellValue(a.getArchivedDt().toString());
				}
				System.out.println("writing finished, sending email ...");
				// .xlsx is the format for Excel Sheets...
				// writing the workbook into the file...
				FileOutputStream fos = new FileOutputStream(fileName);
				workbook.write(fos);
				fos.close();
				workbook.close();
				// out.close();

				String to = "k11.sivalingam@gmail.com";// change accordingly
				final String user = "Shangeri1994@k11.com.sg";// change accordingly
				final String password = "EbSDkwr+Hvc7!U57";// change accordingly

				Properties properties = System.getProperties();
				properties.setProperty("mail.smtp.host", "smtp.k11.com.sg");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.port", "25");

				Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});

				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(user));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					message.setSubject("K11 VMS Records DEV ENV");

					// Create the message part
					BodyPart messageBodyPart = new MimeBodyPart();

					// Fill the message
					messageBodyPart.setText(
							"K11 VMS Records, there is 30 days worth of data in K11 VMS now. "
							+ "The attached excel has visitor & vehicle records that exceed 30 days.");

					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);

					// Part two is attachment
					messageBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(fileName);

					// Now use your ByteArrayDataSource as
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(fileName);
					multipart.addBodyPart(messageBodyPart);

					// 6) set the multiplart object to the message object
					message.setContent(multipart);

					// 7) send message
					Transport.send(message);

					System.out.println("email sent....");
				} catch (MessagingException ex) {
					ex.printStackTrace();
				}
				String visitorDeleteRecords = VMSArchiveManagerDAO.deleteAll();
				String vehicleDeleteRecords = VehMSArchiveManagerDAO.deleteAll();
				System.out.println(visitorDeleteRecords);
				System.out.println(vehicleDeleteRecords);	

				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			} 
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
