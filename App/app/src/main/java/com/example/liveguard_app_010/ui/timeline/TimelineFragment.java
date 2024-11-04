package com.example.liveguard_app_010.ui.timeline;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.models.Timeline;
import com.example.liveguard_app_010.network.ApiService;
import com.example.liveguard_app_010.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimelineFragment extends Fragment {

    private RecyclerView rvActivities;
    private ActivityAdapter activityAdapter;
    private ApiService apiService;

    @Nullable
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
        fetchTimeline("user123"); // 실제 사용자 이름으로 변경 가능

        return view;
    }

    private void fetchTimeline(String username) {
        Call<List<Timeline>> timelineCall = apiService.getTimeline(username);
        timelineCall.enqueue(new Callback<List<Timeline>>() {
            @Override
            public void onResponse(@NonNull Call<List<Timeline>> call, @NonNull Response<List<Timeline>> response) {
                if (response.isSuccessful()) {
                    List<Timeline> timelineList = response.body();
                    if (timelineList != null) {
                        activityAdapter.setActivityList(timelineList);
                        Log.d("TimelineFragment", "Fetched " + timelineList.size() + " timeline items.");
                    }
                } else {
                    Log.e("TimelineFragment", "Timeline Request failed. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Timeline>> call, @NonNull Throwable t) {
                Log.e("TimelineFragment", "Timeline Error: " + t.getMessage());
            }
        });
    }
}
