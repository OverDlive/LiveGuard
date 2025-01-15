package com.example.liveguard_app_010.network;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Retrofit 클라이언트 초기화 클래스
 * (서울시 혼잡도 API에 맞춤)
 */
public class ApiClient {

    // 서울시 Open API 기본 URL
    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/";

    private static Retrofit retrofit = null;

    /**
     * 서울시 혼잡도 Open API Service 제공
     */
    public static SeoulOpenApiService getSeoulOpenApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create()) // XML 파싱
                    .build();
        }
        return retrofit.create(SeoulOpenApiService.class);
    }
}