package com.stark.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stark.R;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    private Button waitBtn;

    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        waitBtn = findViewById(R.id.wait_btn);
        waitBtn.setOnClickListener(this);

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
                            Thread.sleep(10000);
                            waitDialog.dismiss();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
        }
    }
}