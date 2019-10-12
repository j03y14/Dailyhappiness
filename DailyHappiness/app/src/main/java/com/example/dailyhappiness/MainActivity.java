package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dailyhappiness.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Date currentDate = Calendar.getInstance().getTime();
    long nowTime = System.currentTimeMillis();
    Date time = new Date(nowTime);

    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
    SimpleDateFormat timeFormat1 = new SimpleDateFormat("HH",Locale.getDefault());
    SimpleDateFormat timeFormat2 = new SimpleDateFormat("mm",Locale.getDefault());

    String month = monthFormat.format(currentDate);
    String date = dayFormat.format(currentDate);
    String hours = timeFormat1.format(time);
    String minutes = timeFormat2.format(time);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setActivity(this);

        binding.tvDate.setText(month+" / "+date);

        binding.tvLeftTime.setText("남은 시간 "+(23-Integer.parseInt(hours)) + " : " + (60-Integer.parseInt(minutes)));

        binding.tvMission.setText(Mission.getTodayMission());

        binding.imgBtnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WriteReviewActivity.class);
                startActivity(intent);
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제함

                SharedPreferences sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();  //sp에 있는 모든 정보를 기기에서 삭제
                editor.commit();

                Toast.makeText(MainActivity.this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
