package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.example.dailyhappiness.databinding.ActivityYourReviewBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class YourReviewActivity extends AppCompatActivity implements AbsListView.OnScrollListener{

    ActivityYourReviewBinding binding;

    private RetroClient retroClient;
    private ArrayList<Review> reviewArray;

    private ListAdapter listAdapter;

    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private int OFFSET = 10;                        // 한 페이지마다 로드할 데이터 갯수
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i("YourReviewActivity", "ReviewActivity 호출");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_your_review);
        binding.setActivity(this);

        retroClient= RetroClient.getInstance(this).createBaseApi();
        reviewArray = new ArrayList<Review>();
        listAdapter = new ListAdapter();

        getReviews(Account.getUserIndex(), false, 0);
        Log.d("", "onCreate: ");

        binding.progressBar.setVisibility(View.GONE);
        binding.lvView.setOnScrollListener(this);

        binding.iBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.iBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OFFSET=10;
                getReviews(Account.getUserIndex(), false, 0);
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 1. OnScrollListener.SCROLL_STATE_IDLE : 스크롤이 이동하지 않을때의 이벤트(즉 스크롤이 멈추었을때).
        // 2. lastItemVisibleFlag : 리스트뷰의 마지막 셀의 끝에 스크롤이 이동했을때.
        // 3. mLockListView == false : 데이터 리스트에 다음 데이터를 불러오는 작업이 끝났을때.
        // 1, 2, 3 모두가 true일때 다음 데이터를 불러온다.
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            // 화면이 바닦에 닿을때 처리
            // 로딩중을 알리는 프로그레스바를 보인다.
            binding.progressBar.setVisibility(View.VISIBLE);


            mLockListView = true;
            // 다음 데이터를 불러온다.

            // 1초 뒤 프로그레스바를 감추고 데이터를 갱신하고, 중복 로딩 체크하는 Lock을 했던 mLockListView변수를 풀어준다.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listAdapter.notifyDataSetChanged();
                    getReviews(Account.getUserIndex(), false, OFFSET);
                    binding.progressBar.setVisibility(View.GONE);
                    mLockListView = false;
                    OFFSET += 10;
                }
            },1000);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // firstVisibleItem : 화면에 보이는 첫번째 리스트의 아이템 번호.
        // visibleItemCount : 화면에 보이는 리스트 아이템의 갯수
        // totalItemCount : 리스트 전체의 총 갯수
        // 리스트의 갯수가 0개 이상이고, 화면에 보이는 맨 하단까지의 아이템 갯수가 총 갯수보다 크거나 같을때.. 즉 리스트의 끝일때. true
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }


    /*
    userIndex : user의 id number
    getMine : true이면 자신의 미션들을 최신순으로 가져오는 것.
              false이면 모든 미션들을 최신순으로 가져오는 것.
    reviewCount : 현재까지 몇 개의 미션들을 가져왔는지

     */
    public void getReviews(String userIndex, boolean getMine, int reviewCount){
        Log.i("getReviews","getReviews 호출");

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
                    //int weather = review.get("weather").getAsInt();        //날씨 1: 맑음, 2: 비, 3: 눈, 4: 흐림
                    float temperature = review.get("temperature").getAsFloat();  //온도
                    String image = review.get("picture").getAsString();     //인증사진이 보여지는 사진 주소를 가지고 있음
                    String emblem = "https://dailyhappiness.xyz/static/img/emblem/grade"+review.get("grade").getAsString()+".png";
                   // reviewArray.add(new Review(missionNumber,user,date,missionName,content,rating,weather,temperature,image));

                    listAdapter.addItem((new Review(missionNumber,user,date,missionName,content,rating,temperature,image,emblem)));
                }


                if(OFFSET==10){
                    binding.lvView.setAdapter(listAdapter);
                }

                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int code) {
                Log.e("getReviews", "onFailure");
            }
        });
    }
}
