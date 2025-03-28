package com.example.liveguard_app_010.ui.tour;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.feature.FeatureFragment;

public class TourOnboardingFragment extends Fragment {

    private ViewPager2 viewPager;

    public TourOnboardingFragment() {}

    public ViewPager2 getViewPager() {
        return viewPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_onboarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new TourPagerAdapter(this));

        // ❌ 닫기 버튼
        Button btnClose = view.findViewById(R.id.btn_close_onboarding);
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> {
                Log.d("TourOnboarding", "❌ 닫기 버튼 클릭됨");

                // FeatureFragment로 교체
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new FeatureFragment())
                        .addToBackStack(null)
                        .commit();
            });
        } else {
            Log.e("TourOnboarding", "btn_close_onboarding not found in layout");
        }
    }
}
