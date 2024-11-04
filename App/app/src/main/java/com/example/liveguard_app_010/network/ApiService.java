package com.example.liveguard_app_010.network;

import com.example.liveguard_app_010.models.Timeline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/timeline/{username}/")
    Call<List<Timeline>> getTimeline(@Path("username") String username);
}