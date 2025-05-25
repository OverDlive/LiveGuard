package com.example.liveguard_app_010.ui.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.liveguard_app_010.ui.onboarding.OnboardingActivity;

public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "PermissionActivity";

    /** Activity Result API 런타임 권한 요청 런처 */
    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                boolean allGranted = true;
                for (Boolean granted : result.values()) {
                    if (!granted) {
                        allGranted = false;
                        break;
                    }
                }
                if (allGranted) {
                    Log.d(TAG, "권한이 허용됨 → 온보딩 화면으로 이동");
                    moveToOnboarding();
                } else {
                    Toast.makeText(this,
                            "위치 권한이 필요합니다. 앱을 종료합니다.",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            });

    private final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLocationPermission();
    }

    private boolean hasAllPermissions() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 권한 체크 → 요청 → 결과 처리
     */
    private void checkLocationPermission() {
        if (hasAllPermissions()) {
            moveToOnboarding();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("위치 권한 필요")
                    .setMessage("실시간 인구 밀집도 서비스를 사용하려면 위치 권한이 필요합니다.")
                    .setPositiveButton("권한 요청",
                            (dialog, which) -> requestPermissionLauncher.launch(REQUIRED_PERMISSIONS))
                    .setNegativeButton("앱 종료",
                            (dialog, which) -> finish())
                    .show();
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS);
        }
    }

    private void moveToOnboarding() {
        Log.d(TAG, "모든 권한이 허용됨 → 온보딩 화면으로 이동");
        Intent intent = new Intent(this, OnboardingActivity.class);
        startActivity(intent);
        finish();
    }
}
