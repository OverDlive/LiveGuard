package com.example.liveguard_app_010.ui.home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.liveguard_app_010.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;

import java.util.HashMap;
import java.util.Map;

public class MarkerManager {
    private static NaverMap naverMap;
    private static Context context; // Context 추가
    private static Overlay.OnClickListener markerClickListener;
    private static Map<String, Marker> markerMap = new HashMap<>(); // 마커 캐싱

    public static void init(NaverMap map, Context ctx) {
        naverMap = map;
        context = ctx;
    }

    /**
     * 마커 클릭 리스너 설정
     * @param listener 클릭 이벤트 리스너
     */
    public static void setOnMarkerClickListener(Overlay.OnClickListener listener) {
        markerClickListener = listener;
    }

    public static void showMarker(String areaName, String congestLvl, double lat, double lng) {
        if (naverMap == null || context == null) {
            Log.e("MarkerManager", "네이버 맵 또는 Context가 초기화되지 않았습니다.");
            return;
        }

        // 이미 존재하는 마커는 재사용
        Marker marker = markerMap.get(areaName);
        if (marker == null) {
            marker = new Marker();
            markerMap.put(areaName, marker);
        }

        marker.setPosition(new LatLng(lat, lng));
        marker.setCaptionText(areaName + "\n혼잡도: " + congestLvl);
        //marker.setCaptionAligns(Align.Top); // 캡션 위치 상단으로
        marker.setCaptionTextSize(14); // 캡션 크기
        marker.setHideCollidedSymbols(true); // 마커끼리 겹칠 때 작은 마커 숨김

        // 혼잡도 수준에 따라 아이콘 지정
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

        // 마커 클릭 이벤트 처리
        if (markerClickListener != null) {
            marker.setOnClickListener(markerClickListener);
        } else {
            // 기본 마커 클릭 동작
            marker.setOnClickListener(overlay -> {
                Toast.makeText(context, areaName + " : " + congestLvl, Toast.LENGTH_SHORT).show();
                return true;
            });
        }

        marker.setMap(naverMap);
    }

    /**
     * 이름으로 마커 찾기
     * @param areaName 지역명
     * @return 해당 지역의 마커 (없으면 null)
     */
    public static Marker getMarkerByName(String areaName) {
        return markerMap.get(areaName);
    }

    /**
     * 모든 마커 지우기
     */
    public static void clearAllMarkers() {
        for (Marker marker : markerMap.values()) {
            marker.setMap(null);
        }
        markerMap.clear();
    }
}