package edu.javaproject.city.domain;

public class PersonResponse {
    public PersonResponse(){}
    private boolean registered;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean isTemporal() {
        return temporal;
    }

    public void setTemporal(boolean temporal) {
        this.temporal = temporal;
    }

    private boolean temporal;
}

