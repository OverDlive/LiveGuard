package com.example.liveguard_app_010.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "row", strict = false)
public class YouthTrainingFacilityData {

    @Element(name = "UP_ID", required = false)
    private String upId;

    @Element(name = "UP_NM", required = false)
    private String upNm;

    @Element(name = "SALE_COM_CD", required = false)
    private String saleComCd;

    @Element(name = "SALE_COM_NM", required = false)
    private String saleComNm;

    @Element(name = "BAS_CD", required = false)
    private String basCd;

    @Element(name = "BAS_NM", required = false)
    private String basNm;

    @Element(name = "ITEM_CD", required = false)
    private String itemCd;

    @Element(name = "ITEM_NM", required = false)
    private String itemNm;

    @Element(name = "CLS_CD", required = false)
    private String clsCd;

    @Element(name = "CLS_NM", required = false)
    private String clsNm;

    @Element(name = "PGM_CD", required = false)
    private String pgmCd;

    @Element(name = "PGM_NM", required = false)
    private String pgmNm;

    @Element(name = "FG_CD", required = false)
    private String fgCd;

    @Element(name = "FG_NM", required = false)
    private String fgNm;

    @Element(name = "YYMM", required = false)
    private String yymm;

    @Element(name = "TARGET_CD", required = false)
    private String targetCd;

    @Element(name = "TARGET_NM", required = false)
    private String targetNm;

    @Element(name = "MM_QTY", required = false)
    private String mmQty;

    @Element(name = "START_T", required = false)
    private String startT;

    @Element(name = "END_T", required = false)
    private String endT;

    @Element(name = "AREA_CD", required = false)
    private String areaCd;

    @Element(name = "U_PRICE", required = false)
    private String uPrice;

    @Element(name = "START_DAY", required = false)
    private String startDay;

    @Element(name = "REAL_ST_DT", required = false)
    private String realStDt;

    @Element(name = "REAL_ED_DT", required = false)
    private String realEdDt;

    @Element(name = "DT_WEEK_CD", required = false)
    private String dtWeekCd;

    @Element(name = "RGL_QTY", required = false)
    private String rglQty;

    @Element(name = "CLOSE_YN", required = false)
    private String closeYn;

    @Element(name = "USER_NM", required = false)
    private String userNm;

    // Getters & Setters (생략 - 필요 시 생성)

    public String getUpId() {
        return upId;
    }

    public void setUpId(String upId) {
        this.upId = upId;
    }

    public String getUpNm() {
        return upNm;
    }

    public void setUpNm(String upNm) {
        this.upNm = upNm;
    }

    public String getSaleComCd() {
        return saleComCd;
    }

    public void setSaleComCd(String saleComCd) {
        this.saleComCd = saleComCd;
    }

    public String getSaleComNm() {
        return saleComNm;
    }

    public void setSaleComNm(String saleComNm) {
        this.saleComNm = saleComNm;
    }

    public String getBasCd() {
        return basCd;
    }

    public void setBasCd(String basCd) {
        this.basCd = basCd;
    }

    public String getBasNm() {
        return basNm;
    }

    public void setBasNm(String basNm) {
        this.basNm = basNm;
    }

    public String getItemCd() {
        return itemCd;
    }

    public void setItemCd(String itemCd) {
        this.itemCd = itemCd;
    }

    public String getItemNm() {
        return itemNm;
    }

    public void setItemNm(String itemNm) {
        this.itemNm = itemNm;
    }

    public String getClsCd() {
        return clsCd;
    }

    public void setClsCd(String clsCd) {
        this.clsCd = clsCd;
    }

    public String getClsNm() {
        return clsNm;
    }

    public void setClsNm(String clsNm) {
        this.clsNm = clsNm;
    }

    public String getPgmCd() {
        return pgmCd;
    }

    public void setPgmCd(String pgmCd) {
        this.pgmCd = pgmCd;
    }

    public String getPgmNm() {
        return pgmNm;
    }

    public void setPgmNm(String pgmNm) {
        this.pgmNm = pgmNm;
    }

    public String getFgCd() {
        return fgCd;
    }

    public void setFgCd(String fgCd) {
        this.fgCd = fgCd;
    }

    public String getFgNm() {
        return fgNm;
    }

    public void setFgNm(String fgNm) {
        this.fgNm = fgNm;
    }

    public String getYymm() {
        return yymm;
    }

    public void setYymm(String yymm) {
        this.yymm = yymm;
    }

    public String getTargetCd() {
        return targetCd;
    }

    public void setTargetCd(String targetCd) {
        this.targetCd = targetCd;
    }

    public String getTargetNm() {
        return targetNm;
    }

    public void setTargetNm(String targetNm) {
        this.targetNm = targetNm;
    }

    public String getMmQty() {
        return mmQty;
    }

    public void setMmQty(String mmQty) {
        this.mmQty = mmQty;
    }

    public String getStartT() {
        return startT;
    }

    public void setStartT(String startT) {
        this.startT = startT;
    }

    public String getEndT() {
        return endT;
    }

    public void setEndT(String endT) {
        this.endT = endT;
    }

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public String getUPrice() {
        return uPrice;
    }

    public void setUPrice(String uPrice) {
        this.uPrice = uPrice;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getRealStDt() {
        return realStDt;
    }

    public void setRealStDt(String realStDt) {
        this.realStDt = realStDt;
    }

    public String getRealEdDt() {
        return realEdDt;
    }

    public void setRealEdDt(String realEdDt) {
        this.realEdDt = realEdDt;
    }

    public String getDtWeekCd() {
        return dtWeekCd;
    }

    public void setDtWeekCd(String dtWeekCd) {
        this.dtWeekCd = dtWeekCd;
    }

    public String getRglQty() {
        return rglQty;
    }

    public void setRglQty(String rglQty) {
        this.rglQty = rglQty;
    }

    public String getCloseYn() {
        return closeYn;
    }

    public void setCloseYn(String closeYn) {
        this.closeYn = closeYn;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }
}