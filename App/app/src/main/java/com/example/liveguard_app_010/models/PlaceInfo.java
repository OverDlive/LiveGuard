package com.example.liveguard_app_010.models;

import java.io.Serializable;

/**
 * 추천 및 지도 표시를 위한 장소 정보 모델
 */
public class PlaceInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 장소 제목 (ex: “경복궁”) */
    public String title;
    /** 장소 설명 (ex: “조선의 대표 궁궐”) */
    public String description;
    /** 위도 */
    public double lat;
    /** 경도 */
    public double lng;

    public PlaceInfo(String title, String description, double lat, double lng) {
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
    }

    // 필요하다면 getter/setter도 추가 가능
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
}