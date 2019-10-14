package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;

import com.example.dailyhappiness.databinding.ActivityWriteReviewBinding;

public class WriteReviewActivity extends AppCompatActivity {

    ActivityWriteReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_write_review);
        binding.setActivity(this);

        binding.tvMission.setText(Mission.getTodayMission());

        binding.tvReview.setText(Account.getId() + " 님의 한줄평");

        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                System.out.println(rating);

            }
        });
    }
}
