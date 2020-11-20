package com.stark.devicetool;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.stark.R;
import com.stark.camera.CameraActivity;
import com.stark.ui.DialogActivity;
import com.stark.utils.PermissionUtils;

public class ToolsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ToolsActivity";
    private CameraManager manager;

    private Switch torchSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        torchSwitch = findViewById(R.id.torch);
        manager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        torchSwitch.setOnClickListener(this);
        PermissionUtils.verifyCameraPermissions(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.torch:
                if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                    Toast.makeText(this,"no flash light",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    manager.setTorchMode("0", torchSwitch.isChecked());// "0"是主闪光灯
                } catch (Exception e) {
                    String msg = "Torch 控制失败: "+e;
                    Log.d(TAG,msg);
                    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
                }
        }
    }
}