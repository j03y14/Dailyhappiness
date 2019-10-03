package com.example.dailyhappiness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dailyhappiness.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    String sfName = "myFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setActivity(this);

        // 지난번 저장해놨던 사용자 입력값을 꺼내서 보여주기
        SharedPreferences sp = getSharedPreferences(sfName, 0);
        String id = sp.getString("id", ""); // 키값으로 꺼냄
        String pw = sp.getString("pw","");
        binding.edtInputID.setText(id);
        binding.edtInputPW.setText(pw);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {  //로그인 버튼을 눌렀을때
            @Override
            public void onClick(View v) {

                String id = binding.edtInputID.getText().toString();
                String pw = binding.edtInputPW.getText().toString();

                if(!id.equals(Account.getId())){     //해당 아이디가 목록에 없을때
                    Toast.makeText(LoginActivity.this, "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }else if(!pw.equals(Account.getPw())){   //비밀번호가 일치하지 않을때
                    Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();
                }
            }
        });

        binding.btnJoinIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //회원가입하기를 눌렀을때
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivityForResult(intent,1001);
                //startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1001){
            if(resultCode == 2001){
                String id = data.getStringExtra("id");
                binding.edtInputID.setText(id);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Activity가 종료되기 전에 저장
        //SharedPreference에 아이디와 비밀번호 저장

        SharedPreferences sp = getSharedPreferences(sfName, 0);
        SharedPreferences.Editor editor = sp.edit();//저장하려면 editor가 필요

        String id = binding.edtInputID.getText().toString(); // 사용자가 입력한 값
        String pw = binding.edtInputPW.getText().toString();

        editor.putString("id", id); // 입력
        editor.putString("pw", pw);

        editor.commit(); // 파일에 최종 반영함


    }
}
