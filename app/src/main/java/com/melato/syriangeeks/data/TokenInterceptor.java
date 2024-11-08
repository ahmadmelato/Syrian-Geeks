package com.melato.syriangeeks.data;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class TokenInterceptor implements Interceptor {

    private String token;

    public TokenInterceptor() {
        this.token = "";
    }

    public TokenInterceptor(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", token)
                .build();
        return chain.proceed(request);
    }
}
