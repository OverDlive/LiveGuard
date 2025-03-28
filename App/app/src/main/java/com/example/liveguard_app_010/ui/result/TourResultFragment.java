package com.example.liveguard_app_010.ui.result;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.ui.home.HomeFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.geometry.LatLng;

import java.util.Arrays;
import java.util.List;

public class TourResultFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private RecyclerView recyclerView;

    public TourResultFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // 닫기 버튼: 전체 백스택 제거 후 홈으로 전환
        Button btnClose = view.findViewById(R.id.btn_close_result);
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> {
                Log.d("TourResultFragment", "닫기 버튼 클릭됨, 백스택 전체 제거 후 홈 전환");
                // 백스택 전체 제거 (Feature, Onboarding 등 모두 제거)
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                // 홈 화면(HomeFragment)으로 전환 (전체 화면을 덮는 새로운 창)
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new HomeFragment())
                        .commit();
            });
        }

        mapView = view.findViewById(R.id.mapView);
        recyclerView = view.findViewById(R.id.recommendation_list);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        // 예시: 서울 시청 위치에 마커 추가
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.5665, 126.9780));
        marker.setMap(naverMap);

        // 마커 클릭 시 바텀시트(리사이클러뷰)에 데이터 표시
        marker.setOnClickListener(overlay -> {
            showRecommendationList();
            return true;
        });
    }

    private void showRecommendationList() {
        List<String> data = Arrays.asList("추천 1: 서울타워", "추천 2: 경복궁", "추천 3: 남산한옥마을");
        recyclerView.setAdapter(new SimpleListAdapter(data));
    }

    static class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.ViewHolder> {
        private final List<String> items;

        public SimpleListAdapter(List<String> items) {
            this.items = items;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final android.widget.TextView textView;
            ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }

        @NonNull
        @Override
        public SimpleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SimpleListAdapter.ViewHolder holder, int position) {
            holder.textView.setText(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    // MapView 생명주기 처리
    @Override public void onResume() { super.onResume(); mapView.onResume(); }
    @Override public void onPause() { super.onPause(); mapView.onPause(); }
    @Override public void onDestroyView() { super.onDestroyView(); mapView.onDestroy(); }
    @Override public void onLowMemory() { super.onLowMemory(); mapView.onLowMemory(); }
}
