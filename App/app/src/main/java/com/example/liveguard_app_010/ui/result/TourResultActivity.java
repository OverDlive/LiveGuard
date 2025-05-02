package com.example.liveguard_app_010.ui.result;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.liveguard_app_010.MainActivity;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.models.PlaceInfo;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TourResultActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private View bottomSheet;
    private TextView titleText;
    private TextView descriptionText;
    private BottomSheetBehavior<View> bottomSheetBehavior;

    private final Map<Marker, PlaceInfo> markerPlaceInfoMap = new HashMap<>();
    private NaverMap currentNaverMap; // 지도 객체 저장용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_result);

        mapView = findViewById(R.id.mapView);
        View btnClose = findViewById(R.id.btn_close_result);
        bottomSheet = findViewById(R.id.bottom_sheet);
        titleText = findViewById(R.id.info_title);
        descriptionText = findViewById(R.id.info_description);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(0);

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnClose.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.currentNaverMap = naverMap;
        receivePlaceList();
    }

    private void receivePlaceList() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("place_list")) {
            ArrayList<PlaceInfo> placeList = (ArrayList<PlaceInfo>) intent.getSerializableExtra("place_list");
            if (placeList != null && !placeList.isEmpty()) {
                setupMarkers(placeList);
                return;
            }
        }

        // 데이터 없으면 기본 더미 데이터로
        setupDummyMarkers();
    }

    private void setupMarkers(ArrayList<PlaceInfo> placeList) {
        if (currentNaverMap == null) return;

        for (PlaceInfo place : placeList) {
            addMarker(currentNaverMap, place);
        }
        moveCameraToMarkers();
    }

    private void setupDummyMarkers() {
        if (currentNaverMap == null) return;

        ArrayList<PlaceInfo> dummyPlaces = new ArrayList<>();
        dummyPlaces.add(new PlaceInfo("서울 시청", "서울특별시의 중심에 위치한 정부 건물입니다.", 37.5665, 126.9780));
        dummyPlaces.add(new PlaceInfo("남산타워", "서울의 멋진 야경을 볼 수 있는 타워입니다.", 37.5512, 126.9882));
        dummyPlaces.add(new PlaceInfo("경복궁", "조선 왕조의 정궁으로 아름다운 고궁입니다.", 37.5796, 126.9770));
        dummyPlaces.add(new PlaceInfo("롯데월드", "놀이기구와 쇼핑몰, 아쿠아리움이 있는 대형 테마파크입니다.", 37.5110, 127.0980));
        dummyPlaces.add(new PlaceInfo("코엑스", "전시회와 쇼핑, 식사가 가능한 서울 강남의 복합 공간입니다.", 37.5125, 127.0580));

        setupMarkers(dummyPlaces);
    }

    private void addMarker(NaverMap naverMap, PlaceInfo placeInfo) {
        Marker marker = new Marker();
        marker.setPosition(new LatLng(placeInfo.lat, placeInfo.lng));
        marker.setMap(naverMap);

        markerPlaceInfoMap.put(marker, placeInfo);

        marker.setOnClickListener(overlay -> {
            showPlaceInfo(placeInfo);
            return true;
        });
    }

    private void moveCameraToMarkers() {
        if (markerPlaceInfoMap.isEmpty()) return;

        double minLat = Double.MAX_VALUE, maxLat = Double.MIN_VALUE;
        double minLng = Double.MAX_VALUE, maxLng = Double.MIN_VALUE;

        for (PlaceInfo info : markerPlaceInfoMap.values()) {
            minLat = Math.min(minLat, info.lat);
            maxLat = Math.max(maxLat, info.lat);
            minLng = Math.min(minLng, info.lng);
            maxLng = Math.max(maxLng, info.lng);
        }

        LatLng southWest = new LatLng(minLat, minLng);
        LatLng northEast = new LatLng(maxLat, maxLng);
        com.naver.maps.geometry.LatLngBounds bounds = new com.naver.maps.geometry.LatLngBounds(southWest, northEast);

        currentNaverMap.moveCamera(com.naver.maps.map.CameraUpdate.fitBounds(bounds));
    }


    private void showPlaceInfo(PlaceInfo placeInfo) {
        titleText.setText(placeInfo.title);
        descriptionText.setText(placeInfo.description);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    // MapView 생명주기
    @Override protected void onStart() { super.onStart(); mapView.onStart(); }
    @Override protected void onResume() { super.onResume(); mapView.onResume(); }
    @Override protected void onPause() { mapView.onPause(); super.onPause(); }
    @Override protected void onStop() { mapView.onStop(); super.onStop(); }
    @Override protected void onDestroy() { mapView.onDestroy(); super.onDestroy(); }
    @Override public void onLowMemory() { super.onLowMemory(); mapView.onLowMemory(); }
}
