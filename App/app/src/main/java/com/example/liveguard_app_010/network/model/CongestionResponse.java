package com.example.liveguard_app_010.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * 서울시 도시데이터(citydata) XML 응답을 파싱하기 위한 클래스
 */
@Root(name = "Map", strict = false)
public class CongestionResponse {

    // <SeoulRtd.citydata_ppltn> 노드
    @Element(name = "SeoulRtd.citydata_ppltn", required = false)
    private CityDataPpltn citydataPpltn;

    // <RESULT> 노드
    @Element(name = "RESULT", required = false)
    private ResultData result;

    // ----------------------------------
    // Getter / Setter
    // ----------------------------------
    public CityDataPpltn getCitydataPpltn() {
        return citydataPpltn;
    }
    public void setCitydataPpltn(CityDataPpltn citydataPpltn) {
        this.citydataPpltn = citydataPpltn;
    }

    public ResultData getResult() {
        return result;
    }
    public void setResult(ResultData result) {
        this.result = result;
    }

    // =================================================================
    // 1) 내부 클래스: CityDataPpltn
    //    - 210개 태그를 모두 맵핑 (필요한 자료형에 맞춰 수정)
    // =================================================================
    @Root(name = "SeoulRtd.citydata_ppltn", strict = false)
    public static class CityDataPpltn {

        // 예시 1) 사고 통제 상태
        @Element(name = "ACDNT_CNTRL_STTS", required = false)
        private String acdntCntrlStts;

        // 예시 2) 주소
        @Element(name = "ADDRESS", required = false)
        private String address;

        // 예시 3) 혼잡도 레벨
        @Element(name = "AREA_CONGEST_LVL", required = false)
        private String areaCongestLvl;

        // 예시 4) 혼잡도 메시지
        @Element(name = "AREA_CONGEST_MSG", required = false)
        private String areaCongestMsg;

        // 예시 5) 지역명
        @Element(name = "AREA_NM", required = false)
        private String areaNm;

        // 예시 6) 지역 최소/최대 인구
        @Element(name = "AREA_PPLTN_MIN", required = false)
        private int areaPpltnMin;

        @Element(name = "AREA_PPLTN_MAX", required = false)
        private int areaPpltnMax;

        // 예시 7) 남/녀 비율
        @Element(name = "MALE_PPLTN_RATE", required = false)
        private double malePpltnRate;

        @Element(name = "FEMALE_PPLTN_RATE", required = false)
        private double femalePpltnRate;

        // 예시 8) 실시간 인구 업데이트 시각
        @Element(name = "PPLTN_TIME", required = false)
        private String ppltnTime;

        // ---------------------------------------------------------
        // (중략) 나머지 200개 가까운 태그들도 같은 방식으로 추가
        // 예: AIR_IDX, ADD_RATES, BUS_STN_ID, CUR_PRK_CNT, PM10, PM25 등
        // ---------------------------------------------------------

        @Element(name = "AIR_IDX", required = false)
        private String airIdx;

        @Element(name = "AIR_IDX_MAIN", required = false)
        private String airIdxMain;

        @Element(name = "AIR_IDX_MVL", required = false)
        private String airIdxMvl;

        @Element(name = "AIR_MSG", required = false)
        private String airMsg;

        @Element(name = "PM10", required = false)
        private String pm10;

        @Element(name = "PM25", required = false)
        private String pm25;

        // ...
        // 계속 필요한 만큼 추가
        // ...

        // --------------------------------
        // Getter / Setter
        // --------------------------------
        public String getAcdntCntrlStts() {
            return acdntCntrlStts;
        }
        public void setAcdntCntrlStts(String acdntCntrlStts) {
            this.acdntCntrlStts = acdntCntrlStts;
        }

        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }

        public String getAreaCongestLvl() {
            return areaCongestLvl;
        }
        public void setAreaCongestLvl(String areaCongestLvl) {
            this.areaCongestLvl = areaCongestLvl;
        }

        public String getAreaCongestMsg() {
            return areaCongestMsg;
        }
        public void setAreaCongestMsg(String areaCongestMsg) {
            this.areaCongestMsg = areaCongestMsg;
        }

        public String getAreaNm() {
            return areaNm;
        }
        public void setAreaNm(String areaNm) {
            this.areaNm = areaNm;
        }

        public int getAreaPpltnMin() {
            return areaPpltnMin;
        }
        public void setAreaPpltnMin(int areaPpltnMin) {
            this.areaPpltnMin = areaPpltnMin;
        }

        public int getAreaPpltnMax() {
            return areaPpltnMax;
        }
        public void setAreaPpltnMax(int areaPpltnMax) {
            this.areaPpltnMax = areaPpltnMax;
        }

        public double getMalePpltnRate() {
            return malePpltnRate;
        }
        public void setMalePpltnRate(double malePpltnRate) {
            this.malePpltnRate = malePpltnRate;
        }

        public double getFemalePpltnRate() {
            return femalePpltnRate;
        }
        public void setFemalePpltnRate(double femalePpltnRate) {
            this.femalePpltnRate = femalePpltnRate;
        }

        public String getPpltnTime() {
            return ppltnTime;
        }
        public void setPpltnTime(String ppltnTime) {
            this.ppltnTime = ppltnTime;
        }

        public String getAirIdx() {
            return airIdx;
        }
        public void setAirIdx(String airIdx) {
            this.airIdx = airIdx;
        }

        public String getAirIdxMain() {
            return airIdxMain;
        }
        public void setAirIdxMain(String airIdxMain) {
            this.airIdxMain = airIdxMain;
        }

        public String getAirIdxMvl() {
            return airIdxMvl;
        }
        public void setAirIdxMvl(String airIdxMvl) {
            this.airIdxMvl = airIdxMvl;
        }

        public String getAirMsg() {
            return airMsg;
        }
        public void setAirMsg(String airMsg) {
            this.airMsg = airMsg;
        }

        public String getPm10() {
            return pm10;
        }
        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm25() {
            return pm25;
        }
        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        // (... 나머지 100~200개의 필드들도 같은 패턴으로 작성 ...)
    }

    // =================================================================
    // 2) 내부 클래스: ResultData
    //    - <RESULT> 태그에서 응답 코드, 메시지 등을 파싱
    // =================================================================
    @Root(name = "RESULT", strict = false)
    public static class ResultData {

        @Element(name = "RESULT.CODE", required = false)
        private String code;

        @Element(name = "RESULT.MESSAGE", required = false)
        private String message;

        // Getter / Setter
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
    }
}