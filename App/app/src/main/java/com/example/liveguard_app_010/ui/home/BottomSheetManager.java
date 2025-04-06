package com.example.liveguard_app_010.ui.home;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.network.ApiClient;
import com.example.liveguard_app_010.network.SeoulOpenApiService;
import com.example.liveguard_app_010.network.model.CongestionResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 바텀시트의 상태와 데이터를 관리하는 클래스.
 * 지역별 상세 정보 업데이트와 서울 전체 평균 정보 표시를 담당합니다.
 */
public class BottomSheetManager {

    private final Context context;
    private final View bottomSheetView;
    private final BottomSheetBehavior<View> bottomSheetBehavior;

    // UI 요소들
    private final TextView tvRegionName;
    private final TextView tvCongestionValue;
    private final TextView tvPopulation;
    private final TextView tvGenderRatio;
    private final TextView tvUpdateTime;
    private final TextView tvTemperature;
    private final TextView tvPercent10s;
    private final TextView tvPercent20s;
    private final TextView tvPercent30s;
    private final TextView tvPercent40s;
    private final TextView tvPercent50plus;
    private final View progress10s;
    private final View progress20s;
    private final View progress30s;
    private final View progress40s;
    private final View progress50plus;

    private String currentAreaName = "서울 전체";

    /**
     * 생성자
     * @param activity 현재 액티비티 (Context 획득용)
     * @param bottomSheetView 바텀시트 뷰
     * @param behavior 바텀시트 동작 제어용 BottomSheetBehavior
     */
    public BottomSheetManager(FragmentActivity activity, View bottomSheetView, BottomSheetBehavior<View> behavior) {
        this.context = activity;
        this.bottomSheetView = bottomSheetView;
        this.bottomSheetBehavior = behavior;

        // UI 요소 초기화
        tvRegionName = bottomSheetView.findViewById(R.id.tv_region_name);
        tvCongestionValue = bottomSheetView.findViewById(R.id.tv_congestion_value);
        tvPopulation = bottomSheetView.findViewById(R.id.tv_population);
        tvGenderRatio = bottomSheetView.findViewById(R.id.tv_gender_ratio);
        tvUpdateTime = bottomSheetView.findViewById(R.id.tv_update_time);
        tvTemperature = bottomSheetView.findViewById(R.id.tv_temperature);
        tvPercent10s = bottomSheetView.findViewById(R.id.tv_percent_10s);
        tvPercent20s = bottomSheetView.findViewById(R.id.tv_percent_20s);
        tvPercent30s = bottomSheetView.findViewById(R.id.tv_percent_30s);
        tvPercent40s = bottomSheetView.findViewById(R.id.tv_percent_40s);
        tvPercent50plus = bottomSheetView.findViewById(R.id.tv_percent_50plus);
        progress10s = bottomSheetView.findViewById(R.id.progress_10s);
        progress20s = bottomSheetView.findViewById(R.id.progress_20s);
        progress30s = bottomSheetView.findViewById(R.id.progress_30s);
        progress40s = bottomSheetView.findViewById(R.id.progress_40s);
        progress50plus = bottomSheetView.findViewById(R.id.progress_50plus);

        // 초기 데이터 로드 (서울 전체)
        loadSeoulAverage();
    }

    /**
     * 서울 전체 평균 데이터를 로드합니다.
     */
    public void loadSeoulAverage() {
        currentAreaName = "서울 전체";
        tvRegionName.setText(currentAreaName);
        loadCongestionData("광화문");
    }

    /**
     * 특정 지역의 데이터를 로드합니다.
     * @param areaName 지역 이름 (예: "광화문", "강남역")
     */
    public void loadAreaData(String areaName) {
        currentAreaName = areaName;
        tvRegionName.setText(currentAreaName);
        loadCongestionData(areaName);
    }

    /**
     * 서울시 혼잡도 API를 호출하여 데이터를 로드합니다.
     * @param areaName 호출할 지역 이름
     */
    private void loadCongestionData(String areaName) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        String encodedAreaName;
        try {
            encodedAreaName = URLEncoder.encode(areaName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encodedAreaName = areaName;
        }

        SeoulOpenApiService service = ApiClient.getSeoulOpenApiService();
        Call<CongestionResponse> call = service.getRealTimeCongestion(BuildConfig.SEOUL_APP_KEY, encodedAreaName);
        call.enqueue(new Callback<CongestionResponse>() {
            @Override
            public void onResponse(Call<CongestionResponse> call, Response<CongestionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateBottomSheetWithResponse(response.body());
                } else {
                    showErrorState();
                }
            }

            @Override
            public void onFailure(Call<CongestionResponse> call, Throwable t) {
                t.printStackTrace();
                showErrorState();
            }
        });
    }

    /**
     * API 응답 데이터를 기반으로 바텀시트 UI를 업데이트합니다.
     * @param response API 응답 데이터
     */
    private void updateBottomSheetWithResponse(CongestionResponse response) {
        CongestionResponse.CityDataPpltn cityData = response.getCitydataPpltn();
        if (cityData == null) {
            showErrorState();
            return;
        }

        // 혼잡도 업데이트
        String congestLvl = cityData.getAreaCongestLvl();
        tvCongestionValue.setText(congestLvl);
        int colorResId;
        switch (congestLvl) {
            case "여유":
                colorResId = R.color.blue;
                break;
            case "보통":
                colorResId = android.R.color.holo_orange_light;
                break;
            case "약간 붐빔":
                colorResId = android.R.color.holo_orange_dark;
                break;
            case "붐빔":
                colorResId = android.R.color.holo_red_light;
                break;
            default:
                colorResId = R.color.text_primary;
                break;
        }
        tvCongestionValue.setTextColor(context.getResources().getColor(colorResId));

        // 인구 범위 업데이트
        int minPop = cityData.getAreaPpltnMin();
        int maxPop = cityData.getAreaPpltnMax();
        tvPopulation.setText(String.format(Locale.getDefault(), "%d~%d명", minPop, maxPop));

        // 성별 비율 업데이트
        double maleRatio = cityData.getMalePpltnRate();
        double femaleRatio = cityData.getFemalePpltnRate();
        tvGenderRatio.setText(String.format(Locale.getDefault(), "%.1f%% / %.1f%%", maleRatio, femaleRatio));

        // 업데이트 시간 변환 및 업데이트
        String ppltnTime = cityData.getPpltnTime();
        if (ppltnTime != null && !ppltnTime.isEmpty()) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                Date date = inputFormat.parse(ppltnTime);
                tvUpdateTime.setText(date != null ? outputFormat.format(date) : ppltnTime);
            } catch (Exception e) {
                e.printStackTrace();
                tvUpdateTime.setText(ppltnTime);
            }
        } else {
            tvUpdateTime.setText("--:--");
        }

        // 기온 정보 업데이트 (데이터가 있을 경우)
        String temperature = "";
        tvTemperature.setText((temperature != null && !temperature.isEmpty())
                ? String.format(Locale.getDefault(), "%s°C", temperature)
                : "--°C");

        updateAgeDistribution(cityData);
    }

    /**
     * 연령별 분포 차트를 업데이트합니다.
     * @param cityData API 응답의 도시 데이터
     */
    private void updateAgeDistribution(CongestionResponse.CityDataPpltn cityData) {
        double rate10s = cityData.getPpltnRate10();
        double rate20s = cityData.getPpltnRate20();
        double rate30s = cityData.getPpltnRate30();
        double rate40s = cityData.getPpltnRate40();
        double rate50plus = cityData.getPpltnRate50() + cityData.getPpltnRate60() + cityData.getPpltnRate70();

        // 텍스트 업데이트
        tvPercent10s.setText(String.format(Locale.getDefault(), "%.1f%%", rate10s));
        tvPercent20s.setText(String.format(Locale.getDefault(), "%.1f%%", rate20s));
        tvPercent30s.setText(String.format(Locale.getDefault(), "%.1f%%", rate30s));
        tvPercent40s.setText(String.format(Locale.getDefault(), "%.1f%%", rate40s));
        tvPercent50plus.setText(String.format(Locale.getDefault(), "%.1f%%", rate50plus));

        // 프로그레스 바 업데이트
        int containerWidth = ((View) progress10s.getParent().getParent()).getWidth() - 48;
        if (containerWidth <= 0) {
            containerWidth = 1000;
        }

        ViewGroup.LayoutParams params10s = progress10s.getLayoutParams();
        ViewGroup.LayoutParams params20s = progress20s.getLayoutParams();
        ViewGroup.LayoutParams params30s = progress30s.getLayoutParams();
        ViewGroup.LayoutParams params40s = progress40s.getLayoutParams();
        ViewGroup.LayoutParams params50plus = progress50plus.getLayoutParams();

        params10s.width = (int) (containerWidth * (rate10s / 100));
        params20s.width = (int) (containerWidth * (rate20s / 100));
        params30s.width = (int) (containerWidth * (rate30s / 100));
        params40s.width = (int) (containerWidth * (rate40s / 100));
        params50plus.width = (int) (containerWidth * (rate50plus / 100));

        progress10s.setLayoutParams(params10s);
        progress20s.setLayoutParams(params20s);
        progress30s.setLayoutParams(params30s);
        progress40s.setLayoutParams(params40s);
        progress50plus.setLayoutParams(params50plus);
    }

    /**
     * 에러 상태를 표시합니다.
     */
    private void showErrorState() {
        tvCongestionValue.setText("정보 없음");
        tvCongestionValue.setTextColor(context.getResources().getColor(R.color.text_secondary));
        tvPopulation.setText("0~0명");
        tvGenderRatio.setText("0% / 0%");
        tvUpdateTime.setText("--:--");
        tvTemperature.setText("--°C");

        tvPercent10s.setText("0%");
        tvPercent20s.setText("0%");
        tvPercent30s.setText("0%");
        tvPercent40s.setText("0%");
        tvPercent50plus.setText("0%");

        ViewGroup.LayoutParams params = progress10s.getLayoutParams();
        params.width = 0;
        progress10s.setLayoutParams(params);
        progress20s.setLayoutParams(params);
        progress30s.setLayoutParams(params);
        progress40s.setLayoutParams(params);
        progress50plus.setLayoutParams(params);
    }
}