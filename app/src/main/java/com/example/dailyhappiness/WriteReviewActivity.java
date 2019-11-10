package com.example.dailyhappiness;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import com.example.dailyhappiness.databinding.ActivityWriteReviewBinding;
import com.google.gson.JsonObject;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WriteReviewActivity extends AppCompatActivity {

    ActivityWriteReviewBinding binding;

    private File tempFile; //불러온 사진을 임시로 저장하기 위한 변수
    private LocationManager locationManager; //위치를 불러오기위한 manager
    private RetroClient retroClient;
    private Account user;
    private String missionRating;
    //위치 정보를 가지고 있다.
    private String location_lat; //위도
    private String location_lon; //경도
    private String content; //한줄평

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retroClient = RetroClient.getInstance(this).createBaseApi();
        user = Account.getInstance();


        binding = DataBindingUtil.setContentView(this,R.layout.activity_write_review);
        binding.setActivity(this);



        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        int permissionCheck = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if(PackageManager.PERMISSION_GRANTED == permissionCheck) {
            try {
                Log.i("트롸이","위치가져왔지");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener); //민타임은 초,  민디스턴스는 미터
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }




        }else{
            Toast.makeText(WriteReviewActivity.this, "정확한 미션 추천을 위해 위치정보 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),MyReviewActivity.class);
            startActivity(intent);
        }


        binding.tvMission.setText(Mission.getTodayMission());

        binding.tvID.setText(Account.getId() + " 님의 한줄평");

        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                missionRating = Integer.toString(Math.round(rating*2));
                System.out.println(missionRating);

            }
        });



        binding.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
            }
        });



        binding.iBtnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i("ibtnok","ibtnok 진입");
                content  = binding.edtReview.getText().toString();

                Intent intent = new Intent(getApplicationContext(),MyReviewActivity.class);



                uploadImage(tempFile, Account.getUserIndex(), intent);

            }
        });
    }

    public void addImage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("인증하기").setMessage("사진을 추가해주세요");
        builder.setPositiveButton("카메라",
                new DialogInterface.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 카메라

                        int permissionCheck = checkSelfPermission(Manifest.permission.CAMERA);
                        if(PackageManager.PERMISSION_GRANTED == permissionCheck){
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            try {
                                tempFile = createImageFile();
                            } catch (IOException e) {
                                Toast.makeText(WriteReviewActivity.this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                finish();
                                e.printStackTrace();
                            }
                            if (tempFile != null) {

                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                                    Uri photoUri = FileProvider.getUriForFile(WriteReviewActivity.this, "{com.example.dailyhappiness}.provider", tempFile);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                    startActivityForResult(intent, 2);

                                } else {

                                    Uri photoUri = Uri.fromFile(tempFile);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                    startActivityForResult(intent, 2);

                                }
                            }
                        } else Toast.makeText(WriteReviewActivity.this, "사진 및 파일을저장하기 위하여 접근 권한이 필요합니다", Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("앨범",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 갤러리
                        int permissionCheck = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if(PackageManager.PERMISSION_GRANTED == permissionCheck){
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                            startActivityForResult(intent,1);
                        } else Toast.makeText(WriteReviewActivity.this, "사진 및 파일을저장하기 위하여 접근 권한이 필요합니다", Toast.LENGTH_LONG).show();

                    }
                });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  //예외처리
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e("삭제", tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == 1) {

            Uri photoUri = data.getData();
            Log.d("Uri확인", "PICK_FROM_ALBUM photoUri : " + photoUri);

            Cursor cursor = null;

            try {
                //Uri 스키마를  content:/// 에서 file:/// 로  변경
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

                Log.d("result", "tempFile Uri : " + Uri.fromFile(tempFile));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        } else if (requestCode == 2) {

            setImage();

        }

    }

    private File createImageFile() throws IOException {  //카메라에서 찍은 사진을 저장할 파일 만들기

        // 이미지 파일 이름 ( userId_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = Account.getId() + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( dailyHappiness )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/dailyHappiness/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d("파일생성", "createImageFile : " + image.getAbsolutePath());

        return image;
    }

    private void setImage() { // 파일을 bitmap 파일로 변형한 후 ivPhoto에 넣음

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d("setImage", "setImage : " + tempFile.getAbsolutePath());

        binding.ivPhoto.setImageBitmap(originalBm);

         // tempFile 사용 후 null 처리
         //  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         //기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이루어짐



    }

    LocationListener locationListener = new LocationListener() {  //로케이션 리스너
        @Override
        public void onLocationChanged(Location location) { //location에 위도 경도가 들어있음.
            location_lat = Double.toString(location.getLatitude());
            location_lon = Double.toString(location.getLongitude());
            float acc = location.getAccuracy();



            String result = String.format("위도 : %s\n경도 : %s\n정확도 : %f\n",location_lat,location_lon,acc);

            Log.i("위치",result);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void uploadImage(File file, String userIndex,final Intent intent){

        if(file==null){
            //Log.e("err", "uploadImage: file is null");
            return;
        }

        Date currentTime = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(currentTime);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", userIndex+"-"+date+".jpg", requestFile);

        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        retroClient.uploadImage(part, description, new RetroCallback<JsonObject>(){

            @Override
            public void onError(Throwable t) {
                Log.e("error", "uploadImage 오류가 생겼습니다.");
                tempFile = null;
            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                Log.i("uploadimage", " Success! ");
                uploadReview(Account.getUserIndex(),Mission.getMissionNumber(), missionRating,location_lat, location_lon, content,intent);
                tempFile = null;
            }

            @Override
            public void onFailure(int code) {
                Log.e("error", "uploadImage 오류가 생겼습니다.");
                tempFile = null;
            }
        });
    }


    public void uploadReview(String userIndex, int missionIndex, String missionRating, String location_lat, String location_lon, String content,final Intent intent){


        retroClient.uploadReview(userIndex, missionIndex, missionRating, location_lat, location_lon, content, new RetroCallback<JsonObject>(){

            @Override
            public void onError(Throwable t) {
                Log.e("error", "uploadReview 오류가 생겼습니다.");

            }

            @Override
            public void onSuccess(int code, JsonObject receivedData) {
                startActivity(intent);
            }

            @Override
            public void onFailure(int code) {
                Log.e("error", "uploadReview 오류가 생겼습니다.");

            }
        });
    }
}
