package com.example.liveguard_app_010.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class OnboardingManager {
    private static final String PREF_NAME = "app_preferences";
    private static final String KEY_ONBOARDING_SHOWN = "onboarding_shown";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public OnboardingManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean isOnboardingShown() {
        return sharedPreferences.getBoolean(KEY_ONBOARDING_SHOWN, false);
    }

    public void setOnboardingShown() {
        editor.putBoolean(KEY_ONBOARDING_SHOWN, true);
        editor.apply();
    }
}