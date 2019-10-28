package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dailyhappiness.databinding.ActivityMainBinding;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    private RetroClient retroClient;
    private Account user;

    //현재 날짜와 시간 가져오기
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

        retroClient = RetroClient.getInstance(this).createBaseApi();
        user = Account.getInstance();

        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //화면 위에 액션바 없애기

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setActivity(this);

        binding.tvDate.setText(month+" / "+date);

        binding.tvLeftTime.setText("남은 시간 "+(23-Integer.parseInt(hours)) + " : " + (60-Integer.parseInt(minutes)));

        getMission(user.getUserIndex());




        binding.ibtnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WriteReviewActivity.class);
                startActivity(intent);
            }
        });

        binding.ibtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        binding.ibtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제함

                SharedPreferences sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();  //sp에 있는 모든 정보를 기기에서 삭제
                editor.commit();

                Toast.makeText(MainActivity.this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public  void show(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.Theme_AppCompat_Light_Dialog_Alert);

        dialog.setTitle("이 미션이 마음에 안 드시나요?");
        dialog.setPositiveButton("다음에 할래요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //기존 미션을 목록에서 삭제하지 않고 다음으 미션으로 넘기기
                passMission(user.getUserIndex(),"true");

            }
        });

        dialog.setNegativeButton("마음에 안 들어요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //미션 목록에서 지우고 다음 미션으로 넘기기
                passDislikeMission(user.getUserIndex(), "true", String.valueOf(Mission.getMissionNumber()),"true" );
            }
        });

        dialog.show();
    }

    public void getMission(final String userIndex){
        retroClient.getMission(userIndex, new RetroCallback<JsonObject>(){
            @Override
            public void onError(Throwable t) {
                Log.e("getMission error", t.toString());
            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {

                Mission.setMissionNumber(receivedData.get("missionIndex").getAsInt());
                Mission.setTodayMission(receivedData.get("missionName").getAsString());
                binding.tvMission.setText(Mission.getTodayMission());
            }

            @Override
            public void onFailure(int code) {
                Log.e("error", "getMission 오류가 생겼습니다.");
            }
        });
    }

    //다음에 할게요로 미션 넘기기
    public void passMission(final String userIndex, final String cost){
        retroClient.passMission(userIndex, cost, new RetroCallback<JsonObject>() {
            @Override
            public void onError(Throwable t) {
                Log.e("passMission error", t.toString());
            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                //서버에서 count를 증가시키고 user의 missionOrder를 증가시키면 미션을 다시 가져온다.
                getMission(userIndex);
            }

            @Override
            public void onFailure(int code) {
                Log.e("error", "passMission 오류가 생겼습니다.");
            }
        });
    }
    //미션이 싫어서 미션을 넘기는 것
    public void passDislikeMission(final String userIndex, final String cost,final String mission,final String dislike){
        retroClient.passDislikeMission(userIndex, cost, mission, dislike,new RetroCallback<JsonObject>() {
            @Override
            public void onError(Throwable t) {
                Log.e("passDislikMissionerror", t.toString());
            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                //서버에서 count를 증가시키고 user의 missionOrder를 증가시키고 해당 미션 점수를 1점으로 주면 미션을 다시 가져온다.
                getMission(userIndex);
            }

            @Override
            public void onFailure(int code) {
                Log.e("error", "passDislikMissionerror 오류가 생겼습니다.");
            }
        });
    }

}
