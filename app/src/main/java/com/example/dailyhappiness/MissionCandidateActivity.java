package com.example.dailyhappiness;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MissionCandidateActivity extends AppCompatActivity {

    private RetroClient retroClient;
    private ArrayList<MissionCandidate> missionCandidateArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        missionCandidateArray = new ArrayList<MissionCandidate>();


    }


    /* 미션 후보를 넣는 사용자의 index 와 미션이 무슨 미션인지를 받아서 보내면 됨.
    *  미션 후보를 추가할 때 쓰는 함수
    * */
    public void insertMissionCandidate(String userIndex, String missionName){
        retroClient.insertMissionCandidate(userIndex,missionName,new RetroCallback<JsonObject>(){

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

    /*
    * userIndex : 사용자 번호
    * count : 여태까지 몇개의 미션 후보를 가져왔는지. 리뷰 리스트랑 똑같음.
    * mode : 1이면 최신순, 0이면 좋아요가 많은 순.
    * */
    public void getMissionCandidate(String userIndex,int count, int mode){
        retroClient.getMissionCandidate(userIndex,count,mode,new RetroCallback<JsonArray>(){

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, JsonArray receivedData) {
                for(int i =0; i<receivedData.size(); i++){
                    JsonObject missionCandidate = (JsonObject) receivedData.get(i);
                    String missiionName = missionCandidate.get("missionName").getAsString(); //: 미션 내용
                    int index = missionCandidate.get("missionCandidateIndex").getAsInt(); //: 미션 후보 인덱스
                    int likes = missionCandidate.get("totalLikes").getAsInt(); //: 좋아요 수
                    int dislikes = missionCandidate.get("totalDislikes").getAsInt();  //: 싫어요 수
                    int duplicateCount = missionCandidate.get("totalDuplicateCount").getAsInt(); //: 중복체크 수
                    boolean likeChecked = missionCandidate.get("userLikes").getAsBoolean();  //: 유저가 좋아요 눌렀는지
                    boolean dislikeChecked =missionCandidate.get("userDislikes").getAsBoolean(); //: 유저가 싫어요 눌렀는지
                    boolean duplicateChecked =missionCandidate.get("userDuplicateCount").getAsBoolean(); //: 유저가 중복 눌렀는지
                    missionCandidateArray.add(new MissionCandidate(missiionName,index,likes,dislikes,duplicateCount,likeChecked,dislikeChecked,duplicateChecked));
                }
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


