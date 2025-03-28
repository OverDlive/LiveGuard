package com.example.liveguard_app_010.ui.tour;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TourPagerAdapter extends FragmentStateAdapter {

    public TourPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TourIntroFragment();
            case 1:
            case 2:
            case 3:
                return TourChoiceFragment.newInstance(position);
            case 4:
                return new TourChoiceFinalFragment();
            default:
                return new TourIntroFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5; // 총 5페이지
    }
}
