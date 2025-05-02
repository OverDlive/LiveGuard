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
import com.example.liveguard_app_010.models.PlaceInfo;

import java.util.Arrays;
import java.util.List;

import com.example.liveguard_app_010.utils.RecommendationEngine;
import android.location.Location;
import com.example.liveguard_app_010.ui.result.TourResultActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import android.content.Context;
import android.location.LocationManager;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

public class TourOnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button analyzeButton;
    private boolean[] selectionCompleted = new boolean[]{false, false, false, false}; // 2~5페이지용
    private List<Integer> tourPages;

    // 선택된 값을 저장할 필드
    private RecommendationEngine.TimeOfDay selectedTimeOfDay;
    private RecommendationEngine.CompanionType selectedCompanionType;
    private RecommendationEngine.Mood selectedMood;
    private RecommendationEngine.TravelOption selectedTravelOption;

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
                    handleChoiceSelected(pageIndex, choiceValue);
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

    private void handleChoiceSelected(int pageIndex, String choiceValue) {
        if (pageIndex >= 1 && pageIndex <= 4) {
            selectionCompleted[pageIndex - 1] = true;

            // 페이지별 선택값 매핑
            switch (pageIndex) {
                case 1:
                    if ("아침".equals(choiceValue)) selectedTimeOfDay = RecommendationEngine.TimeOfDay.MORNING;
                    else if ("점심".equals(choiceValue)) selectedTimeOfDay = RecommendationEngine.TimeOfDay.LUNCH;
                    else if ("저녁".equals(choiceValue)) selectedTimeOfDay = RecommendationEngine.TimeOfDay.AFTERNOON;
                    else if ("야간".equals(choiceValue)) selectedTimeOfDay = RecommendationEngine.TimeOfDay.NIGHT;
                    break;
                case 2:
                    if ("친구".equals(choiceValue)) selectedCompanionType = RecommendationEngine.CompanionType.FRIENDS;
                    else if ("가족".equals(choiceValue)) selectedCompanionType = RecommendationEngine.CompanionType.FAMILY;
                    else if ("혼자".equals(choiceValue)) selectedCompanionType = RecommendationEngine.CompanionType.ALONE;
                    else if ("커플".equals(choiceValue)) selectedCompanionType = RecommendationEngine.CompanionType.COUPLE;
                    break;
                case 3:
                    if ("편안함".equals(choiceValue)) selectedMood = RecommendationEngine.Mood.HEALING;
                    else if ("모험".equals(choiceValue)) selectedMood = RecommendationEngine.Mood.POWERFUL;
                    else if ("휴식".equals(choiceValue)) selectedMood = RecommendationEngine.Mood.EMOTIONAL;
                    else if ("체험".equals(choiceValue)) selectedMood = RecommendationEngine.Mood.HOTPLACE;
                    break;
                case 4:
                    if ("슬리퍼".equals(choiceValue)) selectedTravelOption = RecommendationEngine.TravelOption.SLIPPER;
                    else if ("30분".equals(choiceValue)) selectedTravelOption = RecommendationEngine.TravelOption.MIN_30;
                    else if ("1시간".equals(choiceValue)) selectedTravelOption = RecommendationEngine.TravelOption.MIN_60;
                    else if ("상관없어".equals(choiceValue)) selectedTravelOption = RecommendationEngine.TravelOption.NO_PREF;
                    break;
            }

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
        // 1) 사용자 선택 값과 위치를 준비
        RecommendationEngine.TimeOfDay time = getSelectedTimeOfDay();
        RecommendationEngine.CompanionType companion = getSelectedCompanionType();
        RecommendationEngine.Mood mood = getSelectedMood();
        RecommendationEngine.TravelOption travelOption = getSelectedTravelOption();
        Location userLocation = getUserLocation();

        // 2) 백그라운드 스레드에서 추천 알고리즘 실행
        Executors.newSingleThreadExecutor().execute(() -> {
            RecommendationEngine engine = new RecommendationEngine(this);
            List<PlaceInfo> recommendedPlaces =
                engine.recommend(time, companion, mood, travelOption, userLocation);

            // 3) UI 스레드에서 결과 화면으로 이동하며 추천 결과 전달
            runOnUiThread(() -> {
                Intent intent = new Intent(TourOnboardingActivity.this, TourResultActivity.class);
                intent.putExtra("place_list", new ArrayList<>(recommendedPlaces));
                startActivity(intent);
                finish();
            });
        });
    }

    private RecommendationEngine.TimeOfDay getSelectedTimeOfDay() {
        return selectedTimeOfDay;
    }

    private RecommendationEngine.CompanionType getSelectedCompanionType() {
        return selectedCompanionType;
    }

    private RecommendationEngine.Mood getSelectedMood() {
        return selectedMood;
    }

    private RecommendationEngine.TravelOption getSelectedTravelOption() {
        return selectedTravelOption;
    }

    private Location getUserLocation() {
        // 권한 체크 및 위치 매니저로 최근 위치 반환
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 요청 로직
            return new Location("default");
        }
        Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) {
            loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return loc != null ? loc : new Location("default");
    }
}