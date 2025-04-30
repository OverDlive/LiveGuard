package com.example.liveguard_app_010.utils;

import android.content.Context;
import android.location.Location;

import com.example.liveguard_app_010.models.PlaceInfo;
import com.example.liveguard_app_010.network.MuseumDataApiCaller;
import com.example.liveguard_app_010.network.TouristAttractionsApiCaller;
import com.example.liveguard_app_010.network.HanokDataApiCaller;
import com.example.liveguard_app_010.network.YouthTrainingFacilityApiCaller;
import com.example.liveguard_app_010.network.ShoppingDataApiCaller;
import com.example.liveguard_app_010.network.PerformanceMovieApiCaller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * RecommendationEngine는 사용자의 온보딩 선택과 위치 정보를 바탕으로
 * 서울 시내의 다양한 관광지 데이터를 종합하여 최적의 장소 5곳을 추천합니다.
 */
public class RecommendationEngine {

    public enum TimeOfDay { MORNING, AFTERNOON, LUNCH, NIGHT }
    public enum CompanionType { ALONE, FRIENDS, FAMILY, COUPLE }
    public enum Mood { POWERFUL, HEALING, EMOTIONAL, HOTPLACE }
    public enum TravelOption { SLIPPER, MIN_30, MIN_60, NO_PREF }

    private final Context context;

    public RecommendationEngine(Context context) {
        this.context = context;
    }

    /**
     * 사용자의 선택과 위치를 기반으로 추천 장소 5곳을 반환
     */
    public List<PlaceInfo> recommend(
            TimeOfDay timeOfDay,
            CompanionType companion,
            Mood mood,
            TravelOption travelOption,
            Location userLocation) {

        // 1. 다양한 API 호출을 통해 전체 장소 리스트 획득
        List<PlaceInfo> allPlaces = fetchAllPlaces();

        // 2. 이동 시간/거리 기준으로 사전 필터링
        List<PlaceInfo> travelFiltered = filterByTravelOption(allPlaces, travelOption, userLocation);

        // 3. 사용자 선택 기반 점수 매기기
        List<ScoredPlace> scored = new ArrayList<>();
        for (PlaceInfo place : travelFiltered) {
            double score = computeScore(place, timeOfDay, companion, mood);
            scored.add(new ScoredPlace(place, score));
        }

        // 4. 점수 내림차순 정렬 후 상위 5개 선택
        Collections.sort(scored, new Comparator<ScoredPlace>() {
            @Override
            public int compare(ScoredPlace a, ScoredPlace b) {
                return Double.compare(b.score, a.score);
            }
        });

        List<PlaceInfo> top5 = new ArrayList<>();
        for (int i = 0; i < Math.min(5, scored.size()); i++) {
            top5.add(scored.get(i).place);
        }
        return top5;
    }

    // ----- 내부 지원 메서드 -----

    /**
     * 모든 관광 API에서 장소 정보를 수집
     */
    private List<PlaceInfo> fetchAllPlaces() {
        List<PlaceInfo> list = new ArrayList<>();
        list.addAll(new MuseumDataApiCaller(context).fetchPlaces());
        list.addAll(new TouristAttractionsApiCaller(context).fetchPlaces());
        list.addAll(new HanokDataApiCaller(context).fetchPlaces());
        list.addAll(new YouthTrainingFacilityApiCaller(context).fetchPlaces());
        // list.addAll(new ShoppingDataApiCaller(context).fetchPlaces());
        // list.addAll(new PerformanceMovieApiCaller(context).fetchPlaces());
        return list;
    }

    /**
     * 사용자의 이동 옵션에 따라 필터링 (대중교통 소요시간 또는 도보 거리)
     */
    private List<PlaceInfo> filterByTravelOption(
            List<PlaceInfo> places,
            TravelOption option,
            Location userLoc) {

        List<PlaceInfo> filtered = new ArrayList<>();
        for (PlaceInfo place : places) {
            float travelMetric;
            if (option == TravelOption.SLIPPER) {
                // 도보 거리(km) 기준, 슬리퍼 옵션은 2km 이내
                float[] results = new float[1];
                Location.distanceBetween(
                        userLoc.getLatitude(), userLoc.getLongitude(),
                        place.lat, place.lng,
                        results);
                travelMetric = results[0] / 1000f;
                if (travelMetric <= 2.0f) filtered.add(place);
            } else if (option == TravelOption.MIN_30 || option == TravelOption.MIN_60) {
                int maxMin = (option == TravelOption.MIN_30 ? 30 : 60);
                int eta = estimateTransitTime(userLoc, place);
                if (eta > 0 && eta <= maxMin) filtered.add(place);
            } else {
                // NO_PREF: 제한 없음
                filtered.add(place);
            }
        }
        return filtered;
    }

    /**
     * 장소별로 사용자 선택 조건에 기반한 간단한 점수 계산
     * (실제 구현 시 키워드 매칭, 머신러닝 모델 등으로 교체 가능)
     */
    private double computeScore(
            PlaceInfo place,
            TimeOfDay timeOfDay,
            CompanionType companion,
            Mood mood) {
        double score = 0;
        String desc = place.title + " " + place.description;
        // 키워드 포함 여부로 점수 부여 예시
        if (desc.contains(keywordForTime(timeOfDay))) score += 1.0;
        if (desc.contains(keywordForCompanion(companion))) score += 1.0;
        if (desc.contains(keywordForMood(mood))) score += 1.0;
        // 무작위 요소 추가
        score += Math.random();
        return score;
    }

    private String keywordForTime(TimeOfDay t) {
        switch (t) {
            case MORNING: return "아침";
            case AFTERNOON: return "낮";
            case LUNCH: return "점심";
            case NIGHT: return "밤";
            default: return "";
        }
    }
    private String keywordForCompanion(CompanionType c) {
        switch (c) {
            case ALONE: return "혼자";
            case FRIENDS: return "친구";
            case FAMILY: return "가족";
            case COUPLE: return "연인";
            default: return "";
        }
    }
    private String keywordForMood(Mood m) {
        switch (m) {
            case POWERFUL: return "파워풀";
            case HEALING: return "힐링";
            case EMOTIONAL: return "갬성";
            case HOTPLACE: return "핫플";
            default: return "";
        }
    }

    /**
     * 대중교통 예상 소요 시간(분) 계산
     * (실제 구현 시 네이버/카카오 교통 API 연동 필요)
     */
    private int estimateTransitTime(Location from, PlaceInfo place) {
        // TODO: Directions API 호출 후 real ETA 반환
        // 현재는 거리 기반 임시 로직: 속도 30km/h 가정
        float[] results = new float[1];
        Location.distanceBetween(
                from.getLatitude(), from.getLongitude(),
                place.lat, place.lng,
                results);
        float km = results[0] / 1000f;
        return (int) ((km / 30f) * 60);
    }

    // 내부 클래스: 점수와 장소 묶음
    private static class ScoredPlace implements Serializable {
        final PlaceInfo place;
        final double score;
        ScoredPlace(PlaceInfo place, double score) {
            this.place = place;
            this.score = score;
        }
    }
}
