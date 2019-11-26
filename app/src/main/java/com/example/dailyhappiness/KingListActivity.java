package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dailyhappiness.databinding.ActivityKingListBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class KingListActivity extends AppCompatActivity {
    ActivityKingListBinding binding;

    private KingListAdapter kingListAdapter;
    private ArrayList<KingList> kingArray;

    private RetroClient retroClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_king_list);
        binding.setActivity(this);

        retroClient = RetroClient.getInstance(this).createBaseApi();

        binding.iBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getMissionKing();




        // onSuccess
    }

    public void getMissionKing(){
        kingListAdapter = new KingListAdapter();
        retroClient.getMissionKing(new RetroCallback<JsonArray>() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, JsonArray receivedData) {

                for(int j = 0; j<receivedData.size(); j++){
                    JsonObject data = (JsonObject)receivedData.get(j);
                    int rank = data.get("ranking").getAsInt();
                    String user = data.get("id").getAsString();
                    int userIndex = data.get("userIndex").getAsInt();
                    int count = data.get("number").getAsInt();
                    String emblem = data.get("emblem").getAsString();
                    kingListAdapter.addItem(new KingList(rank,user,userIndex,count,emblem));
                }



                binding.lvMission.setAdapter(kingListAdapter);
                binding.lvCandidate.setAdapter(kingListAdapter);
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }

}
