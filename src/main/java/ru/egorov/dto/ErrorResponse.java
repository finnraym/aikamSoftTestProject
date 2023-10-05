package ru.egorov.dto;

public class ErrorResponse implements Response {

    private final String type;
    private String message;

    public ErrorResponse() {
        type = "error";
    }

    public ErrorResponse(String message) {
        this();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getType() {
        return null;
    }
}
