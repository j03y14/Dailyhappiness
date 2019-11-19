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

        tvID=findViewById(R.id.tvID);
        tvMissionCandidate = findViewById(R.id.tvMissionCandidate);

    }

    public void setID(String id){
        tvID.setText(id);
    }

    public void setMission(String mission){
        tvMissionCandidate.setText(mission);
    }


}
