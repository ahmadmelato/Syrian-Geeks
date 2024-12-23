package com.melato.syriangeeks.data;


import com.google.gson.Gson;
import com.melato.syriangeeks.model.ErrorAPI;
import com.melato.syriangeeks.model.ResponseBodyModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ClientAPI {
    public static int OK = 200;
    public static int Deny = 400;
    public static int Filed = 0;
    public static int Run = -1;

    public static final String BASE_URL = "https://sygeeks.net";
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

    public static void setClientAPIToken(String token_p) {
        clientAPI = new ClientAPI(token_p);
    }

    public static ClientAPI getClientAPI() {
        if (clientAPI == null) {
            clientAPI = new ClientAPI();
        }
        return clientAPI;
    }

    public Call<ResponseBodyModel> login(String email, String password) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("email", email);
        queryMap.put("password", password);
        return interfaceAPI.login(queryMap);
    }

    public Call<ResponseBodyModel> logout() {
        return interfaceAPI.logout();
    }

    public Call<ResponseBodyModel> getCourses(String sortTag, int page) {
        return interfaceAPI.getCourses(sortTag, page);
    }

    public Call<ResponseBodyModel> getMyCourses(String sortTag) {
        return interfaceAPI.getMyCourses(sortTag);
    }


    public Call<ResponseBodyModel> join(String slug) {
        return interfaceAPI.join(slug);
    }

    public Call<ResponseBodyModel> getBlogs(int page) {
        return interfaceAPI.getBlogs(page);
    }

    public Call<ResponseBodyModel> getEvents() {
        return interfaceAPI.getEvents();
    }

    public Call<ResponseBodyModel> send_verification(String email) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("email", email);
        return interfaceAPI.send_verification(queryMap);
    }

    public Call<ResponseBodyModel> course_lecture_progress(String lesson_id, List<String> completed_lessons) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("lesson_id", lesson_id);
        queryMap.put("completed_lessons", completed_lessons);
        System.out.println(new Gson().toJson(queryMap));
        return interfaceAPI.course_lecture_progress(queryMap);
    }

    public Call<ResponseBodyModel> getCourseActivities() {
        return interfaceAPI.getCourseActivities();
    }

    public Call<ResponseBodyModel> getBlogsDetails(Integer id) {
        return interfaceAPI.getBlogsDetails(id);

    }

    public Call<ResponseBodyModel> getCourseDetails(Integer id) {
        return interfaceAPI.getCourseDetails(id);
    }

    public Call<ResponseBodyModel> getCourseFullDetails(Integer id) {
        return interfaceAPI.getCourseFullDetails(id);
    }

    public Call<ResponseBodyModel> getCertificate() {
        return interfaceAPI.getCertificate();
    }


    public Call<ResponseBodyModel> verify_email(String email, String code) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("email", email);
        queryMap.put("code", code);
        System.out.println(new Gson().toJson(queryMap));
        return interfaceAPI.verify_email(queryMap);
    }

    public Call<ResponseBodyModel> forgot_password(String email) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("email", email);
        System.out.println(new Gson().toJson(queryMap));
        return interfaceAPI.forgot_password(queryMap);
    }

    public Call<ResponseBodyModel> getLeaderBoard(int page) {
        return interfaceAPI.getLeaderBoard(page);
    }

    public Call<ResponseBodyModel> getBooMark() {
        return interfaceAPI.getBooMark();
    }

    public Call<ResponseBodyModel> getQuestions(int page) {
        return interfaceAPI.getQuestions(page);
    }

    public Call<ResponseBodyModel> getQuestionsDetails(Integer id, Integer page) {
        return interfaceAPI.getQuestionsDetails(id, page);
    }

    public Call<ResponseBodyModel> comment(Integer id, String comment) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("comment", comment);
        return interfaceAPI.comment(id, queryMap);
    }

    public Call<ResponseBodyModel> question_store(Integer category,String title,String question) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("category", category);
        queryMap.put("title", title);
        queryMap.put("question", question);
        return interfaceAPI.question_store(queryMap);
    }

    public Call<ResponseBodyModel> answer_store(Integer quesion_id,String answer){
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("quesion_id", quesion_id);
        queryMap.put("answer", answer);
        return interfaceAPI.answer_store(queryMap);
    }


    public Call<ResponseBodyModel> signup(String name, String name_ar, String date_of_birth, String gender, String nationality, String education,
                                          String work_field, String other_work_field, String experience_years, String freelancer, String freelancer_years, String cv_file,
                                          String country, String state, String location, String place, String disability, String email, String phone, String phone_dial,
                                          String password, String password_confirmation, String newsletter, String agree) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            date_of_birth = dateFormat.format(dateFormat.parse(date_of_birth));
        } catch (ParseException ignored) {
        }
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", name);
        queryMap.put("name_ar", name_ar);
        queryMap.put("date_of_birth", date_of_birth);
        queryMap.put("gender", gender);
        queryMap.put("nationality", nationality);
        queryMap.put("education", education);
        queryMap.put("work_field", work_field);
        queryMap.put("other_work_field", other_work_field);
        queryMap.put("experience_years", experience_years);
        queryMap.put("freelancer", freelancer);
        queryMap.put("freelancer_years", freelancer_years);
        queryMap.put("cv_file", cv_file);
        queryMap.put("country", country);
        queryMap.put("state", state);
        queryMap.put("location", location);
        queryMap.put("place", place);
        queryMap.put("disability", disability);
        queryMap.put("email", email);
        queryMap.put("phone", phone);
        queryMap.put("phone_dial", phone_dial);
        queryMap.put("password", password);
        queryMap.put("password_confirmation", password_confirmation);
        queryMap.put("newsletter", newsletter);
        queryMap.put("agree", agree);
        System.err.println(new Gson().toJson(queryMap));
        return interfaceAPI.signup(queryMap);
    }


}
