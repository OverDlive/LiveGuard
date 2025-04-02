package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 여러 지역에 대한 공연/영화 관련 API 요청을 동시에 실행하는 클래스
 */
public class MultiPerformanceMovieRequester {

    private static final String SEOUL_APP_KEY = BuildConfig.SEOUL_APP_KEY;

    public interface RequestCallback {
        void onResponse(String xml);
        void onFailure(Exception e);
    }

    public void request(String requestUrl, RequestCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(requestUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream is = connection.getInputStream();
                        String xml = convertStreamToString(is);
                        callback.onResponse(xml);
                    } else {
                        callback.onFailure(new Exception("Response code: " + responseCode));
                    }
                } catch (Exception e) {
                    callback.onFailure(e);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    // 다른 클래스에서 단 한 번의 함수 호출로 API 요청을 할 수 있도록 하는 정적 메서드
    public static void callApi(String requestUrl, RequestCallback callback) {
        new MultiPerformanceMovieRequester().request(requestUrl, callback);
    }

    public static void main(String[] args) {
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
        MultiPerformanceMovieRequester requester = new MultiPerformanceMovieRequester();

        // 각 지역에 대해 API 요청 실행
        for (Map.Entry<String, String> entry : regionMap.entrySet()) {
            String regionName = entry.getKey();
            String areaName = entry.getValue();

            // Construct the request URL; adjust URL pattern as needed
            String requestUrl = "http://api.seoul.go.kr/performance/movie?key=" + SEOUL_APP_KEY + "&area=" + areaName;
            requester.request(requestUrl, new RequestCallback() {
                @Override
                public void onResponse(String xml) {
                    System.out.println(regionName + " 응답: " + xml);
                    latch.countDown();
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println(regionName + " 요청 에러: " + e.getMessage());
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