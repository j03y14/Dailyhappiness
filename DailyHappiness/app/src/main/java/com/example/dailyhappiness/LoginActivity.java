package com.example.dailyhappiness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dailyhappiness.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setActivity(this);

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
}
