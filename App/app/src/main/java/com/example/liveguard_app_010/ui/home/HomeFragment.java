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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private BottomSheetBehavior<View> bottomSheetBehavior;
    private static final String TAG = "HomeFragment";

    // build.gradle에서 선언한 서울시 인증키
    private static final String SEOUL_APP_KEY = BuildConfig.SEOUL_APP_KEY;

    private NaverMap naverMap;

    // 서울시 각 권역별 지점 목록
    // key: RegionType (또는 권역 이름), value: 해당 권역 내부의 위치 리스트
    private final Map<RegionType, List<LocationData>> regionLocationMap = new HashMap<>();

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

        // 권역별 위치 데이터 초기화
        initRegionLocations();

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
                        // 특정 권역 클릭 시
                        switch (info.type) {
                            case CITY_CENTER:
                                // 도심권
                                animateToMarker(marker, 13f);
                                // 도심권 지점 혼잡도 표시
                                loadRegionMarkers(RegionType.CITY_CENTER);
                                break;
                            case NORTH_WEST:
                                animateToMarker(marker, 13f);
                                loadRegionMarkers(RegionType.NORTH_WEST);
                                break;
                            case SOUTH_EAST:
                                animateToMarker(marker, 13f);
                                loadRegionMarkers(RegionType.SOUTH_EAST);
                                break;
                            case NORTH_EAST:
                                animateToMarker(marker, 13f);
                                loadRegionMarkers(RegionType.NORTH_EAST);
                                break;
                            case SOUTH_WEST:
                                animateToMarker(marker, 13f);
                                loadRegionMarkers(RegionType.SOUTH_WEST);
                                break;
                            default:
                                Toast.makeText(getContext(),
                                        info.regionName + " 클릭됨",
                                        Toast.LENGTH_SHORT
                                ).show();
                                Log.d(TAG, info.regionName + " 클릭됨");
                                break;
                        }
                        return true;
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
     * 초기 권역별 위치 데이터를 담아두는 메서드.
     * 도심권(CITY_CENTER)은 기존처럼 downtownLocations 참고.
     * 예시로 NORTH_WEST, SOUTH_EAST 등에 몇 개 추가.
     */
    private void initRegionLocations() {
        // 도심권
        List<LocationData> downtownLocations = new ArrayList<>();
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
        regionLocationMap.put(RegionType.CITY_CENTER, downtownLocations);

        // 북서권 (NORTH_WEST)
        List<LocationData> northWestLocations = new ArrayList<>();
        northWestLocations.add(new LocationData("홍대입구역(2호선)", 37.557527, 126.9244669));
        northWestLocations.add(new LocationData("신촌·이대역", 37.55699399240634, 126.93895303148832));
        northWestLocations.add(new LocationData("연남동", 37.566223, 126.918034));
        northWestLocations.add(new LocationData("연신내역", 37.618812, 126.920842));
        northWestLocations.add(new LocationData("월드컵공원", 37.5638735, 126.8935007));
        northWestLocations.add(new LocationData("합정역", 37.5495753, 126.9139908));
        northWestLocations.add(new LocationData("난지한강공원", 37.5667873, 126.8780119));
        regionLocationMap.put(RegionType.NORTH_WEST, northWestLocations);

        // 남동권 (SOUTH_EAST)
        List<LocationData> southEastLocations = new ArrayList<>();
        southEastLocations.add(new LocationData("강남역", 37.497942, 127.027621));
        southEastLocations.add(new LocationData("역삼역", 37.500622, 127.036456));
        southEastLocations.add(new LocationData("잠실 관광특구", 37.5067945, 127.0830482));
        southEastLocations.add(new LocationData("선릉역", 37.504487, 127.048957));
        southEastLocations.add(new LocationData("잠실종합운동장", 37.5153013, 127.0728076));
        southEastLocations.add(new LocationData("고속터미널역", 37.5049142, 127.0049151));
        southEastLocations.add(new LocationData("교대역", 37.4934705, 127.0142285));
        southEastLocations.add(new LocationData("양재역", 37.484102, 127.034369));
        southEastLocations.add(new LocationData("가로수길", 37.5209291674577, 127.02288690766));
        southEastLocations.add(new LocationData("청담동 명품거리", 37.5260965, 127.0451308));
        southEastLocations.add(new LocationData("서울대공원", 37.42752473, 127.0170252));
        southEastLocations.add(new LocationData("사당역", 37.476559, 126.981633));
        regionLocationMap.put(RegionType.SOUTH_EAST, southEastLocations);

        // 북동권 (NORTH_EAST)
        List<LocationData> northEastLocations = new ArrayList<>();
        northEastLocations.add(new LocationData("건대입구역", 37.540372, 127.069276));
        northEastLocations.add(new LocationData("혜화역", 37.584132, 127.001160));
        northEastLocations.add(new LocationData("성신여대입구역", 37.59272, 127.016544));
        northEastLocations.add(new LocationData("북한산우이역", 37.662909, 127.012798));
        northEastLocations.add(new LocationData("수유역", 37.6371095, 127.0247325));
        northEastLocations.add(new LocationData("창동 신경제 중심지", 37.6477423, 127.044059));
        northEastLocations.add(new LocationData("왕십리역", 37.561949, 127.038485));
        northEastLocations.add(new LocationData("서울숲공원", 37.5443878, 127.0374424));
        regionLocationMap.put(RegionType.NORTH_EAST, northEastLocations);

        // 남서권 (SOUTH_WEST)
        List<LocationData> southWestLocations = new ArrayList<>();
        southWestLocations.add(new LocationData("여의도", 37.521597, 126.924962));
        southWestLocations.add(new LocationData("영등포 타임스퀘어", 37.5170751, 126.9033411));
        southWestLocations.add(new LocationData("신림역", 37.4842013, 126.9296504));
        southWestLocations.add(new LocationData("김포공항", 37.5655383, 126.8013282));
        southWestLocations.add(new LocationData("가산디지털단지역", 37.48089, 126.8825735));
        southWestLocations.add(new LocationData("대림역", 37.4925043, 126.8949615));
        regionLocationMap.put(RegionType.SOUTH_WEST, southWestLocations);
    }

    /**
     * 특정 권역(RegionType)에 해당하는 위치 데이터를 순회하며 혼잡도 API를 호출해 마커를 표시.
     */
    private void loadRegionMarkers(RegionType regionType) {
        List<LocationData> locations = regionLocationMap.get(regionType);
        if (locations == null || locations.isEmpty()) {
            Log.d(TAG, "해당 권역에 대한 위치 데이터가 없습니다: " + regionType);
            return;
        }
        for (LocationData loc : locations) {
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

                        // 지도에 마커 표시
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

        // 혼잡도에 따른 마커 색상(아이콘) 구분
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
     * 특정 마커로 카메라 애니메이션 이동 & 확대.
     */
    private void animateToMarker(Marker marker, float zoomLevel) {
        LatLng startPosition = naverMap.getCameraPosition().target;
        LatLng endPosition = marker.getPosition();
        float startZoom = (float) naverMap.getCameraPosition().zoom;
        float endZoom = zoomLevel;
        long duration = 1000; // 1초

        CameraAnimationHelper.animateCamera(
                naverMap,
                startPosition,
                endPosition,
                startZoom,
                endZoom,
                duration
        );
        Log.d(TAG, "권역 클릭: 카메라 이동 및 확대");
    }

    /**
     * 카메라 애니메이션 헬퍼 (커스텀)
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
            // 스크롤/줌을 동시에 애니메이션하기 위해
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
