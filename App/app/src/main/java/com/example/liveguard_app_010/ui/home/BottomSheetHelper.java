package com.example.liveguard_app_010.ui.home;

import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class BottomSheetHelper {
    public static BottomSheetBehavior<View> initBottomSheet(View bottomSheet, int peekHeight, float halfExpandedRatio, int expandedOffset) {
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(peekHeight);
        behavior.setFitToContents(false);
        behavior.setHalfExpandedRatio(halfExpandedRatio);
        behavior.setExpandedOffset(expandedOffset);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        return behavior;
    }
}