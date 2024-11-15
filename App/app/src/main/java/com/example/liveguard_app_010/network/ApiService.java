package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.models.Timeline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/timeline/")
    Call<List<Timeline>> getTimeline(@Query("username") String username);
}
