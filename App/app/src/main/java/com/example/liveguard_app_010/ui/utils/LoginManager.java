package com.example.liveguard_app_010.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.navercorp.nid.NaverIdLoginSDK;

public class LoginManager {
    private static final String PREF_NAME = "user_preferences";
    private static final String KEY_ACCESS_TOKEN = "accessToken";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public LoginManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // ✅ 로그인 상태 저장
    public void setLoggedIn(String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            editor.putString(KEY_ACCESS_TOKEN, accessToken);
            editor.apply();
            Log.d("LoginManager", "로그인 상태 저장됨: " + accessToken);
        }
    }

    // ✅ 저장된 토큰 가져오기
    public String getAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    // ✅ 로그인 상태 확인 (토큰이 존재하는지 확인)
    public boolean isLoggedIn() {
        String accessToken = getAccessToken();
        Log.d("LoginManager", "현재 저장된 토큰: " + accessToken);
        return accessToken != null && !accessToken.isEmpty();
    }

    // ✅ 로그아웃 처리
    public void logout() {
        editor.remove(KEY_ACCESS_TOKEN);
        editor.apply();
        NaverIdLoginSDK.INSTANCE.logout();
        Log.d("LoginManager", "로그아웃 완료 - 토큰 삭제됨");
    }
}
