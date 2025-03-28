package com.example.liveguard_app_010.ui.tour;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.viewpager2.widget.ViewPager2;

import com.example.liveguard_app_010.R;

public class TourIntroFragment extends Fragment {

    public TourIntroFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_intro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnStart = view.findViewById(R.id.btn_start);

        btnStart.setOnClickListener(v -> {
            // 부모 Fragment의 ViewPager2를 가져와서 다음 페이지로 이동
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof TourOnboardingFragment) {
                ViewPager2 viewPager = ((TourOnboardingFragment) parentFragment).getViewPager();
                viewPager.setCurrentItem(1, true); // 2페이지로 이동
            }
        });
    }
}
