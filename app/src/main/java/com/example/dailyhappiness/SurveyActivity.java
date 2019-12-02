package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;

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




        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("가나다라",surveyAdapter.items.get(0).getScore()+"");
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
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
}
