package com.stark.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.stark.R;

public class UIComponentActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "UIComponentActivity";
    private Button dialogBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_component);

        dialogBtn = findViewById(R.id.dialog_btn);
        dialogBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_btn:
                Log.d(TAG,"ui");
                Intent waitIntent = new Intent(this, DialogActivity.class);
                startActivity(waitIntent);
                break;
        }
    }
}