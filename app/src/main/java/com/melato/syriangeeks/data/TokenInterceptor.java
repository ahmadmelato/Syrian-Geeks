package com.melato.syriangeeks.data;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class TokenInterceptor implements Interceptor {

    private final String token;

    public TokenInterceptor() {
        this.token = "";
    }

    public TokenInterceptor(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @NonNull
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder().addHeader("Accept","application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        return chain.proceed(request);
    }
}
