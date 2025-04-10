package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.model.MuseumData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 박물관/미술관 데이터 API 호출 클래스
 * ApiClient를 통해 Retrofit 인스턴스를 생성하고,
 * 응답받은 XML 문자열을 MuseumDataXmlParser를 이용하여 파싱한 결과를 반환합니다.
 */
public class MuseumDataApiCaller {

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
        // 응답을 String 형태로 받기 위해 Call<String> 사용 (ScalarsConverterFactory가 적용된 ApiClient)
        Call<String> call = ApiClient.getSeoulOpenApiService().getMuseumData(BuildConfig.SEOUL_APP_KEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String xmlData = response.body();
                    // XML 파서를 통해 박물관 데이터를 파싱함
                    MuseumDataXmlParser parser = new MuseumDataXmlParser();
                    MuseumData museumData = parser.parse(xmlData);
                    callback.onSuccess(museumData);
                } else {
                    callback.onFailure(new Exception("응답이 비정상적이거나 데이터가 없습니다."));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }
}