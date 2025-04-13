package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.model.CongestionResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HanokDataApiCaller {

    public interface DataCallback {
        void onSuccess(CongestionResponse congestionResponse);
        void onFailure(Exception e);
    }

    public void fetchHanokData(final DataCallback callback) {
        // ApiClient가 ScalarsConverterFactory와 SimpleXmlConverterFactory를 올바르게 설정했다면,
        // API 인터페이스의 getHanokData 메서드가 CongestionResponse를 반환하도록 되어 있어야 합니다.
        Call<CongestionResponse> call = ApiClient.getSeoulOpenApiService().getHanokData(BuildConfig.SEOUL_APP_KEY);
        call.enqueue(new Callback<CongestionResponse>() {
            @Override
            public void onResponse(Call<CongestionResponse> call, Response<CongestionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("응답이 비정상적이거나 데이터가 없습니다."));
                }
            }

            @Override
            public void onFailure(Call<CongestionResponse> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }
}