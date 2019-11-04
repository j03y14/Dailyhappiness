package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;

import com.example.dailyhappiness.databinding.ActivityWriteReviewBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class WriteReviewActivity extends AppCompatActivity {

    ActivityWriteReviewBinding binding;

    private RetroClient retroClient;
    private Account user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retroClient = RetroClient.getInstance(this).createBaseApi();
        user = Account.getInstance();

        binding = DataBindingUtil.setContentView(this,R.layout.activity_write_review);
        binding.setActivity(this);

        binding.tvMission.setText(Mission.getTodayMission());

        binding.tvID.setText(Account.getId() + " 님의 한줄평");

        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                System.out.println(rating);

            }
        });
    }

    public void uploadImage(String filePath){

        File file = new File(filePath);

        RequestBody fileReqBody = RequestBody.create(getContentResolver().getType(file.getData()), file);

        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);

        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        retroClient.uploadImage(file, requestBody, new RetroCallback<ResponseBody>(){

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, ResponseBody receivedData) {

            }

            @Override
            public void onFailure(int code) {

            }
        });
    }

}
