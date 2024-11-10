package com.melato.syriangeeks.model;

import com.google.gson.JsonElement;

public class ResponseBodyModel {
    private boolean result;
    private String message;
    private JsonElement data;

    public boolean isResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public JsonElement getData() {
        return data;
    }
}
