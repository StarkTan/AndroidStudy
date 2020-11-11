package com.stark.sensor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.stark.R;

public class SensorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SensorActivity";
    private Button gSensorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        gSensorBtn = findViewById(R.id.g_sensor_btn);
        gSensorBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.g_sensor_btn:
                Log.d(TAG,"GSensor");
                Intent gSensorIntent = new Intent(this, GSensorActivity.class);
                startActivity(gSensorIntent);
                break;
            default:
                break;
        }
    }
}