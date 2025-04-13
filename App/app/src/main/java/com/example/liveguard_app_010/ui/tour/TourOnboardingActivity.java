package com.example.liveguard_app_010.ui.tour;

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
import com.example.liveguard_app_010.network.TouristAttractionData;
import com.example.liveguard_app_010.network.TouristAttractionsApiCaller;
import com.example.liveguard_app_010.network.YouthTrainingFacilityApiCaller;
import com.example.liveguard_app_010.network.model.HanokExperienceResponse;
import com.example.liveguard_app_010.network.model.MuseumData;
import com.example.liveguard_app_010.network.model.CongestionResponse;
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

        TourOnboardingAdapter adapter = new TourOnboardingAdapter(this, tourPages, new TourOnboardingAdapter.ChoiceListener() {
            @Override
            public void onChoiceSelected(int pageIndex) {
                handleChoiceSelected(pageIndex);
            }
        });
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
        // 박물관/미술관 데이터 API 호출
        MuseumDataApiCaller museumApiCaller = new MuseumDataApiCaller();
        museumApiCaller.fetchMuseumData(new MuseumDataApiCaller.DataCallback() {
            @Override
            public void onSuccess(MuseumData museumData) {
                // API 호출 성공: Logcat 또는 UI 업데이트 진행
                Log.d("MuseumDataApiCaller", "API call succeeded. Response: " + museumData.toString());
            }

            @Override
            public void onFailure(Exception e) {
                // API 호출 실패: 에러 처리
                Log.e("MuseumDataApiCaller", "API call failed: " + e.getMessage());
            }
        });

        // 관광지 API 호출
        TouristAttractionsApiCaller touristAttractionsApiCaller = new TouristAttractionsApiCaller();
        touristAttractionsApiCaller.fetchTouristAttractions(new TouristAttractionsApiCaller.DataCallback() {
            @Override
            public void onSuccess(TouristAttractionData data) {
                // Log success message with data details
                Log.d("TourChoiceFinalFragment", "Tourist API call succeeded. Data: " + data.toString());
            }

            @Override
            public void onFailure(Exception e) {
                // Log error message
                Log.e("TourChoiceFinalFragment", "Tourist API call failed: " + e.getMessage());
            }
        });

        // 한옥 체험 API 호출
        HanokDataApiCaller hanokDataApiCaller = new HanokDataApiCaller();
        hanokDataApiCaller.fetchHanokData(new HanokDataApiCaller.DataCallback() {
            @Override
            public void onSuccess(HanokExperienceResponse response) {
                // API 호출 성공: Logcat 또는 UI 업데이트 진행
                Log.d("HanokDataApiCaller", "Hanok API call succeeded. Response: " + response.toString());
            }

            @Override
            public void onFailure(Exception e) {
                // API 호출 실패: 에러 처리
                Log.e("HanokDataApiCaller", "Hanok API call failed: " + e.getMessage());
            }
        });

        // 청소년 수련시설 API 호출
        YouthTrainingFacilityApiCaller youthApiCaller = new YouthTrainingFacilityApiCaller();
        youthApiCaller.fetchYouthTrainingFacilityData(new YouthTrainingFacilityApiCaller.DataCallback() {
            @Override
            public void onSuccess(YouthTrainingFacilityResponse response) {
                // API 호출 성공: Logcat 또는 UI 업데이트 진행
                Log.d("YouthFacilityApiCaller", "Youth Facility API call succeeded. Response: " + response.toString());
            }

            @Override
            public void onFailure(Exception e) {
                // API 호출 실패: 에러 처리
                Log.e("YouthFacilityApiCaller", "Youth Facility API call failed: " + e.getMessage());
            }
        });

        // 결과 화면으로 이동
        Intent intent = new Intent(TourOnboardingActivity.this, TourResultActivity.class);
        startActivity(intent);
        finish();
    }
}
