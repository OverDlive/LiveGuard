package com.example.liveguard_app_010.ui.settings;

import android.os.Bundle;
import android.view.View; // 추가
import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;
import com.example.liveguard_app_010.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey); // Settings UI
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Hide the top navigation view when the SettingsFragment is displayed
        if (getActivity() != null) {
            getActivity().findViewById(R.id.fragment_top_navigation).setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Show the top navigation view when leaving Settings
        if (getActivity() != null) {
            getActivity().findViewById(R.id.fragment_top_navigation).setVisibility(View.VISIBLE);
        }
    }
}
