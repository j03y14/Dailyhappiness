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
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class CalendarActivity extends AppCompatActivity {

    ActivityCalendarBinding binding;

    String time,kcal,menu;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    Cursor cursor;

    //미션 한 날짜
    final String[] result = {"2019,11,01",
            "2019,11,02",
            "2019,11,03",
            "2019,11,05",
            "2019,11,06",
            "2019,11,08",
            "2019,11,09",
            "2019,11,10",
            "2019,11,11",
            "2019,11,12",
            "2019,11,13",
            "2019,11,16"};

    //달력밑에 히스토리 data   "날짜\n한줄평"
    ArrayAdapter arrayAdapter;
    String[] data = {"짜장면\n맛있다","김치찌개\n존맛탱","컴밥\n다음엔스팸참치","햄버거\n버거킹좋아","갈비","스테이크","샐러드","식빵","곱창","닭발","닭볶음탕","치킨","감자탕","라면","불고기"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_calendar);
        binding.setActivity(this);

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
                    for(int j = 0; j < result.length; j ++){
                        if(i == Integer.parseInt(result[j].substring(5,7))-1)  //그 달에 맞는 result(날짜들) 분류
                            month.add(result[j]);
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

        String[] Time_Result;

        ApiSimulator(String[] Time_Result){
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
            for(int i = 0 ; i < Time_Result.length ; i ++){
                String[] time = Time_Result[i].split(",");
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

}
