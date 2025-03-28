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
import com.example.liveguard_app_010.ui.tour.TourIntroFragment;

public class FeatureFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feature, container, false);

        // ❌ 닫기 버튼
        ImageView closeFeature = view.findViewById(R.id.close_feature);
        closeFeature.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // ✅ 관광지 버튼 (버튼1) 눌렀을 때 TourIntroFragment로 전환
        Button btn1 = view.findViewById(R.id.btn_1);
        btn1.setOnClickListener(v -> {
            TourIntroFragment tourIntroFragment = new TourIntroFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_main, tourIntroFragment);
            transaction.addToBackStack(null); // ← 뒤로가기 가능하게
            transaction.commit();
        });

        return view;
    }
}
