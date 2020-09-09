package com.stark.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.stark.R;
import com.stark.utils.PermissionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bluetooth extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "Stark";

    private Button btScanBtn;
    private Button bleScanBtn;
    private ListView listView;
    private ArrayAdapter listAdapter;
    private ArrayList<String> data;
    private BroadcastReceiver mReceiver;
    private BluetoothAdapter adapter;

    private Map<String,BluetoothDevice> foundDevice = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        PermissionUtils.verifyBluetoothPermissions(this);

        btScanBtn = findViewById(R.id.bt_scan);
        bleScanBtn = findViewById(R.id.ble_scan);
        bleScanBtn.setOnClickListener(this);
        btScanBtn.setOnClickListener(this);
        data = new ArrayList<>();
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,data);
        listView = findViewById(R.id.bt_list);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"Click :"+data.get(position));
                String address = data.get(position).split(" : ")[1];
                System.out.println(foundDevice.get(address));
                final BluetoothDevice device = foundDevice.get(address);
                Intent intent = new Intent(Bluetooth.this, RFCOMM.class);
                intent.putExtra("device",device);
                startActivity(intent);
            }
        });

        adapter = BluetoothAdapter.getDefaultAdapter();
        if(!adapter.isEnabled()){
            adapter.enable();
        }

        mReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_scan:
                Log.d(TAG,"BT SCAN");
                data.clear();
                listAdapter.notifyDataSetChanged();
                boolean b = adapter.startDiscovery();
                System.out.println(b);
                break;
            case R.id.ble_scan:
                Log.d(TAG,"BLE SCAN");
                break;
        }

    }


    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG,device.getName()+" : "+device.getAddress());
                data.add(device.getName()+" : "+device.getAddress());
                listAdapter.notifyDataSetChanged();
                if(!foundDevice.containsKey(device.getAddress())){
                    foundDevice.put(device.getAddress(),device);
                }
            }
        }
    }

}
