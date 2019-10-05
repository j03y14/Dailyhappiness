package com.example.dailyhappiness;



import com.google.gson.JsonObject;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroBaseApiService {

    final String Base_Url = "http://flagtag.cafe24.com/";

    @FormUrlEncoded
    @POST("/Register.php")
    Call<JsonObject> createAccount(@Field("id") String id, @Field("password") String password, @Field("gender") String gender, @Field("age") String age);

    @FormUrlEncoded
    @POST("/Login.php")
    Call<JsonObject> login(@Field("id") String id, @Field("password") String password);

}
