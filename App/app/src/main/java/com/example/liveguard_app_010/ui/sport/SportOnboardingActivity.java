package com.example.liveguard_app_010.ui.sport;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.result.TourResultActivity;

import java.util.Arrays;
import java.util.List;

public class SportOnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button analyzeButton;
    private boolean[] selectionCompleted = new boolean[]{false, false, false, false};
    private List<Integer> sportPages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_onboarding);

        viewPager = findViewById(R.id.viewPager);
        analyzeButton = findViewById(R.id.btn_analyze);

        sportPages = Arrays.asList(
                R.layout.sport_intro_page,
                R.layout.sport_choice_page_1,
                R.layout.sport_choice_page_2,
                R.layout.sport_choice_page_3,
                R.layout.sport_choice_final_page
        );

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        SportOnboardingAdapter adapter = new SportOnboardingAdapter(this, sportPages, new SportOnboardingAdapter.ChoiceListener() {
            @Override
            public void onChoiceSelected(int pageIndex) {
                handleChoiceSelected(pageIndex);
            }
        });
        viewPager.setAdapter(adapter);

        viewPager.setUserInputEnabled(false);

        analyzeButton.setOnClickListener(v -> {
            if (allSelectionsCompleted()) {
                moveToResult();
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 4) {
                    updateAnalyzeButton();
                }
            }
        });
    }

    private void handleChoiceSelected(int pageIndex) {
        if (pageIndex >= 1 && pageIndex <= 4) {
            selectionCompleted[pageIndex - 1] = true;
            viewPager.setCurrentItem(pageIndex + 1);

            if (pageIndex == 4) {
                updateAnalyzeButton();
            }
        }
    }

    public void moveToNextPage() {
        viewPager.setCurrentItem(1);
    }

    private boolean allSelectionsCompleted() {
        for (boolean completed : selectionCompleted) {
            if (!completed) return false;
        }
        return true;
    }

    private void updateAnalyzeButton() {
        if (allSelectionsCompleted()) {
            analyzeButton.setEnabled(true);
            analyzeButton.setAlpha(1f);
        } else {
            analyzeButton.setEnabled(false);
            analyzeButton.setAlpha(0.5f);
        }
    }

    private void moveToResult() {
        Intent intent = new Intent(SportOnboardingActivity.this, TourResultActivity.class);
        startActivity(intent);
        finish();
    }
}
