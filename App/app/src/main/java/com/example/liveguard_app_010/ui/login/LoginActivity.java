package com.example.liveguard_app_010.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.liveguard_app_010.MainActivity;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.login.TermsActivity;
import com.example.liveguard_app_010.ui.signup.SignupActivity;
import com.example.liveguard_app_010.ui.utils.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private Button btnNaverLogin;
    private LoginManager loginManager;
    private FirebaseAuth auth;
    private TextInputEditText etLoginEmail, etLoginPassword;
    private TextInputLayout layoutLoginEmail, layoutLoginPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginManager = new LoginManager(this); // ✅ 로그인 상태 관리 초기화

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // ✅ 로그인 상태 확인 → 이미 로그인되었으면 MainActivity로 이동
        if (loginManager.isLoggedIn()) {
            moveToMainActivity();
            return;
        }

        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        layoutLoginEmail = findViewById(R.id.layout_login_email);
        layoutLoginPassword = findViewById(R.id.layout_login_password);
        etLoginEmail = findViewById(R.id.et_login_email);
        etLoginPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);

        Button btnEmailSignup = findViewById(R.id.btn_phone_login);
        btnEmailSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutLoginEmail.setError(null);
                layoutLoginPassword.setError(null);
                String email = etLoginEmail.getText() != null ? etLoginEmail.getText().toString().trim() : "";
                String password = etLoginPassword.getText() != null ? etLoginPassword.getText().toString() : "";

                if (email.isEmpty()) {
                    layoutLoginEmail.setError("이메일을 입력해주세요.");
                    return;
                }
                if (password.isEmpty()) {
                    layoutLoginPassword.setError("비밀번호를 입력해주세요.");
                    return;
                }
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                loginManager.setLoggedIn(user.getUid());
                            }
                            moveToMainActivity();
                        } else {
                            layoutLoginPassword.setError("로그인에 실패했습니다: " + task.getException().getMessage());
                        }
                    });
            }
        });

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