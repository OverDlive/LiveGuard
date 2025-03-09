package com.example.liveguard_app_010.ui.home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.liveguard_app_010.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

public class MarkerManager {
    private static NaverMap naverMap;
    private static Context context; // Context 추가

    public static void init(NaverMap map, Context ctx) {
        naverMap = map;
        context = ctx;
    }

    public static void showMarker(String areaName, String congestLvl, double lat, double lng) {
        if (naverMap == null || context == null) {
            Log.e("MarkerManager", "네이버 맵 또는 Context가 초기화되지 않았습니다.");
            return;
        }

        Marker marker = new Marker();
        marker.setPosition(new LatLng(lat, lng));
        marker.setCaptionText(areaName + "\n혼잡도: " + congestLvl);

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

        marker.setMap(naverMap);
        marker.setOnClickListener(overlay -> {
            Toast.makeText(context, areaName + " : " + congestLvl, Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}