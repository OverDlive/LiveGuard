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
import java.util.ArrayList;
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

    // 도심권 지점 정보(각각 좌표/이름 따로 표기)
    private final List<LocationData> downtownLocations = new ArrayList<>();

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

        // 도심권 지점(예시)
        initDowntownLocations();

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

                            // 2. 도심권 내 각 지점별 혼잡도 API 호출 → 마커 표시
                            loadDowntownConsolidated();

                        } else {
                            // 그 외 권역 클릭 시 Toast 메시지
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
     * 도심권(광화문, 덕수궁, 시청, 종각 등)의 위치/이름 리스트 초기화
     */
    private void initDowntownLocations() {
        downtownLocations.clear();
        downtownLocations.add(new LocationData("광화문광장", 37.572417, 126.976865));
        downtownLocations.add(new LocationData("서울광장", 37.565567, 126.978014));
        downtownLocations.add(new LocationData("보신각", 37.5697599, 126.9836604));
        downtownLocations.add(new LocationData("서울역", 37.555946, 126.972317));
        downtownLocations.add(new LocationData("경복궁", 37.579617, 126.977041));
        downtownLocations.add(new LocationData("청와대", 37.5866076, 126.974811));
        downtownLocations.add(new LocationData("동대문역", 37.571731, 127.011069));
        downtownLocations.add(new LocationData("남산공원", 37.5509895, 126.9908991));
        downtownLocations.add(new LocationData("용산역", 37.5298837, 126.9648019));
        downtownLocations.add(new LocationData("이태원역", 37.534542, 126.994596));
        downtownLocations.add(new LocationData("국립중앙박물관·용산가족공원", 37.520918, 126.978484));
        downtownLocations.add(new LocationData("충정로역", 37.560055, 126.963672));
        downtownLocations.add(new LocationData("명동 관광특구", 37.55998, 126.9858296));
        downtownLocations.add(new LocationData("이촌한강공원", 37.5169202, 126.9717022));
        // 필요하다면 더 추가
    }

    /**
     * [핵심] 도심권 내 여러 지점 각각에 대해 API 호출/마커 표시
     *       => 지점별로 별도 API 요청, 혼잡도 표시
     */
    private void loadDowntownConsolidated() {
        for (LocationData loc : downtownLocations) {
            loadAndShowCongestion(loc.name, loc.lat, loc.lng);
        }
    }

    /**
     * 특정 지점에 대해 citydata_ppltn API 호출 후, 해당 지점 마커 표시
     */
    private void loadAndShowCongestion(String subAreaName, double lat, double lng) {
        // 1. 지역명 URL 인코딩
        String encodedAreaName;
        try {
            encodedAreaName = URLEncoder.encode(subAreaName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encodedAreaName = subAreaName; // 실패 시 그냥 원본 사용
        }

        // 2. Retrofit Service
        SeoulOpenApiService service = ApiClient.getSeoulOpenApiService();

        // 3. 도시데이터 API 요청
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
                        String areaNm = cityData.getAreaNm();           // ex) "광화문"
                        String congestLvl = cityData.getAreaCongestLvl(); // ex) "여유", "보통" 등
                        Log.d(TAG, "지역명: " + areaNm + ", 혼잡도: " + congestLvl);

                        // 4. 지도에 마커 표시 (지점별 혼잡도)
                        showMarker(areaNm, congestLvl, lat, lng);
                    } else {
                        // API 본문 중 citydata_ppltn 태그가 없거나 null일 경우
                        Log.e(TAG, "SeoulRtd.citydata_ppltn 태그가 없습니다. => " + subAreaName);
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
     * 지도의 해당 좌표에 마커를 표시하는 메서드 (개별 혼잡도 적용)
     */
    private void showMarker(String areaName, String congestLvl, double lat, double lng) {
        if (naverMap == null) {
            Log.e(TAG, "네이버 맵 객체가 아직 초기화되지 않았습니다.");
            return;
        }

        Marker marker = new Marker();
        marker.setPosition(new LatLng(lat, lng));
        marker.setCaptionText(areaName + "\n혼잡도: " + congestLvl);

        // 혼잡도에 따른 마커 색상(아이콘) 구분 (리소스 drawable 직접 준비)
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

        marker.setMap(naverMap);

        // 마커 클릭 시 Toast 등 처리
        marker.setOnClickListener(overlay -> {
            Toast.makeText(getContext(),
                    areaName + " : " + congestLvl,
                    Toast.LENGTH_SHORT
            ).show();
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

    /**
     * 지역 이름/좌표를 묶어서 편리하게 관리하기 위한 간단한 데이터 클래스
     */
    private static class LocationData {
        String name;
        double lat;
        double lng;

        LocationData(String name, double lat, double lng) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
        }
    }
}