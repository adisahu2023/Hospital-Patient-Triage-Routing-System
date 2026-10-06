package utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import connection.DBConnection;
import dao.PatientDAO;
import daoImpl.PatientDaoImpl;
import entity.Patient;

public class ProcessingSummary {
	private int totalPendingRecords;
    private int criticalCare;
    private int generalCare;
    private int validationFailed;
    private int skippedDuplicate;
    private int successfullyProcessed;
	
	
    
    public void setTotalPendingRecords(int totalPendingRecords) {
    	this.totalPendingRecords=totalPendingRecords;
    }
	
	public void incrementCriticalCare() {
		criticalCare++;
	}
	
	public void incrementGeneralCare() {
		generalCare++;
	}
	
	public void incrementValidationFailed() {
		validationFailed++;
	}
	
	public void incrementSkippedDuplicate() {
		skippedDuplicate++;
	}
	
	public void incrementsuccessfullyProcessed() {
		successfullyProcessed++;
	}
	
	public void printSummary() {

	    System.out.println("PROCESSING SUMMARY");
	    System.out.println("------------------");
	    System.out.println("Total Pending Records : " + totalPendingRecords);
	    System.out.println("Critical Care         : " + criticalCare);
	    System.out.println("General Care          : " + generalCare);
	    System.out.println("Validation Failed     : " + validationFailed);
	    System.out.println("Skipped Duplicate     : " + skippedDuplicate);
	    System.out.println("Successfully Processed: " + successfullyProcessed);
	}

}
