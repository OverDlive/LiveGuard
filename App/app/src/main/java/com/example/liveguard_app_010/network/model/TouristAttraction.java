package com.example.liveguard_app_010.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * XML <row> 하나의 관광지 정보를 나타내는 모델 클래스
 */
@Root(name = "row", strict = false)
public class TouristAttraction implements Serializable {

    @Element(name = "POST_SN", required = false)
    @Path("row")
    private String postSn;

    @Element(name = "LANG_CODE_ID", required = false)
    @Path("row")
    private String langCodeId;

    @Element(name = "POST_SJ", required = false)
    @Path("row")
    private String postSj;

    @Element(name = "POST_URL", required = false)
    @Path("row")
    private String postUrl;

    @Element(name = "ADDRESS", required = false)
    @Path("row")
    private String address;

    @Element(name = "NEW_ADDRESS", required = false)
    @Path("row")
    private String newAddress;

    @Element(name = "CMMN_TELNO", required = false)
    @Path("row")
    private String cmmnTelno;

    @Element(name = "CMMN_FAX", required = false)
    @Path("row")
    private String cmmnFax;

    @Element(name = "CMMN_HMPG_URL", required = false)
    @Path("row")
    private String cmmnHmpgUrl;

    @Element(name = "CMMN_USE_TIME", required = false)
    @Path("row")
    private String cmmnUseTime;

    @Element(name = "CMMN_BSNDE", required = false)
    @Path("row")
    private String cmmnBsnde;

    @Element(name = "CMMN_RSTDE", required = false)
    @Path("row")
    private String cmmnRstde;

    @Element(name = "SUBWAY_INFO", required = false)
    @Path("row")
    private String subwayInfo;

    @Element(name = "TAG", required = false)
    @Path("row")
    private String tag;

    @Element(name = "BF_DESC", required = false)
    @Path("row")
    private String bfDesc;

    // 기본 생성자 (Simple XML Framework 요구)
    public TouristAttraction() {}

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

    public String getCmmnFax() {
        return cmmnFax;
    }

    public void setCmmnFax(String cmmnFax) {
        this.cmmnFax = cmmnFax;
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

    public String getCmmnBsnde() {
        return cmmnBsnde;
    }

    public void setCmmnBsnde(String cmmnBsnde) {
        this.cmmnBsnde = cmmnBsnde;
    }

    public String getCmmnRstde() {
        return cmmnRstde;
    }

    public void setCmmnRstde(String cmmnRstde) {
        this.cmmnRstde = cmmnRstde;
    }

    public String getSubwayInfo() {
        return subwayInfo;
    }

    public void setSubwayInfo(String subwayInfo) {
        this.subwayInfo = subwayInfo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBfDesc() {
        return bfDesc;
    }

    public void setBfDesc(String bfDesc) {
        this.bfDesc = bfDesc;
    }
}
