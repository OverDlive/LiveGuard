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
import com.example.liveguard_app_010.ui.exhibition.ExhibitionOnboardingActivity;
import com.example.liveguard_app_010.ui.experience.ExperienceOnboardingActivity;
import com.example.liveguard_app_010.ui.performance_movie.PerformanceMovieOnboardingActivity;
import com.example.liveguard_app_010.ui.recommendation.RecommendationOnboardingActivity;
import com.example.liveguard_app_010.ui.sport.SportOnboardingActivity;
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
        view.findViewById(R.id.btn_4).setOnClickListener(v -> startActivity(new Intent(requireContext(), SportOnboardingActivity.class)));
        view.findViewById(R.id.btn_5).setOnClickListener(v -> startActivity(new Intent(requireContext(), PerformanceMovieOnboardingActivity.class)));
        view.findViewById(R.id.btn_6).setOnClickListener(v -> startActivity(new Intent(requireContext(), ExhibitionOnboardingActivity.class)));
        view.findViewById(R.id.btn_7).setOnClickListener(v -> startActivity(new Intent(requireContext(), ExperienceOnboardingActivity.class)));
        view.findViewById(R.id.btn_8).setOnClickListener(v -> startActivity(new Intent(requireContext(), RecommendationOnboardingActivity.class)));

        return view;
    }
}
