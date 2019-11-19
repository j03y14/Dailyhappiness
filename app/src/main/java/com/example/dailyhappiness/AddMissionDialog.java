package com.example.dailyhappiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

public class AddMissionDialog extends Dialog{

    private Button btnOk;
    private Button btnCancel;
    private EditText edtMission;

    private Context context = null;
    private String mission = "";

    private RetroClient retroClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        retroClient = RetroClient.getInstance(context).createBaseApi();

        //사이즈조절
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(getWindow().getAttributes());
        wm.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(wm);

        setContentView(R.layout.activity_add_mission_dialog);

        btnOk=findViewById(R.id.btnOk);
        btnCancel=findViewById(R.id.btnCancel);
        edtMission=findViewById(R.id.edtMission);


        //클릭 리스너 셋팅
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //((MissionCandidateActivity)context).setMission(mission);  //새로 정의 안 하고 미션캔디데잇액티비티의 셋미션 메소드 씀
                mission = edtMission.getText().toString();
                setMission(mission);

                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    //생성자 생성
    public AddMissionDialog(@NonNull Context context) {
        super(context);

    }

    public void setMission(String name){
        mission = name;
        //Log.d("미션이름받아옴?",Account.getUserIndex()+""+mission);

        insertMissionCandidate(Account.getUserIndex(), mission);
    }


    /* 미션 후보를 넣는 사용자의 index 와 미션이 무슨 미션인지를 받아서 보내면 됨.
     *  미션 후보를 추가할 때 쓰는 함수
     * */
    public void insertMissionCandidate(String userIndex, String missionName){
        retroClient.insertMissionCandidate(userIndex,missionName,new RetroCallback<JsonObject>(){
            @Override
            public void onError(Throwable t) {
                Log.i("미션안들어감","웅 안들어감");
            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                Log.i("미션들어감?","웅 들어감");

                // missionCandidateListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int code) {
                Log.i("미션안들어감","웅 안들어감ㅋㅋ");
            }
        });
    };

}
