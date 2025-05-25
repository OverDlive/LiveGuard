package com.example.liveguard_app_010;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.liveguard_app_010.worker.CongestionWorker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.liveguard_app_010.databinding.ActivityMainBinding;
import com.example.liveguard_app_010.ui.login.LoginActivity;
import com.example.liveguard_app_010.ui.utils.LoginManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String WORK_TAG = "CongestionWork";
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginManager = new LoginManager(this);
        // Application 클래스 또는 MainActivity onCreate() 최상단에 추가
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // ✅ 로그인 상태 확인
        if (!loginManager.isLoggedIn()) {
            Log.d("MainActivity", "로그인 상태가 유효하지 않음 → 로그인 화면으로 이동");
            moveToLogin();
            return;
        }

        Log.d("MainActivity", "로그인 유지됨 → MainActivity 실행");

        // ViewBinding 설정
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // BottomNavigationView 초기화
        BottomNavigationView navView = binding.navView;

        // NavController 설정
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment == null) {
            throw new IllegalStateException("NavHostFragment를 찾을 수 없습니다.");
        }
        NavController navController = navHostFragment.getNavController();

        // AppBarConfiguration 설정
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_timeline,
                R.id.navigation_crowd_density,
                R.id.navigation_region_info,
                R.id.navigation_settings
        ).build();

        // NavigationUI 설정
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // ✅ TopNavigationFragment 제거 완료 (관련 코드 삭제)
        // 기존에 있던 top_navigation_container 관련 코드 제거됨

        // 주기적 Worker 스케줄링
        schedulePeriodicWork();
    }

    // ✅ 로그인되지 않은 경우 로그인 화면으로 이동
    private void moveToLogin() {
        Log.d("MainActivity", "로그인 화면으로 이동");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // 이전 액티비티 스택 제거
        startActivity(intent);
        finish();
    }

    /**
     * WorkManager를 이용해 15분 간격으로 혼잡도 데이터를 가져오는 작업을 스케줄링
     */
    private void schedulePeriodicWork() {
        Data inputData = new Data.Builder()
                .putString("areaName", "광화문")
                .build();

        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(CongestionWorker.class, 15, TimeUnit.MINUTES)
                        .setInputData(inputData)
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                WORK_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
        );
    }
}
