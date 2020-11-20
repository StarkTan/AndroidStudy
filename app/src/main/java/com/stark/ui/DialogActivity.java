package com.stark.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stark.R;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    private Button waitBtn;
    private Button toastBtn;

    private WaitDialog waitDialog;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        waitBtn = findViewById(R.id.wait_btn);
        waitBtn.setOnClickListener(this);
        toastBtn = findViewById(R.id.toast_btn);
        toastBtn.setOnClickListener(this);

        mHandler = new MyHandler();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wait_btn:
                waitDialog = new WaitDialog(this);
                waitDialog.setText("wait for 10s");
                waitDialog.show();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(5000);
                            mHandler.sendEmptyMessage(1);
                            Thread.sleep(5000);
                            waitDialog.dismiss();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.toast_btn:
                Toast.makeText(DialogActivity.this,"Toast messgae",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    waitDialog.setText("wait for 5s");
                    break;
                default:
                    break;
            }
        }
    }
}