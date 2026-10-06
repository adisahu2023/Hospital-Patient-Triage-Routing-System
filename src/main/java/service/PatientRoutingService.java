package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import dao.PatientDAO;
import entity.Patient;
import utility.PatientValidator;
import utility.ProcessingSummary;

public class PatientRoutingService {

	public void processPatients(PatientDAO patientDAO, PatientValidator validator) {
		System.out.println("KIRAN ACADEMY - HOSPITAL PATIENT ROUTING\r\n"
				+ "-----------------------------------------");
		Connection conn = DBConnection.getConnection();
		ProcessingSummary pSummary=new ProcessingSummary();
		List<Patient> PendPatients = new ArrayList<Patient>();
		try {
			conn.setAutoCommit(false);
			PendPatients = patientDAO.getPendingPatients(conn);
			pSummary.setTotalPendingRecords(PendPatients.size());

			for (Patient p : PendPatients) {
				List<String> errors = validator.validatePatient(p,conn);
				if (errors.isEmpty()) {
					if (!patientDAO.alreadyTransferred(p.getPatientId(), conn)) {
						try {
							if (isCritical(p)) {
								
								patientDAO.insertCriticalPatient(p, conn);
								patientDAO.markProcessed(p.getPatientId(), conn);
								conn.commit();
		
								pSummary.incrementsuccessfullyProcessed();;
								pSummary.incrementCriticalCare();
								
								
								System.out.println(
									    "Patient " + p.getPatientId()
									    + " -> CRITICAL CARE -> SUCCESS"
									);
							} else {
								
								
								patientDAO.insertGeneralPatient(p, conn);
								patientDAO.markProcessed(p.getPatientId(), conn);
								conn.commit();
								
								pSummary.incrementsuccessfullyProcessed();
								pSummary.incrementGeneralCare();
								
								System.out.println(
									    "Patient " + p.getPatientId()
									    + " -> GENERAL CARE -> SUCCESS"
									);
							}
							
							
							
//							System.out.println(p.getPatientId() + " Inserted Succesfully");

						} catch (Exception e) {

							try {
								conn.rollback();
								System.out.println("transaction rollback");

							} catch (SQLException e1) {

								e1.printStackTrace();
							}

						}

					} else {
						
						pSummary.incrementSkippedDuplicate();
						
						System.out.println(
						        "Patient " + p.getPatientId()
						        + " -> SKIPPED -> Already exists in destination table"
						    );
						continue;
					}

				} else {
					 //increment Validation 
					pSummary.incrementValidationFailed();
					
					System.out.println(
						    "Patient " + p.getPatientId()
						    + " -> FAILED -> " + errors.get(0)
						);
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			
			pSummary.printSummary();

			if (conn != null) {

				try {
					conn.close();

				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}

	}

	public static boolean isCritical(Patient p) {

		if (p.getConditionStatus().equals("Critical")) {
			return true;
		} else if (p.getAdmissionType().equals("Emergency") && p.getTriageScore() >= 7) {
			return true;
		} else if (p.getConditionStatus().equals("Moderate") && p.getTriageScore() >= 8) {
			return true;
		}

		return false;
	}
}
