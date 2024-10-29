package com.example.liveguard_app_010.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.liveguard_app_010.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class HomeFragment extends Fragment {

    private BottomSheetBehavior<View> bottomSheetBehavior;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Bottom Sheet 초기화
        View bottomSheet = view.findViewById(R.id.bottom_sheet);

        // BottomSheetBehavior 설정
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // Bottom Sheet가 화면의 50%로 올라오게 설정
        bottomSheetBehavior.setHalfExpandedRatio(0.5f);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

        return view;
    }
}
