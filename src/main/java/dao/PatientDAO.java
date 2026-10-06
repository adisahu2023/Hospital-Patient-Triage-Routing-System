package dao;

import java.sql.Connection;
import java.util.List;

import entity.Patient;

public interface PatientDAO {
	
	public   List<Patient> getPendingPatients(Connection con) throws Exception;
	public  void insertCriticalPatient(Patient patient, Connection con) throws Exception;
	public void insertGeneralPatient(Patient patient, Connection con) throws Exception;
	void markProcessed(int patientId, Connection con) throws Exception;
	boolean alreadyTransferred(int patientId, Connection con) throws Exception;
}
