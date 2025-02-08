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
}