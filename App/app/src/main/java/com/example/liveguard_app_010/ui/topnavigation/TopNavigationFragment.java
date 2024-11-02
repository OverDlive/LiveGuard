package com.example.liveguard_app_010.ui.topnavigation;

import com.example.liveguard_app_010.MainActivity;
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
import com.example.liveguard_app_010.ui.addlocation.AddLocationFragment; // 새 Fragment import
import com.example.liveguard_app_010.ui.settings.SettingsFragment;

public class TopNavigationFragment extends Fragment {

    private View topNavView; // Top Navigation View Reference

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        topNavView = inflater.inflate(R.layout.fragment_top_navigation, container, false);

        // 신고 버튼 클릭 시
        ImageButton reportButton = topNavView.findViewById(R.id.report);
        reportButton.setOnClickListener(v -> Toast.makeText(getContext(), "신고 버튼 클릭됨", Toast.LENGTH_SHORT).show());

        // 지역 추가 버튼 클릭 시
        ImageButton addLocationButton = topNavView.findViewById(R.id.locationbutton);
        addLocationButton.setOnClickListener(v -> {
            AddLocationFragment addLocationFragment = new AddLocationFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left) // 애니메이션 추가
                    .replace(R.id.nav_host_fragment_activity_main, addLocationFragment)
                    .addToBackStack(null)
                    .commit();
            // 하단 네비게이션과 상단 네비게이션 숨기기
            ((MainActivity) requireActivity()).hideNavigationBars();
        });

        return topNavView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 현재 보이는 Fragment에 따라 Top Navigation의 가시성 설정
        if (getActivity() != null && getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main) instanceof SettingsFragment) {
            topNavView.setVisibility(View.GONE); // SettingsFragment일 경우 숨기기
        } else {
            topNavView.setVisibility(View.VISIBLE); // 그 외의 경우 보이기
        }
    }
}
