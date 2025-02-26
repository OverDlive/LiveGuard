package com.example.liveguard_app_010.ui.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.onboarding.OnboardingActivity;

public class PermissionActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100; // 권한 요청 코드
    private Button btnGrantPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // ✅ 여기를 올바르게 수정

        // 권한이 이미 허용되었으면 바로 온보딩 화면으로 이동
        if (isPermissionGranted()) {
            moveToOnboarding();
            return;
        }

        setContentView(R.layout.activity_permission);
        btnGrantPermission = findViewById(R.id.btn_grant_permission);
        btnGrantPermission.setOnClickListener(v -> requestPermissions());
    }

    // ✅ 현재 위치 권한이 허용되었는지 확인하는 함수
    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (!isPermissionGranted()) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            // 이미 권한이 있는 경우 바로 온보딩 화면으로 이동
            moveToOnboarding();
        }
    }

    private void moveToOnboarding() {
        Toast.makeText(this, "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, OnboardingActivity.class);
        startActivity(intent);
        finish(); // 현재 액티비티 종료
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용되었으면 온보딩 화면으로 이동
                moveToOnboarding();
            } else {
                // 권한이 거부되었을 경우 처리
                Toast.makeText(this, "앱을 사용하려면 위치 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
