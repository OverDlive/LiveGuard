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
import com.example.liveguard_app_010.region.RegionManager;
import com.example.liveguard_app_010.region.RegionManager.RegionType;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private BottomSheetBehavior<View> bottomSheetBehavior;
    private static final String TAG = "HomeFragment";

    // build.gradle에서 선언한 서울시 인증키
    private static final String SEOUL_APP_KEY = BuildConfig.SEOUL_APP_KEY;

    private NaverMap naverMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // --- BottomSheet 초기화 ---
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

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
        // ----------------------------

        // --- 지도 Fragment 설정 ---
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.map_fragment, mapFragment)
                    .commit();
        }

        // onMapReady에서 지도 객체 획득
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull NaverMap map) {
                naverMap = map;

                // 지도 클릭 시 BottomSheet를 접기
                naverMap.setOnMapClickListener((pointF, latLng) -> {
                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                });

                // 서울시 5대 권역 데이터를 가져와서 마커로 추가
                List<RegionManager.RegionInfo> regionInfos = RegionManager.getSeoulRegions();
                for (RegionManager.RegionInfo info : regionInfos) {
                    Marker marker = new Marker();
                    marker.setPosition(new LatLng(info.lat, info.lng));
                    marker.setCaptionText(info.regionName);
                    marker.setMap(naverMap);

                    // 마커 초기 스케일/알파 등 애니메이션을 원하는 경우

                    marker.setAlpha(1f);

                    // 마커 클릭 리스너 설정
                    marker.setOnClickListener(overlay -> {
                        if (info.type == RegionType.CITY_CENTER) {
                            // 1. 카메라 이동 (확대) 애니메이션 포함
                            LatLng startPosition = naverMap.getCameraPosition().target;
                            LatLng endPosition = marker.getPosition();
                            float startZoom = (float) naverMap.getCameraPosition().zoom;
                            float endZoom = 13f; // 원하는 줌 레벨
                            long duration = 1000; // 1초

                            CameraAnimationHelper.animateCamera(
                                    naverMap,
                                    startPosition,
                                    endPosition,
                                    startZoom,
                                    endZoom,
                                    duration
                            );
                            Log.d(TAG, "도심권 클릭: 카메라 이동 및 확대");

                            // 2. 혼잡도 API 호출
                            loadCongestionData("광화문·덕수궁");
                        } else {
                            // 그 외 권역 클릭 시 Toast 메시지 표시
                            Toast.makeText(getContext(),
                                    info.regionName + " 클릭됨",
                                    Toast.LENGTH_SHORT
                            ).show();
                            Log.d(TAG, info.regionName + " 클릭됨");
                        }
                        return true; // 이벤트 소비
                    });
                }

                // 초기 지도 위치 설정 (서울시청 근방)
                CameraUpdate initialUpdate = CameraUpdate.scrollAndZoomTo(
                        new LatLng(37.5666102, 126.9783881), 10
                );
                naverMap.moveCamera(initialUpdate);
                Log.d(TAG, "초기 지도 위치 설정");
            }
        });

        return view;
    }

    /**
     * 서울시 citydata_ppltn API를 통해 특정 지역의 혼잡도 데이터 요청
     */
    public void loadCongestionData(String areaName) {
        // 지역명 URL 인코딩
        String encodedAreaName;
        try {
            encodedAreaName = URLEncoder.encode(areaName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encodedAreaName = "광화문"; // 실패 시 기본값
        }

        // Retrofit Service
        SeoulOpenApiService service = ApiClient.getSeoulOpenApiService();

        // 도시데이터 API 요청
        Call<CongestionResponse> call = service.getRealTimeCongestion(
                SEOUL_APP_KEY,
                encodedAreaName
        );

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
     * 지도에 혼잡도 마커를 표시하는 메서드
     */
    private void showMarkers(String areaName, String congestLvl) {
        if (naverMap == null) {
            Log.e(TAG, "네이버 맵 객체가 아직 초기화되지 않았습니다.");
            return;
        }

        // 혼잡도에 따라 마커의 위치를 설정 (실제 좌표는 필요에 따라 조정)
        double lat, lon;
        if (areaName.contains("광화문")) {
            lat = 37.575957;
            lon = 126.977555;
        } else if (areaName.contains("덕수궁")) {
            lat = 37.565804;
            lon = 126.975148;
        } else {
            lat = 37.5666102;
            lon = 126.9783881;
        }

        Marker marker = new Marker();
        marker.setPosition(new LatLng(lat, lon));

        // 혼잡도에 따른 마커 아이콘 구분 (리소스 존재해야 함)
        switch (congestLvl) {
            case "여유":
                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_green));
                break;
            case "보통":
                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_yellow));
                break;
            case "약간 붐빔":
                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_orange));
                break;
            case "붐빔":
                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_red));
                break;
            default:
                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_gray));
                break;
        }

        marker.setCaptionText(areaName + "\n혼잡도: " + congestLvl);
        marker.setMap(naverMap);

        // 마커 클릭 시 Toast
        marker.setOnClickListener(overlay -> {
            Toast.makeText(getContext(), areaName + " : " + congestLvl, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    /**
     * 카메라 애니메이션 헬퍼 (커스텀)
     * 실제 구현은 필요에 따라 수정/보강 가능
     */
    private static class CameraAnimationHelper {
        public static void animateCamera(
                @NonNull NaverMap naverMap,
                @NonNull LatLng startPosition,
                @NonNull LatLng endPosition,
                float startZoom,
                float endZoom,
                long duration
        ) {
            // 한 번에 스크롤/줌을 동시에 애니메이션하기 위해
            // CameraUpdate.scrollAndZoomTo() + .animate(...) 사용
            CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(endPosition, endZoom)
                    .animate(CameraAnimation.Easing, (int) duration);
            naverMap.moveCamera(cameraUpdate);
        }
    }
}