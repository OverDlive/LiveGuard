package com.example.liveguard_app_010.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimelineResponse {
    @SerializedName("user_id")
    private String userId;

    @SerializedName("activities")
    private List<Activity> activities;

    // 생성자, 게터, 세터

    public String getUserId() {
        return userId;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    // Activity 내부 클래스 또는 별도의 파일로 정의
    public static class Activity {
        @SerializedName("title")
        private String title;

        @SerializedName("timestamp")
        private String timestamp;

        // 생성자, 게터, 세터

        public String getTitle() {
            return title;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }
}
