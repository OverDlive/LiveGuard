package com.example.liveguard_app_010.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.liveguard_app_010.MainActivity;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.utils.LoginManager;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.OAuthLoginCallback;

public class NaverLoginActivity extends AppCompatActivity {

    private static final String TAG = "NaverLoginActivity";
    private LoginManager loginManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginManager = new LoginManager(this);

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Log.d(TAG, "NaverLoginActivity 실행됨");

        try {
            Log.d(TAG, "네이버 로그인 SDK 실행 시작");

            // ✅ 네이버 로그인 실행
            NaverIdLoginSDK.INSTANCE.authenticate(this, new OAuthLoginCallback() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "네이버 로그인 성공");
                    Toast.makeText(NaverLoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();

                    // ✅ 로그인 성공 시 accessToken 저장
                    String accessToken = NaverIdLoginSDK.INSTANCE.getAccessToken();
                    loginManager.setLoggedIn(accessToken);  // 🔥 불필요한 `boolean` 제거

                    moveToMainActivity();
                }

                @Override
                public void onFailure(int httpStatus, @Nullable String message) {
                    Log.e(TAG, "네이버 로그인 실패 - 상태 코드: " + httpStatus + ", 메시지: " + message);
                    Toast.makeText(NaverLoginActivity.this, "로그인 실패: " + message, Toast.LENGTH_LONG).show();
                    moveToLogin();
                }

                @Override
                public void onError(int httpStatus, @Nullable String message) {
                    Log.e(TAG, "네이버 로그인 에러 - 상태 코드: " + httpStatus + ", 메시지: " + message);
                    Toast.makeText(NaverLoginActivity.this, "로그인 오류 발생", Toast.LENGTH_LONG).show();
                    moveToLogin();
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "네이버 로그인 실행 중 예외 발생", e);
            Toast.makeText(this, "로그인 오류 발생: " + e.getMessage(), Toast.LENGTH_LONG).show();
            moveToLogin();
        }
    }

    // ✅ 로그인 성공 시 `MainActivity` 실행
    private void moveToMainActivity() {
        Intent intent = new Intent(NaverLoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // 현재 로그인 액티비티 종료
    }

    // ❌ 로그인 실패 시 다시 로그인 화면으로 이동
    private void moveToLogin() {
        Intent intent = new Intent(NaverLoginActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // 현재 로그인 액티비티 종료
    }
}
