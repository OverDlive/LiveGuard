package com.example.liveguard_app_010.ui.home;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.example.liveguard_app_010.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class BottomSheetHelper {

    public static BottomSheetBehavior<View> initBottomSheet(FragmentActivity activity, View rootView) {
        View bottomSheet = rootView.findViewById(R.id.bottom_sheet);
        if (bottomSheet == null) {
            Log.e("BottomSheetHelper", "bottom_sheet ID를 찾을 수 없음!");
            return null;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        Log.d("BottomSheetHelper", "Screen Height: " + screenHeight);

        float density = activity.getResources().getDisplayMetrics().density;
        // 바텀시트가 네이버 지도 위에 보이도록 elevation 설정 (예: 8dp)
        bottomSheet.setElevation(8 * density);
        // 바텀시트 뷰를 최상위로 올려서 지도 위에 표시되도록 함
        bottomSheet.bringToFront();

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);

        // 바텀시트가 네비게이션 바 위에 약 200dp 정도 보이도록 설정
        int collapsedPeekHeight = (int) (200 * density);
        behavior.setPeekHeight(collapsedPeekHeight);

        // 중간(화면 50%)에서 멈추고 전체 확장도 가능하도록 설정
        behavior.setFitToContents(false);
        behavior.setHalfExpandedRatio(0.5f);
        behavior.setExpandedOffset(0);

        // 초기 상태는 STATE_HALF_EXPANDED
        behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

        return behavior;
    }
}