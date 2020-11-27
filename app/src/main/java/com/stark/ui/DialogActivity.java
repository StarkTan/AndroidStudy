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
    private Button progressBtn;

    private WaitDialog waitDialog;
    private ProgressDialog progressDialog;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        waitBtn = findViewById(R.id.wait_btn);
        waitBtn.setOnClickListener(this);
        toastBtn = findViewById(R.id.toast_btn);
        toastBtn.setOnClickListener(this);
        progressBtn = findViewById(R.id.progress_btn);
        progressBtn.setOnClickListener(this);

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
            case R.id.progress_btn:
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("下载进度");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            for(int i=0;i<=10;i++){
                                Thread.sleep(1000);
                                mHandler.sendEmptyMessage(1);
                                Message message = new Message();
                                message.what = 2;
                                message.arg1 = i*10;
                                mHandler.sendMessage(message);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                progressDialog.show();
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
                case 2:
                    progressDialog.setProgress(msg.arg1);
                    if(msg.arg1==100){
                        progressDialog.dismiss();
                    }
                default:
                    break;
            }
        }
    }
}