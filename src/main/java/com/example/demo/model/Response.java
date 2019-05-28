package com.example.demo.model;

public class Response {
    Boolean isSuccessful;
    String message;
    Object object;

    public Response(){}

    public Response(Boolean isSuccessful, String message, Object object) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.object = object;
    }

    public Boolean getSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(Boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
