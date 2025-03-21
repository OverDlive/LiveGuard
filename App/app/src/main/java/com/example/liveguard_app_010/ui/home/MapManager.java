package com.example.liveguard_app_010.ui.home;

import android.graphics.Color;
import android.view.View;
import android.util.Log;

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

public class MapManager {
    private NaverMap naverMap;

    // [추가] 마커 클릭 이벤트를 처리하기 위한 리스너 인터페이스 정의
    private OnMarkerClickListener markerClickListener;

    public MapManager(NaverMap naverMap) {
        this.naverMap = naverMap;
    }

    /**
     * [추가] 마커 클릭 리스너 인터페이스
     * 지도 마커 클릭 시 바텀시트에 해당 지역 데이터를 로드하기 위한 콜백
     */
    public interface OnMarkerClickListener {
        void onMarkerClick(String regionName);
    }

    /**
     * [추가] 마커 클릭 리스너 설정
     * HomeFragment에서 이 메서드를 호출하여 마커 클릭 이벤트 처리를 등록
     */
    public void setOnMarkerClickListener(OnMarkerClickListener listener) {
        this.markerClickListener = listener;
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

            // 마커 클릭 시 카메라 이동 및 혼잡도 마커 로드
            marker.setOnClickListener(overlay -> {
                animateToMarker(marker, 13f);
                // 해당 권역의 혼잡도 마커 로드 (CongestionManager에서 처리)
                CongestionManager.loadRegionMarkers(info.type);

                // 마커 클릭 리스너가 설정되어 있으면 호출 (바텀시트 데이터 로드)
                if (markerClickListener != null) {
                    Log.d("MapManager", "마커 클릭: " + info.regionName);
                    markerClickListener.onMarkerClick(info.regionName);

                    // 바텀시트가 접혀있으면 확장
                    if (bottomSheetBehavior != null &&
                            bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    }
                }

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
}