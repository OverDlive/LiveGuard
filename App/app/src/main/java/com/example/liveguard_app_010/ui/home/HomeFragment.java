package com.example.liveguard_app_010.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Bottom Sheet 초기화
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
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
            }
        });

        return view;
    }
}
