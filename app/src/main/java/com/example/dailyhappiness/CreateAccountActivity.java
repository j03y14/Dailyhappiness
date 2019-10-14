package com.example.dailyhappiness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dailyhappiness.databinding.ActivityCreateAccountBinding;
import com.google.gson.JsonObject;

public class CreateAccountActivity extends AppCompatActivity {

    ActivityCreateAccountBinding binding;

    //서버 연동 객체
    private RetroClient retroClient;
    private Account user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_account);
        binding.setActivity(this);

        retroClient = RetroClient.getInstance(this).createBaseApi();
        user = Account.getInstance();

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

                    createAccount(v, id,pw,gender,age);

                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    intent.putExtra("id",id);
                    setResult(2001,intent);
                    finish();
                }

            }
        });
    }

    public void createAccount(View v, final String id, String pw, String gender, String age){
        retroClient.createAccount(id, pw, gender, age, new RetroCallback<JsonObject>() {
            @Override
            public void onError(Throwable t) {
                Log.e("error", t.toString());
            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                String success = receivedData.get("success").toString();

                Log.d("회원가입", success);

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra("id",id);
                setResult(2001,intent);
                finish();

            }

            @Override
            public void onFailure(int code) {
                Log.e("error", "오류가 생겼습니다.");
            }
        });
    }
}
