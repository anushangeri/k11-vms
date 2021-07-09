package net.javatutorial.tutorials;

import net.javatutorial.DAO.VMSArchiveManagerDAO;

public class SchedulerMain {

    
    public static void main(String[] args) throws Exception {
    	String message = VMSArchiveManagerDAO.moveVisitor();
    	System.out.println("Visitor Records Moved: " + message);
    }

}
