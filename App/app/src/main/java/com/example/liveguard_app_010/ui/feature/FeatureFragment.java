package com.example.liveguard_app_010.ui.feature;

import android.content.Intent;
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
import com.example.liveguard_app_010.ui.food.FoodOnboardingActivity;
import com.example.liveguard_app_010.ui.shopping.ShoppingOnboardingActivity;

public class FeatureFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feature, container, false);

        ImageView closeFeature = view.findViewById(R.id.close_feature);
        closeFeature.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

        // 각 버튼에 온보딩 액티비티 연결
        view.findViewById(R.id.btn_1).setOnClickListener(v -> startActivity(new Intent(requireContext(), TourOnboardingActivity.class)));
        view.findViewById(R.id.btn_2).setOnClickListener(v -> startActivity(new Intent(requireContext(), FoodOnboardingActivity.class)));
        view.findViewById(R.id.btn_3).setOnClickListener(v -> startActivity(new Intent(requireContext(), ShoppingOnboardingActivity.class)));

        return view;
    }
}
