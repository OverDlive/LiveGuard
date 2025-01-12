package com.example.liveguard_app_010.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.ApiClient;
import com.example.liveguard_app_010.network.SkOpenApiService;
import com.example.liveguard_app_010.network.model.CongestionResponse;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 주기적으로 혼잡도 데이터를 가져오는 Worker
 */
public class CongestionWorker extends Worker {

    private static final String TAG = "CongestionWorker";
    private static final String SK_APP_KEY = BuildConfig.SK_APP_KEY;

    public CongestionWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // 1. 파라미터로부터 poiId, lat, lon 등을 가져오기 (옵션)
        String poiId = getInputData().getString("poiId");
        double lat = getInputData().getDouble("lat", 37.51723636);
        double lon = getInputData().getDouble("lon", 126.90344592);

        // 2. 혼잡도 API 동기 호출(예: Retrofit execute()) - 주의: 네트워크 on main thread 금지
        //    WorkManager는 백그라운드 스레드에서 doWork()가 실행되므로 동기 호출 가능
        try {
            SkOpenApiService apiService = ApiClient.getSkOpenApiService();
            Call<CongestionResponse> call = apiService.getRealTimeCongestion(
                    SK_APP_KEY,
                    poiId,
                    lat,
                    lon
            );

            // 동기 호출
            Response<CongestionResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                CongestionResponse data = response.body();
                String code = data.getStatus().getCode();
                if ("00".equals(code)) {
                    // 성공적으로 받아왔으므로, DB 저장 혹은 SharedPreferences 저장 등
                    // -> 화면에 즉시 반영하려면 LiveData / Broadcast / EventBus 등 활용
                    Log.d(TAG, "[Success] " + data.getContents().getPoiName());
                } else {
                    // API 오류
                    Log.e(TAG, "[API Error] code=" + code
                            + ", message=" + data.getStatus().getMessage());
                }
            } else {
                Log.e(TAG, "[HTTP Error] code=" + response.code()
                        + ", message=" + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.retry(); // 네트워크 오류 시 재시도
        }

        // 모든 작업이 정상 완료된 경우
        return Result.success();
    }
}