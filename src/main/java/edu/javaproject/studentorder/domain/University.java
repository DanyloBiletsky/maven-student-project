package edu.javaproject.studentorder.domain;

public class University {
    private String universityId;
    private String universityName;

    public University() {}
    public University(String universityId, String universityName){
        this.universityId = universityId;
        this.universityName = universityName;
    }
    public String getUniversityId() {
        return universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    @Override
    public String toString() {
        return "University{" +
                "universityId='" + universityId + '\'' +
                ", universityName='" + universityName + '\'' +
                '}';
    }
}
