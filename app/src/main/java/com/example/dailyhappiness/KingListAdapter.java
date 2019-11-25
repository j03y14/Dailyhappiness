package com.example.dailyhappiness;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class KingListAdapter extends BaseAdapter {
    ArrayList<KingList> items=new ArrayList<>();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        KingListView view=new KingListView(parent.getContext());
        KingList item=items.get(position);

        view.setRank(item.getRank());
        //view.setImageID()
        view.setID(item.getUser());
        view.setCount(item.getCount());

        return view;
    }

    public void addItem(KingList listItem){
        items.add(listItem);
    }
}
