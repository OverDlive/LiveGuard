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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_location, container, false);

        // 뒤로가기 버튼 클릭 시 Fragment 닫기
        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            // 네비게이션 바 다시 보이기
            ((MainActivity) requireActivity()).showNavigationBars();
        });

        // OnSwipeTouchListener를 Context만 전달하여 생성
        view.setOnTouchListener(new OnSwipeTouchListener(requireContext()) {
            // 스와이프 감지 동작 구현
            @Override
            public void onSwipeRight() {
                requireActivity().getSupportFragmentManager().popBackStack();
                // 네비게이션 바 다시 보이기
                ((MainActivity) requireActivity()).showNavigationBars();
            }
        });

        return view;
    }

}

