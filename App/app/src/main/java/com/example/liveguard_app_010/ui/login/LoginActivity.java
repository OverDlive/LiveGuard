package com.example.liveguard_app_010.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.login.TermsActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnNaverLogin, btnPhoneLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnNaverLogin = findViewById(R.id.btn_naver_login);
        btnPhoneLogin = findViewById(R.id.btn_phone_login);

        // 🚫 휴대폰 로그인 버튼 비활성화
        btnPhoneLogin.setEnabled(false);

        // ✅ 네이버 로그인 버튼 클릭 → 이용약관 동의 화면으로 이동
        btnNaverLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, TermsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
