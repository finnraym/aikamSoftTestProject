package ru.egorov.service;

public class Response {
    private String type;

    public Response(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
