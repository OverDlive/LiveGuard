package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.BuildConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * API 호출 클래스: ApiClient를 통해 관광지 API를 호출하고,
 * SimpleXmlConverterFactory를 이용하여 XML을 자동으로 파싱하여 반환합니다.
 */
public class TouristAttractionsApiCaller {
    private final String apiKey = BuildConfig.SEOUL_APP_KEY;

    // 관광지 API를 호출하는 메서드
    public void fetchTouristAttractions(final DataCallback callback) {
        // ApiClient를 이용하여 서울 Open API Service 인스턴스 획득
        Call<TouristAttractionData> call = ApiClient.getSeoulOpenApiService().getTouristAttractions(apiKey);
        call.enqueue(new Callback<TouristAttractionData>() {
            @Override
            public void onResponse(Call<TouristAttractionData> call, Response<TouristAttractionData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TouristAttractionData data = response.body();
                    callback.onSuccess(data);
                } else {
                    callback.onFailure(new Exception("응답이 비정상적이거나 데이터가 없습니다."));
                }
            }

            @Override
            public void onFailure(Call<TouristAttractionData> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }

    // 결과 전달을 위한 콜백 인터페이스
    public interface DataCallback {
        void onSuccess(TouristAttractionData data);
        void onFailure(Exception e);
    }
}