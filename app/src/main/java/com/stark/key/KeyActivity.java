package com.stark.key;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stark.R;

public class KeyActivity extends AppCompatActivity {

    private HomeKeyObserver mHomeKeyObserver;
    private PowerKeyObserver mPowerKeyObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);
        init();
    }

    private void init() {
        mHomeKeyObserver = new HomeKeyObserver(this);
        mHomeKeyObserver.setHomeKeyListener(new HomeKeyObserver.OnHomeKeyListener() {
            @Override
            public void onHomeKeyPressed() {
                System.out.println("----> 按下Home键");
            }

            @Override
            public void onHomeKeyLongPressed() {
                System.out.println("----> 长按Home键");
            }
        });
        mHomeKeyObserver.startListen();

        //////////////////////////////////////////

        mPowerKeyObserver = new PowerKeyObserver(this);
        mPowerKeyObserver.setHomeKeyListener(new PowerKeyObserver.OnPowerKeyListener() {
            @Override
            public void onPowerKeyPressed() {
                System.out.println("----> 按下电源键");
            }
        });
        mPowerKeyObserver.startListen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomeKeyObserver.stopListen();

        //////////////////////////////////////////

        mPowerKeyObserver.stopListen();
    }
}
