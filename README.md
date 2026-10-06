# Hospital Patient Triage & Routing System

## 📌 Project Overview

The **Hospital Patient Triage & Routing System** is a console-based Java application that processes hospital patient intake records and automatically routes patients to either **Critical Care** or **General Care** based on predefined triage rules.

The application reads pending patient records from a MySQL database, validates the patient information, checks for duplicate transfers, applies routing rules, and stores the patient in the appropriate destination table.

---

## 🎯 Objective

The main objective of this project is to automate the patient triage and routing process while maintaining data accuracy and consistency.

The system:

- Processes only `PENDING` patient records.
- Validates patient information.
- Prevents duplicate patient transfers.
- Routes patients according to triage rules.
- Uses JDBC transactions to maintain database consistency.
- Updates the source record only after successful routing.
- Provides a processing summary.

---

## 🛠️ Technologies Used

- **Java**
- **Core Java**
- **JDBC**
- **MySQL**
- **Maven**
- **Eclipse IDE**

---

## ✨ Features

### 1. Patient Validation

The system validates:

- Patient name
- Age
- Gender
- Disease
- Admission type
- Condition status
- Triage score
- Doctor name
- Admission date
- Mobile number
- Transfer status

### 2. Patient Routing

Patients are routed using the following rules:

- `condition_status = Critical` → **Critical Care**
- `admission_type = Emergency` and `triage_score >= 7` → **Critical Care**
- `condition_status = Moderate` and `triage_score >= 8` → **Critical Care**
- All other valid patients → **General Care**

### 3. Duplicate Prevention

Before routing a patient, the system checks whether the same `source_patient_id` already exists in either destination table.

If it already exists, the patient is skipped and reported as:

```text
SKIPPED -> Already exists in destination table
```

### 4. Transaction Management

The application uses JDBC transactions to ensure data consistency.

- `commit()` is performed when processing succeeds.
- `rollback()` is performed when a database operation fails.
- The source patient remains `PENDING` if the transaction fails.

### 5. Processing Summary

After processing, the application displays a summary containing:

- Total pending records
- Critical Care records
- General Care records
- Validation failures
- Skipped duplicates
- Successfully processed records

---

## 🗄️ Database Setup

### 1. Create the database

Create a MySQL database:

```sql
CREATE DATABASE hospital_patient_db;
```

### 2. Create the required tables

The project requires these three tables:

```text
hospital_patient_intake
critical_care_patients
general_care_patients
```

Use the SQL scripts provided with the project to create the tables and insert test data.

### 3. Configure MySQL Connection

Update the database configuration with your local MySQL details:

```text
Database: hospital_patient_db
Username: root
Password: <your-password>
```

**Note:** Do not upload your actual database password to GitHub. Keep local database configuration outside the repository or use a configuration file that is included in `.gitignore`.

---

## ▶️ How to Run the Project

### Prerequisites

Make sure you have:

- JDK installed
- MySQL Server installed
- Maven installed
- Eclipse IDE or another Java IDE
- MySQL JDBC Driver

### Steps

1. Clone the repository.
2. Open the project in Eclipse as a Maven project.
3. Create the `hospital_patient_db` database.
4. Create the required tables.
5. Insert the required test records.
6. Configure your local MySQL username and password.
7. Run:

```text
HospitalRoutingApp.java
```

8. The application will process the pending patient records and display the routing result and processing summary in the console.

---

## 📊 Sample Output

```text
KIRAN ACADEMY - HOSPITAL PATIENT ROUTING
-----------------------------------------

Patient 1 -> CRITICAL CARE -> SUCCESS
Patient 2 -> GENERAL CARE -> SUCCESS
Patient 3 -> SKIPPED -> Already exists in destination table
Patient 4 -> FAILED -> Triage score must be between 1 and 10
Patient 5 -> CRITICAL CARE -> SUCCESS

PROCESSING SUMMARY
------------------
Total Pending Records : 5
Critical Care         : 2
General Care          : 1
Validation Failed     : 1
Skipped Duplicate     : 1
Successfully Processed: 3
```
<img width="746" height="778" alt="image" src="https://github.com/user-attachments/assets/0e9429fb-3ee2-4887-8db5-d5fc4b75b573" />


---

## 🏗️ Project Structure

```text
Hospital_Patient_Triage_and_Routing_System
│
├── pom.xml
├── README.md
├── .gitignore
│
└── src
    ├── main
    │   └── java
    │       ├── connection
    │       ├── dao
    │       ├── entity
    │       ├── mainclass
    │       └── service
    │
    └── test
        └── java
```

---

## 🔄 Processing Flow

```text
Fetch PENDING Patients
        ↓
Validate Patient
        ↓
Invalid? ── Yes ──→ Validation Failed
        ↓ No
Check Duplicate
        ↓
Duplicate? ── Yes ──→ Skipped
        ↓ No
Apply Triage Rules
        ↓
 ┌───────────────┐
 │               │
Critical       General
 │               │
 ↓               ↓
Critical       General
Table          Table
        \       /
         ↓     ↓
       Mark PROCESSED
             ↓
          Commit
```

---

## 📚 Key Concepts Demonstrated

- Object-Oriented Programming
- Java Collections
- Exception Handling
- JDBC
- PreparedStatement
- ResultSet
- MySQL
- Database Transactions
- Commit and Rollback
- DAO Pattern
- Input Validation
- Business Rule Implementation
- Duplicate Prevention

---

## 👨‍💻 Author

**Aditya Sahu**
