package edu.javaproject.studentorder.domain;

import edu.javaproject.studentorder.domain.Person;

import java.time.LocalDate;

public class Adult extends Person {
    private String passportNumber;

    // Дата видачі паспорта:
    private LocalDate passportIssueDate;

    // Орган, що видав паспорт:
    private PassportOffice passportDepartment;
    private University university;
    private String studentId;

    //Constructors:
    public Adult(String surname, String name, String patronymic, LocalDate dateOfBirth) {
        super(surname, name, patronymic, dateOfBirth);
    }

    public Adult(){}

    //Methods:

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getPassportIssueDate() {
        return passportIssueDate;
    }

    public void setPassportIssueDate(LocalDate passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setPassportDepartment(PassportOffice passportDepartment){
        this.passportDepartment = passportDepartment;
    }
    public PassportOffice getPassportDepartment(){
        return passportDepartment;
    }

    @Override
    public String toString() {
        return "Adult{" +
                "passportNumber='" + passportNumber + '\'' +
                ", passportIssueDate=" + passportIssueDate +
                ", passportDepartment=" + passportDepartment +
                ", university=" + university +
                ", studentId='" + studentId + '\'' +
                "} " + super.toString();
    }
}
