package ru.egorov.service;

public class ErrorResponse extends Response {

    private String message;
    public ErrorResponse() {
        super("error");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
