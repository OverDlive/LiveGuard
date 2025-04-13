package com.example.liveguard_app_010.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "row", strict = false)
public class HanokExperienceData {

    @Element(name = "OPNSFTEAMCODE", required = false)
    private String opnsfTeamCode;

    @Element(name = "MGTNO", required = false)
    private String mgtNo;

    @Element(name = "APVPERMYMD", required = false)
    private String apvPermYmd;

    @Element(name = "SITEWHLADDR", required = false)
    private String siteWhlAddr;

    @Element(name = "RDNWHLADDR", required = false)
    private String rdnWhlAddr;

    @Element(name = "BPLCNM", required = false)
    private String bplcNm;

    @Element(name = "LASTMODTS", required = false)
    private String lastModTs;

    // 필요한 경우 나머지 태그에 대한 필드를 추가할 수 있습니다.
    // 예) X, Y, CLOSE_YN 등

    public String getOpnsfTeamCode() {
        return opnsfTeamCode;
    }

    public void setOpnsfTeamCode(String opnsfTeamCode) {
        this.opnsfTeamCode = opnsfTeamCode;
    }

    public String getMgtNo() {
        return mgtNo;
    }

    public void setMgtNo(String mgtNo) {
        this.mgtNo = mgtNo;
    }

    public String getApvPermYmd() {
        return apvPermYmd;
    }

    public void setApvPermYmd(String apvPermYmd) {
        this.apvPermYmd = apvPermYmd;
    }

    public String getSiteWhlAddr() {
        return siteWhlAddr;
    }

    public void setSiteWhlAddr(String siteWhlAddr) {
        this.siteWhlAddr = siteWhlAddr;
    }

    public String getRdnWhlAddr() {
        return rdnWhlAddr;
    }

    public void setRdnWhlAddr(String rdnWhlAddr) {
        this.rdnWhlAddr = rdnWhlAddr;
    }

    public String getBplcNm() {
        return bplcNm;
    }

    public void setBplcNm(String bplcNm) {
        this.bplcNm = bplcNm;
    }

    public String getLastModTs() {
        return lastModTs;
    }

    public void setLastModTs(String lastModTs) {
        this.lastModTs = lastModTs;
    }

    @Override
    public String toString() {
        return "HanokExperienceData{" +
                "opnsfTeamCode='" + opnsfTeamCode + '\'' +
                ", mgtNo='" + mgtNo + '\'' +
                ", apvPermYmd='" + apvPermYmd + '\'' +
                ", siteWhlAddr='" + siteWhlAddr + '\'' +
                ", rdnWhlAddr='" + rdnWhlAddr + '\'' +
                ", bplcNm='" + bplcNm + '\'' +
                ", lastModTs='" + lastModTs + '\'' +
                '}';
    }
}