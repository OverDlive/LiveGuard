package com.example.liveguard_app_010.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.liveguard_app_010.BuildConfig;
import com.example.liveguard_app_010.R;
import com.example.liveguard_app_010.network.ApiClient;
import com.example.liveguard_app_010.network.SkOpenApiService;
import com.example.liveguard_app_010.network.model.CongestionResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private BottomSheetBehavior<View> bottomSheetBehavior;
    private static final String SK_APP_KEY = BuildConfig.SK_APP_KEY; // BuildConfig에서 가져온 API 키
    private NaverMap naverMap; // onMapReady에서 받은 네이버 맵 객체를 저장
    private static final String TAG = "HomeFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 1) 바텀 시트 초기화
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // 2) 화면 높이 가져오기
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        // 3) 바텀 시트 설정
        bottomSheetBehavior.setPeekHeight(500);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.5f);
        bottomSheetBehavior.setExpandedOffset((int) (screenHeight * 0.02f));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // 4) 바텀 시트 콜백 (필요 시 사용)
        bottomSheetBehavior.addBottomSheetCallback(
                new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {}
                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
                }
        );

        // 5) 지도 Fragment 가져오기
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.map_fragment, mapFragment)
                    .commit();
        }

        // 6) onMapReady 콜백
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull NaverMap map) {
                // 네이버 지도 객체를 전역 변수로 저장
                naverMap = map;

                // 지도를 탭하면 바텀시트 닫기
                naverMap.setOnMapClickListener((pointF, latLng) -> {
                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                });

                // 7) API 호출 후, 데이터 받아서 지도 위에 표시
                // (예: 특정 POI ID로부터 혼잡도 조회)
                String poiId = "1172091"; // 타임스퀘어 POI ID
                double lat = 37.51723636;  // 실제 위도
                double lon = 126.90344592; // 실제 경도

                // 실제 API 호출
                loadCongestionData(poiId, lat, lon);
            }
        });

        return view;
    }

    public void loadCongestionData(String poiId, double lat, double lon) {
        SkOpenApiService service = ApiClient.getSkOpenApiService();
        Call<CongestionResponse> call = service.getRealTimeCongestion(
                SK_APP_KEY,
                poiId,
                lat,
                lon
        );

        call.enqueue(new Callback<CongestionResponse>() {
            @Override
            public void onResponse(Call<CongestionResponse> call, Response<CongestionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CongestionResponse data = response.body();

                    // status 코드와 메시지 확인
                    if (data.getStatus() != null) {
                        String code = data.getStatus().getCode();        // ex) "00"
                        String message = data.getStatus().getMessage(); // ex) "success"
                        int totalCount = data.getStatus().getTotalCount();

                        if ("00".equals(code)) {
                            // 성공 로직
                            if (data.getContents() != null) {
                                CongestionResponse.Contents contents = data.getContents();

                                // poiId, poiName
                                String poiName = contents.getPoiName();

                                // rltm 리스트
                                List<CongestionResponse.RltmItem> rltmList = contents.getRltm();
                                if (rltmList != null && !rltmList.isEmpty()) {
                                    // 이제 rltmList를 이용해 지도에 마커 표시
                                    showMarkers(rltmList);
                                } else {
                                    Log.e(TAG, "rltm is empty or null");
                                }
                            }
                        } else {
                            // code != "00"인 경우 => 에러 처리
                            Log.e(TAG, "API 오류: code=" + code + ", message=" + message);
                        }
                    } else {
                        // status 자체가 null
                        Log.e(TAG, "API 응답에 status가 없음");
                    }
                } else {
                    Log.e(TAG, "응답 실패: " + response.code() + ", " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CongestionResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "네트워크 오류: " + t.getMessage());
            }
        });
    }

    private void showMarkers(List<CongestionResponse.RltmItem> rltmList) {
        // 여기서 rltmList를 돌면서 마커 표시
        for (CongestionResponse.RltmItem item : rltmList) {
            double lat = 37.51723636; // 만약 특정 좌표가 있으면..
            double lon = 126.90344592;
            // 사실 응답 안에 lat, lon이 없으므로,
            // poiId가 고정이면 poi의 위치는 고정값을 세팅해야 할 수도 있음

            Marker marker = new Marker();
            marker.setPosition(new LatLng(lat, lon));

            // 혼잡도 레벨 예시
            int level = item.getCongestionLevel();
            switch (level) {
                case 3: // 예시로 HIGH
                    marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_red));
                    break;
                case 2: // MEDIUM
                    marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_yellow));
                    break;
                case 1: // LOW
                default:
                    marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker_green));
                    break;
            }

            marker.setCaptionText("혼잡도: " + item.getCongestion() + "\nLevel: " + level);
            marker.setMap(naverMap);
        }
    }}