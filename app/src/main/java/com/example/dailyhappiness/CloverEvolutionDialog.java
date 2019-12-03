package com.example.dailyhappiness;

import androidx.annotation.NonNull;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class CloverEvolutionDialog extends Dialog {

    private Button btnOk;
    private TextView tvClover;
    private String clover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_mission_dialog);

        // 다이얼로그 크기 조절
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lp.dimAmount = 0.8f;
        Window window = this.getWindow();
        window.setAttributes(lp);

        btnOk = findViewById(R.id.btnOk);
        tvClover = findViewById(R.id.tvClover);

        tvClover.setText(clover);

        //클릭 리스너 셋팅
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    //생성자 생성
    public CloverEvolutionDialog(@NonNull Context context, String clover) {
        super(context);
        this.clover = clover;
    }

}
