package com.example.liveguard_app_010.ui.tour;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.liveguard_app_010.R;

public class TourChoiceFinalFragment extends Fragment {

    public TourChoiceFinalFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_choice_final, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnAnalyze = view.findViewById(R.id.btn_analyze);

        btnAnalyze.setOnClickListener(v -> {
            // 결과화면으로 이동
            Navigation.findNavController(view)
                    .navigate(R.id.action_tourChoiceFinalFragment_to_tourResultFragment);
        });
    }
}
