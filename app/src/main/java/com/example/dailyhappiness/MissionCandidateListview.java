package com.example.dailyhappiness;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MissionCandidateListview extends LinearLayout {

    private TextView tvID;
    private TextView tvMissionCandidate;
    private TextView tvLike;
    private TextView tvDislike;
    private TextView tvDuplicate;

    public MissionCandidateListview(Context context) {
        super(context);
        init(context);
    }

    public MissionCandidateListview(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mission_candidate_listview,this,true);

        tvID = findViewById(R.id.tvID);
        tvMissionCandidate = findViewById(R.id.tvMissionCandidate);
        tvLike = findViewById(R.id.tvLike);
        tvDislike = findViewById(R.id.tvDislike);
        tvDuplicate = findViewById(R.id.tvDuplicate);
    }

    public void setID(String id){
        tvID.setText(id);
    }

    public void setMission(String mission){
        tvMissionCandidate.setText(mission);
    }

    public  void setLikeCount(int count){
        tvLike.setText(count+"");
    }

    public  void setDislikeCount(int count){
        tvDislike.setText(count+"");
    }

    public  void setDuplicateCount(int count){
        tvDuplicate.setText(count+"");
    }

}
