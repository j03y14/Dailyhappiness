package com.example.dailyhappiness;

import android.app.LauncherActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MissionCandidateListAdapter extends BaseAdapter {

    ArrayList<MissionCandidate> items;

    public MissionCandidateListAdapter(ArrayList<MissionCandidate> items){
        this.items = items;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        MissionCandidateListview view = new MissionCandidateListview(parent.getContext());
        MissionCandidate item=items.get(position);

        view.setID(item.getUser());
        view.setMission(item.getMissionName());
        return view;
    }

    public void addItem(MissionCandidate listItem){
        items.add(listItem);
    }
}
