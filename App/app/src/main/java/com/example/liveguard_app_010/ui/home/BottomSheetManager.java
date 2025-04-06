package com.example.liveguard_app_010.ui.home;

import android.content.Context;
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
 * 바텀시트의 상태와 데이터를 관리하는 클래스
 * 지역별 상세 정보 업데이트와 서울 전체 평균 정보 표시를 담당합니다.
 */
public class BottomSheetManager {
    private final Context context;
    private final View bottomSheetView;
    private final BottomSheetBehavior<View> bottomSheetBehavior;

    // 바텀시트의 UI 요소들
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

    // 현재 정보가 표시되고 있는 지역명
    private String currentAreaName = "서울 전체";

    /**
     * 바텀시트 관리자 생성자
     * @param activity 현재 액티비티 (Context를 얻기 위함)
     * @param bottomSheetView 바텀시트 뷰
     * @param behavior 바텀시트의 BottomSheetBehavior
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

        // 연령별 분포 텍스트뷰
        tvPercent10s = bottomSheetView.findViewById(R.id.tv_percent_10s);
        tvPercent20s = bottomSheetView.findViewById(R.id.tv_percent_20s);
        tvPercent30s = bottomSheetView.findViewById(R.id.tv_percent_30s);
        tvPercent40s = bottomSheetView.findViewById(R.id.tv_percent_40s);
        tvPercent50plus = bottomSheetView.findViewById(R.id.tv_percent_50plus);

        // 연령별 분포 프로그레스 바
        progress10s = bottomSheetView.findViewById(R.id.progress_10s);
        progress20s = bottomSheetView.findViewById(R.id.progress_20s);
        progress30s = bottomSheetView.findViewById(R.id.progress_30s);
        progress40s = bottomSheetView.findViewById(R.id.progress_40s);
        progress50plus = bottomSheetView.findViewById(R.id.progress_50plus);

        // 초기 데이터 로드 (서울 전체)
        loadSeoulAverage();
    }

    /**
     * 서울 전체 평균 데이터 로드
     * 네이버의 경우 "광화문"을 로드하고 서울 전체로 표시
     */
    public void loadSeoulAverage() {
        currentAreaName = "서울 전체";
        tvRegionName.setText(currentAreaName);
        loadCongestionData("광화문"); // 일반적으로 광화문은 대표적인 지역으로 사용됨
    }

    /**
     * 특정 지역의 혼잡도 데이터 로드
     * @param areaName 지역 이름 (ex: "광화문", "강남역")
     */
    public void loadAreaData(String areaName) {
        currentAreaName = areaName;
        tvRegionName.setText(currentAreaName);
        loadCongestionData(areaName);
    }

    /**
     * 서울시 혼잡도 API를 호출하여 데이터 로드
     * @param areaName API 호출할 지역명
     */
    private void loadCongestionData(String areaName) {
        // 바텀시트를 반만 펼침
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

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
                    // 응답 실패 처리: 기본 값이나 에러 메시지 표시
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
     * 응답 데이터로 바텀시트 UI 업데이트
     * @param response API 응답 데이터
     */
    private void updateBottomSheetWithResponse(CongestionResponse response) {
        CongestionResponse.CityDataPpltn cityData = response.getCitydataPpltn();
        if (cityData == null) {
            showErrorState();
            return;
        }

        // 혼잡도 상태 업데이트
        String congestLvl = cityData.getAreaCongestLvl();
        tvCongestionValue.setText(congestLvl);

        // 혼잡도에 따라 텍스트 색상 변경
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

        // 업데이트 시간
        String ppltnTime = cityData.getPpltnTime();
        if (ppltnTime != null && !ppltnTime.isEmpty()) {
            try {
                // API에서 받은 시간 형식을 파싱하여 간단한 형식으로 변환
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                Date date = inputFormat.parse(ppltnTime);
                if (date != null) {
                    tvUpdateTime.setText(outputFormat.format(date));
                } else {
                    tvUpdateTime.setText(ppltnTime); // 파싱 실패 시 원본 표시
                }
            } catch (Exception e) {
                e.printStackTrace();
                tvUpdateTime.setText(ppltnTime); // 예외 발생 시 원본 표시
            }
        } else {
            tvUpdateTime.setText("--:--");
        }

        // 기온 정보 (서울시 API에서 제공하면 업데이트)
        String temperature = ""; // 실제 데이터가 있으면 업데이트
        if (temperature != null && !temperature.isEmpty()) {
            tvTemperature.setText(String.format(Locale.getDefault(), "%s°C", temperature));
        } else {
            tvTemperature.setText("--°C");
        }

        // 연령별 분포 업데이트
        updateAgeDistribution(cityData);
    }

    /**
     * 연령별 분포 차트 업데이트
     * @param cityData API 응답의 도시 데이터
     */
    private void updateAgeDistribution(CongestionResponse.CityDataPpltn cityData) {
        // API로부터 받은 연령별 비율
        double rate10s = cityData.getPpltnRate10();
        double rate20s = cityData.getPpltnRate20();
        double rate30s = cityData.getPpltnRate30();
        double rate40s = cityData.getPpltnRate40();
        // 50대 이상은 50, 60, 70대 합산
        double rate50plus = cityData.getPpltnRate50() + cityData.getPpltnRate60() + cityData.getPpltnRate70();

        // 텍스트 업데이트
        tvPercent10s.setText(String.format(Locale.getDefault(), "%.1f%%", rate10s));
        tvPercent20s.setText(String.format(Locale.getDefault(), "%.1f%%", rate20s));
        tvPercent30s.setText(String.format(Locale.getDefault(), "%.1f%%", rate30s));
        tvPercent40s.setText(String.format(Locale.getDefault(), "%.1f%%", rate40s));
        tvPercent50plus.setText(String.format(Locale.getDefault(), "%.1f%%", rate50plus));

        // 프로그레스 바 너비 업데이트
        ViewGroup.LayoutParams params10s = progress10s.getLayoutParams();
        ViewGroup.LayoutParams params20s = progress20s.getLayoutParams();
        ViewGroup.LayoutParams params30s = progress30s.getLayoutParams();
        ViewGroup.LayoutParams params40s = progress40s.getLayoutParams();
        ViewGroup.LayoutParams params50plus = progress50plus.getLayoutParams();

        // 프로그레스 바 컨테이너 너비 (최대 너비)
        int containerWidth = ((View) progress10s.getParent().getParent()).getWidth() - 48; // 여백 고려
        if (containerWidth <= 0) {
            containerWidth = 1000; // 기본값
        }

        // 가장 큰 비율을 찾아 상대적인 너비 계산
        double maxRate = Math.max(Math.max(Math.max(Math.max(rate10s, rate20s), rate30s), rate40s), rate50plus);

        // 각 연령대별 프로그레스 바 너비 설정
        params10s.width = (int)(containerWidth * (rate10s / 100));
        params20s.width = (int)(containerWidth * (rate20s / 100));
        params30s.width = (int)(containerWidth * (rate30s / 100));
        params40s.width = (int)(containerWidth * (rate40s / 100));
        params50plus.width = (int)(containerWidth * (rate50plus / 100));

        // 레이아웃 파라미터 적용
        progress10s.setLayoutParams(params10s);
        progress20s.setLayoutParams(params20s);
        progress30s.setLayoutParams(params30s);
        progress40s.setLayoutParams(params40s);
        progress50plus.setLayoutParams(params50plus);
    }

    /**
     * API 호출 실패 또는 데이터 없을 때 에러 상태 표시
     */
    private void showErrorState() {
        tvCongestionValue.setText("정보 없음");
        tvCongestionValue.setTextColor(context.getResources().getColor(R.color.text_secondary));
        tvPopulation.setText("0~0명");
        tvGenderRatio.setText("0% / 0%");
        tvUpdateTime.setText("--:--");
        tvTemperature.setText("--°C");

        // 연령별 분포 초기화
        tvPercent10s.setText("0%");
        tvPercent20s.setText("0%");
        tvPercent30s.setText("0%");
        tvPercent40s.setText("0%");
        tvPercent50plus.setText("0%");

        // 프로그레스 바 너비 초기화
        ViewGroup.LayoutParams params = progress10s.getLayoutParams();
        params.width = 0;
        progress10s.setLayoutParams(params);
        progress20s.setLayoutParams(params);
        progress30s.setLayoutParams(params);
        progress40s.setLayoutParams(params);
        progress50plus.setLayoutParams(params);
    }

    /**
     * 현재 바텀시트에 표시 중인 지역명 반환
     * @return 현재 지역명
     */
    public String getCurrentAreaName() {
        return currentAreaName;
    }
}