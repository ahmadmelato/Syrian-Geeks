package com.melato.syriangeeks.data;


import com.melato.syriangeeks.model.ResponseBodyModel;
import com.melato.syriangeeks.model.UserModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceAPI {

    @POST("sign-in")
    Call<ResponseBodyModel> login(@Body Map<String, Object> queryMap);
//
//    @POST("singup")
//    Call<UserModel> singup(@Body Map<String, Object> queryMap);

    
}
