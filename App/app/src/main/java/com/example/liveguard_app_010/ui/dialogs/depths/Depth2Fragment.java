package com.example.liveguard_app_010.ui.dialogs.depths;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.liveguard_app_010.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Depth2Fragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_depth2, container, false);

        // 위치 서비스 초기화
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // "이전 단계" 버튼 클릭 시 뒤로 가기
        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        // "현재 위치 조회" 버튼 클릭 시 위치 조회
        Button currentLocationButton = view.findViewById(R.id.current_location_button);
        currentLocationButton.setOnClickListener(v -> getCurrentLocation());

        return view;
    }

    // 위치 권한을 확인하고, 권한이 없으면 요청
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fetchLocation();
        }
    }

    // 위치를 가져오는 메서드
    private void fetchLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // 현재 위치를 가져오면 그 위치를 사용 (예: Toast로 출력)
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Toast.makeText(requireContext(), "현재 위치: " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(requireContext(), "위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            } else {
                Toast.makeText(requireContext(), "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
