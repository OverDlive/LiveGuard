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
import com.example.liveguard_app_010.ui.topnavigation.TopNavigationFragment; // TopNavigationFragment 임포트

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

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

        // NavHostFragment를 FragmentManager에서 찾아 가져옵니다.
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);

        // 만약 navHostFragment가 null인 경우, 예외 처리를 추가합니다.
        if (navHostFragment == null) {
            throw new IllegalStateException("NavHostFragment를 찾을 수 없습니다.");
        }

        // NavController 초기화
        NavController navController = navHostFragment.getNavController();

        // AppBarConfiguration 설정
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_timeline, R.id.navigation_crowd_density,
                R.id.navigation_region_info, R.id.navigation_settings)
                .build();

        // ActionBar와 NavController 연결
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // BottomNavigationView와 NavController 연결
        NavigationUI.setupWithNavController(navView, navController);

        // TopNavigationFragment를 MainActivity에 추가
        TopNavigationFragment topNavigationFragment = new TopNavigationFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.top_navigation_container, topNavigationFragment) // top_navigation_container는 activity_main.xml에 추가한 컨테이너 ID
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
