package com.example.liveguard_app_010.ui.result;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;              // ✅ 추가
import android.view.ViewGroup;        // ✅ 추가
import android.view.LayoutInflater;   // ✅ 추가

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liveguard_app_010.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.util.Arrays;
import java.util.List;


public class TourResultActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private RecyclerView recyclerView;
    private Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_result);

        mapView = findViewById(R.id.mapView);
        recyclerView = findViewById(R.id.recommendation_list);
        closeButton = findViewById(R.id.btn_close_result);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        closeButton.setOnClickListener(v -> finish()); // 닫기 버튼 누르면 종료
    }

    @Override
    public void onMapReady(NaverMap naverMap) {
        // 예시 마커 추가 (서울 시청)
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.5665, 126.9780));
        marker.setMap(naverMap);

        // 마커 클릭 시 추천 리스트 보여주기
        marker.setOnClickListener(overlay -> {
            showRecommendationList();
            return true;
        });
    }

    private void showRecommendationList() {
        List<String> data = Arrays.asList(
                "추천 1: 서울타워",
                "추천 2: 경복궁",
                "추천 3: 남산한옥마을"
        );
        recyclerView.setAdapter(new SimpleListAdapter(data));
    }

    static class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.ViewHolder> {
        private final List<String> items;

        SimpleListAdapter(List<String> items) {
            this.items = items;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final android.widget.TextView textView;
            ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            android.view.View view = android.view.LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    // MapView 생명주기 처리
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
