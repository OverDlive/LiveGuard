package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.model.PerformanceMovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class PerformanceMovieApiCaller {

    public interface DataCallback {
        void onSuccess(List<PerformanceMovieResponse> responses);
        void onFailure(Exception e);
    }

    /**
     * 서울시 구별 공연/영화 데이터를 모두 호출하여 리스트로 반환합니다.
     */
    public void fetchPerformanceMovieData(final String code, final DataCallback callback) {
        final List<PerformanceMovieResponse> responseList = new ArrayList<>();

        Call<PerformanceMovieResponse> call = ApiClient.getSeoulOpenApiService().getPerformanceMovieData(BuildConfig.SEOUL_APP_KEY, code);
        call.enqueue(new Callback<PerformanceMovieResponse>() {
            @Override
            public void onResponse(Call<PerformanceMovieResponse> call, Response<PerformanceMovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseList.add(response.body());
                    callback.onSuccess(responseList);
                } else {
                    callback.onFailure(new Exception("응답이 비정상적이거나 데이터가 없습니다."));
                }
            }

            @Override
            public void onFailure(Call<PerformanceMovieResponse> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }
}