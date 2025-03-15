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
    // XML: <SeoulRtd.citydata_ppltn> : Contains detailed city data including population and congestion information.
    public CityDataPpltn getCitydataPpltn() {
        return citydataPpltn;
    }
    // XML: <SeoulRtd.citydata_ppltn> : Contains detailed city data including population and congestion information.
    public void setCitydataPpltn(CityDataPpltn citydataPpltn) {
        this.citydataPpltn = citydataPpltn;
    }

    // XML: <RESULT> : Contains the API response's result code and message.
    public ResultData getResult() {
        return result;
    }
    // XML: <RESULT> : Contains the API response's result code and message.
    public void setResult(ResultData result) {
        this.result = result;
    }

    // 추가: 응답 코드 반환 함수
    public String getResponseCode() {
        if (result != null) {
            return result.getCode();
        }
        return null;
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

        // --------------------------------
        // Getter / Setter
        // --------------------------------
        // XML: <ACDNT_CNTRL_STTS> : Represents the accident control status, indicating if accident control measures are active.
        public String getAcdntCntrlStts() {
            return acdntCntrlStts;
        }
        // XML: <ACDNT_CNTRL_STTS> : Represents the accident control status, indicating if accident control measures are active.
        public void setAcdntCntrlStts(String acdntCntrlStts) {
            this.acdntCntrlStts = acdntCntrlStts;
        }

        // XML: <ADDRESS> : Represents the address of the location.
        public String getAddress() {
            return address;
        }
        // XML: <ADDRESS> : Represents the address of the location.
        public void setAddress(String address) {
            this.address = address;
        }

        // XML: <AREA_CONGEST_LVL> : Represents the congestion level of the area (e.g., 붐빔, 여유).
        public String getAreaCongestLvl() {
            return areaCongestLvl;
        }
        // XML: <AREA_CONGEST_LVL> : Represents the congestion level of the area (e.g., 붐빔, 여유).
        public void setAreaCongestLvl(String areaCongestLvl) {
            this.areaCongestLvl = areaCongestLvl;
        }

        // XML: <AREA_CONGEST_MSG> : Provides a message detailing the congestion status and potential crowding issues.
        public String getAreaCongestMsg() {
            return areaCongestMsg;
        }
        // XML: <AREA_CONGEST_MSG> : Provides a message detailing the congestion status and potential crowding issues.
        public void setAreaCongestMsg(String areaCongestMsg) {
            this.areaCongestMsg = areaCongestMsg;
        }

        // XML: <AREA_NM> : Represents the name of the area.
        public String getAreaNm() {
            return areaNm;
        }
        // XML: <AREA_NM> : Represents the name of the area.
        public void setAreaNm(String areaNm) {
            this.areaNm = areaNm;
        }

        // XML: <AREA_PPLTN_MIN> : Represents the minimum estimated population of the area.
        public int getAreaPpltnMin() {
            return areaPpltnMin;
        }
        // XML: <AREA_PPLTN_MIN> : Represents the minimum estimated population of the area.
        public void setAreaPpltnMin(int areaPpltnMin) {
            this.areaPpltnMin = areaPpltnMin;
        }

        // XML: <AREA_PPLTN_MAX> : Represents the maximum estimated population of the area.
        public int getAreaPpltnMax() {
            return areaPpltnMax;
        }
        // XML: <AREA_PPLTN_MAX> : Represents the maximum estimated population of the area.
        public void setAreaPpltnMax(int areaPpltnMax) {
            this.areaPpltnMax = areaPpltnMax;
        }

        // XML: <MALE_PPLTN_RATE> : Represents the percentage of the male population in the area.
        public double getMalePpltnRate() {
            return malePpltnRate;
        }
        // XML: <MALE_PPLTN_RATE> : Represents the percentage of the male population in the area.
        public void setMalePpltnRate(double malePpltnRate) {
            this.malePpltnRate = malePpltnRate;
        }

        // XML: <FEMALE_PPLTN_RATE> : Represents the percentage of the female population in the area.
        public double getFemalePpltnRate() {
            return femalePpltnRate;
        }
        // XML: <FEMALE_PPLTN_RATE> : Represents the percentage of the female population in the area.
        public void setFemalePpltnRate(double femalePpltnRate) {
            this.femalePpltnRate = femalePpltnRate;
        }

        // XML: <PPLTN_TIME> : Represents the timestamp when the population data was updated.
        public String getPpltnTime() {
            return ppltnTime;
        }
        // XML: <PPLTN_TIME> : Represents the timestamp when the population data was updated.
        public void setPpltnTime(String ppltnTime) {
            this.ppltnTime = ppltnTime;
        }

        // XML: <AIR_IDX> : Represents the air quality index value for the area.
        public String getAirIdx() {
            return airIdx;
        }
        // XML: <AIR_IDX> : Represents the air quality index value for the area.
        public void setAirIdx(String airIdx) {
            this.airIdx = airIdx;
        }

        // XML: <AIR_IDX_MAIN> : Represents the primary air quality index reading.
        public String getAirIdxMain() {
            return airIdxMain;
        }
        // XML: <AIR_IDX_MAIN> : Represents the primary air quality index reading.
        public void setAirIdxMain(String airIdxMain) {
            this.airIdxMain = airIdxMain;
        }

        // XML: <AIR_IDX_MVL> : Represents an additional air quality index metric (MVL).
        public String getAirIdxMvl() {
            return airIdxMvl;
        }
        // XML: <AIR_IDX_MVL> : Represents an additional air quality index metric (MVL).
        public void setAirIdxMvl(String airIdxMvl) {
            this.airIdxMvl = airIdxMvl;
        }

        // XML: <AIR_MSG> : Provides descriptive information about the air quality.
        public String getAirMsg() {
            return airMsg;
        }
        // XML: <AIR_MSG> : Provides descriptive information about the air quality.
        public void setAirMsg(String airMsg) {
            this.airMsg = airMsg;
        }

        // XML: <PM10> : Represents the PM10 particulate matter measurement.
        public String getPm10() {
            return pm10;
        }
        // XML: <PM10> : Represents the PM10 particulate matter measurement.
        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        // XML: <PM25> : Represents the PM2.5 particulate matter measurement.
        public String getPm25() {
            return pm25;
        }
        // XML: <PM25> : Represents the PM2.5 particulate matter measurement.
        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        // -----------------------------
        // Additional Fields for missing XML tags
        // -----------------------------

        // XML: <AREA_CD> : Represents the area code.
        @Element(name = "AREA_CD", required = false)
        private String areaCd;

        // XML: <PPLTN_RATE_0> : Represents the percentage of the population aged 0.
        @Element(name = "PPLTN_RATE_0", required = false)
        private double ppltnRate0;

        // XML: <PPLTN_RATE_10> : Represents the percentage of the population aged 10.
        @Element(name = "PPLTN_RATE_10", required = false)
        private double ppltnRate10;

        // XML: <PPLTN_RATE_20> : Represents the percentage of the population aged 20.
        @Element(name = "PPLTN_RATE_20", required = false)
        private double ppltnRate20;

        // XML: <PPLTN_RATE_30> : Represents the percentage of the population aged 30.
        @Element(name = "PPLTN_RATE_30", required = false)
        private double ppltnRate30;

        // XML: <PPLTN_RATE_40> : Represents the percentage of the population aged 40.
        @Element(name = "PPLTN_RATE_40", required = false)
        private double ppltnRate40;

        // XML: <PPLTN_RATE_50> : Represents the percentage of the population aged 50.
        @Element(name = "PPLTN_RATE_50", required = false)
        private double ppltnRate50;

        // XML: <PPLTN_RATE_60> : Represents the percentage of the population aged 60.
        @Element(name = "PPLTN_RATE_60", required = false)
        private double ppltnRate60;

        // XML: <PPLTN_RATE_70> : Represents the percentage of the population aged 70.
        @Element(name = "PPLTN_RATE_70", required = false)
        private double ppltnRate70;

        // XML: <RESNT_PPLTN_RATE> : Represents the percentage of the resident population.
        @Element(name = "RESNT_PPLTN_RATE", required = false)
        private double resntPpltnRate;

        // XML: <NON_RESNT_PPLTN_RATE> : Represents the percentage of the non-resident population.
        @Element(name = "NON_RESNT_PPLTN_RATE", required = false)
        private double nonResntPpltnRate;

        // XML: <REPLACE_YN> : Indicates whether the data has been replaced (Y/N).
        @Element(name = "REPLACE_YN", required = false)
        private String replaceYn;

        // XML: <FCST_YN> : Indicates whether forecast data is provided (Y/N).
        @Element(name = "FCST_YN", required = false)
        private String fcstYn;

        // XML: <FCST_PPLTN> : Container for forecast population data.
        @Element(name = "FCST_PPLTN", required = false)
        private ForecastContainer fcstPpltn;

        // --------------------------------
        // Getter / Setter for additional fields
        // --------------------------------

        // XML: <AREA_CD> : Represents the area code.
        public String getAreaCd() {
            return areaCd;
        }
        // XML: <AREA_CD> : Represents the area code.
        public void setAreaCd(String areaCd) {
            this.areaCd = areaCd;
        }

        // XML: <PPLTN_RATE_0> : Represents the percentage of the population aged 0.
        public double getPpltnRate0() {
            return ppltnRate0;
        }
        // XML: <PPLTN_RATE_0> : Represents the percentage of the population aged 0.
        public void setPpltnRate0(double ppltnRate0) {
            this.ppltnRate0 = ppltnRate0;
        }

        // XML: <PPLTN_RATE_10> : Represents the percentage of the population aged 10.
        public double getPpltnRate10() {
            return ppltnRate10;
        }
        // XML: <PPLTN_RATE_10> : Represents the percentage of the population aged 10.
        public void setPpltnRate10(double ppltnRate10) {
            this.ppltnRate10 = ppltnRate10;
        }

        // XML: <PPLTN_RATE_20> : Represents the percentage of the population aged 20.
        public double getPpltnRate20() {
            return ppltnRate20;
        }
        // XML: <PPLTN_RATE_20> : Represents the percentage of the population aged 20.
        public void setPpltnRate20(double ppltnRate20) {
            this.ppltnRate20 = ppltnRate20;
        }

        // XML: <PPLTN_RATE_30> : Represents the percentage of the population aged 30.
        public double getPpltnRate30() {
            return ppltnRate30;
        }
        // XML: <PPLTN_RATE_30> : Represents the percentage of the population aged 30.
        public void setPpltnRate30(double ppltnRate30) {
            this.ppltnRate30 = ppltnRate30;
        }

        // XML: <PPLTN_RATE_40> : Represents the percentage of the population aged 40.
        public double getPpltnRate40() {
            return ppltnRate40;
        }
        // XML: <PPLTN_RATE_40> : Represents the percentage of the population aged 40.
        public void setPpltnRate40(double ppltnRate40) {
            this.ppltnRate40 = ppltnRate40;
        }

        // XML: <PPLTN_RATE_50> : Represents the percentage of the population aged 50.
        public double getPpltnRate50() {
            return ppltnRate50;
        }
        // XML: <PPLTN_RATE_50> : Represents the percentage of the population aged 50.
        public void setPpltnRate50(double ppltnRate50) {
            this.ppltnRate50 = ppltnRate50;
        }

        // XML: <PPLTN_RATE_60> : Represents the percentage of the population aged 60.
        public double getPpltnRate60() {
            return ppltnRate60;
        }
        // XML: <PPLTN_RATE_60> : Represents the percentage of the population aged 60.
        public void setPpltnRate60(double ppltnRate60) {
            this.ppltnRate60 = ppltnRate60;
        }

        // XML: <PPLTN_RATE_70> : Represents the percentage of the population aged 70.
        public double getPpltnRate70() {
            return ppltnRate70;
        }
        // XML: <PPLTN_RATE_70> : Represents the percentage of the population aged 70.
        public void setPpltnRate70(double ppltnRate70) {
            this.ppltnRate70 = ppltnRate70;
        }

        // XML: <RESNT_PPLTN_RATE> : Represents the percentage of the resident population.
        public double getResntPpltnRate() {
            return resntPpltnRate;
        }
        // XML: <RESNT_PPLTN_RATE> : Represents the percentage of the resident population.
        public void setResntPpltnRate(double resntPpltnRate) {
            this.resntPpltnRate = resntPpltnRate;
        }

        // XML: <NON_RESNT_PPLTN_RATE> : Represents the percentage of the non-resident population.
        public double getNonResntPpltnRate() {
            return nonResntPpltnRate;
        }
        // XML: <NON_RESNT_PPLTN_RATE> : Represents the percentage of the non-resident population.
        public void setNonResntPpltnRate(double nonResntPpltnRate) {
            this.nonResntPpltnRate = nonResntPpltnRate;
        }

        // XML: <REPLACE_YN> : Indicates whether the data has been replaced (Y/N).
        public String getReplaceYn() {
            return replaceYn;
        }
        // XML: <REPLACE_YN> : Indicates whether the data has been replaced (Y/N).
        public void setReplaceYn(String replaceYn) {
            this.replaceYn = replaceYn;
        }

        // XML: <FCST_YN> : Indicates whether forecast data is provided (Y/N).
        public String getFcstYn() {
            return fcstYn;
        }
        // XML: <FCST_YN> : Indicates whether forecast data is provided (Y/N).
        public void setFcstYn(String fcstYn) {
            this.fcstYn = fcstYn;
        }

        // XML: <FCST_PPLTN> : Container for forecast population data.
        public ForecastContainer getFcstPpltn() {
            return fcstPpltn;
        }
        // XML: <FCST_PPLTN> : Container for forecast population data.
        public void setFcstPpltn(ForecastContainer fcstPpltn) {
            this.fcstPpltn = fcstPpltn;
        }

        // =================================================================
        // Inner Class: ForecastContainer
        // - Maps the <FCST_PPLTN> container element which holds forecast entries.
        // =================================================================
        @Root(name = "FCST_PPLTN", strict = false)
        public static class ForecastContainer {

            // XML: <FCST_PPLTN> : List of forecast data entries.
            @ElementList(entry = "FCST_PPLTN", inline = true, required = false)
            private List<ForecastData> forecastDataList;

            // Getter / Setter for forecastDataList
            public List<ForecastData> getForecastDataList() {
                return forecastDataList;
            }
            public void setForecastDataList(List<ForecastData> forecastDataList) {
                this.forecastDataList = forecastDataList;
            }
        }

        // =================================================================
        // Inner Class: ForecastData
        // - Represents individual forecast data entries within the forecast container.
        // =================================================================
        @Root(name = "FCST_PPLTN", strict = false)
        public static class ForecastData {

            // XML: <FCST_TIME> : Represents the forecast time.
            @Element(name = "FCST_TIME", required = false)
            private String fcstTime;

            // XML: <FCST_CONGEST_LVL> : Represents the forecast congestion level.
            @Element(name = "FCST_CONGEST_LVL", required = false)
            private String fcstCongestLvl;

            // XML: <FCST_PPLTN_MIN> : Represents the minimum forecast population.
            @Element(name = "FCST_PPLTN_MIN", required = false)
            private int fcstPpltnMin;

            // XML: <FCST_PPLTN_MAX> : Represents the maximum forecast population.
            @Element(name = "FCST_PPLTN_MAX", required = false)
            private int fcstPpltnMax;

            // Getter / Setter for fcstTime
            public String getFcstTime() {
                return fcstTime;
            }
            public void setFcstTime(String fcstTime) {
                this.fcstTime = fcstTime;
            }

            // Getter / Setter for fcstCongestLvl
            public String getFcstCongestLvl() {
                return fcstCongestLvl;
            }
            public void setFcstCongestLvl(String fcstCongestLvl) {
                this.fcstCongestLvl = fcstCongestLvl;
            }

            // Getter / Setter for fcstPpltnMin
            public int getFcstPpltnMin() {
                return fcstPpltnMin;
            }
            public void setFcstPpltnMin(int fcstPpltnMin) {
                this.fcstPpltnMin = fcstPpltnMin;
            }

            // Getter / Setter for fcstPpltnMax
            public int getFcstPpltnMax() {
                return fcstPpltnMax;
            }
            public void setFcstPpltnMax(int fcstPpltnMax) {
                this.fcstPpltnMax = fcstPpltnMax;
            }
        }
    }

    // =================================================================
    // 2) 내부 클래스: ResultData
    //    - <RESULT> 태그에서 응답 코드, 메시지 등을 파싱
    // =================================================================
    @Root(name = "RESULT", strict = false)
    public static class ResultData {

        // XML: <RESULT.CODE> : Represents the response code of the API (e.g., INFO-000 for success).
        @Element(name = "RESULT.CODE", required = false)
        private String code;

        // XML: <RESULT.MESSAGE> : Represents the detailed message corresponding to the API response code.
        @Element(name = "RESULT.MESSAGE", required = false)
        private String message;

        // Getter / Setter
        // XML: <RESULT.CODE> : Represents the response code of the API (e.g., INFO-000 for success).
        public String getCode() {
            return code;
        }
        // XML: <RESULT.CODE> : Represents the response code of the API (e.g., INFO-000 for success).
        public void setCode(String code) {
            this.code = code;
        }

        // XML: <RESULT.MESSAGE> : Represents the detailed message corresponding to the API response code.
        public String getMessage() {
            return message;
        }
        // XML: <RESULT.MESSAGE> : Represents the detailed message corresponding to the API response code.
        public void setMessage(String message) {
            this.message = message;
        }
    }
}