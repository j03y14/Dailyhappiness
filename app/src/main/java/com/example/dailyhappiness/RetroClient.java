package com.example.dailyhappiness;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {


    private RetroBaseApiService apiService;
    private static final String URL = RetroBaseApiService.Base_Url;
    private static Context mContext;
    private static Retrofit retrofit;

    private static class SingletonHolder {
        private static RetroClient INSTANCE = new RetroClient(mContext);
    }

    public static RetroClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }


    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build();

    private RetroClient(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    public RetroClient createBaseApi(){
        apiService = create(RetroBaseApiService.class);
        return this;
    }



    public static <S> S create(Class<S> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    public void createAccount(String id, String pw, String gender, String age, final RetroCallback callback){
        apiService.createAccount(id, pw, gender ,age).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public void idCheck(String id, final RetroCallback callback){
        apiService.idCheck(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void login(String id, String pw, final RetroCallback callback){
        apiService.login(id, pw).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getMission(String userIndex, final RetroCallback callback){
        apiService.getMission(userIndex).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }

    public void incrementMissionOrder(String userIndex, final RetroCallback callback){
        apiService.incrementMissionOrder(userIndex).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }

    public void passMission(String userIndex, String count, final RetroCallback callback){
        apiService.passMission(userIndex,count).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }

    public void passDislikeMission(String userIndex, String count, String mission, String dislike, final RetroCallback callback){
        apiService.passDislikeMission(userIndex,count,mission,dislike).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }

    public void uploadImage(MultipartBody.Part file, RequestBody requestBody, final RetroCallback callback){
        apiService.uploadImage(file,requestBody).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }

    public void uploadReview(String userIndex, int missionIndex, String missionRating, String location_lat, String location_lon, String content, final RetroCallback callback){
        apiService.uploadReview(userIndex, missionIndex, missionRating, location_lat, location_lon, content).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    public void getReviews(String userIndex, boolean getMine, int reviewCount, final RetroCallback callback){
        apiService.getReviews(userIndex,getMine,reviewCount).enqueue(new Callback<JsonArray>(){
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }

    public void insertMissionCandidate(String userIndex, String missionName, final RetroCallback callback){
        apiService.insertMissionCandidate(userIndex,missionName).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }
    public void getMissionKing(final RetroCallback callback){
        apiService.getMissionKing().enqueue(new Callback<JsonArray>(){
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }


    public void getMissionCandidate(String userIndex, int count, int mode, final RetroCallback callback){
        apiService.getMissionCandidate(userIndex,count,mode).enqueue(new Callback<JsonArray>(){
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }

    public void evaluateMissionCandidate(String userIndex, int missionCandidateIndex,int which, int value, final RetroCallback callback){
        apiService.evaluateMissionCandidate(userIndex,missionCandidateIndex,which,value).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }

    public void mypage(String userIndex, int time_affordable,int expense_affordable, int push_notification, final RetroCallback callback){
        apiService.mypage(userIndex,time_affordable,expense_affordable,push_notification).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onError(t);
            }
        } );
    }

}
