package com.example.liveguard_app_010.network;

import android.content.Context;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.model.MuseumData;
import com.example.liveguard_app_010.network.model.MuseumDataResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import com.example.liveguard_app_010.models.PlaceInfo;

/**
 * 박물관/미술관 데이터 API 호출 클래스
 * ApiClient를 통해 Retrofit 인스턴스를 생성하고,
 * 자동 파싱된 MuseumDataResponse에서 XML 데이터를 추출해 MuseumData 객체로 반환합니다.
 */
public class MuseumDataApiCaller {

    private final Context context;

    public MuseumDataApiCaller(Context context) {
        this.context = context;
    }

    /**
     * 결과 전달을 위한 콜백 인터페이스
     */
    public interface DataCallback {
        void onSuccess(MuseumData museumData);
        void onFailure(Exception e);
    }

    /**
     * 박물관/미술관 데이터를 가져오는 메서드
     * BuildConfig.SEOUL_APP_KEY를 이용하여 API 키를 전달합니다.
     *
     * @param callback API 호출 결과를 전달받기 위한 콜백 인터페이스
     */
    public void fetchMuseumData(final DataCallback callback) {
        // API 인터페이스가 MuseumDataResponse를 반환하도록 정의되어 있다고 가정합니다.
        Call<MuseumDataResponse> call = ApiClient.getSeoulOpenApiService().getMuseumData(BuildConfig.SEOUL_APP_KEY);
        call.enqueue(new Callback<MuseumDataResponse>() {
            @Override
            public void onResponse(Call<MuseumDataResponse> call, Response<MuseumDataResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MuseumDataResponse museumResponse = response.body();
                    if (museumResponse.getRow() != null && !museumResponse.getRow().isEmpty()) {
                        // 첫 번째 row 데이터를 callback으로 전달 (필요에 따라 전체 리스트를 처리할 수 있습니다)
                        MuseumData museumData = museumResponse.getRow().get(0);
                        callback.onSuccess(museumData);
                    } else {
                        callback.onFailure(new Exception("응답 데이터의 row가 비어있습니다."));
                    }
                } else {
                    callback.onFailure(new Exception("응답이 비정상적이거나 데이터가 없습니다."));
                }
            }

            @Override
            public void onFailure(Call<MuseumDataResponse> call, Throwable t) {
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
            Response<MuseumDataResponse> response = ApiClient.getSeoulOpenApiService()
                    .getMuseumData(BuildConfig.SEOUL_APP_KEY)
                    .execute();
            if (response.isSuccessful() && response.body() != null) {
                MuseumDataResponse museumResponse = response.body();
                if (museumResponse.getRow() != null) {
                    for (MuseumData data : museumResponse.getRow()) {
                        PlaceInfo place = new PlaceInfo(
                                data.getBplcNm(),       // place name from XML <BPLCNM>
                                data.getCapt(),         // description from XML <CAPT>
                                Double.parseDouble(data.getY()), // latitude from XML <Y>
                                Double.parseDouble(data.getX())  // longitude from XML <X>
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