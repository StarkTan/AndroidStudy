package com.stark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.stark.battery.BatteryActivity;
import com.stark.bluetooth.Bluetooth;
import com.stark.camera.CameraActivity;
import com.stark.key.KeyActivity;
import com.stark.screen.ScreenActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "Stark";
    private Button btBtn;
    private Button cameraBtn;
    private Button batteryBtn;
    private Button screenBtn;
    private Button keyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btBtn = findViewById(R.id.bluetooth);
        btBtn.setOnClickListener(this);
        cameraBtn = findViewById(R.id.camera);
        cameraBtn.setOnClickListener(this);
        batteryBtn = findViewById(R.id.battery);
        batteryBtn.setOnClickListener(this);
        screenBtn = findViewById(R.id.screen);
        screenBtn.setOnClickListener(this);
        keyBtn = findViewById(R.id.key);
        keyBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bluetooth:
                Log.d(TAG,"bluetooth");
                Intent btIntent = new Intent(this, Bluetooth.class);
                startActivity(btIntent);
                break;
            case R.id.camera:
                Log.d(TAG,"camera");
                Intent cameraIntent = new Intent(this, CameraActivity.class);
                startActivity(cameraIntent);
                break;
            case R.id.battery:
                Log.d(TAG,"battery");
                Intent batteryIntent = new Intent(this, BatteryActivity.class);
                startActivity(batteryIntent);
                break;
            case R.id.screen:
                Log.d(TAG,"battery");
                Intent screenIntent = new Intent(this, ScreenActivity.class);
                startActivity(screenIntent);
                break;
            case R.id.key:
                Log.d(TAG,"battery");
                Intent keyIntent = new Intent(this, KeyActivity.class);
                startActivity(keyIntent);
                break;
        }
    }


}
