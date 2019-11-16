package com.example.dailyhappiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.dailyhappiness.databinding.ActivityCalendarBinding;
import com.example.dailyhappiness.databinding.ActivityMyReviewBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class CalendarActivity extends AppCompatActivity {

    ActivityCalendarBinding binding;

    private RetroClient retroClient;

    String time,kcal,menu;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    Cursor cursor;

    //미션 한 날짜
    private ArrayList<String> result;


    //달력밑에 히스토리 data   "날짜\n한줄평"
    ArrayAdapter arrayAdapter;
    ArrayList<String> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_calendar);
        binding.setActivity(this);

        retroClient= RetroClient.getInstance(this).createBaseApi();
        result = new ArrayList<String>();
        data = new ArrayList<String>();

        getReviews(Account.getUserIndex(), true,0);




//        binding.calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//                int Year = date.getYear();
//                int Month = date.getMonth() + 1;
//                int Day = date.getDay();
//
//                Log.i("Year test", Year + "");
//                Log.i("Month test", Month + "");
//                Log.i("Day test", Day + "");
//
//                String shot_Day = Year + "," + Month + "," + Day;
//
//                Log.i("shot_Day test", shot_Day + "");
//                binding.calendarView.clearSelection();
//            }
//        });

        //달이 바꼈을 때
        binding.calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                int m = date.getMonth();

                ArrayList<ArrayList<String>> monthList = new ArrayList<ArrayList<String>>();
                for(int i = 0 ; i < 12; i ++){
                    ArrayList<String> month = new ArrayList<String>();  //1 month
                    for(int j = 0; j < result.size(); j ++){
                        if(i == Integer.parseInt(result.get(j).substring(5,7))-1)  //그 달에 맞는 result(날짜들) 분류
                            month.add(result.get(j));
                    }
                    monthList.add(month);                               //1~12 month
                }

            }
        });

        binding.iBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        ArrayList<String> Time_Result;

        ApiSimulator(ArrayList<String> Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            //특정날짜 달력에 점표시해주는곳
            //월은 0이 1월 년,일은 그대로
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.size() ; i ++){
                String[] time = Time_Result.get(i).split("-");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                calendar.set(year,month-1,dayy);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            binding.calendarView.addDecorator(new EventDecorator(R.color.colorAccent, calendarDays,CalendarActivity.this));
        }
    }

    public void getReviews(String userIndex, boolean getMine, int reviewCount){
        Log.i("getReviews","getReviews 호출");

        retroClient.getReviews(userIndex, getMine, reviewCount, new RetroCallback<JsonArray>() {
            @Override
            public void onError(Throwable t) {
                Log.e("getReviews", "onError");
            }

            @Override
            public void onSuccess(int code, JsonArray receivedData) {

                for(int i=0; i<receivedData.size();i++) {
                    JsonObject review = (JsonObject) receivedData.get(i);
                    result.add(review.get("date").getAsString());
                    data.add(review.get("date").getAsString() +"\n" +review.get("missionName").getAsString());
                }

                arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,data);
                binding.lvList.setAdapter(arrayAdapter);

                binding.calendarView.state().edit()
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                        .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                        .setCalendarDisplayMode(CalendarMode.MONTHS)
                        .commit();

                binding.calendarView.addDecorators(
                        new SundayDecorator(),
                        new SaturdayDecorator(),
                        oneDayDecorator);



                new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

            }

            @Override
            public void onFailure(int code) {
                Log.e("getReviews", "onFailure");
            }
        });
    }

}
