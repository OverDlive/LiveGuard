package com.example.liveguard_app_010.ui.tour;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liveguard_app_010.R;

public class TourOnboardingFragment extends Fragment {

    private ViewPager2 viewPager;

    public TourOnboardingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_onboarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.viewPager);

        TourPagerAdapter adapter = new TourPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }

    public ViewPager2 getViewPager() {
        return viewPager;
    }
}
