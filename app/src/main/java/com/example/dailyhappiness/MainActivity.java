package com.example.dailyhappiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyhappiness.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private RetroClient retroClient;
    private Account user;

    private Thread thread;
    private Bitmap bitmapProfile;
    private String clover = "";

    //현재 날짜와 시간 가져오기
    SimpleDateFormat timeFormat1;
    SimpleDateFormat timeFormat2;
    SimpleDateFormat timeFormat3;
    String hours;
    String minutes;
    String seconds;
    Date currentDate = Calendar.getInstance().getTime();


    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());


    String month = monthFormat.format(currentDate);
    String date = dayFormat.format(currentDate);

    int state = 0;

    //DrawerActivity에서 가져다 씀
    View drawer;
    ImageButton tvLogout;
    ImageButton btnMyPage;
    ImageButton btnMyReview;
    ImageButton btnYourReview;
    ImageButton btnCalendar;
    ImageButton btnCandidate;
    ImageButton btnKingOfKings;
    ImageView ivID;
    TextView tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        retroClient = RetroClient.getInstance(this).createBaseApi();
        user = Account.getInstance();

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setActivity(this);

//        if(state == 0){
//            Intent intent = new Intent(getApplicationContext(),SurveyActivity.class);
//            startActivity(intent);
//            state++;
//            finish();
//        }


        //메뉴
        drawer = findViewById(R.id.drawer);
        tvLogout = findViewById(R.id.tvLogout);
        btnMyPage = findViewById(R.id.btnMyPage);
        btnMyReview = findViewById(R.id.btnMyReview);
        btnYourReview = findViewById(R.id.btnYourReview);
        btnCalendar = findViewById(R.id.btnCalendar);
        btnCandidate = findViewById(R.id.btnCandidate);
        btnKingOfKings = findViewById(R.id.btnKingOfKings);
        ivID = findViewById(R.id.ivID);
        tvId = findViewById(R.id.tvId);
        //메뉴끝

        binding.tvDate.setText(month+" / "+date);

        showLeftTime();

        getMission(Account.getUserIndex());


        binding.iBtnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WriteReviewActivity.class);
                startActivity(intent);
            }
        });

        binding.ibtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Mission.getCount()>=3){
                    Toast.makeText(MainActivity.this, "미션은 하루에 2번만 넘길 수 있어요", Toast.LENGTH_SHORT).show();
                }else{
                    show();


                }
            }
        });

        binding.iBtnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                binding.drawerLayout.openDrawer(drawer);

            }
        });

        binding.drawerLayout.setDrawerListener(drawerListener);
        drawer.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return true;
            }
        });

        //메뉴에서 넘어갈때
        clover = Account.getEmblem();
        getProfile(clover);
        tvId.setText(Account.getId());
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제함

                SharedPreferences sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();  //sp에 있는 모든 정보를 기기에서 삭제
                editor.commit();

                Toast.makeText(MainActivity.this, "로그아웃되었습니다", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MyPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),MyReviewActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnYourReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),YourReviewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MissionCandidateActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnKingOfKings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),KingListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //메뉴 끝

    }

    DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {

        public void onDrawerClosed(View drawerView) {
        }
        public void onDrawerOpened(View drawerView) {
        }

        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        public void onDrawerStateChanged(int newState) {

        }
    };

    public void showLeftTime(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                // super.handleMessage(msg);

                timeFormat1 = new SimpleDateFormat("HH",Locale.getDefault());
                timeFormat2 = new SimpleDateFormat("mm",Locale.getDefault());
                //timeFormat3 = new SimpleDateFormat("ss",Locale.getDefault());

                hours = timeFormat1.format(new Date());
                minutes = timeFormat2.format(new Date());
                //seconds = timeFormat3.format(new Date());

                binding.tvLeftTime.setText("남은 시간 "+(23-Integer.parseInt(hours)) + " : " + (60-Integer.parseInt(minutes)) );
            }
        };

        Runnable task = new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(1);  //핸들러를 호출해 시간을 갱신
                }

            }
        };

        Thread thread = new Thread(task);
        thread.start();
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
                // int count = Mission.getCount();

                    Mission.setCount(receivedData.get("count").getAsInt());
                    getMission(userIndex);


                // Mission.setCount(count++);
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
                //int count = Mission.getCount();
                Mission.setCount(receivedData.get("count").getAsInt());
                getMission(userIndex);
                // Mission.setCount(count++);
            }

            @Override
            public void onFailure(int code) {
                Log.e("error", "passDislikMissionerror 오류가 생겼습니다.");
            }
        });

    }

    //클로버 가져오기
    private void getProfile(final String imageFile){
        thread = new Thread(){
            public void run(){

                try {
                    URL url = new URL(imageFile);
                    //https://dailyhappiness.xyz/image?filename=70-2019_11_12.jpg

                    // Web에서 이미지를 가져온 뒤
                    // ImageView에 지정할 Bitmap을 만든다
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로 부터 응답 수신
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmapProfile = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start(); // Thread 실행

        try {
            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            thread.join();

            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
            Log.d("이미지로드확인","휴");
            ivID.setImageBitmap(bitmapProfile);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
