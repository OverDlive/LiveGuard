package com.example.liveguard_app_010.ui.tour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.liveguard_app_010.R;

public class TourChoiceFragment extends Fragment {

    private int pageIndex;  // 2, 3, 4 중 하나

    public TourChoiceFragment() {}

    public static TourChoiceFragment newInstance(int pageIndex) {
        TourChoiceFragment fragment = new TourChoiceFragment();
        Bundle args = new Bundle();
        args.putInt("pageIndex", pageIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageIndex = getArguments().getInt("pageIndex", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_choice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button[] buttons = new Button[] {
                view.findViewById(R.id.btn_option1),
                view.findViewById(R.id.btn_option2),
                view.findViewById(R.id.btn_option3),
                view.findViewById(R.id.btn_option4)
        };

        // 버튼을 누르기 전에는 다음 페이지로 못 가게 막음
        ViewPager2 viewPager = ((TourOnboardingFragment) getParentFragment()).getViewPager();
        viewPager.setUserInputEnabled(false);

        for (Button btn : buttons) {
            btn.setOnClickListener(v -> {
                // 예: 선택한 값 저장 가능
                // 선택 시 스와이프 가능하게 설정
                viewPager.setUserInputEnabled(true);
                viewPager.setCurrentItem(pageIndex + 1, true); // 다음 페이지로 이동
            });
        }

        // 질문이나 텍스트 설정은 페이지별로 다르게 할 수도 있음
    }
}
