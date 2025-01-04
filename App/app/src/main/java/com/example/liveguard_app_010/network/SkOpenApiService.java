package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.network.model.CongestionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * SK Open API와의 통신을 위한 Retrofit 인터페이스
 */
public interface SkOpenApiService {

    /**
     * 특정 POI의 실시간 혼잡도 데이터를 가져오는 메서드
     *
     * @param appKey  발급받은 SK Open API 키
     * @param poiId   관심 장소(POI) ID
     * @param lat     위도
     * @param lon     경도
     * @return        혼잡도 데이터 응답
     */
    @GET("/puzzle/place/congestion/rltm/pois/{poiId}")
    Call<CongestionResponse> getRealTimeCongestion(
            @Header("appkey") String appKey,  // 헤더 키명
            @Path("poiId") String poiId,      // POI ID
            @Query("lat") double lat,         // 위도
            @Query("lon") double lon          // 경도
    );
}