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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        // RecyclerView 초기화
        rvActivities = view.findViewById(R.id.rvActivities);
        rvActivities.setLayoutManager(new LinearLayoutManager(getContext()));
        activityAdapter = new ActivityAdapter(null);
        rvActivities.setAdapter(activityAdapter);

        // Retrofit을 이용해 Django 백엔드와 연동
        ApiService apiService = RetrofitClient.getApiService();

        // 타임라인 테스트
        Call<TimelineResponse> timelineCall = apiService.getTimeline("user123");
        timelineCall.enqueue(new Callback<TimelineResponse>() {
            @Override
            public void onResponse(@NonNull Call<TimelineResponse> call, @NonNull Response<TimelineResponse> response) {
                if (response.isSuccessful()) {
                    TimelineResponse timeline = response.body();
                    if (timeline != null) {
                        Log.d("TimelineFragment", "UserId: " + timeline.getUserId());
                        Log.d("TimelineFragment", "Activities: " + timeline.getActivities());

                        // 활동 목록을 어댑터에 설정
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

        return view;
    }
}
