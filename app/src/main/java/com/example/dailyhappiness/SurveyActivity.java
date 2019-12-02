package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;

import com.example.dailyhappiness.databinding.ActivitySurveyBinding;

public class SurveyActivity extends AppCompatActivity {

    ActivitySurveyBinding binding;

    SurveyAdapter surveyAdapter;
    SurveyListview surveyListview;
    int number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_survey);
        binding.setActivity(this);

        //getSurveyList 안에 넣을 것
        surveyAdapter = new SurveyAdapter();

        //onSuccess 넣을 것
        for(int i=0;i<10;i++){
            surveyAdapter.addItem(new Survey(1,"world"));
        }
        //surveyAdapter.addItem();
        binding.lvView.setAdapter(surveyAdapter);

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
