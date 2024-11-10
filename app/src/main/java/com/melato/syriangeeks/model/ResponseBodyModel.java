package com.melato.syriangeeks.model;

import com.google.gson.JsonElement;

public class ResponseBodyModel {
    public boolean result;
    public String message;
    public JsonElement data;

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
