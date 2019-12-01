package com.example.dailyhappiness;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class SurveyAdapter extends BaseAdapter {
    ArrayList<Survey> items=new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //convertView가 한번 만들어지면 다시 안 만들어도 되기때문에 원래 컨벌트가 널인지 아닌지 먼저 확인을 해봐야 함
        SurveyListview view=new SurveyListview(parent.getContext());
        Survey item=items.get(position);

        view.setNumber(item.getNumber());
        view.setMission(item.getMission());

        return view;
    }

    public void addItem(Survey listItem){
        items.add(listItem);
    }
}
