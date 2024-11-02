package com.example.liveguard_app_010;

import android.os.Bundle;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.liveguard_app_010.databinding.ActivityMainBinding;
import com.example.liveguard_app_010.ui.topnavigation.TopNavigationFragment;

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
                .replace(R.id.top_navigation_container, topNavigationFragment)
                .commit();

        // Fragment 변경 시 TopNavigationFragment의 Visibility 조정
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // 조건문을 사용하여 특정 Fragment에서 상단 및 하단 내비게이션 바 숨기기
            if (destination.getId() == R.id.navigation_settings) { // SettingsFragment ID 사용
                binding.topNavigationContainer.setVisibility(View.GONE); // 상단 내비게이션 숨기기
                binding.navView.setVisibility(View.VISIBLE); // 하단 내비게이션 보이기
            } else if (destination.getId() == R.id.add_location_fragment) { // AddLocationFragment ID 사용
                binding.topNavigationContainer.setVisibility(View.GONE); // 상단 내비게이션 숨기기
                binding.navView.setVisibility(View.GONE); // 하단 내비게이션 숨기기
            } else {
                binding.topNavigationContainer.setVisibility(View.VISIBLE); // 상단 내비게이션 보이기
                binding.navView.setVisibility(View.VISIBLE); // 하단 내비게이션 보이기
            }
        });
    }

    // 네비게이션 바 숨기기
    public void hideNavigationBars() {
        binding.topNavigationContainer.setVisibility(View.GONE);
        binding.navView.setVisibility(View.GONE);
    }

    // 네비게이션 바 보이기
    public void showNavigationBars() {
        binding.topNavigationContainer.setVisibility(View.VISIBLE);
        binding.navView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
