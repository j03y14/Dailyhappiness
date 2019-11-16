package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dailyhappiness.databinding.ActivityMyReviewBinding;
import com.example.dailyhappiness.databinding.ActivityYourReviewBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class YourReviewActivity extends AppCompatActivity {

    ActivityYourReviewBinding binding;

    private RetroClient retroClient;
    private ArrayList<Review> reviewArray;

    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i("YourReviewActivity", "ReviewActivity 호출");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_your_review);
        binding.setActivity(this);

        retroClient= RetroClient.getInstance(this).createBaseApi();
        reviewArray = new ArrayList<Review>();

        getReviews(Account.getUserIndex(), false, 0);
        Log.d("", "onCreate: ");

        binding.iBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    /*
    userIndex : user의 id number
    getMine : true이면 자신의 미션들을 최신순으로 가져오는 것.
              false이면 모든 미션들을 최신순으로 가져오는 것.
    reviewCount : 현재까지 몇 개의 미션들을 가져왔는지

     */
    public void getReviews(String userIndex, boolean getMine, int reviewCount){
        Log.i("getReviews","getReviews 호출");
        reviewArray.clear();
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
                    String missionName = review.get("missionName").getAsString();//미션내용
                    String user = review.get("id").getAsString();          //유저 아이디
                    String date = review.get("date").getAsString();        //날짜
                    String content = review.get("comment").getAsString();     //내용
                    int rating = review.get("rating").getAsInt();         //평점 (1점 ~ 10점)
                    int weather = review.get("weather").getAsInt();        //날씨 1: 맑음, 2: 비, 3: 눈, 4: 흐림
                    float temperature = review.get("temperature").getAsFloat();  //온도
                    String image = review.get("picture").getAsString();     //인증사진이 보여지는 사진 주소를 가지고 있음
                    reviewArray.add(new Review(missionNumber,user,date,missionName,content,rating,weather,temperature,image));
                }
                reviewArray.isEmpty();
                listAdapter = new ListAdapter();

                for(int i=0;i<reviewArray.size();i++){
                    listAdapter.addItem(reviewArray.get(i));
                }

                binding.lvView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(int code) {
                Log.e("getReviews", "onFailure");
            }
        });
    }
}
