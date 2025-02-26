package com.example.liveguard_app_010;

import android.app.Application;
import com.navercorp.nid.NaverIdLoginSDK;

public class LiveGuardApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // ✅ 네이버 로그인 SDK 초기화 (앱 실행 시 한 번만 실행)
        NaverIdLoginSDK.INSTANCE.initialize(
                this,
                getString(R.string.naver_client_id),
                getString(R.string.naver_client_secret),
                "LiveGuard"
        );
    }
}
