package com.example.dailyhappiness;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;

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
        final SurveyListview view=new SurveyListview(parent.getContext());
        final Survey item=items.get(position);

        view.setNumber(item.getNumber());
        view.setMission(item.getMission());
        view.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { //움직임 중
                view.number = seekBar.getProgress();
                item.setScore(view.number);
                view.setScore();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { //움직임이 시작될때
                view.number = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { //움직임이 멈췄을 때
                view.number = seekBar.getProgress();
            }
        });

        return view;
    }

    public void addItem(Survey listItem){
        items.add(listItem);
    }
}
