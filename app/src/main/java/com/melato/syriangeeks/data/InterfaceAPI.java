package com.melato.syriangeeks.data;


import com.melato.syriangeeks.model.ProfileModel;
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
    Call<ResponseBodyModel> getCourses(@Query("sortTag") String sortTag, @Query("page") int page);

    @GET("/api/v1/blogs")
    Call<ResponseBodyModel> getBlogs(@Query("page") int page);

    @GET("/api/v1/blogs/{blog_id}/details")
    Call<ResponseBodyModel> getBlogsDetails(@Path("blog_id") Integer integer);

    @GET("/api/v1/course/{course_id}/details")
    Call<ResponseBodyModel> getCourseDetails(@Path("course_id") Integer integer);

    @GET("/api/v1/student/course/{course_id}/full-details")
    Call<ResponseBodyModel> getCourseFullDetails(@Path("course_id") Integer integer);

    @GET("/api/v1/student/course-activities")
    Call<ResponseBodyModel> getCourseActivities();

    @GET("/api/v1/student/certificate")
    Call<ResponseBodyModel> getCertificate();

    @GET("/api/v1/student/leader-board")
    Call<ResponseBodyModel> getLeaderBoard(@Query("page") Integer page);

    @GET("/api/v1/student/bookmark")
    Call<ResponseBodyModel> getBooMark();

    @GET("api/v1/student/course/join")
    Call<ResponseBodyModel> join(@Query("slug") String slug);

    @POST("api/v1/student/course-lecture-progress")
    Call<ResponseBodyModel> course_lecture_progress(@Body Map<String, Object> queryMap);

    @GET("/api/v1/events")
    Call<ResponseBodyModel> getEvents();

    @GET("/api/v1/student/courses")
    Call<ResponseBodyModel> getMyCourses(@Query("search") String sortTag);

    @POST("/api/v1/sign-up")
    Call<ResponseBodyModel> signup(@Body Map<String, Object> queryMap);

    @POST("/api/v1/student/send-verification")
    Call<ResponseBodyModel> send_verification(@Body Map<String, Object> queryMap);

    @POST("/api/v1/student/verify-email")
    Call<ResponseBodyModel> verify_email(@Body Map<String, Object> queryMap);

    @POST("/api/v1/student/forgot-password")
    Call<ResponseBodyModel> forgot_password(@Body Map<String, Object> queryMap);

    @GET("/api/v1/forum/questions")
    Call<ResponseBodyModel> getQuestions(@Query("page") Integer page);

    @GET("/api/v1/forum/questions/details/{id}")
    Call<ResponseBodyModel> getQuestionsDetails(@Path("id") Integer id, @Query("page") Integer page);

    @POST("/api/v1/forum/comment/store/{id}")
    Call<ResponseBodyModel> comment(@Path("id") Integer id, @Body Map<String, Object> queryMap);

    @POST("/api/v1/forum/question/store")
    Call<ResponseBodyModel> question_store(@Body Map<String, Object> queryMap);

    @POST("/api/v1/forum/answer/store")
    Call<ResponseBodyModel> answer_store(@Body Map<String, Object> queryMap);

    @GET("/api/v1/student/setting/profile")
    Call<ResponseBodyModel> getProfile();

    @POST("/api/v1/student/setting/update-password")
    Call<ResponseBodyModel> update_password(@Body Map<String, Object> queryMap);

    @GET("/api/v1/student/my-profile")
    Call<ResponseBodyModel> getMyProfile();

    @POST("/api/v1/student/setting/update-profile")
    Call<ResponseBodyModel> update_profile(@Body ProfileModel model);

    @POST("/api/v1/student/setting/update-institute/{id}")
    Call<ResponseBodyModel> update_institute(@Path("id") Integer id, @Body ProfileModel.Institute model);

    @POST("/api/v1/student/setting/store-institute")
    Call<ResponseBodyModel> store_institute(@Body ProfileModel.Institute model);

    @POST("/api/v1/student/setting/delete-institute/{id}")
    Call<ResponseBodyModel> delete_institute(@Path("id") Integer id);

    @POST("/api/v1/student/setting/update-experience/{id}")
    Call<ResponseBodyModel> update_experience(@Path("id") Integer id, @Body ProfileModel.Experience model);

    @POST("/api/v1/student/setting/store-experience")
    Call<ResponseBodyModel> store_experience(@Body ProfileModel.Experience model);

    @POST("/api/v1/student/setting/delete-experience/{id}")
    Call<ResponseBodyModel> delete_experience(@Path("id") Integer id);

    @POST("/api/v1/student/setting/store-skills")
    Call<ResponseBodyModel> store_skills(@Body Map<String, Object> queryMap);

    @POST("/api/v1/student/setting/store-social")
    Call<ResponseBodyModel> store_social(@Body Map<String, Object> queryMap);

    @GET("/api/v1/student/public-profile/{user_id}")
    Call<ResponseBodyModel> getPublicProfile(@Path("user_id") Integer id);

    @POST("/api/v1/contact")
    Call<ResponseBodyModel> contact(@Body Map<String, Object> queryMap);

    @GET("/api/v1/student/event/notifications")
    Call<ResponseBodyModel> getNotifications();

    @POST("/api/v1/student/event/read-all-notification")
    Call<ResponseBodyModel> readAllNotification();

    @POST("/api/v1/student/mentoring/new")
    Call<ResponseBodyModel> mentoringNew(@Body Map<String, Object> queryMap);

}
