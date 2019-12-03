package com.example.dailyhappiness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
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

public class MissionCandidateActivity extends AppCompatActivity implements AbsListView.OnScrollListener{
    public static Context context;

    ActivityMissionCandidateBinding binding;

    private RetroClient retroClient;
    private ArrayList<MissionCandidate> missionCandidateArray;

    MissionCandidateListAdapter missionCandidateListAdapter;

    private AddMissionDialog addMissionDialog;

    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private int OFFSET = 20;                        // 한 페이지마다 로드할 데이터 갯수
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = DataBindingUtil.setContentView(this,R.layout.activity_mission_candidate);
        binding.setActivity(this);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        missionCandidateArray = new ArrayList<MissionCandidate>();


        getMissionCandidate(Account.userIndex,0,1);

        binding.progressBar.setVisibility(View.GONE);
        binding.lvView.setOnScrollListener(this);

        addMissionDialog = new AddMissionDialog(this);

        // 커스텀 다이얼로그 호출
        binding.iBtnAddMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파라미터에 리스너 등록
                addMissionDialog.show();


            }
        });

        binding.iBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 1. OnScrollListener.SCROLL_STATE_IDLE : 스크롤이 이동하지 않을때의 이벤트(즉 스크롤이 멈추었을때).
        // 2. lastItemVisibleFlag : 리스트뷰의 마지막 셀의 끝에 스크롤이 이동했을때.
        // 3. mLockListView == false : 데이터 리스트에 다음 데이터를 불러오는 작업이 끝났을때.
        // 1, 2, 3 모두가 true일때 다음 데이터를 불러온다.
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            // 화면이 바닦에 닿을때 처리
            // 로딩중을 알리는 프로그레스바를 보인다.
            binding.progressBar.setVisibility(View.VISIBLE);
            mLockListView = true;
            // 다음 데이터를 불러온다.

            // 1초 뒤 프로그레스바를 감추고 데이터를 갱신하고, 중복 로딩 체크하는 Lock을 했던 mLockListView변수를 풀어준다.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    missionCandidateListAdapter.notifyDataSetChanged();
                    getMissionCandidate(Account.getUserIndex(), OFFSET,1);
                    binding.progressBar.setVisibility(View.GONE);
                    mLockListView = false;
                    OFFSET += 20;
                }
            },1000);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // firstVisibleItem : 화면에 보이는 첫번째 리스트의 아이템 번호.
        // visibleItemCount : 화면에 보이는 리스트 아이템의 갯수
        // totalItemCount : 리스트 전체의 총 갯수
        // 리스트의 갯수가 0개 이상이고, 화면에 보이는 맨 하단까지의 아이템 갯수가 총 갯수보다 크거나 같을때.. 즉 리스트의 끝일때. true
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }


    /*
    * userIndex : 사용자 번호
    * count : 여태까지 몇개의 미션 후보를 가져왔는지. 리뷰 리스트랑 똑같음.
    * mode : 1이면 최신순, 0이면 좋아요가 많은 순.
    * */
    public void getMissionCandidate(String userIndex,int count, int mode){
        retroClient.getMissionCandidate(userIndex,count,mode,new RetroCallback<JsonArray>(){

            @Override
            public void onError(Throwable t) {
                Log.i("에드리뷰리스트","확인3");

            }

            @Override
            public void onSuccess(int code, JsonArray receivedData) {
                missionCandidateArray.clear();
                for(int i =0; i<receivedData.size(); i++){
                    JsonObject missionCandidate = (JsonObject) receivedData.get(i);
                    String user = missionCandidate.get("user").getAsString();//유저 아이디
                    String missionName = missionCandidate.get("missionName").getAsString(); //: 미션 내용
                    int index = missionCandidate.get("missionCandidateIndex").getAsInt(); //: 미션 후보 인덱스
                    int likes = missionCandidate.get("totalLikes").getAsInt(); //: 좋아요 수
                    int dislikes = missionCandidate.get("totalDislikes").getAsInt();  //: 싫어요 수
                    int duplicateCount = missionCandidate.get("totalDuplicateCount").getAsInt(); //: 중복체크 수
                    int likeChecked = missionCandidate.get("userLikes").getAsInt();  //: 유저가 좋아요 눌렀는지
                    int dislikeChecked =missionCandidate.get("userDislikes").getAsInt(); //: 유저가 싫어요 눌렀는지
                    int duplicateChecked =missionCandidate.get("userDuplicateCount").getAsInt(); //: 유저가 중복 눌렀는지
                    missionCandidateArray.add(new MissionCandidate(user,missionName,index,likes,dislikes,duplicateCount,likeChecked,dislikeChecked,duplicateChecked));
                }

                missionCandidateListAdapter = new MissionCandidateListAdapter(missionCandidateArray,MissionCandidateActivity.this);

                if(OFFSET==20){
                    binding.lvView.setAdapter(missionCandidateListAdapter);
                }

                missionCandidateListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int code) {
                Log.i("에드리뷰리스트","확인2");
            }
        });
    }


}


