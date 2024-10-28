// com/example/liveguard_app_010/ui/settings/SettingsFragment.java
package com.example.liveguard_app_010.ui.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.example.liveguard_app_010.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey); // Settings UI
    }
}