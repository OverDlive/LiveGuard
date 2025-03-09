package com.example.liveguard_app_010.ui.home;

import android.util.Log;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.ApiClient;
import com.example.liveguard_app_010.network.SeoulOpenApiService;
import com.example.liveguard_app_010.network.model.CongestionResponse;
import com.example.liveguard_app_010.region.RegionManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CongestionManager {
    // HomeFragment.LocationData와 RegionManager.RegionType를 사용
    private static Map<RegionManager.RegionType, List<HomeFragment.LocationData>> regionLocationMap;

    public static void setRegionLocationMap(Map<RegionManager.RegionType, List<HomeFragment.LocationData>> map) {
        regionLocationMap = map;
    }

    /**
     * 특정 권역의 위치 데이터를 순회하며 혼잡도 API를 호출합니다.
     */
    public static void loadRegionMarkers(RegionManager.RegionType regionType) {
        List<HomeFragment.LocationData> locations = regionLocationMap.get(regionType);
        if (locations == null || locations.isEmpty()) {
            Log.d("CongestionManager", "해당 권역에 대한 위치 데이터가 없습니다: " + regionType);
            return;
        }
        for (HomeFragment.LocationData loc : locations) {
            loadAndShowCongestion(loc.name, loc.lat, loc.lng);
        }
    }

    /**
     * 특정 지점에 대해 혼잡도 API를 호출하고, 결과를 MarkerManager를 통해 지도에 표시합니다.
     */
    public static void loadAndShowCongestion(String subAreaName, double lat, double lng) {
        String encodedAreaName;
        try {
            encodedAreaName = URLEncoder.encode(subAreaName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encodedAreaName = subAreaName;
        }

        SeoulOpenApiService service = ApiClient.getSeoulOpenApiService();
        Call<CongestionResponse> call = service.getRealTimeCongestion(BuildConfig.SEOUL_APP_KEY, encodedAreaName);
        call.enqueue(new Callback<CongestionResponse>() {
            @Override
            public void onResponse(Call<CongestionResponse> call, Response<CongestionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CongestionResponse data = response.body();
                    CongestionResponse.CityDataPpltn cityData = data.getCitydataPpltn();
                    if (cityData != null) {
                        String areaNm = cityData.getAreaNm();
                        String congestLvl = cityData.getAreaCongestLvl();
                        Log.d("CongestionManager", "지역명: " + areaNm + ", 혼잡도: " + congestLvl);
                        MarkerManager.showMarker(areaNm, congestLvl, lat, lng);
                    } else {
                        Log.e("CongestionManager", "SeoulRtd.citydata_ppltn 태그가 없습니다. => " + subAreaName);
                    }
                } else {
                    Log.e("CongestionManager", "응답 실패: code=" + response.code() + ", msg=" + response.message());
                }
            }
            @Override
            public void onFailure(Call<CongestionResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("CongestionManager", "네트워크 오류: " + t.getMessage());
            }
        });
    }
}