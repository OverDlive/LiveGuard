package com.example.liveguard_app_010.ui.topnavigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.liveguard_app_010.R;

public class TopNavigationFragment extends Fragment {

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_navigation, container, false);

        // 신고 버튼 클릭 시
        ImageButton reportButton = view.findViewById(R.id.report);
        reportButton.setOnClickListener(v -> Toast.makeText(getContext(), "신고 버튼 클릭됨", Toast.LENGTH_SHORT).show());

        // 지역 추가 버튼 클릭 시
        ImageButton addLocationButton = view.findViewById(R.id.locationbutton);
        addLocationButton.setOnClickListener(v -> Toast.makeText(getContext(), "지역 추가 버튼 클릭됨", Toast.LENGTH_SHORT).show());

        return view;
    }
}
