package com.example.liveguard_app_010.network;

import android.content.Context;
import android.util.Log;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.model.HanokExperienceResponse;
import com.example.liveguard_app_010.network.model.HanokExperienceData;
import com.example.liveguard_app_010.models.PlaceInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HanokDataApiCaller {

    private final Context context;

    public HanokDataApiCaller(Context context) {
        this.context = context;
    }

    public interface DataCallback {
        void onSuccess(HanokExperienceResponse response);
        void onFailure(Exception e);
    }

    public void fetchHanokData(final DataCallback callback) {
        Call<HanokExperienceResponse> call =
                ApiClient.getSeoulOpenApiService()
                        .getHanokData(BuildConfig.SEOUL_APP_KEY);
        call.enqueue(new Callback<HanokExperienceResponse>() {
            @Override
            public void onResponse(Call<HanokExperienceResponse> call, Response<HanokExperienceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("응답이 비정상적이거나 데이터가 없습니다."));
                }
            }

            @Override
            public void onFailure(Call<HanokExperienceResponse> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }

    /**
     * RecommendationEngine용 동기 장소 리스트 반환 메서드
     */
    public List<PlaceInfo> fetchPlaces() {
        List<PlaceInfo> placeList = new ArrayList<>();
        Log.d("HanokDataApiCaller", "fetchPlaces(): starting synchronous fetch");
        try {
            Response<HanokExperienceResponse> response = ApiClient.getSeoulOpenApiService()
                    .getHanokData(BuildConfig.SEOUL_APP_KEY)
                    .execute();

            if (response.isSuccessful() && response.body() != null) {
                HanokExperienceResponse hanokResponse = response.body();
                if (hanokResponse.getRow() != null) {
                    for (HanokExperienceData item : hanokResponse.getRow()) {
                        PlaceInfo place = new PlaceInfo(
                                item.getBplcNm(),
                                item.getCapt(),
                                Double.parseDouble(item.getY()),
                                Double.parseDouble(item.getX())
                        );
                        placeList.add(place);
                        Log.d("HanokDataApiCaller", "Added hanok place: name=" + item.getBplcNm()
                                + ", description=" + item.getCapt()
                                + ", lat=" + item.getY()
                                + ", lng=" + item.getX());
                    }
                }
            }
            Log.d("HanokDataApiCaller", "fetchPlaces(): total places fetched = " + placeList.size());
        } catch (IOException e) {
            Log.e("HanokDataApiCaller", "fetchPlaces() failed", e);
        }
        return placeList;
    }
}