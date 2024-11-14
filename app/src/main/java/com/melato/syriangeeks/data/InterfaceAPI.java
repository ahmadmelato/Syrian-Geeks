package com.melato.syriangeeks.data;


import com.melato.syriangeeks.model.ResponseBodyModel;
import com.melato.syriangeeks.model.UserModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InterfaceAPI {

    @POST("/api/v1/sign-in")
    Call<ResponseBodyModel> login(@Body Map<String, Object> queryMap);

    @POST("/api/v1/logout")
    Call<ResponseBodyModel> logout();

    @GET("/api/v1/courses")
    Call<ResponseBodyModel> getCourses(@Query("sortTag") String sortTag);

    @GET("/api/v1/blogs")
    Call<ResponseBodyModel> getBlogs();

    @GET("/api/v1/blogs/{blog_id}/details")
    Call<ResponseBodyModel> getBlogsDetails(@Path("blog_id") Integer integer);

    @GET("/api/v1/course/{course_id}/details")
    Call<ResponseBodyModel> getCourseDetails(@Path("course_id") Integer integer);

    @GET("/api/v1/events")
    Call<ResponseBodyModel> getEvents();

    @POST("/api/v1/sign-up")
    Call<ResponseBodyModel> signup(@Body Map<String, Object> queryMap);

    @POST("/api/v1/student/send-verification")
    Call<ResponseBodyModel> send_verification(@Body Map<String, Object> queryMap);

    @POST("/api/v1/student/verify-email")
    Call<ResponseBodyModel> verify_email(@Body Map<String, Object> queryMap);


    
}
