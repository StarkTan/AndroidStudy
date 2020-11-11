package com.stark.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.stark.R;

public class GSensorActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xDataTV;
    private TextView yDataTV;
    private TextView zDataTV;
    private TextView aDataTV;
    private TextView angleDataTV;

    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_sensor);

        xDataTV = findViewById(R.id.gsensor_x_data);
        yDataTV = findViewById(R.id.gsensor_y_data);
        zDataTV = findViewById(R.id.gsensor_z_data);
        aDataTV = findViewById(R.id.gsensor_a_data);
        angleDataTV = findViewById(R.id.gsensor_angle_data);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            double absACC = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
            double angle = Math.toDegrees(Math.acos(y / absACC));  //屏幕与竖直线的角度
            xDataTV.setText(String.format("%s", x));
            yDataTV.setText(String.format("%s", y));
            zDataTV.setText(String.format("%s", z));
            aDataTV.setText(String.format("%s", absACC));
            angleDataTV.setText(String.format("%s", angle));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}