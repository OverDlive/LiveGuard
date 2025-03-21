package com.example.liveguard_app_010.ui.home;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.region.RegionManager;
import com.example.liveguard_app_010.ui.feature.FeatureFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.geometry.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private BottomSheetBehavior<View> bottomSheetBehavior;
    private NaverMap naverMap;
    private MapManager mapManager;

    // 지역별 위치 데이터 관리
    private final Map<RegionManager.RegionType, List<LocationData>> regionLocationMap = new HashMap<>();

    // 바텀시트 데이터 표시를 위한 뷰 변수들
    private TextView textMalePercentage;
    private TextView textFemalePercentage;
    private TextView textAgeDistribution;
    private ProgressBar progressCongestion;
    private TextView textCongestionLevel;

    // 현재 선택된 지역 이름
    private String currentRegionName = "";

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

        // BottomSheet 초기화
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        // 바텀시트관련 로그
        if (bottomSheet != null) {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

            // 바텀시트 콘텐츠 추가
            View bottomSheetContent = getLayoutInflater().inflate(R.layout.bottom_sheet_content, null);
            ((ViewGroup) bottomSheet).addView(bottomSheetContent);

            // 바텀시트 데이터 뷰 초기화
            initDataViews(bottomSheetContent);

            // 상세 정보 버튼 리스너 설정
            setupDetailButtons(bottomSheetContent);

            // 초기 데이터 로딩
            loadRegionData("서울 전체");
        } else {
            Log.e("HomeFragment", "bottom_sheet ID를 찾을 수 없음!");
        }

        // 지도 Fragment 설정
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }

        // 지역별 위치 데이터 초기화
        initRegionLocations();
        // CongestionManager에 지역 데이터 전달
        CongestionManager.setRegionLocationMap(regionLocationMap);

        mapFragment.getMapAsync(map -> {
            naverMap = map;
            MarkerManager.init(naverMap, requireContext());
            mapManager = new MapManager(naverMap);

            // 지도 클릭 시 BottomSheet 접기
            naverMap.setOnMapClickListener((pointF, latLng) -> {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            });

            // 서울시 5대 권역 마커 추가
            List<RegionManager.RegionInfo> regionInfos = RegionManager.getSeoulRegions();
            mapManager.addRegionMarkers(regionInfos, bottomSheetBehavior);

            // 마커 클릭 이벤트 리스너 설정 (지역 데이터 로딩)
            mapManager.setOnMarkerClickListener(regionName -> {
                loadRegionData(regionName);
                // 바텀시트가 접혀있으면 확장
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                }
            });

            // GeoJSON 데이터를 통한 서울시 경계 표시
            try {
                JSONObject seoulGeoJson = RegionManager.getSeoulGeoJsonData(getContext());
                mapManager.displaySeoulRegions(seoulGeoJson);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("HomeFragment", "GeoJSON 파싱 오류: " + e.getMessage());
            }

            // 초기 지도 위치 설정 (서울시청 근방)
            CameraUpdate initialUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(37.5666102, 126.9783881), 10);
            naverMap.moveCamera(initialUpdate);
            Log.d("HomeFragment", "초기 지도 위치 설정");
        });
    }

    /**
     * 바텀시트의 데이터 표시 뷰를 초기화합니다.
     */
    private void initDataViews(View bottomSheetContent) {
        // 성별 정보 뷰
        textMalePercentage = bottomSheetContent.findViewById(R.id.text_male_percentage);
        textFemalePercentage = bottomSheetContent.findViewById(R.id.text_female_percentage);

        // 세대 정보 뷰
        textAgeDistribution = bottomSheetContent.findViewById(R.id.text_age_distribution);

        // 혼잡도 정보 뷰
        progressCongestion = bottomSheetContent.findViewById(R.id.progress_congestion);
        textCongestionLevel = bottomSheetContent.findViewById(R.id.text_congestion_level);
    }

    /**
     * 상세 정보 버튼에 클릭 리스너를 설정합니다.
     */
    private void setupDetailButtons(View bottomSheetContent) {
        // 성별 정보 상세 버튼
        Button btnDetailGender = bottomSheetContent.findViewById(R.id.btn_detail_gender);
        btnDetailGender.setOnClickListener(v -> showGenderDetails());

        // 세대 정보 상세 버튼
        Button btnDetailAge = bottomSheetContent.findViewById(R.id.btn_detail_age);
        btnDetailAge.setOnClickListener(v -> showAgeDetails());

        // 혼잡도 정보 상세 버튼
        Button btnDetailCongestion = bottomSheetContent.findViewById(R.id.btn_detail_congestion);
        btnDetailCongestion.setOnClickListener(v -> showCongestionDetails());
    }

    /**
     * 지역에 따른 데이터를 로드합니다.
     * @param regionName 지역 이름
     */
    private void loadRegionData(String regionName) {
        currentRegionName = regionName;
        Log.d("HomeFragment", "지역 데이터 로드: " + regionName);

        // 여기서 지역에 따른 API 파싱 데이터를 가져와 표시
        // 예시 코드 (실제 API 데이터 사용으로 대체 필요)

        // 성별 데이터 업데이트
        // 실제 구현 시 아래 코드 대신 API 파싱 결과를 사용
        int malePercentage, femalePercentage;

        // 지역에 따라 다른 샘플 데이터 사용 (실제 구현 시 API 데이터로 대체)
        switch (regionName) {
            case "도심권":
                malePercentage = 48;
                femalePercentage = 52;
                updateGenderData(malePercentage, femalePercentage);
                updateAgeData("20대(28%), 30대(22%), 40대(18%), 50대(32%)");
                updateCongestionData(75);
                break;
            case "동남권":
                malePercentage = 46;
                femalePercentage = 54;
                updateGenderData(malePercentage, femalePercentage);
                updateAgeData("20대(33%), 30대(27%), 40대(15%), 50대(25%)");
                updateCongestionData(65);
                break;
            case "서남권":
                malePercentage = 47;
                femalePercentage = 53;
                updateGenderData(malePercentage, femalePercentage);
                updateAgeData("20대(25%), 30대(30%), 40대(20%), 50대(25%)");
                updateCongestionData(45);
                break;
            case "서북권":
                malePercentage = 51;
                femalePercentage = 49;
                updateGenderData(malePercentage, femalePercentage);
                updateAgeData("20대(35%), 30대(25%), 40대(15%), 50대(25%)");
                updateCongestionData(55);
                break;
            case "동북권":
                malePercentage = 49;
                femalePercentage = 51;
                updateGenderData(malePercentage, femalePercentage);
                updateAgeData("20대(20%), 30대(25%), 40대(22%), 50대(33%)");
                updateCongestionData(35);
                break;
            default: // 서울 전체 또는 기타 지역
                malePercentage = 49;
                femalePercentage = 51;
                updateGenderData(malePercentage, femalePercentage);
                updateAgeData("20대(30%), 30대(25%), 40대(20%), 50대(25%)");
                updateCongestionData(60);
                break;
        }
    }

    /**
     * 성별 데이터를 업데이트합니다.
     */
    private void updateGenderData(int malePercentage, int femalePercentage) {
        if (textMalePercentage != null && textFemalePercentage != null) {
            textMalePercentage.setText("남성: " + malePercentage + "%");
            textFemalePercentage.setText("여성: " + femalePercentage + "%");
        }
    }

    /**
     * 세대 데이터를 업데이트합니다.
     */
    private void updateAgeData(String ageDistributionData) {
        if (textAgeDistribution != null) {
            textAgeDistribution.setText(ageDistributionData);
        }
    }

    /**
     * 혼잡도 데이터를 업데이트합니다.
     */
    private void updateCongestionData(int congestionLevel) {
        if (progressCongestion != null && textCongestionLevel != null) {
            progressCongestion.setProgress(congestionLevel);

            String congestionText;
            int congestionColor;

            if (congestionLevel < 40) {
                congestionText = "여유";
                congestionColor = R.color.green_low;
            } else if (congestionLevel < 70) {
                congestionText = "혼잡";
                congestionColor = R.color.orange_congestion;
            } else {
                congestionText = "매우 혼잡";
                congestionColor = R.color.red_high;
            }

            textCongestionLevel.setText(congestionText + " (" + congestionLevel + "%)");
            textCongestionLevel.setTextColor(getResources().getColor(congestionColor));
            progressCongestion.setProgressTintList(getResources().getColorStateList(congestionColor));
        }
    }

    /**
     * 성별 정보 상세 보기 화면으로 이동합니다.
     */
    private void showGenderDetails() {
        Toast.makeText(getContext(), currentRegionName + " 성별 정보 상세", Toast.LENGTH_SHORT).show();
        // 상세 화면으로 이동하는 코드 추가 (필요 시)
    }

    /**
     * 세대 정보 상세 보기 화면으로 이동합니다.
     */
    private void showAgeDetails() {
        Toast.makeText(getContext(), currentRegionName + " 세대 정보 상세", Toast.LENGTH_SHORT).show();
        // 상세 화면으로 이동하는 코드 추가 (필요 시)
    }

    /**
     * 혼잡도 정보 상세 보기 화면으로 이동합니다.
     */
    private void showCongestionDetails() {
        Toast.makeText(getContext(), currentRegionName + " 혼잡도 정보 상세", Toast.LENGTH_SHORT).show();
        // 상세 화면으로 이동하는 코드 추가 (필요 시)
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
}