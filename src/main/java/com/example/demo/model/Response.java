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
}
