package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.dailyhappiness.databinding.ActivitySurveyBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SurveyActivity extends AppCompatActivity {

    ActivitySurveyBinding binding;

    SurveyAdapter surveyAdapter;
    SurveyListview surveyListview;
    int number = 1;

    RetroClient retroClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_survey);
        binding.setActivity(this);

        //getSurveyList 안에 넣을 것
        surveyAdapter = new SurveyAdapter();

        retroClient = RetroClient.getInstance(this).createBaseApi();

        getSurveyMissions();



        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("가나다라",surveyAdapter.items.get(0).getScore()+"");
                for(int i=0; i<surveyAdapter.items.size(); i++){
                    int isLast=0;
                    if(i==surveyAdapter.items.size()-1){
                        isLast =1;
                    }
                    writeSurveyMissions(Account.getUserIndex(), surveyAdapter.items.get(i).number,surveyAdapter.items.get(i).score,isLast);
                }

            }
        });
    }

    private void getSurveyMissions(){
        retroClient.getSurveyMission(new RetroCallback<JsonArray>(){

            @Override
            public void onError(Throwable t) {


            }

            @Override
            public void onSuccess(int code, JsonArray receivedData) {
                for(int i=0; i<receivedData.size();i++)
                {
                    JsonObject mission = (JsonObject)receivedData.get(i);
                    surveyAdapter.addItem(new Survey(mission.get("missionID").getAsInt(),mission.get("missionName").getAsString()));

                }

                //surveyAdapter.addItem(); //for문 안에
                binding.lvView.setAdapter(surveyAdapter);


            }

            @Override
            public void onFailure(int code) {

            }
        });
    }

    private void writeSurveyMissions(String userIndex,int missionID, int rating,int isLast){
        retroClient.writeSurveyMission(userIndex, missionID, rating,isLast, new RetroCallback<JsonObject>() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                if(receivedData.get("end").getAsInt()==1){
                    login(Account.getId(),Account.getPw());
                    Intent intent = new Intent(getApplicationContext(),MyPageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }
    public void login(final String id, final String pw){
        retroClient.login(id, pw, new RetroCallback<JsonObject>(){

            @Override
            public void onError(Throwable t) {
                Log.e("error", t.toString());
            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                Account.setId(receivedData.get("id").getAsString());
                Account.setPw(receivedData.get("password").getAsString());
                Account.setAge(receivedData.get("age").getAsString());
                Account.setGender(receivedData.get("gender").getAsString());
                Account.setUserIndex(receivedData.get("userIndex").getAsString());
                Account.setEmblem("https://dailyhappiness.xyz/static/img/emblem/grade"+receivedData.get("grade").getAsString()+".png");
                Account.setPush_notification(receivedData.get("push_notification").getAsInt());
                Account.setMissionCount(receivedData.get("missionCount").getAsInt());
                //isFirst가 1이면 처음 접속하는 유저. 0이면 접속 한 적이 있는 유저
                Account.setIsFirst(receivedData.get("isFirst").getAsInt());
                Account.setExpense_affordable(receivedData.get("expense_affordable").getAsInt());
                Account.setTime_affordable(receivedData.get("time_affordable").getAsInt());
                Account.setDidSurvey(receivedData.get("didSurvey").getAsInt());
                Mission.setCount(receivedData.get("count").getAsInt());




            }

            @Override
            public void onFailure(int code)
            {
                Log.e("error", "오류가 생겼습니다.");
            }
        });
    }
}
