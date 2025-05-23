package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.network.model.CongestionResponse;
import com.example.liveguard_app_010.network.model.HanokExperienceResponse;
import com.example.liveguard_app_010.network.model.MuseumDataResponse;
import com.example.liveguard_app_010.network.model.PerformanceMovieResponse;
import com.example.liveguard_app_010.network.model.ShoppingDataResponse;
import com.example.liveguard_app_010.network.model.YouthTrainingFacilityResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 서울시 혼잡도 Open API와의 통신을 위한 Retrofit 인터페이스
 */
public interface SeoulOpenApiService {

    /**
     * 특정 지역의 실시간 혼잡도 데이터를 가져오는 메서드
     *
     * 예) http://openapi.seoul.go.kr:8088/{apiKey}/xml/citydata_ppltn/1/5/{areaName}
     *
     * @param apiKey   서울시 공공데이터 인증키
     * @param areaName 조회할 지역명 ("광화문", "명동" 등)
     * @return         혼잡도 데이터 응답
     */
    // 혼잡도
    @GET("{apiKey}/xml/citydata_ppltn/1/5/{areaName}")
    Call<CongestionResponse> getRealTimeCongestion(
            @Path("apiKey") String apiKey,
            @Path("areaName") String areaName
    );

    // 지역별 공연/영화 정보
    @GET("{apiKey}/xml/LOCALDATA_031302_{areaName}/1/5/")
    Call<PerformanceMovieResponse> getPerformanceMovieData(
            @Path("apiKey") String apiKey,
            @Path("areaName") String areaName
    );

    // 박물관, 미술관(완)
    @GET("{apiKey}/xml/LOCALDATA_030705/1/5/")
    Call<MuseumDataResponse> getMuseumData(
            @Path("apiKey") String apiKey
    );

    // 한옥 체험(완)
    @GET("{apiKey}/xml/LOCALDATA_031106/1/5/")
    Call<HanokExperienceResponse> getHanokData(
            @Path("apiKey") String apiKey
    );

    // 청소년 수련시설(완)
    @GET("{apiKey}/xml/LampScpgmtb/1/5/")
    Call<YouthTrainingFacilityResponse> getYouthTrainingFacilityData(
            @Path("apiKey") String apiKey
    );

    // 관광지 정보(수정 필요)
    @GET("{apiKey}/xml/TbVwAttractions/1/5/")
    Call<TouristAttractionData> getTouristAttractions(
            @Path("apiKey") String apiKey
    );

    // 쇼핑 정보
    @GET("{apiKey}/xml/TbVwShopping/1/5/")
    Call<ShoppingDataResponse> getShoppingData(
            @Path("apiKey") String apiKey
    );
}