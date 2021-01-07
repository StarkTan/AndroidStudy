package com.stark.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.stark.R;

public class BatteryActivity extends AppCompatActivity {

    private static final String TAG = "BatteryActivity";

    private TextView batteryStatusTV;
    private TextView batteryHealthStatusTV;
    private TextView batteryUseTV;
    private TextView batteryLevelTV;
    private TextView batteryScaleTV;
    private TextView batteryPluggedTV;
    private TextView batteryVoltageTV;
    private TextView batteryTempTV;
    private TextView batteryTechTV;

    private BroadcastReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Activity Launch ");
        setContentView(R.layout.activity_battery);
        batteryStatusTV = findViewById(R.id.battery_status_msg);
        batteryHealthStatusTV = findViewById(R.id.battery_health_status_msg);
        batteryUseTV = findViewById(R.id.battery_use_msg);
        batteryLevelTV = findViewById(R.id.battery_level_msg);
        batteryScaleTV = findViewById(R.id.battery_scale_msg);
        batteryPluggedTV = findViewById(R.id.battery_plugged_msg);
        batteryVoltageTV =findViewById(R.id.battery_voltage_msg);
        batteryTempTV = findViewById(R.id.battery_temp_msg);
        batteryTechTV = findViewById(R.id.battery_tech_msg);

        batteryReceiver = new BatteryStatusReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver( batteryReceiver,filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }

    private class BatteryStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);//电池是否存在
            if(present){
                batteryUseTV.setText("YES");
            }else
                {
                batteryUseTV.setText("NO");
            }
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0); //电池状态
            switch (status){
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    batteryStatusTV.setText("UNKNOWN");
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    batteryStatusTV.setText("CHARGING");
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    batteryStatusTV.setText("DISCHARGING");
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    batteryStatusTV.setText("NOT_CHARGING");
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    batteryStatusTV.setText("FULL");
                    break;
            }
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0); //电池健康状态
            switch (health){
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    batteryHealthStatusTV.setText("UNKNOWN");
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    batteryHealthStatusTV.setText("GOOD");
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    batteryHealthStatusTV.setText("OVERHEAT");
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    batteryHealthStatusTV.setText("DEAD");
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    batteryHealthStatusTV.setText("OVER_VOLTAGE");
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    batteryHealthStatusTV.setText("UNSPECIFIED_FAILURE");
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD:
                    batteryHealthStatusTV.setText("COLD");
                    break;
            }
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);// 电池电量
            batteryLevelTV.setText(level+" %");
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);//电池总电量
            batteryScaleTV.setText(scale+"");
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);//电池充电方式
            switch (plugged){
                case BatteryManager.BATTERY_PLUGGED_AC:
                    batteryPluggedTV.setText("AC");
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    batteryPluggedTV.setText("USB");
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    batteryPluggedTV.setText("WIRELESS");
                    break;
            }
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);//电池电压
            batteryVoltageTV.setText(voltage+"");
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);//电池温度
            batteryTempTV.setText(temperature+"");
            String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);//电池技术
            batteryTechTV.setText(technology);
        }
    };
}
