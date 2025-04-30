package com.example.liveguard_app_010.ui.tour;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.network.HanokDataApiCaller;
import com.example.liveguard_app_010.network.MuseumDataApiCaller;
import com.example.liveguard_app_010.network.PerformanceMovieApiCaller;
import com.example.liveguard_app_010.network.ShoppingDataApiCaller;
import com.example.liveguard_app_010.network.TouristAttractionData;
import com.example.liveguard_app_010.network.TouristAttractionsApiCaller;
import com.example.liveguard_app_010.network.YouthTrainingFacilityApiCaller;
import com.example.liveguard_app_010.network.model.HanokExperienceResponse;
import com.example.liveguard_app_010.network.model.MuseumData;
import com.example.liveguard_app_010.network.model.PerformanceMovieResponse;
import com.example.liveguard_app_010.network.model.ShoppingDataResponse;
import com.example.liveguard_app_010.network.model.YouthTrainingFacilityResponse;
import com.example.liveguard_app_010.ui.result.TourResultActivity;

import java.util.Arrays;
import java.util.List;

public class TourOnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button analyzeButton;
    private boolean[] selectionCompleted = new boolean[]{false, false, false, false}; // 2~5페이지용
    private List<Integer> tourPages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_onboarding);

        viewPager = findViewById(R.id.viewPager);
        analyzeButton = findViewById(R.id.btn_analyze);

        tourPages = Arrays.asList(
                R.layout.tour_intro_page,
                R.layout.tour_choice_page_1,
                R.layout.tour_choice_page_2,
                R.layout.tour_choice_page_3,
                R.layout.tour_choice_final_page
        );

        // Define button IDs per page (pages 1–4)
        List<List<Integer>> buttonIdLists = Arrays.asList(
            Arrays.asList(R.id.btn_choice_1, R.id.btn_choice_2, R.id.btn_choice_3, R.id.btn_choice_4),
            Arrays.asList(R.id.btn_choice_1, R.id.btn_choice_2, R.id.btn_choice_3, R.id.btn_choice_4),
            Arrays.asList(R.id.btn_choice_1, R.id.btn_choice_2, R.id.btn_choice_3, R.id.btn_choice_4),
            Arrays.asList(R.id.btn_choice_1, R.id.btn_choice_2, R.id.btn_choice_3, R.id.btn_choice_4)
        );
        // Define labels per page (replace with actual labels)
        List<List<String>> buttonLabelLists = Arrays.asList(
            Arrays.asList("아침","점심","저녁","야간"),
            Arrays.asList("친구","가족","혼자","커플"),
            Arrays.asList("편안함","모험","휴식","체험"),
            Arrays.asList("슬리퍼", "30분", "1시간", "상관없어")
        );

        TourOnboardingAdapter adapter = new TourOnboardingAdapter(
            this,
            tourPages,
            buttonIdLists,
            buttonLabelLists,
            new TourOnboardingAdapter.ChoiceListener() {
                @Override
                public void onChoiceSelected(int pageIndex, String choiceValue) {
                    // Now receives the actual button text as choiceValue
                    Log.d("TourOnboarding", "Page " + pageIndex + " selected: " + choiceValue);
                    handleChoiceSelected(pageIndex);
                }
            }
        );
        viewPager.setAdapter(adapter);

        viewPager.setUserInputEnabled(false); // 스와이프 막기

        analyzeButton.setOnClickListener(v -> {
            if (allSelectionsCompleted()) {
                moveToResult();
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 4) { // 마지막 페이지
                    updateAnalyzeButton();
                }
            }
        });
    }

    private void handleChoiceSelected(int pageIndex) {
        if (pageIndex >= 1 && pageIndex <= 4) {
            selectionCompleted[pageIndex - 1] = true;

            viewPager.setCurrentItem(pageIndex + 1); // 다음 페이지로 이동

            if (pageIndex == 4) {
                updateAnalyzeButton();
            }
        }
    }

    public void moveToNextPage() {
        viewPager.setCurrentItem(1); // Intro 다음 2페이지로 이동
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
        // 쇼핑몰(쇼핑 정보) API 호출
        ShoppingDataApiCaller shoppingApiCaller = new ShoppingDataApiCaller();
        shoppingApiCaller.fetchShoppingData(new ShoppingDataApiCaller.DataCallback() {
            @Override
            public void onSuccess(ShoppingDataResponse response) {
                // API 호출 성공: Logcat 또는 UI 업데이트 진행
                Log.d("ShoppingDataApiCaller", "Shopping API call succeeded. Response: " + response.toString());
            }

            @Override
            public void onFailure(Exception e) {
                // API 호출 실패: 에러 처리
                Log.e("ShoppingDataApiCaller", "Shopping API call failed: " + e.getMessage());
            }
        });

        // 공연/영화 API 호출 - 지역별로 for문을 사용하여 호출
        String[] regionCodes = {"GN", "GD", "GB", "GS", "GA", "JR", "JG", "YS", "SD", "GJ", "DD", "NR", "SB", "DB", "NW", "EP", "SDM", "MP", "YC", "GR", "GC", "YD", "SC", "SP"};
        for (String code : regionCodes) {
            PerformanceMovieApiCaller performanceMovieApiCaller = new PerformanceMovieApiCaller();
            performanceMovieApiCaller.fetchPerformanceMovieData(code, new PerformanceMovieApiCaller.DataCallback() {
                @Override
                public void onSuccess(List<PerformanceMovieResponse> responses) {
                    Log.d("PerformanceMovieApiCaller", "Performance/Movie API call succeeded for region " + code + ": " + responses.toString());
                }

                @Override
                public void onFailure(Exception e) {
                    Log.e("PerformanceMovieApiCaller", "Performance/Movie API call failed for region " + code + ": " + e.getMessage());
                }
            });
        }

        // 결과 화면으로 이동
        Intent intent = new Intent(TourOnboardingActivity.this, TourResultActivity.class);
        startActivity(intent);
        finish();
    }
}