package com.example.liveguard_app_010.cluster;

import com.naver.maps.geometry.LatLng;

/**
 * 클러스터링을 위한 기본 ClusterItem 인터페이스
 */
public interface ClusterItem {
    /**
     * 클러스터링 시 사용될 위치 반환
     *
     * @return 위치 정보
     */
    LatLng getPosition();
}