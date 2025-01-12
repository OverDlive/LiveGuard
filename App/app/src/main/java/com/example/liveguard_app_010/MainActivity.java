package com.example.liveguard_app_010;

import android.os.Bundle;

import com.example.liveguard_app_010.worker.CongestionWorker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.liveguard_app_010.ui.topnavigation.TopNavigationFragment;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String WORK_TAG = "CongestionWork";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                R.id.navigation_home, R.id.navigation_timeline, R.id.navigation_crowd_density,
                R.id.navigation_region_info, R.id.navigation_settings)
                .build();

        // NavigationUI 설정
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // TopNavigationFragment 추가
        TopNavigationFragment topNavigationFragment = new TopNavigationFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.top_navigation_container, topNavigationFragment)
                .commit();

        schedulePeriodicWork();
    }

    @Override
    public boolean onSupportNavigateUp() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    private void schedulePeriodicWork() {
        // 1. Worker에 전달할 파라미터 (poiId, lat, lon 등)
        Data inputData = new Data.Builder()
                .putString("poiId", "1172091")  // 타임스퀘어 예시
                .putDouble("lat", 37.51723636)
                .putDouble("lon", 126.90344592)
                .build();

        // 2. 15분 간격 반복 작업 요청
        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(CongestionWorker.class, 15, TimeUnit.MINUTES)
                        .setInputData(inputData)
                        .build();

        // 3. WorkManager에 등록
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                WORK_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
        );
    }
}
