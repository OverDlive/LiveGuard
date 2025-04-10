package com.example.liveguard_app_010.ui.result;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.liveguard_app_010.MainActivity;
import com.example.liveguard_app_010.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourResultActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private View bottomSheet;
    private TextView titleText;
    private TextView descriptionText;
    private BottomSheetBehavior<View> bottomSheetBehavior;

    private final List<PlaceInfo> placeInfoList = new ArrayList<>(); // 💡 장소 데이터 리스트
    private final Map<Marker, PlaceInfo> markerPlaceInfoMap = new HashMap<>(); // 💡 마커 - 장소 매핑

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_result);

        mapView = findViewById(R.id.mapView);
        bottomSheet = findViewById(R.id.bottom_sheet);
        titleText = findViewById(R.id.info_title);
        descriptionText = findViewById(R.id.info_description);
        Button btnClose = findViewById(R.id.btn_close_result);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(0);

        btnClose.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        initDummyData(); // 🔥 더미 데이터 세팅
    }

    private void initDummyData() {
        placeInfoList.add(new PlaceInfo("서울 시청", "서울특별시의 중심에 위치한 정부 건물입니다.", 37.5665, 126.9780));
        placeInfoList.add(new PlaceInfo("남산타워", "서울의 멋진 야경을 볼 수 있는 타워입니다.", 37.5512, 126.9882));
        placeInfoList.add(new PlaceInfo("경복궁", "조선 왕조의 정궁으로 아름다운 고궁입니다.", 37.5796, 126.9770));
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        addMarkersToMap(naverMap, placeInfoList);
        moveCameraToMarkers(naverMap);
    }

    private void addMarkersToMap(NaverMap naverMap, List<PlaceInfo> places) {
        for (PlaceInfo place : places) {
            Marker marker = new Marker();
            marker.setPosition(new LatLng(place.latitude, place.longitude));
            marker.setMap(naverMap);

            markerPlaceInfoMap.put(marker, place);

            marker.setOnClickListener(overlay -> {
                showPlaceInfo(place);
                return true;
            });
        }
    }

    private void moveCameraToMarkers(NaverMap naverMap) {
        if (markerPlaceInfoMap.isEmpty()) return;

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (Marker marker : markerPlaceInfoMap.keySet()) {
            boundsBuilder.include(marker.getPosition());
        }

        LatLngBounds bounds = boundsBuilder.build();
        naverMap.moveCamera(CameraUpdate.fitBounds(bounds, 100)); // 패딩값 100
    }

    private void showPlaceInfo(PlaceInfo placeInfo) {
        titleText.setText(placeInfo.title);
        descriptionText.setText(placeInfo.description);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private static class PlaceInfo {
        String title;
        String description;
        double latitude;
        double longitude;

        PlaceInfo(String title, String description, double latitude, double longitude) {
            this.title = title;
            this.description = description;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    // MapView 생명주기
    @Override protected void onStart() { super.onStart(); mapView.onStart(); }
    @Override protected void onResume() { super.onResume(); mapView.onResume(); }
    @Override protected void onPause() { mapView.onPause(); super.onPause(); }
    @Override protected void onStop() { mapView.onStop(); super.onStop(); }
    @Override protected void onDestroy() { mapView.onDestroy(); super.onDestroy(); }
    @Override public void onLowMemory() { super.onLowMemory(); mapView.onLowMemory(); }
}
