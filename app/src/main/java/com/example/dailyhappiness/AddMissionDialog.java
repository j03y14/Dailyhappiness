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
import android.widget.Adapter;
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

        retroClient = RetroClient.getInstance(context).createBaseApi();

        setContentView(R.layout.activity_add_mission_dialog);

        // 다이얼로그 크기 조절
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lp.dimAmount = 0.8f;
        Window window = this.getWindow();
        window.setAttributes(lp);

        btnOk=findViewById(R.id.btnOk);
        btnCancel=findViewById(R.id.btnCancel);
        edtMission=findViewById(R.id.edtMission);


        //클릭 리스너 셋팅
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mission = edtMission.getText().toString();
                setMission(mission);
                ((MissionCandidateActivity)MissionCandidateActivity.context).getMissionCandidate(Account.userIndex,0,1);
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

                //((MissionCandidateActivity)context).missionCandidateListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int code) {
                Log.i("미션안들어감","웅 안들어감ㅋㅋ");
            }
        });
    };

}
