package com.example.liveguard_app_010.ui.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.liveguard_app_010.ui.onboarding.OnboardingActivity;

public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "PermissionActivity";
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (hasAllPermissions()) {
            moveToOnboarding();
        } else {
            requestPermissions();
        }
    }

    private boolean hasAllPermissions() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE);
    }

    private void moveToOnboarding() {
        Log.d(TAG, "모든 권한이 허용됨 → 온보딩 화면으로 이동");
        Intent intent = new Intent(this, OnboardingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (hasAllPermissions()) {
                Log.d(TAG, "권한이 허용됨 → 온보딩 화면으로 이동");
                moveToOnboarding();
            } else {
                Log.w(TAG, "권한이 거부됨 → 권한 요청 유지");
                requestPermissions();
            }
        }
    }
}
