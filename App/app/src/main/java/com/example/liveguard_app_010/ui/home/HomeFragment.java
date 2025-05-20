package com.example.liveguard_app_010.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.animation.ValueAnimator;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.region.RegionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

import com.naver.maps.map.overlay.PolygonOverlay;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import android.widget.ImageView;
import com.example.liveguard_app_010.ui.feature.FeatureFragment;
import com.naver.maps.map.widget.LocationButtonView;

import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import android.widget.Toast;

import com.naver.maps.map.UiSettings;

public class HomeFragment extends Fragment {

    private BottomSheetBehavior<View> bottomSheetBehavior;
    private NaverMap naverMap;
    private MapManager mapManager;
    private BottomSheetManager bottomSheetManager;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    // 지역별 위치 데이터 관리
    private final Map<RegionManager.RegionType, List<LocationData>> regionLocationMap = new HashMap<>();

    // 서울시 밖 토스트 1회만 표시
    private boolean hasShownOutOfSeoulToast = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 🔘 검색 버튼 클릭 시 기능 페이지 (`FeatureFragment`) 추가
        ImageView searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            FeatureFragment featureFragment = new FeatureFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(android.R.id.content, featureFragment)  // HomeFragment 위에 추가
                    .addToBackStack(null)
                    .commit();
        });

        // 바텀시트 설정
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        if (bottomSheet != null) {
            bottomSheetBehavior = BottomSheetHelper.setupBottomSheet(bottomSheet, requireActivity(), view);

            // 바텀시트 관리자 초기화
            bottomSheetManager = new BottomSheetManager(requireActivity(), bottomSheet, bottomSheetBehavior);
        } else {
            Log.e("HomeFragment", "bottom_sheet ID를 찾을 수 없음!");
        }

        // 지도 Fragment 설정
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }

        // 위치 권한 및 현재 위치 표시용 LocationSource 초기화
        locationSource = new FusedLocationSource(getActivity(), LOCATION_PERMISSION_REQUEST_CODE);

        // 지역별 위치 데이터 초기화
        initRegionLocations();
        // CongestionManager에 지역 데이터 전달
        CongestionManager.setRegionLocationMap(regionLocationMap);

        mapFragment.getMapAsync(map -> {
            naverMap = map;
            // 초기 지도 위치를 서울시청 근방으로 설정
            CameraUpdate initialUpdate = CameraUpdate.scrollAndZoomTo(
                new LatLng(37.5666102, 126.9783881), 10);
            naverMap.moveCamera(initialUpdate);
            Log.d("HomeFragment", "초기 지도 위치 설정");
            // 서울시 영역으로 지도 이동 제약 설정
            LatLng southWest = new LatLng(37.413294, 126.734086);
            LatLng northEast = new LatLng(37.715251, 127.269311);
            LatLngBounds seoulBounds = new LatLngBounds(southWest, northEast);

            // 줌 레벨 최소/최대 제한 설정 (초기 로드 배율 유지)
            final float INITIAL_ZOOM = 10f;
            final float MAX_ZOOM = 13f;
            naverMap.setMinZoom(INITIAL_ZOOM);
            naverMap.setMaxZoom(MAX_ZOOM);

            // 현재 위치 버튼 표시 및 위치 트래킹 모드 설정
            naverMap.setLocationSource(locationSource);
            naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            naverMap.getUiSettings().setLocationButtonEnabled(false);
            // Disable inertial fling by enabling stop gestures
            UiSettings uiSettings = naverMap.getUiSettings();
            uiSettings.setStopGesturesEnabled(true);

            LocationButtonView locationButton = view.findViewById(R.id.custom_location_button);
            locationButton.setMap(naverMap);

            // 현재 위치가 변경될 때마다 서울시 내 여부를 확인하여 서비스 제한 안내
            naverMap.addOnLocationChangeListener(location -> {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                if (!seoulBounds.contains(currentLocation)) {
                    if (!hasShownOutOfSeoulToast) {
                        Toast.makeText(requireContext(), "서비스는 서울시 내에서만 이용 가능합니다.", Toast.LENGTH_LONG).show();
                        hasShownOutOfSeoulToast = true;
                    }
                }
            });

            MarkerManager.init(naverMap, requireContext());
            mapManager = new MapManager(naverMap);

            // 지도 클릭 시 BottomSheet 접기 및 서울 전체 정보로 초기화
            naverMap.setOnMapClickListener((pointF, latLng) -> {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                // 혼잡도 마커만 페이드 아웃하며 제거
                if (mapManager != null) {
                    for (Marker m : mapManager.getMarkers()) {
                        String caption = m.getCaptionText();
                        if (caption != null && caption.contains("\n")) {
                            fadeOutAndRemoveMarker(m);
                        }
                    }
                }
                // 서울 전체 정보로 초기화
                bottomSheetManager.loadSeoulAverage();
            });

            // 마커 클릭 이벤트 리스너 설정
            setMarkerClickListener();

            // 서울시 5대 권역 마커 추가
            List<RegionManager.RegionInfo> regionInfos = RegionManager.getSeoulRegions();
            mapManager.addRegionMarkers(regionInfos, bottomSheetBehavior);

            // 1) GeoJSON 파일 로드 및 각 구역을 hole로 파싱
            List<List<LatLng>> holes = new ArrayList<>();
            try (InputStream is = getContext().getAssets().open("seoul_districts.geojson")) {
                byte[] bytes = is.readAllBytes();
                String geoJsonStr = new String(bytes, StandardCharsets.UTF_8);
                JSONObject geoJson = new JSONObject(geoJsonStr);
                JSONArray features = geoJson.getJSONArray("features");
                for (int f = 0; f < features.length(); f++) {
                    JSONObject geometry = features.getJSONObject(f).getJSONObject("geometry");
                    String type = geometry.getString("type");
                    JSONArray coords = type.equals("MultiPolygon")
                        ? geometry.getJSONArray("coordinates").getJSONArray(0).getJSONArray(0)
                        : geometry.getJSONArray("coordinates").getJSONArray(0);
                    List<LatLng> boundary = new ArrayList<>();
                    for (int i = 0; i < coords.length(); i++) {
                        JSONArray c = coords.getJSONArray(i);
                        boundary.add(new LatLng(c.getDouble(1), c.getDouble(0)));
                    }
                    // Ensure CCW ordering for hole
                    // (GeoJSON may be in CW, so reverse)
                    Collections.reverse(boundary);
                    holes.add(boundary);
                }
            } catch (IOException | JSONException e) {
                Log.e("HomeFragment", "GeoJSON parse failed: " + e.getMessage());
            }

            // 2) 외곽 폴리곤 정의 (서울시 바깥을 완전히 차단)
            // 서울시 Bounds에 margin 추가
            LatLngBounds b = seoulBounds;
            double margin = 0.1;  // degrees
            LatLng sw = new LatLng(
                b.getSouthWest().latitude - margin,
                b.getSouthWest().longitude - margin
            );
            LatLng ne = new LatLng(
                b.getNorthEast().latitude + margin,
                b.getNorthEast().longitude + margin
            );
            List<LatLng> outer = Arrays.asList(
                new LatLng(ne.latitude, sw.longitude),
                new LatLng(sw.latitude, sw.longitude),
                new LatLng(sw.latitude, ne.longitude),
                new LatLng(ne.latitude, ne.longitude)
            );

            // 3) 다크 오버레이 생성 (서울만 밝게)
            PolygonOverlay dimOverlay = new PolygonOverlay();
            dimOverlay.setCoords(outer);
            dimOverlay.setHoles(holes);
            dimOverlay.setColor(Color.argb(120, 0, 0, 0));  // 밝기를 높여 반투명 처리
            dimOverlay.setOutlineWidth(0);                  // 테두리 없음
            dimOverlay.setZIndex(1);                        // 맵 타일 위에 렌더링
            dimOverlay.setMap(naverMap);

            // 지도 이동 제한: dim overlay용 외곽 사각형 범위로 설정
            LatLng outerSW = new LatLng(b.getSouthWest().latitude - margin, b.getSouthWest().longitude - margin);
            LatLng outerNE = new LatLng(b.getNorthEast().latitude + margin, b.getNorthEast().longitude + margin);
            final LatLngBounds outerBounds = new LatLngBounds(outerSW, outerNE); // ensure final for listener
            naverMap.setExtent(outerBounds);

            // 서울 전체 정보 로드
            bottomSheetManager.loadSeoulAverage();

            // 줌 레벨 변경 후 Idle 상태일 때 혼잡도 마커 제거 및 카메라 클램핑
            final float ZOOM_THRESHOLD = 12f; // 필요에 따라 조정
            // 카메라 Idle 상태에서 클램핑 및 마커 제거
            naverMap.addOnCameraIdleListener(() -> {
                // Clear markers if zoom too low
                float currentZoom = (float) naverMap.getCameraPosition().zoom;
                if (currentZoom < ZOOM_THRESHOLD) {
                    MarkerManager.clearAllMarkers();
                }
                // Clamp camera target within bounds
                LatLng target = naverMap.getCameraPosition().target;
                double clampedLat = Math.max(outerBounds.getSouthWest().latitude,
                    Math.min(target.latitude, outerBounds.getNorthEast().latitude));
                double clampedLng = Math.max(outerBounds.getSouthWest().longitude,
                    Math.min(target.longitude, outerBounds.getNorthEast().longitude));
                if (clampedLat != target.latitude || clampedLng != target.longitude) {
                    CameraUpdate fix = CameraUpdate.scrollTo(new LatLng(clampedLat, clampedLng));
                    naverMap.moveCamera(fix);
                }
            });
        });
    }

    /**
     * 마커 클릭 리스너 설정
     * 마커가 클릭되면 해당 지역의 상세 정보를 바텀시트에 표시
     */
    private void setMarkerClickListener() {
        // MarkerManager의 마커 클릭 리스너 수정
        MarkerManager.setOnMarkerClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                if (overlay instanceof Marker) {
                    Marker marker = (Marker) overlay;
                    String areaName = marker.getCaptionText();
                    if (areaName != null && !areaName.isEmpty()) {
                        // 혼잡도 메시지 제거 (예: "혼잡도: 여유" -> "광화문")
                        if (areaName.contains("\n")) {
                            areaName = areaName.split("\n")[0];
                        }

                        bottomSheetManager.loadAreaData(areaName);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 페이드 아웃 애니메이션 후 마커 제거
     */
    private void fadeOutAndRemoveMarker(Marker marker) {
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        animator.setDuration(500);
        animator.addUpdateListener(animation -> {
            float alpha = (float) animation.getAnimatedValue();
            marker.setAlpha(alpha);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                marker.setMap(null);
            }
        });
        animator.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        requireActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        // Ensure status bar icons (battery, alarm) are light for visibility
        WindowInsetsControllerCompat controller =
            WindowCompat.getInsetsController(requireActivity().getWindow(), requireActivity().getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        requireActivity().getWindow().setStatusBarColor(
            ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
        );
        // Revert status bar icons to dark for other screens
        WindowInsetsControllerCompat controller =
            WindowCompat.getInsetsController(requireActivity().getWindow(), requireActivity().getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(false);
    }

    /**
     * 각 권역별 위치 데이터를 초기화합니다.
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
        regionLocationMap.put(RegionManager.RegionType.CITY_CENTER, downtownLocations);

        // 북서권
        List<LocationData> northWestLocations = new ArrayList<>();
        northWestLocations.add(new LocationData("홍대입구역(2호선)", 37.557527, 126.9244669));
        northWestLocations.add(new LocationData("신촌·이대역", 37.55699399240634, 126.93895303148832));
        northWestLocations.add(new LocationData("연남동", 37.566223, 126.918034));
        northWestLocations.add(new LocationData("연신내역", 37.618812, 126.920842));
        northWestLocations.add(new LocationData("월드컵공원", 37.5638735, 126.8935007));
        northWestLocations.add(new LocationData("합정역", 37.5495753, 126.9139908));
        northWestLocations.add(new LocationData("난지한강공원", 37.5667873, 126.8780119));
        regionLocationMap.put(RegionManager.RegionType.NORTH_WEST, northWestLocations);

        // 남동권
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
        regionLocationMap.put(RegionManager.RegionType.SOUTH_EAST, southEastLocations);

        // 북동권
        List<LocationData> northEastLocations = new ArrayList<>();
        northEastLocations.add(new LocationData("건대입구역", 37.540372, 127.069276));
        northEastLocations.add(new LocationData("혜화역", 37.584132, 127.001160));
        northEastLocations.add(new LocationData("성신여대입구역", 37.59272, 127.016544));
        northEastLocations.add(new LocationData("북한산우이역", 37.662909, 127.012798));
        northEastLocations.add(new LocationData("수유역", 37.6371095, 127.0247325));
        northEastLocations.add(new LocationData("창동 신경제 중심지", 37.6477423, 127.044059));
        northEastLocations.add(new LocationData("왕십리역", 37.561949, 127.038485));
        northEastLocations.add(new LocationData("서울숲공원", 37.5443878, 127.0374424));
        regionLocationMap.put(RegionManager.RegionType.NORTH_EAST, northEastLocations);

        // 남서권
        List<LocationData> southWestLocations = new ArrayList<>();
        southWestLocations.add(new LocationData("여의도", 37.521597, 126.924962));
        southWestLocations.add(new LocationData("영등포 타임스퀘어", 37.5170751, 126.9033411));
        southWestLocations.add(new LocationData("신림역", 37.4842013, 126.9296504));
        southWestLocations.add(new LocationData("김포공항", 37.5655383, 126.8013282));
        southWestLocations.add(new LocationData("가산디지털단지역", 37.48089, 126.8825735));
        southWestLocations.add(new LocationData("대림역", 37.4925043, 126.8949615));
        regionLocationMap.put(RegionManager.RegionType.SOUTH_WEST, southWestLocations);
    }

    /**
     * 단순 위치 정보를 담기 위한 데이터 클래스입니다.
     */
    public static class LocationData {
        public String name;
        public double lat;
        public double lng;

        public LocationData(String name, double lat, double lng) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}