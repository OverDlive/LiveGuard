package com.example.liveguard_app_010.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.network.ApiClient;
import com.example.liveguard_app_010.network.SeoulOpenApiService;
import com.example.liveguard_app_010.network.model.CongestionResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private BottomSheetBehavior<View> bottomSheetBehavior;
    private static final String TAG = "HomeFragment";

    // build.gradle.kts에서 선언한 서울시 인증키 (예: SEOUL_APP_KEY)
    private static final String SEOUL_APP_KEY = BuildConfig.SEOUL_APP_KEY;

    private NaverMap naverMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 바텀 시트 초기화
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // 화면 높이 가져오기
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        bottomSheetBehavior.setPeekHeight(500);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.5f);
        bottomSheetBehavior.setExpandedOffset((int) (screenHeight * 0.02f));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) { }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) { }
        });

        // 지도 Fragment
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.map_fragment, mapFragment)
                    .commit();
        }

        // onMapReady
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull NaverMap map) {
                naverMap = map;
                naverMap.setOnMapClickListener((pointF, latLng) -> {
                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                });

                // 지역명 "광화문·덕수궁" 처럼 특수문자가 있을 수도 있으므로, 인코딩 후 호출
                loadCongestionData("광화문·덕수궁");
            }
        });

        return view;
    }

    /**
     * 서울시 citydata_ppltn API를 통해 특정 지역의 혼잡도 데이터 요청
     */
    public void loadCongestionData(String areaName) {
        // 지역명을 URL 인코딩
        String encodedAreaName;
        try {
            encodedAreaName = URLEncoder.encode(areaName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // 인코딩 실패 시 기본값(광화문 등)으로 대체하거나, 요청 중단
            encodedAreaName = "광화문";
        }

        // Retrofit Service
        SeoulOpenApiService service = ApiClient.getSeoulOpenApiService();

        // 도시데이터 API 요청
        Call<CongestionResponse> call = service.getRealTimeCongestion(
                SEOUL_APP_KEY,   // 인증키
                encodedAreaName  // 인코딩된 지역명
        );

        // 비동기 콜백
        call.enqueue(new Callback<CongestionResponse>() {
            @Override
            public void onResponse(Call<CongestionResponse> call, Response<CongestionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CongestionResponse data = response.body();

                    CongestionResponse.CityDataPpltn cityData = data.getCitydataPpltn();
                    if (cityData != null) {
                        String areaNm = cityData.getAreaNm();
                        String congestLvl = cityData.getAreaCongestLvl();
                        Log.d(TAG, "지역명: " + areaNm + ", 혼잡도: " + congestLvl);

                        // 지도에 마커 표시
                        showMarkers(areaNm, congestLvl);
                    } else {
                        Log.e(TAG, "SeoulRtd.citydata_ppltn 태그가 없습니다.");
                    }
                } else {
                    Log.e(TAG, "응답 실패: code=" + response.code() + ", msg=" + response.message());
                }
            }

            @Override
            public void onFailure(Call<CongestionResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "네트워크 오류: " + t.getMessage());
            }
        });
    }

    /**
     * 지도에 마커를 표시하는 메서드
     */
    private void showMarkers(String areaName, String congestLvl) {
        if (naverMap == null) {
            Log.e(TAG, "네이버 맵 객체가 아직 초기화되지 않았습니다.");
            return;
        }

        double lat, lon;
        if (areaName.contains("광화문")) {
            lat = 37.575957;
            lon = 126.977555;
        } else if (areaName.contains("덕수궁")) {
            // 임의로 덕수궁 근처 좌표
            lat = 37.565804;
            lon = 126.975148;
        } else {
            lat = 37.5666102;
            lon = 126.9783881;
        }

        Marker marker = new Marker();
        marker.setPosition(new LatLng(lat, lon));

        if ("여유".equals(congestLvl)) {
            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_green));
        } else if ("보통".equals(congestLvl)) {
            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_yellow));
        } else if ("약간 붐빔".equals(congestLvl)) {
            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_orange));
        } else if ("붐빔".equals(congestLvl)) {
            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_red));
        } else {
            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_gray));
        }

        marker.setCaptionText(areaName + "\n혼잡도: " + congestLvl);
        marker.setMap(naverMap);

        // 마커 클릭 시 Toast
        marker.setOnClickListener(overlay -> {
            Toast.makeText(getContext(), areaName + " : " + congestLvl, Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}