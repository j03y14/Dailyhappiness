package com.example.dailyhappiness;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dailyhappiness.databinding.ActivityMissionCandidateBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MissionCandidateActivity extends AppCompatActivity {

    ActivityMissionCandidateBinding binding;

    private RetroClient retroClient;
    private ArrayList<MissionCandidate> missionCandidateArray;

    private MissionCandidateListAdapter missionCandidateListAdapter;

    private AddMissionDialog addMissionDialog;
    private String mission="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        missionCandidateArray = new ArrayList<MissionCandidate>();

        binding = DataBindingUtil.setContentView(this,R.layout.activity_mission_candidate);
        binding.setActivity(this);


        // 커스텀 다이얼로그 호출
        binding.iBtnAddMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파라미터에 리스너 등록
                addMissionDialog = new AddMissionDialog(MissionCandidateActivity.this);
                addMissionDialog.show();

                //insertMissionCandidate(Account.userIndex,AddMissionDialog.mission);
            }

        });


    }

    public void setMission(String name){
        mission = name;
        Log.d("미션이름받아옴?",Account.getUserIndex()+""+mission);

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

    /*
    * userIndex : 사용자 번호
    * count : 여태까지 몇개의 미션 후보를 가져왔는지. 리뷰 리스트랑 똑같음.
    * mode : 1이면 최신순, 0이면 좋아요가 많은 순.
    * */
    public void getMissionCandidate(String user, String userIndex,int count, int mode){
        retroClient.getMissionCandidate(userIndex,count,mode,new RetroCallback<JsonArray>(){

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, JsonArray receivedData) {
                for(int i =0; i<receivedData.size(); i++){
                    JsonObject missionCandidate = (JsonObject) receivedData.get(i);
                    String user = missionCandidate.get("user").getAsString();//유저 아이디
                    String missionName = missionCandidate.get("missionName").getAsString(); //: 미션 내용
                    int index = missionCandidate.get("missionCandidateIndex").getAsInt(); //: 미션 후보 인덱스
                    int likes = missionCandidate.get("totalLikes").getAsInt(); //: 좋아요 수
                    int dislikes = missionCandidate.get("totalDislikes").getAsInt();  //: 싫어요 수
                    int duplicateCount = missionCandidate.get("totalDuplicateCount").getAsInt(); //: 중복체크 수
                    boolean likeChecked = missionCandidate.get("userLikes").getAsBoolean();  //: 유저가 좋아요 눌렀는지
                    boolean dislikeChecked =missionCandidate.get("userDislikes").getAsBoolean(); //: 유저가 싫어요 눌렀는지
                    boolean duplicateChecked =missionCandidate.get("userDuplicateCount").getAsBoolean(); //: 유저가 중복 눌렀는지
                    missionCandidateArray.add(new MissionCandidate(user,missionName,index,likes,dislikes,duplicateCount,likeChecked,dislikeChecked,duplicateChecked));
                }

                missionCandidateArray.isEmpty();
                missionCandidateListAdapter = new MissionCandidateListAdapter();

                for(int i=0;i<missionCandidateArray.size();i++){
                    missionCandidateListAdapter.addItem(missionCandidateArray.get(i));
                }

                binding.lvView.setAdapter(missionCandidateListAdapter);

            }

            @Override
            public void onFailure(int code) {

            }
        });
    };
    /*
    * 좋아요나 싫어요, 중복 버튼이 눌렸을 때 호출하는 함수
    * userIndex : 사용자 번호
    * missionCandidateIndex : 후보 미션 번호
    * which : 어떤 것을 수정할 것인지 선택 ( 1 : 좋아요 , 2 : 싫어요 , 3 : 중복 )
    * value : which에 해당하는 것을 줄일 것인지 늘릴것인지 ( 1 : 늘리기, -1 : 줄이기 )
    * 예를들어, 좋아요 버튼을 누르면 which = 1, value = 1
    * 그 이후에 다시 좋아요 버튼을 누르면 좋아요 해제 which =1 , value = -1
    * */
    public void evaluateMissionCandidate(String userIndex, int missionCandidateIndex,int which, int value){
        retroClient.evaluateMissionCandidate(userIndex,missionCandidateIndex,which,value,new RetroCallback<JsonObject>(){

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {

            }

            @Override
            public void onFailure(int code) {

            }
        });
    };


}


