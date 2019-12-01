package com.example.dailyhappiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dailyhappiness.databinding.ActivityKingListBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class KingListActivity extends AppCompatActivity {
    ActivityKingListBinding binding;

    private KingListAdapter kingListAdapter;
    private KingListAdapter kingListAdapter2;

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

    }

    public void getMissionKing(){
        kingListAdapter = new KingListAdapter();
        kingListAdapter2 = new KingListAdapter();

        retroClient.getMissionKing(new RetroCallback<JsonArray>() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, JsonArray receivedData) {

                for(int j = 0; j<receivedData.size(); j++){
                    JsonObject data = (JsonObject)receivedData.get(j);
                    //which가 1이면 미션왕 0이면 추천왕
                    int which = data.get("which").getAsInt();
                    int rank = data.get("ranking").getAsInt();
                    String user = data.get("id").getAsString();
                    int userIndex = data.get("userIndex").getAsInt();
                    int count = data.get("number").getAsInt();
                    String emblem = data.get("emblem").getAsString();
                    if(which == 1){
                        //미션왕 리스트에 넣기
                        kingListAdapter.addItem(new KingList(rank,user,userIndex,count,emblem));
                        Log.i("미션왕","미션왕");
                    }else if(which == 0){
                        //추천왕 리스트에 넣기
                        kingListAdapter2.addItem(new KingList(rank,user,userIndex,count,emblem));
                        Log.i("추천왕","추천왕");
                    }

                }

                binding.lvMission.setAdapter(kingListAdapter);
                binding.lvCandidate.setAdapter(kingListAdapter2);
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }

}
