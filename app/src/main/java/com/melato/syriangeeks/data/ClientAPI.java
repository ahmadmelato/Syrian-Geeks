package com.melato.syriangeeks.data;



import com.melato.syriangeeks.model.ErrorAPI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientAPI {
    public static int OK = 200;
    public static int Deny = 400;
    public static int Filed = 0;
    public static int Run = -1;

    private static final String BASE_URL = "http://192.168.1.100:5000/";
    private InterfaceAPI interfaceAPI;
    public static ClientAPI clientAPI;
    public TokenInterceptor tokenInterceptor;
    private static Socket socket;

    public ClientAPI() {
        tokenInterceptor = new TokenInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaceAPI = retrofit.create(InterfaceAPI.class);
    }

    public ClientAPI(String token_p) {

        tokenInterceptor = new TokenInterceptor(token_p);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaceAPI = retrofit.create(InterfaceAPI.class);
    }

    public static Socket getSocket() {
        if (socket == null) {
            try {
                socket = IO.socket(BASE_URL);
            } catch (URISyntaxException ex) {
                Logger.getLogger(ClientAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return socket;
    }

    public static void socketConnect() {
        if (socket == null) {
            try {
                socket = IO.socket(BASE_URL);
            } catch (URISyntaxException ex) {
                Logger.getLogger(ClientAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (socket != null && !socket.connected())
            socket.connect();
    }

    public static void socketDisconnect() {
        if (socket != null && !socket.connected())
            socket.disconnect();
    }

    public static ErrorAPI parseError(Response<?> response) {
        Converter<ResponseBody, ErrorAPI> converter;
        converter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient()).build()
                .responseBodyConverter(ErrorAPI.class, new java.lang.annotation.Annotation[0]);

        ErrorAPI error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorAPI();
        }

        return error;
    }

    public static ClientAPI setClientAPIToken(String token_p) {
        clientAPI = new ClientAPI(token_p);
        return clientAPI;
    }

    public static ClientAPI getClientAPI() {
        if (clientAPI == null) {
            clientAPI = new ClientAPI();
        }
        return clientAPI;
    }

//    public Call<UserModel> login(String mail_p, String password_p) {
//        Map<String, Object> queryMap = new HashMap<>();
//        queryMap.put("mail_p", mail_p);
//        queryMap.put("password_p", password_p);
//        return interfaceAPI.login(queryMap);
//    }
//
//    public Call<UserModel> singup(String first_name_p, String last_name_p, String mail_p, String password_p) {
//        Map<String, Object> queryMap = new HashMap<>();
//        queryMap.put("first_name_p", first_name_p);
//        queryMap.put("last_name_p", last_name_p);
//        queryMap.put("mail_p", mail_p);
//        queryMap.put("password_p", password_p);
//        return interfaceAPI.singup(queryMap);
//    }


}
