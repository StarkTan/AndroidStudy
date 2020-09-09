package com.stark.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import com.stark.R;
import com.stark.utils.PermissionUtils;

import java.util.Arrays;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "CameraActivity";

    private TextureView textureView;
    private Button switchBtn;
    private Button photoBtn;
    private Button videoBtn;
    private Button fileBtn;

    private CameraCaptureSession mCameraCaptureSession;
    private CameraDevice mCameraDevice;
    private Surface mPreviewSurface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        textureView = findViewById(R.id.textureView);
        switchBtn = findViewById(R.id.camera_switch);
        photoBtn = findViewById(R.id.camera_photo);
        videoBtn = findViewById(R.id.camera_video);
        fileBtn = findViewById(R.id.camera_file);
        switchBtn.setOnClickListener(this);
        photoBtn.setOnClickListener(this);
        videoBtn.setOnClickListener(this);
        fileBtn.setOnClickListener(this);

        PermissionUtils.verifyCameraPermissions(CameraActivity.this);

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {

            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture arg0, int arg1, int arg2) {
                // TODO 自动生成的方法存根
                mPreviewSurface = new Surface(arg0);
                CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                try {

                    if (ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG,"No Permission");
                        return;
                    }
                    manager.openCamera("1", new CameraDevice.StateCallback() {

                        @Override
                        public void onOpened(CameraDevice arg0) {
                            // TODO 自动生成的方法存根s
                            Log.d(TAG,"Camera Opened");
                            mCameraDevice = arg0;
                            try {
                                mCameraDevice.createCaptureSession(Arrays.asList(mPreviewSurface), new CameraCaptureSession.StateCallback() {

                                    @Override
                                    public void onConfigured(CameraCaptureSession arg0) {
                                        // TODO 自动生成的方法存根
                                        mCameraCaptureSession = arg0;
                                        try {
                                            CaptureRequest.Builder builder;
                                            builder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                                            builder.addTarget(mPreviewSurface);
                                            mCameraCaptureSession.setRepeatingRequest(builder.build(), null, null);
                                        } catch (CameraAccessException e1) {
                                            // TODO 自动生成的 catch 块
                                            e1.printStackTrace();
                                        }


                                    }

                                    @Override
                                    public void onConfigureFailed(CameraCaptureSession arg0) {
                                        // TODO 自动生成的方法存根

                                    }
                                }, null);
                            } catch (CameraAccessException e) {
                                // TODO 自动生成的 catch 块
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(CameraDevice arg0, int arg1) {
                            // TODO 自动生成的方法存根

                        }

                        @Override
                        public void onDisconnected(CameraDevice arg0) {
                            // TODO 自动生成的方法存根

                        }
                    }, null);
                } catch (CameraAccessException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture arg0) {
                // TODO 自动生成的方法存根
                return false;
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture arg0, int arg1, int arg2) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture arg0) {
                // TODO 自动生成的方法存根

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_switch:
                Log.d(TAG,"camera switch");
                break;
            case R.id.camera_photo:
                Log.d(TAG,"take photo");
                break;
            case R.id.camera_video:
                if(videoBtn.getText().toString().equals("录像")){
                    Log.d(TAG,"start video clip");
                    switchBtn.setEnabled(false);
                    photoBtn.setEnabled(false);
                    fileBtn.setEnabled(false);
                    videoBtn.setText("停止");
                }else {
                    Log.d(TAG,"start video clip");
                    switchBtn.setEnabled(true);
                    photoBtn.setEnabled(true);
                    fileBtn.setEnabled(true);
                    videoBtn.setText("录像");
                }
                break;
            case R.id.camera_file:
                Log.d(TAG,"Check Camera File");
                break;
        }
    }
}
