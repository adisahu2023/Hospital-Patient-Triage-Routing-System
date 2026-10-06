package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Patient {

	 private int patientId;
	    private String patientName;
	    private int age;
	    private String gender;
	    private String disease;
	    private String admissionType;
	    private String conditionStatus;
	    private int triageScore;
	    private String doctorName;
	    private LocalDate admissionDate;
	    private String mobile;
	    private String transferStatus;
	    private LocalDateTime processedAt;
	    private LocalDateTime createdAt;
}
