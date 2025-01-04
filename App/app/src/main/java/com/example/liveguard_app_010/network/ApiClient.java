package com.example.liveguard_app_010.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 클라이언트 초기화 클래스
 */
public class ApiClient {

    private static final String BASE_URL = "https://apis.openapi.sk.com"; // 올바른 BASE URL
    private static Retrofit retrofit = null;

    public static SkOpenApiService getSkOpenApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(SkOpenApiService.class);
    }
}