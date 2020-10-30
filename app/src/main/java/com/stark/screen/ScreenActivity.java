package com.stark.screen;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Looper;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stark.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ScreenActivity";

    private BroadcastReceiver screenReceiver;

    private TextView screenOnTimeTV;
    private TextView screenOffTimeTV;
    private Button onOffBtn;

    private PowerManager powerManager = null;

    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG,"Activity Launch ");
        setContentView(R.layout.activity_screen);
        screenOnTimeTV = findViewById(R.id.last_screen_on_time);
        screenOffTimeTV = findViewById(R.id.last_screen_off_time);
        onOffBtn =findViewById(R.id.screen_on_off_btn);
        onOffBtn.setOnClickListener(this);

        powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        screenReceiver = new ScreenStatusReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.screen_on_off_btn:

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(10000);
                            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                                    PowerManager.ACQUIRE_CAUSES_WAKEUP, this.getClass().getSimpleName());
                            wakeLock.acquire(60*1000L /*1 minutes*/);
                            wakeLock.release();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
        }
    }

    private class ScreenStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
                Log.e(TAG, "SCREEN_ON");
                Date date=new Date();
                screenOnTimeTV.setText(dateFormat.format(date));
            }
            else if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
                Log.e(TAG, "SCREEN_OFF");
                Date date=new Date();
                screenOffTimeTV.setText(dateFormat.format(date));
            }
        }
    }
}
