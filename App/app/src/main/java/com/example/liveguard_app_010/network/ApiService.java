package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.models.TimelineResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/timeline/{userId}/")
    Call<TimelineResponse> getTimeline(@Path("userId") String userId);
}
