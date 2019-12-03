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
        final MissionCandidateListview view = new MissionCandidateListview(parent.getContext());
        final MissionCandidate item = items.get(position);

        view.setID(item.getUser());
        view.setMission(item.getMissionName());
        view.setLikeCount(item.getLikes());
        view.setDislikeCount(item.getDislikes());
        view.setDuplicateCount(item.getDuplicateCount());

        final ImageButton iBtnLike = view.findViewById(R.id.iBtnLike);
        final ImageButton iBtnDislike = view.findViewById(R.id.iBtnDislike);
        final ImageButton iBtnDuplicate = view.findViewById(R.id.iBtnDuplicate);

        //버튼 체크
        final boolean[] isLike = {false};
        final boolean[] isDislike = {false};
        final boolean[] isDuplicate = {false};
        //좋아요,싫어요,중복 개수
        final int[] likeCount = {0};
        final int[] dislikeCount = {0};
        final int[] duplicateCount = {0};

        //현재 상태를 가져옴
        if(item.isLikeChecked() == 0) {  //isLikeChecked가 0이면 빈좋아요
            iBtnLike.setImageResource(R.drawable.imgemptylike);
            isLike[0] = false;
            likeCount[0] = item.getLikes();
            iBtnDislike.setEnabled(true);
        } else{
            isLike[0] = true;
            iBtnLike.setImageResource(R.drawable.like2);
            likeCount[0] = item.getLikes();
            iBtnDislike.setEnabled(false);
        }
        if(item.isDislikeChecked() == 0) {  //isDislikeChecked가 0이면 빈싫어요
            iBtnDislike.setImageResource(R.drawable.imgemptydislike);
            isDislike[0] = false;
            dislikeCount[0] = item.getDislikes();
            iBtnLike.setEnabled(true);
        } else{
            isDislike[0] = true;
            dislikeCount[0] = item.getDislikes();
            iBtnDislike.setImageResource(R.drawable.imgunlike);
            iBtnLike.setEnabled(false);
        }
        if(item.isDuplicateCountChecked() == 0) {  //isDuplicateCountChecke가 0이면 빈중복
            iBtnDuplicate.setImageResource(R.drawable.imgemptyduplicate);
            isDuplicate[0] = false;
            duplicateCount[0] = item.getDuplicateCount();
        } else{
            isDuplicate[0] = true;
            iBtnDuplicate.setImageResource(R.drawable.imgduplicate);
            duplicateCount[0] = item.getDuplicateCount();
        }

        //좋아요버튼 눌렀을 때
        iBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLike[0]) {  //빈하트일때
                    evaluateMissionCandidate(Account.userIndex,item.index,1,1);  //서버에 보냄
                    isLike[0] = true; //좋아요가 눌렸다고 상태 바뀜
                    iBtnLike.setImageResource(R.drawable.like2); //꽉 찬 하트로 바뀜
                    //view.setLikeCount(item.getLikes()+1);
                    likeCount[0]++; //좋아요 갯수를 올림
                    view.setLikeCount(likeCount[0]); //화면에 set
                    iBtnDislike.setEnabled(false); //싫어요 버튼 비활성화
                    Toast.makeText(context, "좋아요", Toast.LENGTH_SHORT).show();
                }
                else {
                    evaluateMissionCandidate(Account.userIndex,item.index,1,-1); //서버에 보냄
                    isLike[0] = false; //좋아요가 풀림
                    iBtnLike.setImageResource(R.drawable.imgemptylike); //빈하트로 바뀜
                    //view.setLikeCount(item.getLikes()+1);
                    likeCount[0]--; //좋아요 갯수 내림
                    view.setLikeCount(likeCount[0]); //화면에 set
                    iBtnDislike.setEnabled(true); //싫어요 버튼 활성화
                    Toast.makeText(context, "좋아요가 취소되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //싫어요 버튼 눌렀을때
        iBtnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDislike[0]){
                    evaluateMissionCandidate(Account.userIndex,item.index,2,1);
                    isDislike[0] = true;
                    iBtnDislike.setImageResource(R.drawable.imgunlike);
                    //view.setDislikeCount(item.getDislikes()+1);
                    dislikeCount[0]++;
                    view.setDislikeCount(dislikeCount[0]);
                    iBtnLike.setEnabled(false);
                    Toast.makeText(context, "싫어요", Toast.LENGTH_SHORT).show();
                }
                else {
                    evaluateMissionCandidate(Account.userIndex,item.index,2,-1);
                    isDislike[0] = false;
                    iBtnDislike.setImageResource(R.drawable.imgemptydislike);
                    //view.setDislikeCount(item.getDislikes()-1);
                    dislikeCount[0]--;
                    view.setDislikeCount(dislikeCount[0]);
                    iBtnLike.setEnabled(true);
                    Toast.makeText(context, "싫어요가 취소되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //중복버튼 눌렀을때
        iBtnDuplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDuplicate[0]){
                    evaluateMissionCandidate(Account.userIndex,item.index,3,1);
                    isDuplicate[0] = true;
                    iBtnDuplicate.setImageResource(R.drawable.imgduplicate);
                    //view.setDuplicateCount(item.getDuplicateCount()+1);
                    duplicateCount[0]++;
                    view.setDuplicateCount(duplicateCount[0]);
                    Toast.makeText(context, "중복 체크", Toast.LENGTH_SHORT).show();
                }
                else {
                    evaluateMissionCandidate(Account.userIndex,item.index,3,-1);
                    isDuplicate[0] = false;
                    iBtnDuplicate.setImageResource(R.drawable.imgemptyduplicate);
                    //view.setDuplicateCount(item.getDuplicateCount()-1);
                    duplicateCount[0]--;
                    view.setDuplicateCount(duplicateCount[0]);
                    Toast.makeText(context, "중복 체크가 취소되었습니다", Toast.LENGTH_SHORT).show();
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
