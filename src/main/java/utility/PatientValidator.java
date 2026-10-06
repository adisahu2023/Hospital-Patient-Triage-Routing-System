package utility;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import dao.PatientDAO;
import daoImpl.PatientDaoImpl;
import entity.Patient;

public class PatientValidator {
	
		
	 public List<String> validatePatient(Patient patient,Connection conn) throws Exception{
		 List<String>errors=new ArrayList<>();
		 
		 if(patient.getPatientName()==null ||patient.getPatientName().isBlank() || patient.getPatientName().trim().length() <3 ) {
			 errors.add("Patient name must be at least 3 characters");
		 }
		 if(patient.getAge()<0 || patient.getAge()>120) {
			 errors.add("Patient Age must be between 0 - 120");
			 
		 }
		 if(!List.of("Male","Female","Other").contains(patient.getGender())){
			 errors.add("Gender must be Male, Female, or Other");
		 }
		 if(patient.getDisease()!=null &&  patient.getDisease().isBlank()) {
			 errors.add(" disease must not be blank.");
		 }
		 if(!List.of("Emergency","Regular").contains(patient.getAdmissionType())){
			 errors.add(" admission_type must be Emergency or Regular");
		 }
		 if(!List.of("Critical","Moderate","Stable").contains(patient.getConditionStatus())) {
			 errors.add(" condition_status must be Critical, Moderate or Stable");
		 }
		 if(patient.getTriageScore() < 1 || patient.getTriageScore() > 10) {
			 errors.add("triage_score must be between 1 and 10");
		 }
		 if (patient.getDoctorName() == null ||
				    patient.getDoctorName().isBlank()) {
				    errors.add("Doctor name must not be blank");
				}
		 if (patient.getAdmissionDate() != null &&
				    patient.getAdmissionDate().isAfter(LocalDate.now())) {
				    errors.add("Admission date cannot be in the future");
				}
		 if (patient.getMobile() == null ||
				    !patient.getMobile().matches("\\d{10}")) {
				    errors.add("Mobile must contain exactly 10 digits");
				}
		 if (!"PENDING".equals(patient.getTransferStatus())) {
			    errors.add("Transfer status must be PENDING");
			}
		 
		 
		 return errors;
	 }
}

