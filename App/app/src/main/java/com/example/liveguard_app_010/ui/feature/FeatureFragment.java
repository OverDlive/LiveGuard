package com.example.liveguard_app_010.ui.feature;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.tour.TourOnboardingFragment;

public class FeatureFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feature, container, false);

        // ❌ X 버튼 클릭 시 프래그먼트 닫기
        ImageView closeFeature = view.findViewById(R.id.close_feature);
        closeFeature.setOnClickListener(v -> requireActivity()
                .getSupportFragmentManager()
                .popBackStack());

        // 관광지 버튼 클릭 시 온보딩 화면 추가
        Button tourismBtn = view.findViewById(R.id.btn_1);
        tourismBtn.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            transaction.setCustomAnimations(
                    android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left, android.R.anim.slide_out_right
            );
            transaction.add(android.R.id.content, new TourOnboardingFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
