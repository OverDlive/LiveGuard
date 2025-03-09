package com.example.liveguard_app_010.region;

import android.content.Context;

import com.example.liveguard_app_010.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RegionManager {

    public enum RegionType {
        CITY_CENTER, // 도심권
        NORTH_EAST,  // 동북권
        NORTH_WEST,  // 서북권
        SOUTH_WEST,  // 서남권
        SOUTH_EAST   // 동남권
    }

    public static class RegionInfo {
        public RegionType type;
        public String regionName;
        public double lat;
        public double lng;

        public RegionInfo(RegionType type, String regionName, double lat, double lng) {
            this.type = type;
            this.regionName = regionName;
            this.lat = lat;
            this.lng = lng;
        }
    }

    /**
     * 5개 권역에 대한 간단한 예시
     */
    public static List<RegionInfo> getSeoulRegions() {
        List<RegionInfo> list = new ArrayList<>();

        // 도심권(예: 서울시청 근방)
        list.add(new RegionInfo(
                RegionType.CITY_CENTER,
                "도심권",
                37.5666102, // 대략 시청 좌표
                126.9783881
        ));

        // 동북권(예: 청량리역 근방)
        list.add(new RegionInfo(
                RegionType.NORTH_EAST,
                "동북권",
                37.5822883,
                127.0396675
        ));

        // 서북권(예: 은평/불광 근방)
        list.add(new RegionInfo(
                RegionType.NORTH_WEST,
                "서북권",
                37.6096864,
                126.9313054
        ));

        // 서남권(예: 영등포/신림 근방)
        list.add(new RegionInfo(
                RegionType.SOUTH_WEST,
                "서남권",
                37.5136866,
                126.9139207
        ));

        // 동남권(예: 강남역 근방)
        list.add(new RegionInfo(
                RegionType.SOUTH_EAST,
                "동남권",
                37.4979461,
                127.0276188
        ));

        return list;
    }

    public static JSONObject getSeoulGeoJsonData(Context context) {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.seoul_districts);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            return new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRegionForDistrict(String districtName) {
        // 도심권: 종로, 중구, 용산
        if (districtName.contains("종로") || districtName.contains("중구") || districtName.contains("용산")) {
            return "도심권";
        }
        // 동북권: 동대문, 성북, 강북, 도봉, 노원
        else if (districtName.contains("동대문") || districtName.contains("성북") || districtName.contains("강북") || districtName.contains("도봉") || districtName.contains("노원") || districtName.contains("성동") || districtName.contains("광진") || districtName.contains("중랑")) {
            return "동북권";
        }
        // 서북권: 은평, 서대문, 마포
        else if (districtName.contains("은평") || districtName.contains("서대문") || districtName.contains("마포")) {
            return "서북권";
        }
        // 서남권: 영등포, 구로, 금천, 동작, 관악
        else if (districtName.contains("영등포") || districtName.contains("구로") || districtName.contains("금천") || districtName.contains("동작") || districtName.contains("관악") || districtName.contains("양천") || districtName.contains("강서")) {
            return "서남권";
        }
        // 동남권: 강남, 송파, 서초, 강동
        else if (districtName.contains("강남") || districtName.contains("송파") || districtName.contains("서초") || districtName.contains("강동")) {
            return "동남권";
        }
        return null;
    }
}