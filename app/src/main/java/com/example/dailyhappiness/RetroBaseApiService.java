package com.example.dailyhappiness;


import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetroBaseApiService {

    final String Base_Url = "https://dailyhappiness.xyz";

    @FormUrlEncoded
    @POST("/register/")
    Call<JsonObject> createAccount(@Field("id") String id, @Field("password") String password, @Field("gender") String gender, @Field("age") String age);

    @FormUrlEncoded
    @POST("/login/")
    Call<JsonObject> login(@Field("id") String id, @Field("password") String password);

    @FormUrlEncoded
    @POST("/missionBundle/get")
    Call<JsonObject> getMission(@Field("userIndex") String userIndex);

    @FormUrlEncoded
    @POST("/missionBundle/increment")
    Call<JsonObject> incrementMissionOrder(@Field("userIndex") String userIndex);

    @FormUrlEncoded
    @POST("/missionBundle/increment")
    Call<JsonObject> passMission(@Field("userIndex") String userIndex, @Field("count") String count);

    @FormUrlEncoded
    @POST("/missionBundle/increment")
    Call<JsonObject> passDislikeMission(@Field("userIndex") String userIndex, @Field("count") String count, @Field("mission") String missionNumber, @Field("dislike") String dislike);

    @Multipart
    @POST("/writeReview/image")
    Call<JsonObject> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody);


    @FormUrlEncoded
    @POST("/writeReview/review")
    Call<JsonObject> uploadReview(@Field("userIndex") String userIndex, @Field("missionIndex") int missionIndex, @Field("missionRating") String missionRating, @Field("locationlat") String location_lat, @Field("locationlon") String location_lon,@Field("content") String content);





}
