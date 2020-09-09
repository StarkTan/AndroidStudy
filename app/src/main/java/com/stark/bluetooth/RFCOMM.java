package com.stark.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.stark.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class RFCOMM extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "Stark";
    private TextView statusTextView;
    private Button backBtn;
    private Button sendBtn;
    private EditText sendText;
    private TextView responseTextView;

    private SocketThread socketThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfcomm);

        statusTextView = findViewById(R.id.rfcomm_status);
        backBtn = findViewById(R.id.rfcomm_back);
        backBtn.setOnClickListener(this);
        BluetoothDevice device = getIntent().getParcelableExtra("device");
        socketThread = new SocketThread(device);
        socketThread.start();

        sendBtn = findViewById(R.id.rfcomm_send_btn);
        sendBtn.setOnClickListener(this);
        sendText = findViewById(R.id.rfcomm_send);

        responseTextView =findViewById(R.id.rfcomm_response);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketThread.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rfcomm_back:
                finish();
                break;
            case R.id.rfcomm_send_btn:
                String data = sendText.getText().toString();
                boolean b = socketThread.sendData(data);
                System.out.println(b);
        }
    }

    private class SocketThread extends Thread{

        private BluetoothDevice device;

        BluetoothSocket socket;
        OutputStream outputStream;

        SocketThread(BluetoothDevice device){
            this.device = device;
        }

        void close(){
            if(socket!=null&&socket.isConnected()){
                try {
                    socket.close();
                } catch (IOException e1) {
                    Log.d(TAG,device.getName()+"Socket Close Error");
                }
            }
        }

        boolean sendData(String data){
            if(!socket.isConnected()){
                return false;
            }
            try{
                outputStream.write(data.getBytes());
                outputStream.flush();
                return true;
            }catch (IOException e){
                Log.d(TAG,"Send Data Failed");
                return false;
            }
        }

        @Override
        public void run() {
            super.run();
            try {
                socket = device.createRfcommSocketToServiceRecord(
                        UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee"));
                if(socket!=null){
                    socket.connect();
                    statusTextView.setText("Connect");
                    outputStream = socket.getOutputStream();
                    try (InputStream inputStream = socket.getInputStream()) {
                        int bufSize = 1024;
                        byte[] buffer = new byte[bufSize];
                        int index = 0;
                        while (true) {
                            int read = inputStream.read();
                            if (read != -1) {
                                buffer[index++] = (byte) read;
                                if (read == 10) {
                                    System.out.println();
                                    responseTextView.append(new String(buffer,0,index));
                                    index = 0;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                statusTextView.setText("Disconnect");
                Log.d(TAG,device.getName()+"RFCOMM CONN Failed");
                if(socket!=null){
                    try {
                        socket.close();
                    } catch (IOException e1) {
                        Log.d(TAG,device.getName()+"Socket Close Error");
                    }
                }
            }
        }
    }
}






