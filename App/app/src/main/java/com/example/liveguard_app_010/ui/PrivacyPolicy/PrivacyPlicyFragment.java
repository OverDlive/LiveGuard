package com.example.liveguard_app_010.ui.PrivacyPolicy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;

import com.example.liveguard_app_010.R;

public class PrivacyPlicyFragment extends Fragment {

    public PrivacyPlicyFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_privacy_policy, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(requireActivity().getWindow(), requireActivity().getWindow().getDecorView());
        // Use dark status bar icons
        controller.setAppearanceLightStatusBars(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(requireActivity().getWindow(), requireActivity().getWindow().getDecorView());
        // Restore default (light) status bar icons
        controller.setAppearanceLightStatusBars(false);
    }
}
