package com.example.dailyhappiness;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
    Call<JsonObject> passMission(@Field("userIndex") String userIndex, @Field("cost") String cost);

    @FormUrlEncoded
    @POST("/missionBundle/increment")
    Call<JsonObject> passDislikeMission(@Field("userIndex") String userIndex, @Field("cost") String cost, @Field("mission") String missionNumber, @Field("dislike") String dislike);

}
