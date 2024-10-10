package edu.javaproject.studentorder.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Цей клас є класом заявки як такої, в якому наявні різні
 * поля нестандартних типів (екземплярів інших класів).
 */
// Структура заявки як такої
public class StudentOrder {
    private StudentOrderStatus studentOrderStatus;
    private LocalDateTime studentOrderDate;
    private Adult husband;
    private Adult wife;
    private List<Child> children;
    private LocalDate marriageDate;
    private String studentOrderId;
    private RegisterOffice marriageOffice;
    private String marriageCertificateId;

    public String getStudentOrderId() {
        return studentOrderId;
    }

    public void setStudentOrderId(String studentOrderId) {
        this.studentOrderId = studentOrderId;
    }

    public String getMarriageCertificateId() {
        return marriageCertificateId;
    }

    public void setMarriageCertificateId(String marriageCertificateId) {
        this.marriageCertificateId = marriageCertificateId;
    }

    public LocalDate getMarriageDate() {
        return this.marriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
                this.marriageDate = marriageDate;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public RegisterOffice getMarriageOffice() {
        return marriageOffice;
    }

    public void setMarriageOffice(RegisterOffice marriageOffice) {
        this.marriageOffice = marriageOffice;
    }

    public Adult getHusband() {
        return husband;
    }

    public void setHusband(Adult husband) {
        this.husband = husband;
    }

    public Adult getWife() {
        return wife;
    }

    public void setWife(Adult wife) {
        this.wife = wife;
    }

    public List<Child> getChildren() {
        return children;
    }

    public StudentOrderStatus getStudentOrderStatus() {
        return studentOrderStatus;
    }

    public void setStudentOrderStatus(StudentOrderStatus studentOrderStatus) {
        this.studentOrderStatus = studentOrderStatus;
    }

    public LocalDateTime getStudentOrderDate() {
        return studentOrderDate;
    }

    public void setStudentOrderDate(LocalDateTime studentOrderDate) {
        this.studentOrderDate = studentOrderDate;
    }

    public void addChild(Child child){
        if (children == null){
            children = new ArrayList<>(5);
        }
        children.add(child);
    }

    @Override
    public String toString() {
        return "StudentOrder{" +
                "studentOrderStatus=" + studentOrderStatus +
                ", studentOrderDate=" + studentOrderDate +
                ", husband=" + husband +
                ", wife=" + wife +
                ", children=" + children +
                ", marriageDate=" + marriageDate +
                ", studentOrderId='" + studentOrderId + '\'' +
                ", marriageOffice=" + marriageOffice +
                ", marriageCertificateId='" + marriageCertificateId + '\'' +
                '}';
    }
}
