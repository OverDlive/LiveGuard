package com.example.liveguard_app_010.ui.LicenseInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.method.LinkMovementMethod;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Color;
import android.view.View;

import com.example.liveguard_app_010.R;

public class LicenseInfoFragment extends Fragment {

    public LicenseInfoFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_license_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Enable clickable links in license TextViews
        TextView tvNaverMap = view.findViewById(R.id.tvLicenseNaverMap);
        TextView tvNaverLogin = view.findViewById(R.id.tvLicenseNaverLogin);
        TextView tvSeoulOpenApi = view.findViewById(R.id.tvLicenseSeoulOpenApi);
        // Set link movement method
        LinkMovementMethod linkMethod = (LinkMovementMethod) LinkMovementMethod.getInstance();
        tvNaverMap.setMovementMethod(linkMethod);
        tvNaverLogin.setMovementMethod(linkMethod);
        tvSeoulOpenApi.setMovementMethod(linkMethod);

        // Invert status bar icon color to dark on light background
        // Ensure status bar background is white and icons are dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = requireActivity().getWindow();
            // Remove translucent flag if set
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Allow drawing system bar backgrounds
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // Set status bar background color to white
            window.setStatusBarColor(Color.WHITE);
            // Set dark icons
            View decor = window.getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
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
