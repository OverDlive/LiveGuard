package com.example.liveguard_app_010.network.model;

public class MuseumData {
    private String bplcNm;       // 박물관 명 (예: 겸재정선미술관)
    private String siteWhlAddr;   // 전체 주소 (예: 서울특별시 강서구 마곡동 815 겸재정선미술관)
    private String rdnWhlAddr;    // 도로명 주소 (예: 서울특별시 강서구 양천로47길 36, 겸재정선미술관 (마곡동))
    private String culphyEdcObNm; // 미술관 종류 (예: 미술관)
    private String openYmd;       // 개관일 (예: 20090423)

    public String getBplcNm() {
        return bplcNm;
    }

    public void setBplcNm(String bplcNm) {
        this.bplcNm = bplcNm;
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

    public String getCulphyEdcObNm() {
        return culphyEdcObNm;
    }

    public void setCulphyEdcObNm(String culphyEdcObNm) {
        this.culphyEdcObNm = culphyEdcObNm;
    }

    public String getOpenYmd() {
        return openYmd;
    }

    public void setOpenYmd(String openYmd) {
        this.openYmd = openYmd;
    }

    @Override
    public String toString() {
        return "MuseumData{" +
                "bplcNm='" + bplcNm + '\'' +
                ", siteWhlAddr='" + siteWhlAddr + '\'' +
                ", rdnWhlAddr='" + rdnWhlAddr + '\'' +
                ", culphyEdcObNm='" + culphyEdcObNm + '\'' +
                ", openYmd='" + openYmd + '\'' +
                '}';
    }
}