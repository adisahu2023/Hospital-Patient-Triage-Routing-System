package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import connection.DBConnection;
import dao.PatientDAO;
import entity.Patient;
import service.PatientRoutingService;

public class PatientDaoImpl implements PatientDAO {

	@Override
	public List<Patient> getPendingPatients(Connection con)throws Exception {
		
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Patient>list=new ArrayList<Patient>();
		
		 ps=con.prepareStatement("select patient_id, patient_name, age, gender, disease, admission_type, "
				+ "                condition_status, triage_score, doctor_name, admission_date, mobile,"
				+ "                 transfer_status, processed_at, created_at from hospital_patient_intake where transfer_status=?");
		ps.setString(1,"PENDING");
		 rs=ps.executeQuery();
		while(rs.next()) {
			Patient p=new Patient();
			 p.setPatientId(rs.getInt("patient_id"));
	            p.setPatientName(rs.getString("patient_name"));
	            p.setAge(rs.getInt("age"));
	            p.setGender(rs.getString("gender"));
	            p.setDisease(rs.getString("disease"));
	            p.setAdmissionType(rs.getString("admission_type"));
	            p.setConditionStatus(rs.getString("condition_status"));
	            p.setTriageScore(rs.getInt("triage_score"));
	            p.setDoctorName(rs.getString("doctor_name"));
	            
	            Date addDate=rs.getDate("admission_date");
			p.setAdmissionDate(
					addDate != null ?addDate.toLocalDate():null
					);
			 p.setMobile(rs.getString("mobile"));
	            p.setTransferStatus(rs.getString("transfer_status"));
	            
	            Timestamp processedTs = rs.getTimestamp("processed_at");
	            p.setProcessedAt(processedTs != null ? processedTs.toLocalDateTime() : null);
	            
	            Timestamp createdTs = rs.getTimestamp("created_at");
	            p.setCreatedAt(createdTs != null ? createdTs.toLocalDateTime() : null);
			
			list.add(p);
			
		}
		
		  
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    
		   
		}
		return list;
	}

	@Override
	public void insertCriticalPatient(Patient p, Connection conn) throws Exception{
		PreparedStatement ps=null;

			
			ps=conn.prepareStatement("INSERT INTO critical_care_patients"
					+ "(source_patient_id, patient_name, age, disease, admission_type,"
					+ " condition_status, triage_score, doctor_name	,routed_at)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1,p.getPatientId());
			ps.setString(2,p.getPatientName());
			ps.setInt(3,p.getAge());
			ps.setString(4,p.getDisease());
			ps.setString(5,p.getAdmissionType());
			ps.setString(6,p.getConditionStatus());
			ps.setInt(7,p.getTriageScore());
			ps.setString(8,p.getDoctorName());
			ps.setTimestamp(9,Timestamp.valueOf(LocalDateTime.now()));
			
			if(ps.executeUpdate()>0) {
//				System.out.println("Data inserted into Critical Patient");
			}else {
//				System.out.println("Data does not insert");
			}
			
				
			    if (ps != null) {
			        try {
			            ps.close();
			        } catch (SQLException e) {
			            e.printStackTrace();
			        }
			    }
			   
			
		
		
	}

	@Override
	public void insertGeneralPatient(Patient p, Connection con) throws Exception {
		
		PreparedStatement ps=null;
		String sql="INSERT INTO general_care_patients"
				+ "(source_patient_id, patient_name, age, disease, admission_type,"
				+ " condition_status, triage_score, doctor_name	,routed_at)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		
			ps=con.prepareStatement(sql);
			ps.setInt(1,p.getPatientId());
			ps.setString(2,p.getPatientName());
			ps.setInt(3,p.getAge());
			ps.setString(4,p.getDisease());
			ps.setString(5,p.getAdmissionType());
			ps.setString(6,p.getConditionStatus());
			ps.setInt(7,p.getTriageScore());
			ps.setString(8,p.getDoctorName());
			ps.setTimestamp(9,Timestamp.valueOf(LocalDateTime.now()));
		
			if(ps.executeUpdate()>0) {
//				System.out.println("Data inserted into Critical Patient");
			}else {
//				System.out.println("Data does not insert");
			}
		
			
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		
		
		
	}

	@Override
	public void markProcessed(int patientId, Connection con)throws Exception {
		PreparedStatement ps=null;
		
		String sql="update hospital_patient_intake set transfer_status=? ,processed_at=? where patient_id=?";
		
			ps=con.prepareStatement(sql);
			ps.setString(1, "PROCESSED");
			ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(3, patientId);
			if(ps.executeUpdate()>0) {
//				System.out.println("marked as proccesed");
			}else {
//				System.err.println("Not marked as proccesed");
			}
		
	}

	@Override
	public boolean alreadyTransferred(int patientId, Connection con) throws Exception  {
		
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select 1 from critical_care_patients where source_patient_id =?"
				+ " union all "
				+ "select 1 from general_care_patients where source_patient_id =?";
		
			ps=con.prepareStatement(sql);
			ps.setInt(1, patientId);
			ps.setInt(2, patientId);
			rs=ps.executeQuery();
			boolean isDuplicate=rs.next();
			if(ps!=null) {
				ps.close();
			}
			if(rs!=null) {
				rs.close();
			}
			
		return isDuplicate;
		
		
	}

}
