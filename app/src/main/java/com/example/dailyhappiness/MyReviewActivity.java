package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MyReviewActivity extends AppCompatActivity {

    private RetroClient retroClient;
    private ArrayList<Review> reviewArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MyReviewActivity", "MyReviewActivity 호출");
        setContentView(R.layout.activity_my_review);

        retroClient= RetroClient.getInstance(this).createBaseApi();
        reviewArray = new ArrayList<Review>();
        getReviews(Account.getUserIndex(), true, 0);
    }





    /*
    userIndex : user의 id number
    getMine : true이면 자신의 미션들을 최신순으로 가져오는 것.
              false이면 모든 미션들을 최신순으로 가져오는 것.
    reviewCount : 현재까지 몇 개의 미션들을 가져왔는지

     */
    public void getReviews(String userIndex, boolean getMine, int reviewCount){
        Log.i("getReives","getRevies 호출");
        retroClient.getReviews(userIndex, getMine, reviewCount, new RetroCallback<JsonArray>() {
            @Override
            public void onError(Throwable t) {
                Log.e("getReviews", "onError");
            }

            @Override
            public void onSuccess(int code, JsonArray receivedData) {
                Log.i("getReviews", "onSuccess");
                for(int i=0; i<receivedData.size();i++) {
                    JsonObject review = (JsonObject) receivedData.get(i);
                    int missionNumber = review.get("mission").getAsInt();  //미션번호
                    String user = review.get("id").getAsString();          //유저 아이디
                    String date = review.get("date").getAsString();        //날짜
                    String content = review.get("comment").getAsString();     //내용
                    int rating = review.get("rating").getAsInt();         //평점 (1점 ~ 10점)
                    int weather = review.get("weather").getAsInt();        //날씨 1: 맑음, 2: 비, 3: 눈, 4: 흐림
                    float temperature = review.get("temperature").getAsFloat();  //온도
                    String image = review.get("picture").getAsString();     //인증사진이 보여지는 사진 주소를 가지고 있음
                    reviewArray.add(new Review(missionNumber,user,date,content,rating,weather,temperature,image));
                }
                reviewArray.isEmpty();
            }

            @Override
            public void onFailure(int code) {
                Log.e("getReviews", "onFailure");
            }
        });
    }
}












