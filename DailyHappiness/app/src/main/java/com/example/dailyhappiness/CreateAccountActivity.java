package com.example.dailyhappiness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dailyhappiness.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {

    ActivityCreateAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_account);
        binding.setActivity(this);

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = binding.edtInputID.getText().toString();
                String pw = binding.edtInputPW.getText().toString();
                String resultPW = binding.edtRepeatInputPW.getText().toString();

                RadioButton rd = findViewById(binding.rgGender.getCheckedRadioButtonId());  //선택된 라디오 버튼의 아이디 값 받아오기
                String gender = rd.getText().toString();

                String age = binding.edtInputAge.getText().toString();


                if(!pw.equals(resultPW)){ //비밀번호와 비밀번호확인에 쓴게 다를경우
                    Toast.makeText(CreateAccountActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                }else{
                    Account.setId(id);
                    Account.setPw(pw);
                    Account.setGender(gender);
                    Account.setAge(age);

                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    intent.putExtra("id",id);
                    setResult(2001,intent);
                    finish();
                }

            }
        });
    }
}
