package com.example.liveguard_app_010.region;

import androidx.annotation.NonNull;
import com.example.liveguard_app_010.cluster.ClusterItem;

import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * 네이버 지도 클러스터링을 위한 RegionClusterItem 예시
 * - 반드시 ClusterItem 인터페이스를 구현해야 합니다.
 */
public class RegionClusterItem implements ClusterItem {

    // 필수: 클러스터링에 필요한 위치 정보
    private final LatLng position;

    // 선택: 권역 이름(도심권, 동북권 등)
    private final String regionName;

    // 선택: 권역 타입 (enum 등)
    private final RegionManager.RegionType regionType;

    /**
     * 권역 클러스터 아이템 생성자
     *
     * @param lat         권역 대표 위도
     * @param lng         권역 대표 경도
     * @param regionName  권역 이름 (예: "도심권")
     * @param regionType  권역 유형 (예: RegionManager.RegionType.CITY_CENTER)
     */
    public RegionClusterItem(double lat, double lng, String regionName, RegionManager.RegionType regionType) {
        this.position = new LatLng(lat, lng);
        this.regionName = regionName;
        this.regionType = regionType;
    }

    /**
     * ClusterItem에서 꼭 구현해야 하는 메서드:
     * - 아이템이 지도에 표시될 좌표
     */
    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    // 권역 이름 Getter
    public String getRegionName() {
        return regionName;
    }

    // 권역 타입 Getter
    public RegionManager.RegionType getRegionType() {
        return regionType;
    }

    /**
     * 서울시 5대 권역 정보를 담은 ClusterItem 리스트를 반환합니다.
     * 필요 시 원하는 위도/경도 및 이름, 타입을 자유롭게 수정해 사용할 수 있습니다.
     */
    public static List<RegionClusterItem> getSeoulFiveRegions() {
        List<RegionClusterItem> seoulFiveRegions = new ArrayList<>();

        // 예시 좌표 및 명칭 (임의 값이므로 실제 프로젝트 상황에 맞게 수정하세요)
        seoulFiveRegions.add(new RegionClusterItem(
                37.5662952, // 도심권 예시 위도
                126.9779451, // 도심권 예시 경도
                "도심권",
                RegionManager.RegionType.CITY_CENTER
        ));
        seoulFiveRegions.add(new RegionClusterItem(
                37.640949, // 동북권 예시 위도
                127.070233, // 동북권 예시 경도
                "동북권",
                RegionManager.RegionType.NORTH_EAST
        ));
        seoulFiveRegions.add(new RegionClusterItem(
                37.592128, // 서북권 예시 위도
                126.934387, // 서북권 예시 경도
                "서북권",
                RegionManager.RegionType.NORTH_WEST
        ));
        seoulFiveRegions.add(new RegionClusterItem(
                37.530221, // 서남권 예시 위도
                126.882732, // 서남권 예시 경도
                "서남권",
                RegionManager.RegionType.SOUTH_WEST
        ));
        seoulFiveRegions.add(new RegionClusterItem(
                37.496503, // 동남권 예시 위도
                127.027543, // 동남권 예시 경도
                "동남권",
                RegionManager.RegionType.SOUTH_EAST
        ));

        return seoulFiveRegions;
    }
}