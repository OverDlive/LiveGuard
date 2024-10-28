package com.example.liveguard_app_010;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.liveguard_app_010.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding 설정
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // BottomNavigationView 초기화
        BottomNavigationView navView = binding.navView;

        // NavHostFragment를 FragmentManager에서 찾아 가져옵니다.
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);

        // 만약 navHostFragment가 null인 경우, 예외 처리를 추가합니다.
        if (navHostFragment == null) {
            throw new IllegalStateException("NavHostFragment를 찾을 수 없습니다.");
        }

        // NavController 초기화
        NavController navController = navHostFragment.getNavController();

        // AppBarConfiguration 설정: 이곳에 네비게이션 바 메뉴 아이템 ID 추가
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_timeline, R.id.navigation_crowd_density,
                R.id.navigation_region_info, R.id.navigation_settings)
                .build();

        // ActionBar와 NavController 연결
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // BottomNavigationView와 NavController 연결
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}