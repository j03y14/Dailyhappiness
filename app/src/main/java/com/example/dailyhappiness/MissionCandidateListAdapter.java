package com.example.dailyhappiness;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MissionCandidateListAdapter extends BaseAdapter {
    ArrayList<MissionCandidate> items;
    Context context;

    private RetroClient retroClient;


    public MissionCandidateListAdapter(ArrayList<MissionCandidate> items, Context context){
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public MissionCandidate getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //convertView가 한번 만들어지면 다시 안 만들어도 되기때문에 원래 컨벌트가 널인지 아닌지 먼저 확인을 해봐야 함
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        retroClient = RetroClient.getInstance(context).createBaseApi();
        MissionCandidateListview view = new MissionCandidateListview(parent.getContext());
        final MissionCandidate item=items.get(position);

        view.setID(item.getUser());
        view.setMission(item.getMissionName());
        view.setLikeCount(item.getLikes());
        view.setDislikeCount(item.getDislikes());
        view.setDuplicateCount(item.getDuplicateCount());

        final ImageButton iBtnLike = view.findViewById(R.id.iBtnLike);
        final ImageButton iBtnDislike = view.findViewById(R.id.iBtnDislike);
        final ImageButton iBtnDuplicate = view.findViewById(R.id.iBtnDuplicate);

        //final boolean[] isLike = {false};
        final boolean[] isDisLike = {false};
        final boolean[] isDuplicate = {false};

        //좋아요버튼 눌렀을 때
        iBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.isLikeChecked() == 0) {  //isLikeChecked가 0이면 좋아요로 바뀜
                    //isLike[0] = true;
                    evaluateMissionCandidate(Account.userIndex,item.index,1,1);
                    iBtnLike.setImageResource(R.drawable.like2);
                    Toast.makeText(context, "좋아요", Toast.LENGTH_SHORT).show();
                }
                else {
                    //isLike[0] = false;
                    evaluateMissionCandidate(Account.userIndex,item.index,1,-1);
                    iBtnLike.setImageResource(R.drawable.imgemptylike);
                    Toast.makeText(context, "별로에요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        iBtnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDisLike[0]){
                    isDisLike[0] = true;
                    evaluateMissionCandidate(Account.userIndex,item.index,2,1);
                    iBtnDislike.setImageResource(R.drawable.imgunlike);
                }
                else {
                    isDisLike[0] = false;
                    evaluateMissionCandidate(Account.userIndex,item.index,2,-1);
                    iBtnDislike.setImageResource(R.drawable.imgemptydislike);
                }
            }
        });

        iBtnDuplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDuplicate[0]){
                    isDuplicate[0] = true;
                    evaluateMissionCandidate(Account.userIndex,item.index,3,1);
                    iBtnDuplicate.setImageResource(R.drawable.imgduplicate);
                }
                else {
                    isDuplicate[0] = false;
                    evaluateMissionCandidate(Account.userIndex,item.index,3,-1);
                    iBtnDuplicate.setImageResource(R.drawable.imgemptyduplicate);
                }
            }
        });

        return view;
    }

    public void addItem(MissionCandidate listItem){
        items.add(listItem);
    }

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
    }

}
