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

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    ActivityCreateAccountBinding binding;

    //서버 연동 객체
    private RetroClient retroClient;
    private  Account user;

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
                String cryptoPW = "";

                RadioButton rd = findViewById(binding.rgGender.getCheckedRadioButtonId());  //선택된 라디오 버튼의 아이디 값 받아오기
                String gender = rd.getText().toString();

                String age = binding.edtInputAge.getText().toString();

                String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]+$";  //비밀번호에 특수문자, 숫자, 문자(대소문자 구분) 있는지 확인하기 위한 패턴
                Matcher matcher = Pattern.compile(pwPattern).matcher(pw); //Pattern 클래스의 compile(), matcher() 함수를 활용하여 Matcher 클래스 생성

                if(id.length() == 0 || pw.length() == 0 || resultPW.length() == 0 || age.length() == 0 ){
                    Toast.makeText(CreateAccountActivity.this, "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show();
                }
//                else if(id.equals(user.getId())){
//                    Toast.makeText(CreateAccountActivity.this, "중복 아이디 입니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show();
//
//                }
                else if(pw.length() < 8){
                    Toast.makeText(CreateAccountActivity.this, "비밀번호를 8자리 이상으로 설정해주세요", Toast.LENGTH_SHORT).show();
                }else if(!matcher.find()){
                    Toast.makeText(CreateAccountActivity.this, "비밀번호는 기호,숫자,문자를 포함해야 합니다", Toast.LENGTH_SHORT).show();
                }else if(!pw.equals(resultPW)){ //비밀번호와 비밀번호확인에 쓴게 다를경우
                    Toast.makeText(CreateAccountActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                }else{

                    try {    //비밀번호 암호화
                        cryptoPW = Crypto.sha256(pw);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    Log.i("crypto",cryptoPW);
                    createAccount(v,id, cryptoPW, gender, age);

                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    intent.putExtra("id",id);
                    setResult(2001,intent);
                    finish();

                }

            }
        });
    }

    public void createAccount(View v, final String id, final String pw, String gender, String age){
        retroClient.createAccount(id, pw, gender, age, new RetroCallback<JsonObject>() {
            @Override
            public void onError(Throwable t) {
                Log.e("error", t.toString());
            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                String success = receivedData.get("success").toString();

                Log.d("회원가입", success);
                Log.i("암호화",pw);

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

    /*
    * id를 넣어주면 id가 중복인지 아닌지 확인한다.
    * isDuplicate가 true이면 중복이고 False이면 중복이 아니다.
    * */
    public void idCheck(final String id){
        retroClient.idCheck(id, new RetroCallback<JsonObject>(){
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                Boolean isDuplicate = receivedData.get("duplicate").getAsBoolean();

                if(isDuplicate){
                    //중복이면
                }else{
                    //중복이 아니면
                }

            }

            @Override
            public void onFailure(int code) {

            }
        });
    }
}
