package com.example.dailyhappiness;


import com.google.gson.JsonArray;
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
    @POST("/register/idCheck")
    Call<JsonObject> idCheck(@Field("id") String id);

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


    @FormUrlEncoded
    @POST("/getReviews/")
    Call<JsonArray> getReviews(@Field("userIndex") String userIndex, @Field("getMine") boolean getMine, @Field("reviewCount") int reviewCount);

    @FormUrlEncoded
    @POST("/missionCandidate/insert")
    Call<JsonObject> insertMissionCandidate(@Field("userIndex") String userIndex, @Field("missionName") String missionName);

    @FormUrlEncoded
    @POST("/missionCandidate/get")
    Call<JsonArray> getMissionCandidate(@Field("userIndex") String userIndex, @Field("missionCandidateCount") int count,@Field("mode") int mode);

    @FormUrlEncoded
    @POST("/missionCandidate/increment")
    Call<JsonObject> evaluateMissionCandidate(@Field("userIndex") String userIndex, @Field("missionCandidateIndex") int missionCandidateIndex, @Field("which") int which,@Field("value") int value);


    @POST("/missionKing/get")
    Call<JsonArray> getMissionKing();

    @FormUrlEncoded
    @POST("/register/mypage")
    Call<JsonObject> mypage(@Field("userIndex") String userIndex, @Field("time_affordable") int time_affordable, @Field("expense_affordable") int expense_affordable, @Field("push_notification") int push_notification);

    @FormUrlEncoded
    @POST("/missionBundle/getSurveyMission")
    Call<JsonObject> getSurveyMission();


}
