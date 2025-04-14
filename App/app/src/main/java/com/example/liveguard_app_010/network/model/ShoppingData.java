package com.example.liveguard_app_010.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "row", strict = false)
public class ShoppingData {

    @Element(name = "POST_SN", required = false)
    private String postSn;

    @Element(name = "LANG_CODE_ID", required = false)
    private String langCodeId;

    @Element(name = "POST_SJ", required = false)
    private String postSj;

    @Element(name = "POST_URL", required = false)
    private String postUrl;

    @Element(name = "ADDRESS", required = false)
    private String address;

    @Element(name = "NEW_ADDRESS", required = false)
    private String newAddress;

    @Element(name = "CMMN_TELNO", required = false)
    private String cmmnTelno;

    @Element(name = "CMMN_HMPG_URL", required = false)
    private String cmmnHmpgUrl;

    @Element(name = "CMMN_USE_TIME", required = false)
    private String cmmnUseTime;

    @Element(name = "SUBWAY_INFO", required = false)
    private String subwayInfo;

    public String getPostSn() {
        return postSn;
    }

    public void setPostSn(String postSn) {
        this.postSn = postSn;
    }

    public String getLangCodeId() {
        return langCodeId;
    }

    public void setLangCodeId(String langCodeId) {
        this.langCodeId = langCodeId;
    }

    public String getPostSj() {
        return postSj;
    }

    public void setPostSj(String postSj) {
        this.postSj = postSj;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }

    public String getCmmnTelno() {
        return cmmnTelno;
    }

    public void setCmmnTelno(String cmmnTelno) {
        this.cmmnTelno = cmmnTelno;
    }

    public String getCmmnHmpgUrl() {
        return cmmnHmpgUrl;
    }

    public void setCmmnHmpgUrl(String cmmnHmpgUrl) {
        this.cmmnHmpgUrl = cmmnHmpgUrl;
    }

    public String getCmmnUseTime() {
        return cmmnUseTime;
    }

    public void setCmmnUseTime(String cmmnUseTime) {
        this.cmmnUseTime = cmmnUseTime;
    }

    public String getSubwayInfo() {
        return subwayInfo;
    }

    public void setSubwayInfo(String subwayInfo) {
        this.subwayInfo = subwayInfo;
    }

    @Override
    public String toString() {
        return "ShoppingData{" +
                "postSn='" + postSn + '\'' +
                ", langCodeId='" + langCodeId + '\'' +
                ", postSj='" + postSj + '\'' +
                ", postUrl='" + postUrl + '\'' +
                ", address='" + address + '\'' +
                ", newAddress='" + newAddress + '\'' +
                ", cmmnTelno='" + cmmnTelno + '\'' +
                ", cmmnHmpgUrl='" + cmmnHmpgUrl + '\'' +
                ", cmmnUseTime='" + cmmnUseTime + '\'' +
                ", subwayInfo='" + subwayInfo + '\'' +
                '}';
    }
}