package edu.javaproject.studentorder.domain;

import edu.javaproject.studentorder.domain.Person;

import java.time.LocalDate;

public class Child extends Person {
    private String birthCertificateNumber;
    private LocalDate dateOfReceivingBirthCertificate;
    //department that gives the certificate of child's birth
    private RegisterOffice departmentOfVitalRecords;

    public Child(String surname, String name, String patronymic, LocalDate dateOfBirth) {
        super(surname, name, patronymic, dateOfBirth);
    }
    public Child(){}

    public String getBirthCertificateNumber() {
        return birthCertificateNumber;
    }

    public void setBirthCertificateNumber(String birthCertificateNumber) {
        this.birthCertificateNumber = birthCertificateNumber;
    }

    public LocalDate getDateOfReceivingBirthCertificate() {
        return dateOfReceivingBirthCertificate;
    }

    public void setDateOfReceivingBirthCertificate(LocalDate dateOfReceivingBirthCertificate) {
        this.dateOfReceivingBirthCertificate = dateOfReceivingBirthCertificate;
    }

    public void setDepartmentOfVitalRecords(RegisterOffice registerOffice){
        this.departmentOfVitalRecords = registerOffice;
    }
    public RegisterOffice getDepartmentOfVitalRecords(){
        return departmentOfVitalRecords;
    }

    @Override
    public String toString() {
        return "Child{" +
                "birthCertificateNumber='" + birthCertificateNumber + '\'' +
                ", dateOfReceivingBirthCertificate=" + dateOfReceivingBirthCertificate +
                ", departmentOfVitalRecords=" + departmentOfVitalRecords +
                "} " + super.toString();
    }
}
