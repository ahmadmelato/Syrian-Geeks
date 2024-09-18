package com.melato.syriangeeks.data;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class TokenInterceptor implements Interceptor {

    private String token;
    private  static String signature = "5F:56:B7:4F:FE:25:B2:76:1F:5E:07:76:A9:36:21:54:38:57:21:B6";

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
                .addHeader("signature",signature)
                .addHeader("token_p", token)
                .build();
        return chain.proceed(request);
    }
}
