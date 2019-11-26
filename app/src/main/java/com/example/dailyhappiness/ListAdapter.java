package com.example.dailyhappiness;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    ArrayList<Review> items=new ArrayList<>();

    private Thread thread;
    private Bitmap bitmapPhoto;
    private ImageView ivPhoto;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //convertView가 한번 만들어지면 다시 안 만들어도 되기때문에 원래 컨벌트가 널인지 아닌지 먼저 확인을 해봐야 함

        final Context context = parent.getContext();
        ReviewListView view=new ReviewListView(parent.getContext());

        if (view==null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (ReviewListView) inflater.inflate((R.layout.activity_review_list_view),parent,false);
        }

//        if (convertView==null) {
//            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate((R.layout.activity_review_list_view),parent,false);
//        }


        Review item=items.get(position);

        ivPhoto = view.findViewById(R.id.ivPhoto);

        view.setID(item.getUser());
        view.setDate(item.getDate());
        //view.setImageID(item.getImageResource());
        setImage(item.getImage());
        view.setMission(item.getMissionName());
        view.setRatingBar(item.getRating());
        view.setContents(item.getContent());

        return view;
    }

    public void addItem(Review review){
        items.add(review);
    }

    public void setImage(final String imageFile){

        thread = new Thread(){
            public void run(){

                try {
                    URL url = new URL(imageFile);
                    //https://dailyhappiness.xyz/image?filename=70-2019_11_12.jpg

                    // Web에서 이미지를 가져온 뒤
                    // ImageView에 지정할 Bitmap을 만든다
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로 부터 응답 수신
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmapPhoto = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start(); // Thread 실행

        try {
            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            thread.join();

            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
            Log.d("이미지로드확인","휴");
            ivPhoto.setImageBitmap(bitmapPhoto);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
