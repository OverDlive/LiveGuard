package com.example.liveguard_app_010.network.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * SK Open API (실시간 장소 혼잡도)의 새 JSON 구조를 매핑하는 DTO 클래스
 */
public class CongestionResponse {

    @SerializedName("status")
    private Status status;

    @SerializedName("contents")
    private Contents contents;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    /**
     * status 필드를 매핑하는 내부 클래스
     */
    public static class Status {

        @SerializedName("code")
        private String code;

        @SerializedName("message")
        private String message;

        @SerializedName("totalCount")
        private int totalCount;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }

    /**
     * contents 필드를 매핑하는 내부 클래스
     */
    public static class Contents {

        @SerializedName("poiId")
        private String poiId;

        @SerializedName("poiName")
        private String poiName;

        @SerializedName("rltm")
        private List<RltmItem> rltm;

        public String getPoiId() {
            return poiId;
        }

        public void setPoiId(String poiId) {
            this.poiId = poiId;
        }

        public String getPoiName() {
            return poiName;
        }

        public void setPoiName(String poiName) {
            this.poiName = poiName;
        }

        public List<RltmItem> getRltm() {
            return rltm;
        }

        public void setRltm(List<RltmItem> rltm) {
            this.rltm = rltm;
        }
    }

    /**
     * rltm 배열 내 아이템을 매핑하는 내부 클래스
     */
    public static class RltmItem {

        @SerializedName("datetime")
        private String datetime;

        @SerializedName("congestion")
        private double congestion;

        @SerializedName("congestionLevel")
        private int congestionLevel;

        @SerializedName("type")
        private int type;

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public double getCongestion() {
            return congestion;
        }

        public void setCongestion(double congestion) {
            this.congestion = congestion;
        }

        public int getCongestionLevel() {
            return congestionLevel;
        }

        public void setCongestionLevel(int congestionLevel) {
            this.congestionLevel = congestionLevel;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}