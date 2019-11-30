package com.example.dailyhappiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.example.dailyhappiness.databinding.ActivityMyPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyPageActivity extends AppCompatActivity {

    ActivityMyPageBinding binding;
    private Thread thread;
    private Bitmap bitmapProfile;
    private String clover = "";
    private boolean pushSate = true; //푸시 알람 상태 받아오기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_page);
        binding.setActivity(this);

        //clover = 사진주소
        getProfile(clover);  //프로필 클로버 가져오기

        binding.tvId.setText(Account.getId());

        binding.tvMissionCount.setText("10");  //수행한 미션 개수

        binding.tvTime.setText("30"+" 분");    //설정한 시간

        binding.tvCost.setText("10000"+" 원"); //설정한 비용

        //푸시 알람
        if(pushSate){
            binding.switchPush.setChecked(true);
        }else {
            binding.switchPush.setChecked(false);
        }
        binding.switchPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //푸시 알림 동의 설정 할 때 쓰는 코드
                    FirebaseMessaging.getInstance().subscribeToTopic("agree")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                }
                            });

                }else {
                    //푸시 알림 거절 설정 할 때 쓰는 코드
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("agree");
                }
            }
        });

        //시간,단위 10분
        binding.btnMinus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = binding.tvTime.getText().toString().length();
                int time = Integer.parseInt(binding.tvTime.getText().toString().substring(0,length-2));
                time -= 10;
                binding.tvTime.setText(time+" 분");
            }
        });

        binding.btnPlus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = binding.tvTime.getText().toString().length();
                int time = Integer.parseInt(binding.tvTime.getText().toString().substring(0,length-2));
                time += 10;
                binding.tvTime.setText(time+" 분");
            }
        });

        //비용, 단위 1000원
        binding.btnMinus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = binding.tvCost.getText().toString().length();
                int cost = Integer.parseInt(binding.tvCost.getText().toString().substring(0,length-2));
                cost -= 1000;
                binding.tvCost.setText(cost+" 원");
            }
        });

        binding.btnPlus2.setOnClickListener(new View.OnClickListener() { //시간 마이너스, 단위 10분
            @Override
            public void onClick(View v) {
                int length = binding.tvCost.getText().toString().length();
                int cost = Integer.parseInt(binding.tvCost.getText().toString().substring(0,length-2));
                cost += 1000;
                binding.tvCost.setText(cost+" 원");
            }
        });

        binding.iBtnBack.setOnClickListener(new View.OnClickListener() { //뒤로가기 버튼 누르면 현재 상태 저장
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
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
            binding.ivProfile.setImageBitmap(bitmapProfile);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
