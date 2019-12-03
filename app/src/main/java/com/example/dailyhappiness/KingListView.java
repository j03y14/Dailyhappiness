package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class KingListView extends RelativeLayout {

    TextView tvRank;
    ImageView ivID;
    TextView tvID;
    TextView tvCount;


    public KingListView(Context context) {
        super(context);
        init(context);
    }

    public  KingListView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_king_list_view,this,true);

        tvRank = findViewById(R.id.tvRank);
        ivID = findViewById(R.id.ivID);
        tvID = findViewById(R.id.tvID);
        tvCount = findViewById(R.id.tvCount);

    }

    public void setRank(int rank){
        tvRank.setText(rank+"");
    }

//    public void setImageID(int resid){
//        ivID.setImageResource(resid);
//    }

    public void setID(String id){
        tvID.setText(id);
    }

    public void setCount(int count){
        tvCount.setText(count+"");
    }


}
