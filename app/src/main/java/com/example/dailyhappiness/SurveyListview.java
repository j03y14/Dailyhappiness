package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class SurveyListview extends LinearLayout {
    TextView tvNumber;
    TextView tvMission;
    TextView tvScore;
    SeekBar seekBar;
    int number= 0 ;

    public SurveyListview(Context context) {
        super(context);
        init(context);
    }

    public SurveyListview(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_survey_listview,this,true);

        tvNumber = findViewById(R.id.tvNumber);
        tvMission = findViewById(R.id.tvMission);
        tvScore = findViewById(R.id.tvScore);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { //움직임 중
                number = seekBar.getProgress();
                setScore();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { //움직임이 시작될때
                number = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { //움직임이 멈췄을 때
                number = seekBar.getProgress();
            }
        });

    }

    public void setNumber(int number){
        tvNumber.setText(number+"");
    }

    public void setMission(String mission){
        tvMission.setText(mission);
    }

    public void setScore(){
        tvScore.setText(new StringBuilder().append(number));
    }
}
