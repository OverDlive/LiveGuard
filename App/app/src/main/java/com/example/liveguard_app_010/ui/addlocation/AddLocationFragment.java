package com.example.liveguard_app_010.ui.addlocation;

import com.example.liveguard_app_010.MainActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.OnSwipeTouchListener;

public class AddLocationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_location, container, false);

        // 뒤로가기 버튼 클릭 시 Fragment 닫기
        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> closeFragment());

        // 슬라이드 제스처 감지
        view.setOnTouchListener(new OnSwipeTouchListener(requireContext()) {
            @Override
            public void onSwipeRight() {
                closeFragment(); // 슬라이드 제스처로 Fragment 닫기
            }
        });

        return view;
    }

    private void closeFragment() {
        // 현재 Fragment가 AddLocationFragment인지 확인
        if (isVisible()) {
            requireActivity().getSupportFragmentManager().popBackStack();
            // 네비게이션 바 다시 보이기
            ((MainActivity) requireActivity()).showNavigationBars();
        }
    }
}
