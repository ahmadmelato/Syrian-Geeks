package com.melato.syriangeeks.data;


import com.melato.syriangeeks.model.ResponseBodyModel;
import com.melato.syriangeeks.model.UserModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceAPI {

    @POST("sign-in")
    Call<ResponseBodyModel> login(@Body Map<String, Object> queryMap);

    @POST("logout")
    Call<ResponseBodyModel> logout();

    @GET("courses")
    Call<ResponseBodyModel> getCourses(@Query("sortTag") String sortTag);
//
//    @POST("singup")
//    Call<UserModel> singup(@Body Map<String, Object> queryMap);

    
}
