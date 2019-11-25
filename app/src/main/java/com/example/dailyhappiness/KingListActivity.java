package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dailyhappiness.databinding.ActivityKingListBinding;

import java.util.ArrayList;

public class KingListActivity extends AppCompatActivity {
    ActivityKingListBinding binding;

    private KingListAdapter kingListAdapter;
    private ArrayList<KingList> kingArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_king_list);
        binding.setActivity(this);

        binding.iBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // onSuccess 에 들어갈 부분
        kingListAdapter = new KingListAdapter();

        for(int i=0;i<10;i++){
//            kingListAdapter.addItem(kingArray.get(i));
        }

        binding.lvMission.setAdapter(kingListAdapter);
        binding.lvCandidate.setAdapter(kingListAdapter);
        // onSuccess
    }
}
