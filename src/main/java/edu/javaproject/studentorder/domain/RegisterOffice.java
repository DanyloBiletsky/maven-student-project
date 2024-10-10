package edu.javaproject.studentorder.domain;

// Marriage Register Office Data. Все тільки про офіс реєстрації. Ай-ді зайвки тут немає
public class RegisterOffice {
    private String officeId;
    private String officeName;
    private String officeAreaId;

    public String getOfficeAreaId() {
        return officeAreaId;
    }

    public void setOfficeAreaIdId(String officeAreaId) {
        this.officeAreaId = officeAreaId;
    }

    public RegisterOffice(String officeId, String officeName, String officeAreaId) {
        this.officeId = officeId;
        this.officeName = officeName;
        this.officeAreaId = officeAreaId;
    }

    public RegisterOffice() {}

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    @Override
    public String toString() {
        return "RegisterOffice{" +
                "officeId='" + officeId + '\'' +
                ", officeName='" + officeName + '\'' +
                ", officeAreaId='" + officeAreaId + '\'' +
                '}';
    }
}
