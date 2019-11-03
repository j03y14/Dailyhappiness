package com.example.dailyhappiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.dailyhappiness.databinding.ActivityWriteReviewBinding;

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
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WriteReviewActivity extends AppCompatActivity {

    ActivityWriteReviewBinding binding;

    private File tempFile;
    private Boolean isPermission = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        binding.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage();
            }
        });

        binding.iBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(),MyReviewActivity.class);
                startActivity(intent);
            }
        });
    }




    public void addImage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("인증하기").setMessage("사진을 추가해주세요");
        builder.setPositiveButton("카메라",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 카메라

                        if(isPermission){
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
                        } else Toast.makeText(WriteReviewActivity.this, getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("앨범",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 갤러리

                        if(isPermission){
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                            startActivityForResult(intent,1);
                        } else Toast.makeText(WriteReviewActivity.this, getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();

                    }
                });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

    private File createImageFile() throws IOException {

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

    private void setImage() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d("setImage", "setImage : " + tempFile.getAbsolutePath());

        binding.ivPhoto.setImageBitmap(originalBm);

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        tempFile = null;

    }
}
