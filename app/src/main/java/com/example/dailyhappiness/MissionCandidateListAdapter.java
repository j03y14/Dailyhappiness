package com.example.dailyhappiness;

import android.app.LauncherActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MissionCandidateListAdapter extends BaseAdapter {

    ArrayList<MissionCandidate> items=new ArrayList<>();
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //convertView가 한번 만들어지면 다시 안 만들어도 되기때문에 원래 컨벌트가 널인지 아닌지 먼저 확인을 해봐야 함
        MissionCandidateListview view=new MissionCandidateListview(parent.getContext());
        MissionCandidate item=items.get(position);

        view.setID(item.getUser());
        view.setMessage(item.getMissionName());

        return view;
    }

    public void addItem(MissionCandidate listItem){
        items.add(listItem);
    }
}
