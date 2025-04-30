package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.model.YouthTrainingFacilityData;
import com.example.liveguard_app_010.network.model.YouthTrainingFacilityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import com.example.liveguard_app_010.models.PlaceInfo;
import com.example.liveguard_app_010.network.model.YouthTrainingFacilityResponse;
import retrofit2.Response;

public class YouthTrainingFacilityApiCaller {
    private final Context context;

    public YouthTrainingFacilityApiCaller(Context context) {
        this.context = context;
    }

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

    /**
     * RecommendationEngine용 동기 장소 리스트 반환 메서드
     */
    public List<PlaceInfo> fetchPlaces() {
        List<PlaceInfo> placeList = new ArrayList<>();
        try {
            Response<YouthTrainingFacilityResponse> response = ApiClient.getSeoulOpenApiService()
                    .getYouthTrainingFacilityData(BuildConfig.SEOUL_APP_KEY)
                    .execute();
            if (response.isSuccessful() && response.body() != null) {
                YouthTrainingFacilityResponse data = response.body();
                if (data.getRow() != null) {
                    for (YouthTrainingFacilityData item : data.getRow()) {
                        PlaceInfo place = new PlaceInfo(
                            item.getUpNm(),                // 시설명 from XML <UP_NM>
                            item.getAreaCd(),   // 주소 from XML <SITEWHLADDR>
                                0.0,
                            0.0
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
}