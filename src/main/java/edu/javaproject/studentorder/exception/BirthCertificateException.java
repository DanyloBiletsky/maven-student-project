package edu.javaproject.studentorder.exception;

public class BirthCertificateException extends Exception {
    public BirthCertificateException() {
    }
    public BirthCertificateException(String message) {
        super(message);
    }
    public BirthCertificateException(String message, Throwable cause){
        super(message,cause);
    }

}
