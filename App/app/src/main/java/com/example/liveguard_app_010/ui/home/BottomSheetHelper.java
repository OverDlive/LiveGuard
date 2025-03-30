package com.example.liveguard_app_010.ui.home;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.liveguard_app_010.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class BottomSheetHelper {

    /**
     * 주어진 bottomSheet 뷰에 대해 네이버 지도 스타일의 바텀시트(화면의 10%만 보임, 중간 상태, 최대 확장 시 상태바 아래 틈 등)를 설정합니다.
     *
     * @param bottomSheet 설정할 바텀시트 뷰
     * @param activity 현재 액티비티 (자원 및 디스플레이 정보를 얻기 위함)
     * @param rootView HomeFragment의 루트 뷰 (여기서 R.id.map 뷰를 찾아 터치 이벤트를 설정합니다)
     * @return 구성된 BottomSheetBehavior 인스턴스
     */
    public static BottomSheetBehavior<View> setupBottomSheet(View bottomSheet, Activity activity, View rootView) {
        // 화면 높이 및 밀도 계산
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        float density = displayMetrics.density;

        // BottomSheetBehavior 생성 및 설정 시작
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);

        // 화면의 10%만 보이도록 peek height 설정 (네이버 지도 스타일)
        int collapsedPeekHeight = (int) (screenHeight * 0.1);
        behavior.setPeekHeight(collapsedPeekHeight);

        // 중간 상태 (화면 50%) 지원 설정
        behavior.setFitToContents(false);
        behavior.setHalfExpandedRatio(0.5f);

        // 최대 확장 시 상태바 높이와 10dp 마진을 고려하여 상단에 틈을 둠
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = 0;
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        int additionalMargin = (int) (10 * density);
        int expandedOffset = statusBarHeight + additionalMargin;
        behavior.setExpandedOffset(expandedOffset);

        // 초기 상태를 최소화 (STATE_COLLAPSED)로 설정
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // 지도(View ID: R.id.map) 터치 시 바텀시트를 최소화하도록 설정
        View mapView = rootView.findViewById(R.id.map);
        if (mapView != null) {
            mapView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            });
        }

        return behavior;
    }
}