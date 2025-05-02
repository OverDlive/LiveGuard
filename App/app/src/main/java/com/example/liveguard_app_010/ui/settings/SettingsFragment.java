package com.example.liveguard_app_010.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.permission.PermissionActivity;
import com.example.liveguard_app_010.ui.utils.OnboardingManager;
import com.example.liveguard_app_010.ui.utils.LoginManager;
import com.navercorp.nid.NaverIdLoginSDK;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";
    private LoginManager loginManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        loginManager = new LoginManager(requireContext());

        // 로그아웃 버튼
        Button logoutButton = view.findViewById(R.id.naverLogoutButton);
        logoutButton.setOnClickListener(v -> naverLogout());

        // 개발자용 초기화 버튼 (권한도 리셋)
        Button resetButton = view.findViewById(R.id.devResetButton);
        resetButton.setOnClickListener(v -> resetApp());

        return view;
    }

    private void naverLogout() {
        Log.d(TAG, "네이버 로그아웃 요청");

        // ✅ 저장된 로그인 정보 삭제
        loginManager.logout();

        // ✅ 네이버 로그아웃 API 호출
        NaverIdLoginSDK.INSTANCE.logout();

        // ✅ UI 업데이트 및 토스트 메시지 표시
        Toast.makeText(requireContext(), "로그아웃 완료", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "로그아웃 완료");

        // ✅ 로그인 화면으로 이동 (이전 스택 제거)
        Intent intent = new Intent(requireContext(), PermissionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void resetApp() {
        Log.d(TAG, "앱 초기화 요청");

        // ✅ 모든 SharedPreferences 데이터 삭제 (로그인 정보, 권한 설정, 온보딩 상태)
        SharedPreferences preferences = requireContext().getSharedPreferences("app_preferences", 0);
        preferences.edit().clear().apply();

        SharedPreferences userPreferences = requireContext().getSharedPreferences("user_preferences", 0);
        userPreferences.edit().clear().apply();

        // ✅ 네이버 로그인 상태 초기화
        NaverIdLoginSDK.INSTANCE.logout();

        // ✅ 온보딩 다시 표시되도록 설정
        OnboardingManager onboardingManager = new OnboardingManager(requireContext());
        onboardingManager.setOnboardingShown(false);

        // ✅ 권한 초기화 (사용자가 직접 설정에서 변경하도록 안내)
        resetPermissions();

        // ✅ UI 업데이트 및 토스트 메시지 표시
        Toast.makeText(requireContext(), "앱이 초기화되었습니다. 권한을 다시 설정해주세요.", Toast.LENGTH_LONG).show();
        Log.d(TAG, "앱 초기화 완료");

        // ✅ 초기 상태로 돌아가기 (PermissionActivity 실행)
        Intent intent = new Intent(requireContext(), PermissionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void resetPermissions() {
        Log.d(TAG, "앱 권한 초기화 요청");

        // ✅ 앱의 설정 화면으로 이동하여 사용자가 직접 권한을 다시 설정하도록 유도
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(android.net.Uri.parse("package:" + requireContext().getPackageName()));
        startActivity(intent);

        Toast.makeText(requireContext(), "앱 설정에서 권한을 다시 설정해주세요.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowInsetsControllerCompat controller =
            WindowCompat.getInsetsController(requireActivity().getWindow(), requireActivity().getWindow().getDecorView());
        // Use dark status bar icons
        controller.setAppearanceLightStatusBars(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        WindowInsetsControllerCompat controller =
            WindowCompat.getInsetsController(requireActivity().getWindow(), requireActivity().getWindow().getDecorView());
        // Restore default (light) status bar icons
        controller.setAppearanceLightStatusBars(false);
    }
}
