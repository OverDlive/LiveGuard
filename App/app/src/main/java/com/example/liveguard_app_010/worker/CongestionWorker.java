package com.example.liveguard_app_010.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.ApiClient;
import com.example.liveguard_app_010.network.SeoulOpenApiService;
import com.example.liveguard_app_010.network.model.CongestionResponse;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 주기적으로 서울시 혼잡도(citydata_ppltn) 데이터를 가져오는 Worker
 */
public class CongestionWorker extends Worker {

    private static final String TAG = "CongestionWorker";
    // build.gradle.kts에서 정의한 서울시 인증키 (기존 SK_APP_KEY 대신 SEOUL_APP_KEY)
    private static final String SEOUL_APP_KEY = BuildConfig.SEOUL_APP_KEY;

    public CongestionWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // 1) InputData로부터 'areaName' 받기 (없으면 기본값 "광화문")
        String areaName = getInputData().getString("areaName");
        if (areaName == null || areaName.isEmpty()) {
            areaName = "광화문";
        }

        // 2) 서울시 혼잡도 API를 동기 호출
        try {
            SeoulOpenApiService apiService = ApiClient.getSeoulOpenApiService();

            // citydata_ppltn API 요청: http://openapi.seoul.go.kr:8088/{인증키}/xml/citydata_ppltn/1/5/{areaName}
            Call<CongestionResponse> call = apiService.getRealTimeCongestion(
                    SEOUL_APP_KEY,
                    areaName
            );

            // WorkManager의 doWork()는 백그라운드 스레드에서 수행 → 동기 호출 가능
            Response<CongestionResponse> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                // 3) 응답 파싱
                CongestionResponse data = response.body();
                // <SeoulRtd.citydata_ppltn> 태그에 해당하는 객체
                CongestionResponse.CityDataPpltn cityData = data.getCitydataPpltn();

                if (cityData != null) {
                    // 예: 지역명, 현재 혼잡도, 인구 범위, 업데이트 시각 등
                    String areaNm     = cityData.getAreaNm();
                    String congestLvl = cityData.getAreaCongestLvl();
                    int ppltnMin      = cityData.getAreaPpltnMin();
                    int ppltnMax      = cityData.getAreaPpltnMax();
                    String ppltnTime  = cityData.getPpltnTime();

                    Log.d(TAG, "[Success] AREA_NM: " + areaNm
                            + ", congestLvl: " + congestLvl
                            + ", 인구수 범위: " + ppltnMin + "~" + ppltnMax
                            + ", 갱신시간: " + ppltnTime);

                    // TODO: DB 저장, SharedPreferences 저장 등 원하는 로직 수행
                    // ex) 저장 후, UI가 필요한 경우 LiveData / Broadcast / EventBus 등으로 화면에 반영
                } else {
                    Log.e(TAG, "[API Warning] cityData is null");
                }

                // 응답 코드/메시지를 확인하고 싶다면
                // <RESULT> 태그: data.getResult()
                // CongestionResponse.ResultData resultData = data.getResult();
                // if (resultData != null) {
                //     Log.d(TAG, "Result Code: " + resultData.getCode()
                //             + ", Message: " + resultData.getMessage());
                // }

            } else {
                // HTTP 오류 or Body null
                Log.e(TAG, "[HTTP Error] code=" + response.code()
                        + ", message=" + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 네트워크 등 문제 발생 시 재시도
            return Result.retry();
        }

        // 모든 작업 정상 완료
        return Result.success();
    }
}