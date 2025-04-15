package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.network.model.ShoppingDataResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingDataApiCaller {

    public interface DataCallback {
        void onSuccess(ShoppingDataResponse response);
        void onFailure(Exception e);
    }

    public void fetchShoppingData(final DataCallback callback) {
        // ApiClient의 getShoppingData() 메서드가 아래와 같이 정의되어 있어야 합니다.
        // @GET("{apiKey}/xml/TbVwShopping/1/5/")
        // Call<ShoppingDataResponse> getShoppingData(@Path("apiKey") String apiKey);
        Call<ShoppingDataResponse> call = ApiClient.getSeoulOpenApiService().getShoppingData(BuildConfig.SEOUL_APP_KEY);
        call.enqueue(new Callback<ShoppingDataResponse>() {
            @Override
            public void onResponse(Call<ShoppingDataResponse> call, Response<ShoppingDataResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("응답이 비정상적이거나 데이터가 없습니다."));
                }
            }

            @Override
            public void onFailure(Call<ShoppingDataResponse> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }
}