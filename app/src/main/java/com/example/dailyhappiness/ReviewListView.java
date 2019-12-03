package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dailyhappiness.databinding.ActivityReviewListViewBinding;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ReviewListView extends RelativeLayout {

    private ImageView ivID;
    private ImageView ivPhoto;
    private TextView tvID;
    private TextView tvDate;
    private TextView tvMission;
    private RatingBar ratingBar;
    private TextView tvContents;


    public ReviewListView(Context context) {
        super(context);
        init(context);
    }

    public ReviewListView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_review_list_view,this,true);

        ivID = findViewById(R.id.ivID);
        ivPhoto = findViewById(R.id.ivPhoto);
        tvID = findViewById(R.id.tvID);
        tvDate = findViewById(R.id.tvDate);
        tvMission = findViewById(R.id.tvMission);
        ratingBar = findViewById(R.id.ratingBar);
        tvContents = findViewById(R.id.tvContents);

    }

    public void setID(String id){
        tvID.setText(id);
    }

    public void setDate(String date){
        tvDate.setText(date);
    }

//    public void setImageID(int resid){
//        ivID.setImageResource(resid);
//    }



    public void setMission(String missionName){
        tvMission.setText(missionName);
    }

    public void setRatingBar(float rating){
        ratingBar.setRating(rating/2);
    }

    public void setContents(String contents){
        tvContents.setText(contents);
    }

}
