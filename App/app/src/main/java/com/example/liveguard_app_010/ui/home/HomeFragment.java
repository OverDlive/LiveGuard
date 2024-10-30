package com.example.liveguard_app_010.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.liveguard_app_010.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.navigation.fragment.NavHostFragment;

public class HomeFragment extends Fragment {

    private BottomSheetBehavior<View> bottomSheetBehavior;
    private View blockTouchView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 바텀 시트 초기화
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
<<<<<<< HEAD
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // Bottom Sheet의 Peek Height 설정
        int peekHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.4); // 40%
        bottomSheetBehavior.setPeekHeight(peekHeight, true);

        // Bottom Sheet의 초기 상태를 STATE_COLLAPSED로 설정
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED); // 접힌 상태

        // 필요한 정보 텍스트 설정
        TextView infoText = view.findViewById(R.id.info_text);
        infoText.setText("필요한 정보입니다.");

        // 버튼 클릭 리스너 설정
        Button navigateButton = view.findViewById(R.id.navigate_button);
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigation to fragment_region_info
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.navigation_region_info);
=======

        // 터치 차단용 뷰 초기화
        blockTouchView = view.findViewById(R.id.block_touch_view);



        // 터치 이벤트를 소비하도록 설정
        blockTouchView.setOnTouchListener((v, event) -> true);

        // BottomSheetBehavior 설정
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // 화면 높이 가져오기
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        // 바텀 시트 설정
        bottomSheetBehavior.setPeekHeight(500);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.5f);
        bottomSheetBehavior.setExpandedOffset((int) (screenHeight * 0.02f));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // 바텀 시트 콜백 설정
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // 바텀 시트 상태에 따라 터치 차단용 뷰의 가시성 제어
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    blockTouchView.setVisibility(View.GONE);
                } else {
                    blockTouchView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // 슬라이드 중일 때의 처리 (필요하다면)
>>>>>>> 7b5f042c5ad0fcb641a810edf2f7040c9731368b
            }
        });

        return view;
    }
}
