package com.example.dailyhappiness;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dailyhappiness.databinding.ActivityDrawerBinding;

public class DrawerActivity extends AppCompatActivity {

    ActivityDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_drawer);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_drawer);
        binding.setActivity(this);

    }
}
