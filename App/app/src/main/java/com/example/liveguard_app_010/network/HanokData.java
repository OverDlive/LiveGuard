package com.example.liveguard_app_010.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "row", strict = false)
public class HanokData {

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

    @Element(name = "OPENYMD", required = false)
    private String openYmd;

    @Element(name = "CULPHYEDCOBNM", required = false)
    private String culphyEdcObNm;

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

    public String getOpenYmd() {
        return openYmd;
    }

    public void setOpenYmd(String openYmd) {
        this.openYmd = openYmd;
    }

    public String getCulphyEdcObNm() {
        return culphyEdcObNm;
    }

    public void setCulphyEdcObNm(String culphyEdcObNm) {
        this.culphyEdcObNm = culphyEdcObNm;
    }

    @Override
    public String toString() {
        return "HanokData{" +
                "opnsfTeamCode='" + opnsfTeamCode + '\'' +
                ", mgtNo='" + mgtNo + '\'' +
                ", apvPermYmd='" + apvPermYmd + '\'' +
                ", siteWhlAddr='" + siteWhlAddr + '\'' +
                ", rdnWhlAddr='" + rdnWhlAddr + '\'' +
                ", bplcNm='" + bplcNm + '\'' +
                ", openYmd='" + openYmd + '\'' +
                ", culphyEdcObNm='" + culphyEdcObNm + '\'' +
                '}';
    }
}