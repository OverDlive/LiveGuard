package com.example.liveguard_app_010.network;

import android.content.Context;
import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.models.PlaceInfo;
import com.example.liveguard_app_010.network.ApiClient;
import com.example.liveguard_app_010.network.TouristAttractionData;
import com.example.liveguard_app_010.network.model.TouristAttraction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

/**
 * API 호출 클래스: ApiClient를 통해 관광지 API를 호출하고,
 * SimpleXmlConverterFactory를 이용하여 XML을 자동으로 파싱하여 반환합니다.
 */
public class TouristAttractionsApiCaller {
    private final Context context;

    public TouristAttractionsApiCaller(Context context) {
        this.context = context;
    }

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

    /**
     * RecommendationEngine용 동기 장소 리스트 반환 메서드
     */
    public List<PlaceInfo> fetchPlaces() {
        List<PlaceInfo> placeList = new ArrayList<>();
        try {
            Response<TouristAttractionData> response = ApiClient.getSeoulOpenApiService()
                    .getTouristAttractions(BuildConfig.SEOUL_APP_KEY)
                    .execute();
            if (response.isSuccessful() && response.body() != null) {
                TouristAttractionData data = response.body();
                if (data.getRow() != null) {
                    for (TouristAttraction item : data.getRow()) {
                        PlaceInfo place = new PlaceInfo(
                            item.getPostSj(),   // attraction title from XML <POST_SJ>
                            item.getAddress(),  // description: address from XML <ADDRESS>
                            0.0,                // latitude placeholder (to be set via geocoding)
                            0.0                 // longitude placeholder (to be set via geocoding)
                        );
                        placeList.add(place);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return placeList;
    }

    // 결과 전달을 위한 콜백 인터페이스
    public interface DataCallback {
        void onSuccess(TouristAttractionData data);
        void onFailure(Exception e);
    }
}