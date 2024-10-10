package edu.javaproject.studentorder.domain;

public class Street {
    private String streetCode;
    private String streetName;

    public Street() {
    }

    public Street(String streetCode, String streetName) {
        this.streetCode = streetCode;
        this.streetName = streetName;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public String toString() {
        return "Street{" +
                "streetCode='" + streetCode + '\'' +
                ", streetName='" + streetName + '\'' +
                '}';
    }
}
