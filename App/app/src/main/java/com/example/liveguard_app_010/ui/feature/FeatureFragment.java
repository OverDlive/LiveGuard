package com.example.liveguard_app_010.ui.feature;

import android.content.Intent; // ✅ 추가
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.tour.TourOnboardingActivity;

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

        // 관광지 버튼 클릭 시 온보딩 화면 시작
        Button tourismBtn = view.findViewById(R.id.btn_1);
        tourismBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TourOnboardingActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
