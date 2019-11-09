package com.example.dailyhappiness;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.example.dailyhappiness.databinding.ActivityLoginBinding;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static com.example.dailyhappiness.Account.userIndex;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    SharedPreferences sp; //(저장될 키, 값)

    //서버 연동 객체
    private RetroClient retroClient;
    private Account user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        tedPermission();


        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setActivity(this);

        sp = getSharedPreferences("sp",Activity.MODE_PRIVATE); //(저장될 키, 값)
        String loginID = sp.getString("id", ""); // 처음엔 값이 없으므로 ""
        String loginPW = sp.getString("pw","");
        //String loginUserID = sp.getString("userIndex","");

        retroClient = RetroClient.getInstance(this).createBaseApi();
        user = Account.getInstance();

        if(loginID != "" && loginPW != "") {
            Toast.makeText(this, "자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
            login(loginID,loginPW);
        }
        else if(loginID == "" && loginPW == "") {
            binding.btnLogin.setOnClickListener(new View.OnClickListener() {  //로그인 버튼을 눌렀을때
                @Override
                public void onClick(View v) {

                    String id = binding.edtInputID.getText().toString();
                    String pw = binding.edtInputPW.getText().toString();
                    String cryptoPW = "";

                    try {
                        cryptoPW = Crypto.sha256(pw);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    login( id, cryptoPW);

                }
            });
        }

        binding.btnJoinIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //회원가입하기를 눌렀을때
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivityForResult(intent,1001);
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

    public void login(final String id, final String pw){
        retroClient.login(id, pw, new RetroCallback<JsonObject>(){

            @Override
            public void onError(Throwable t) {
                Log.e("error", t.toString());
            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                Account.setId(receivedData.get("id").getAsString());
                Account.setPw(receivedData.get("password").getAsString());
                Account.setAge(receivedData.get("age").getAsString());
                Account.setGender(receivedData.get("gender").getAsString());
                Account.setUserIndex(receivedData.get("userIndex").getAsString());
                Mission.setCount(receivedData.get("count").getAsInt());

                if (!id.equals(Account.getId())) {     //해당 아이디가 목록에 없을때
                    Toast.makeText(LoginActivity.this, "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if (!pw.equals(Account.getPw())) {   //비밀번호가 일치하지 않을때
                    Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);

                    SharedPreferences.Editor editor = sp.edit(); //로그인 정보 저장
                    editor.putString("id", id);
                    editor.putString("pw", pw);
                    editor.putString("userIndex", Account.getUserIndex());
                    editor.commit();
                    finish();
                }
            }

            @Override
            public void onFailure(int code)
            {
                Log.e("error", "오류가 생겼습니다.");
            }
        });
    }

    private void tedPermission() { //권한 요청

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

    }


}
