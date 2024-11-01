package com.example.liveguard_app_010.ui.timeline;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.models.TimelineResponse;
import com.example.liveguard_app_010.network.ApiService;
import com.example.liveguard_app_010.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.recyclerview.widget.RecyclerView;

public class TimelineFragment extends Fragment {

    private RecyclerView rvActivities;
    private ActivityAdapter activityAdapter;
    private ApiService apiService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        // RecyclerView 초기화
        rvActivities = view.findViewById(R.id.rvActivities);
        rvActivities.setLayoutManager(new LinearLayoutManager(getContext()));
        activityAdapter = new ActivityAdapter(null);
        rvActivities.setAdapter(activityAdapter);

        // ApiService 초기화
        apiService = RetrofitClient.getApiService();

        // 타임라인 데이터 가져오기
        fetchTimeline("user123");

        return view;
    }

    private void fetchTimeline(String username) {
        Call<TimelineResponse> timelineCall = apiService.getTimeline(username);
        timelineCall.enqueue(new Callback<TimelineResponse>() {
            @Override
            public void onResponse(@NonNull Call<TimelineResponse> call, @NonNull Response<TimelineResponse> response) {
                if (response.isSuccessful()) {
                    TimelineResponse timeline = response.body();
                    if (timeline != null) {
                        activityAdapter.setActivityList(timeline.getActivities());
                    }
                } else {
                    Log.e("TimelineFragment", "Timeline Request failed. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TimelineResponse> call, @NonNull Throwable t) {
                Log.e("TimelineFragment", "Timeline Error: " + t.getMessage());
            }
        });
    }
}
