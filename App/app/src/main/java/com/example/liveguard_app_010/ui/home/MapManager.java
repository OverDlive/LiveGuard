package com.example.liveguard_app_010.ui.home;

import android.graphics.Color;
import android.view.View;

import com.example.liveguard_app_010.region.RegionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PolygonOverlay;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class MapManager {
    // 화면에 추가된 모든 마커를 보관합니다.
    private final List<Marker> markers = new ArrayList<>();
    private NaverMap naverMap;

    public MapManager(NaverMap naverMap) {
        this.naverMap = naverMap;
    }

    /**
     * 서울시 5대 권역 마커를 지도에 추가합니다.
     */
    public void addRegionMarkers(List<RegionManager.RegionInfo> regionInfos, BottomSheetBehavior<View> bottomSheetBehavior) {
        for (RegionManager.RegionInfo info : regionInfos) {
            Marker marker = new Marker();
            marker.setPosition(new LatLng(info.lat, info.lng));
            marker.setCaptionText(info.regionName);
            marker.setMap(naverMap);

            // 리스트에 마커를 추가
            markers.add(marker);

            // 마커 클릭 시 카메라 이동 및 혼잡도 마커 로드
            marker.setOnClickListener(overlay -> {
                animateToMarker(marker, 13f);
                // 해당 권역의 혼잡도 마커 로드 (CongestionManager에서 처리)
                CongestionManager.loadRegionMarkers(info.type);
                return true;
            });
        }
    }

    /**
     * 마커 위치로 카메라를 애니메이션하며 이동시킵니다.
     */
    public void animateToMarker(Marker marker, float zoomLevel) {
        LatLng endPosition = marker.getPosition();
        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(endPosition, zoomLevel)
                .animate(CameraAnimation.Easing, 1000);
        naverMap.moveCamera(cameraUpdate);
    }

    /**
     * GeoJSON 데이터를 파싱하여 서울시 각 권역의 경계(폴리곤)를 지도에 표시합니다.
     */
    public void displaySeoulRegions(JSONObject geoJsonData) throws JSONException {
        Map<String, List<PolygonOverlay>> regionOverlays = new HashMap<>();
        regionOverlays.put("도심권", new ArrayList<>());
        regionOverlays.put("동북권", new ArrayList<>());
        regionOverlays.put("동남권", new ArrayList<>());
        regionOverlays.put("서북권", new ArrayList<>());
        regionOverlays.put("서남권", new ArrayList<>());

        JSONArray features = geoJsonData.getJSONArray("features");
        for (int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            JSONObject properties = feature.getJSONObject("properties");
            String districtName = properties.getString("sggnm");
            String region = RegionManager.getRegionForDistrict(districtName);
            if (region == null) continue;

            JSONObject geometry = feature.getJSONObject("geometry");
            JSONArray multiPolygons = geometry.getJSONArray("coordinates");
            for (int j = 0; j < multiPolygons.length(); j++) {
                JSONArray polygonGroup = multiPolygons.getJSONArray(j);
                JSONArray outerRing = polygonGroup.getJSONArray(0);
                List<LatLng> coords = new ArrayList<>();
                for (int k = 0; k < outerRing.length(); k++) {
                    JSONArray point = outerRing.getJSONArray(k);
                    double lon = point.getDouble(0);
                    double lat = point.getDouble(1);
                    coords.add(new LatLng(lat, lon));
                }
                PolygonOverlay overlay = new PolygonOverlay();
                overlay.setCoords(coords);
                // 권역별 스타일 지정
                switch (region) {
                    case "도심권":
                        overlay.setColor(Color.argb(80, 255, 255, 255));
                        overlay.setOutlineColor(Color.BLUE);
                        overlay.setOutlineWidth(5);
                        break;
                    case "동북권":
                        overlay.setColor(Color.argb(80, 255, 255, 255));
                        overlay.setOutlineColor(Color.MAGENTA);
                        overlay.setOutlineWidth(5);
                        break;
                    case "동남권":
                        overlay.setColor(Color.argb(80, 255, 255, 255));
                        overlay.setOutlineColor(Color.RED);
                        overlay.setOutlineWidth(5);
                        break;
                    case "서북권":
                        overlay.setColor(Color.argb(80, 255, 255, 255));
                        overlay.setOutlineColor(Color.GREEN);
                        overlay.setOutlineWidth(5);
                        break;
                    case "서남권":
                        overlay.setColor(Color.argb(80, 255, 255, 255));
                        overlay.setOutlineColor(Color.YELLOW);
                        overlay.setOutlineWidth(5);
                        break;
                }
                overlay.setMap(naverMap);
                regionOverlays.get(region).add(overlay);
            }
        }
    }

    /**
     * 현재 화면에 표시된 모든 마커를 반환합니다.
     */
    public List<Marker> getMarkers() {
        return markers;
    }
}