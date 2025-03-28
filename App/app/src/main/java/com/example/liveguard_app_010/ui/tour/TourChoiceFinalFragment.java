package com.example.liveguard_app_010.ui.tour;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.result.TourResultFragment;

public class TourChoiceFinalFragment extends Fragment {

    public TourChoiceFinalFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_choice_final, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnAnalyze = view.findViewById(R.id.btn_analyze);
        if (btnAnalyze != null) {
            btnAnalyze.setOnClickListener(v -> {
                // 분석하기 버튼 클릭 시 전체 화면을 덮는 새로운 결과 화면(TourResultFragment)을 띄운다.
                TourResultFragment resultFragment = new TourResultFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right
                );
                // 여기서 android.R.id.content는 액티비티의 루트 뷰를 의미한다.
                transaction.add(android.R.id.content, resultFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }
    }
}
