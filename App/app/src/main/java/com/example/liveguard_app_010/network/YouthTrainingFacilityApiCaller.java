package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.model.YouthTrainingFacilityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouthTrainingFacilityApiCaller {

    public interface DataCallback {
        void onSuccess(YouthTrainingFacilityResponse response);
        void onFailure(Exception e);
    }

    public void fetchYouthTrainingFacilityData(final DataCallback callback) {
        Call<YouthTrainingFacilityResponse> call = ApiClient.getSeoulOpenApiService().getYouthTrainingFacilityData(BuildConfig.SEOUL_APP_KEY);
        call.enqueue(new Callback<YouthTrainingFacilityResponse>() {
            @Override
            public void onResponse(Call<YouthTrainingFacilityResponse> call, Response<YouthTrainingFacilityResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("응답이 비정상적이거나 데이터가 없습니다."));
                }
            }

            @Override
            public void onFailure(Call<YouthTrainingFacilityResponse> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }
}