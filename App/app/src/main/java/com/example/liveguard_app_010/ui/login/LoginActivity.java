package com.example.liveguard_app_010.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.liveguard_app_010.MainActivity;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.login.TermsActivity;
import com.example.liveguard_app_010.ui.utils.LoginManager;

public class LoginActivity extends AppCompatActivity {

    private Button btnNaverLogin;
    private LoginManager loginManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginManager = new LoginManager(this); // ✅ 로그인 상태 관리 초기화

        // ✅ 로그인 상태 확인 → 이미 로그인되었으면 MainActivity로 이동
        if (loginManager.isLoggedIn()) {
            moveToMainActivity();
            return;
        }

        setContentView(R.layout.activity_login);
        btnNaverLogin = findViewById(R.id.btn_naver_login);

        // ✅ 네이버 로그인 버튼 클릭 → 이용약관 동의 후 진행
        btnNaverLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, TermsActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // ✅ 로그인 상태라면 MainActivity로 이동
    private void moveToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // 현재 로그인 액티비티 종료
    }
}