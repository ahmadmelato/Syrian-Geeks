package com.melato.syriangeeks.model;

public class ResponseBodyModel {
    public boolean result;
    public String message;
    public Object data;

    public boolean isResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
