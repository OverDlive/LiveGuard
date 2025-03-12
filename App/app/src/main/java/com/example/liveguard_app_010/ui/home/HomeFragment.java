package com.example.liveguard_app_010.ui.home;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.region.RegionManager;
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
import android.content.Intent; // Intent 관련 오류 해결
import android.widget.EditText;  // EditText 관련 오류 해결
import android.widget.ImageView;  // ImageView 관련 오류 해결
import com.example.liveguard_app_010.ui.search.SearchActivity;


public class HomeFragment extends Fragment {

    private BottomSheetBehavior<View> bottomSheetBehavior;
    private NaverMap naverMap;
    private MapManager mapManager;

    // 지역별 위치 데이터 관리
    private final Map<RegionManager.RegionType, List<LocationData>> regionLocationMap = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // ✅ 검색창 & 버튼 추가
        EditText searchBar = view.findViewById(R.id.search_bar);
        ImageView searchButton = view.findViewById(R.id.search_button);

        // 검색 버튼 클릭 시 SearchActivity 실행
        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        // BottomSheet 초기화
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        bottomSheetBehavior = BottomSheetHelper.initBottomSheet(bottomSheet, 500, 0.5f, (int) (screenHeight * 0.02f));

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

        return view;
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