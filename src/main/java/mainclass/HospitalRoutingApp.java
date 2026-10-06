package mainclass;


import dao.PatientDAO;
import daoImpl.PatientDaoImpl;
import service.PatientRoutingService;
import utility.PatientValidator;


public class HospitalRoutingApp {
	public static void main(String[] args) {
	PatientDAO dao=new PatientDaoImpl();
	PatientValidator pv=new PatientValidator();
	
	PatientRoutingService service=new PatientRoutingService();
	service.processPatients(dao, pv);
	
	
   
	}
}

