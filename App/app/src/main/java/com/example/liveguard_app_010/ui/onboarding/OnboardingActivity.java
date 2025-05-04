package com.example.liveguard_app_010.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.login.LoginActivity;
import com.example.liveguard_app_010.ui.utils.OnboardingManager;

import java.util.Arrays;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button homeButton;
    private OnboardingManager onboardingManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        onboardingManager = new OnboardingManager(this);

        // ✅ 온보딩이 이미 표시된 경우, 로그인 화면으로 이동
        if (onboardingManager.isOnboardingShown()) {
            moveToLogin();
            return;
        }

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        viewPager = findViewById(R.id.viewPager);
        homeButton = findViewById(R.id.btn_home);

        List<Integer> onboardingScreens = Arrays.asList(
                R.layout.onboarding_page_1,
                R.layout.onboarding_page_2,
                R.layout.onboarding_page_3
        );

        // ✅ 오류 해결: OnboardingAdapter 생성 시 context 추가
        OnboardingAdapter onboardingAdapter = new OnboardingAdapter(this, onboardingScreens);
        viewPager.setAdapter(onboardingAdapter);

        homeButton.setOnClickListener(v -> {
            onboardingManager.setOnboardingShown(true); // ✅ 온보딩 완료 저장
            moveToLogin();
        });
    }

    private void moveToLogin() {
        Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
