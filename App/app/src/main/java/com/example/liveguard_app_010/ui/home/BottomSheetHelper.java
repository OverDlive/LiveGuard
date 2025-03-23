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
        return BottomSheetBehavior.from(bottomSheet);
    }
}