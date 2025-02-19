package com.example.liveguard_app_010.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.liveguard_app_010.R;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.profile.NidProfileCallback;
import com.navercorp.nid.profile.data.NidProfile;
import com.navercorp.nid.profile.data.NidProfileResponse;

public class SettingsFragment extends Fragment {

    private static final String TAG = "NaverLoginTest";
    private TextView userInfoTextView;
    private NidOAuthLogin nidOAuthLogin; // NidOAuthLogin 인스턴스 추가

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // 네이버 로그인 SDK 초기화
        NaverIdLoginSDK.INSTANCE.initialize(
                requireContext(),
                getString(R.string.naver_client_id),   // Client ID (strings.xml에서 가져오기)
                getString(R.string.naver_client_secret), // Client Secret (strings.xml에서 가져오기)
                "테스트 앱"
        );

        // NidOAuthLogin 인스턴스 생성
        nidOAuthLogin = new NidOAuthLogin();

        // UI 요소
        Button loginButton = view.findViewById(R.id.naverLoginButton);
        Button logoutButton = view.findViewById(R.id.naverLogoutButton);
        userInfoTextView = view.findViewById(R.id.userInfoTextView);

        // 네이버 로그인 버튼 클릭
        loginButton.setOnClickListener(v -> naverLogin());

        // 로그아웃 버튼 클릭
        logoutButton.setOnClickListener(v -> naverLogout());

        return view;
    }

    private void naverLogin() {
        Log.d(TAG, "네이버 로그인 요청 시작");
        NaverIdLoginSDK.INSTANCE.authenticate(requireActivity(), new OAuthLoginCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "네이버 로그인 성공");
                Toast.makeText(requireContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                getUserProfile();
            }

            @Override
            public void onFailure(int httpStatus, @Nullable String message) {
                Log.e(TAG, "로그인 실패 - 상태 코드: " + httpStatus + ", 메시지: " + message);
                Toast.makeText(requireContext(), "로그인 실패: " + message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(int httpStatus, @Nullable String message) {
                Log.e(TAG, "로그인 에러 - 상태 코드: " + httpStatus + ", 메시지: " + message);
                Toast.makeText(requireContext(), "로그인 오류 발생", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUserProfile() {
        Log.d(TAG, "사용자 프로필 정보 요청 중...");
        nidOAuthLogin.callProfileApi(new NidProfileCallback<NidProfileResponse>() {
            @Override
            public void onSuccess(NidProfileResponse result) {
                NidProfile profile = result.getProfile();
                if (profile != null) {
                    String userInfo = "이름: " + profile.getName() + "\n"
                            + "이메일: " + profile.getEmail() + "\n"
                            + "프로필 이미지: " + profile.getProfileImage();
                    userInfoTextView.setText(userInfo);
                    Log.d(TAG, "사용자 정보 가져오기 성공:\n" + userInfo);
                } else {
                    Log.e(TAG, "프로필 데이터가 null입니다.");
                }
            }

            @Override
            public void onFailure(int httpStatus, @Nullable String message) {
                Log.e(TAG, "프로필 가져오기 실패 - 상태 코드: " + httpStatus + ", 메시지: " + message);
                Toast.makeText(requireContext(), "프로필 가져오기 실패", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(int httpStatus, @Nullable String message) {
                Log.e(TAG, "프로필 가져오기 에러 - 상태 코드: " + httpStatus + ", 메시지: " + message);
                Toast.makeText(requireContext(), "프로필 가져오기 오류 발생", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void naverLogout() {
        Log.d(TAG, "네이버 로그아웃 요청");
        NaverIdLoginSDK.INSTANCE.logout();
        userInfoTextView.setText("로그아웃 되었습니다.");
        Toast.makeText(requireContext(), "로그아웃 완료", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "로그아웃 완료");
    }
}
