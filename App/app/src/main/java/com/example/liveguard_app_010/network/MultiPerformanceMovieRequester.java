package com.example.liveguard_app_010.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.model.CongestionResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 여러 지역에 대한 공연/영화 관련 API 요청을 동시에 실행하는 클래스
 */
public class MultiPerformanceMovieRequester {

    private static final String SEOUL_APP_KEY = BuildConfig.SEOUL_APP_KEY;

    public static void main(String[] args) {
        // ApiClient에서 서울시 Open API 서비스 인스턴스 가져오기
        SeoulOpenApiService service = ApiClient.getSeoulOpenApiService();

        // 지역명과 해당 엔드포인트를 구성할 코드 매핑
        Map<String, String> regionMap = new HashMap<>();
        regionMap.put("강남구", "GN");
        regionMap.put("강동구", "GD");
        regionMap.put("강북구", "GB");
        regionMap.put("강서구", "GS");
        regionMap.put("관악구", "GA");
        regionMap.put("광진구", "GJ");
        regionMap.put("구로구", "GG");
        regionMap.put("금천구", "GC");
        regionMap.put("노원구", "NW");
        regionMap.put("도봉구", "DB");
        regionMap.put("동대문구", "DD");
        regionMap.put("동작구", "DJ");
        regionMap.put("마포구", "MP");
        regionMap.put("서대문구", "SD");
        regionMap.put("서초구", "SC");
        regionMap.put("성동구", "SDC");
        regionMap.put("성북구", "SB");
        regionMap.put("송파구", "SP");
        regionMap.put("양천구", "YC");
        regionMap.put("영등포구", "YDP");

        // 비동기 요청 완료를 기다리기 위한 CountDownLatch
        CountDownLatch latch = new CountDownLatch(regionMap.size());

        // 각 지역에 대해 API 요청 실행
        for (Map.Entry<String, String> entry : regionMap.entrySet()) {
            String regionName = entry.getKey();
            String areaName = entry.getValue();

            Call<CongestionResponse> call = service.getPerformanceMovieData(SEOUL_APP_KEY, areaName);
            call.enqueue(new Callback<CongestionResponse>() {
                @Override
                public void onResponse(Call<CongestionResponse> call, Response<CongestionResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        System.out.println(regionName + " 응답: " + response.body());
                    } else {
                        System.out.println(regionName + " 요청 실패: " + response.message());
                    }
                    latch.countDown();
                }

                @Override
                public void onFailure(Call<CongestionResponse> call, Throwable t) {
                    System.out.println(regionName + " 요청 에러: " + t.getMessage());
                    latch.countDown();
                }
            });
        }

        // 모든 요청이 완료될 때까지 대기
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("모든 지역 요청 완료");
    }
}