package com.stark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.stark.bluetooth.Bluetooth;
import com.stark.camera.CameraActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "Stark";
    private Button btBtn;
    private Button cameraBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btBtn = findViewById(R.id.bluetooth);
        btBtn.setOnClickListener(this);
        cameraBtn = findViewById(R.id.camera);
        cameraBtn.setOnClickListener(this);
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
        }
    }


}
