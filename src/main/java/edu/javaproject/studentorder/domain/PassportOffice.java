package edu.javaproject.studentorder.domain;

public class PassportOffice {
    private String officeId;
    private String officeAreaId;
    private String officeName;

    public PassportOffice(String officeId, String officeAreaId, String officeName) {
        this.officeId = officeId;
        this.officeAreaId = officeAreaId;
        this.officeName = officeName;
    }

    public PassportOffice() {
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeAreaId() {
        return officeAreaId;
    }

    public void setOfficeAreaId(String officeAreaId) {
        this.officeAreaId = officeAreaId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    @Override
    public String toString() {
        return "PassportOffice{" +
                "officeId='" + officeId + '\'' +
                ", officeAreaId='" + officeAreaId + '\'' +
                ", officeName='" + officeName + '\'' +
                '}';
    }
}
