package com.stark.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;


public class PermissionUtils {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    public static void verifyStoragePermissions(Activity activity) {
          String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }

    }

    public static void verifyScreenLockPermissions(Activity activity) {
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }

    }



    public static void verifyBluetoothPermissions(Activity activity){
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        boolean permissionOK = true;
        for(String permission:PERMISSIONS_STORAGE){
            int flag = ActivityCompat.checkSelfPermission(activity,
                    permission);
            if(flag!=PackageManager.PERMISSION_GRANTED){
                permissionOK = false;
                break;
            }
        }
        if (!permissionOK) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    public static void verifyCameraPermissions(Activity activity){
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.CAMERA,
                };
        boolean permissionOK = true;
        for(String permission:PERMISSIONS_STORAGE){
            int flag = ActivityCompat.checkSelfPermission(activity,
                    permission);
            if(flag!=PackageManager.PERMISSION_GRANTED){
                permissionOK = false;
                break;
            }
        }
        if (!permissionOK) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }


}
