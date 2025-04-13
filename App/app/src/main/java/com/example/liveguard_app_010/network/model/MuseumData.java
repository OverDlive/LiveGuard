package com.example.liveguard_app_010.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "row", strict = false)
public class MuseumData {

    @Element(name = "OPNSFTEAMCODE", required = false)
    private String opnsfTeamCode;

    @Element(name = "MGTNO", required = false)
    private String mgtNo;

    @Element(name = "APVPERMYMD", required = false)
    private String apvPermYmd;

    @Element(name = "APVCANCELYMD", required = false)
    private String apvCancelYmd;

    @Element(name = "TRDSTATEGBN", required = false)
    private String trdStateGbn;

    @Element(name = "TRDSTATENM", required = false)
    private String trdStateNm;

    @Element(name = "DTLSTATEGBN", required = false)
    private String dtlStateGbn;

    @Element(name = "DTLSTATENM", required = false)
    private String dtlStateNm;

    @Element(name = "DCBYMD", required = false)
    private String dcbYmd;

    @Element(name = "CLGSTDT", required = false)
    private String clgstDt;

    @Element(name = "CLGENDDT", required = false)
    private String clgendDt;

    @Element(name = "ROPNYMD", required = false)
    private String ropnymd;

    @Element(name = "SITETEL", required = false)
    private String siteTel;

    @Element(name = "SITEAREA", required = false)
    private String siteArea;

    @Element(name = "SITEPOSTNO", required = false)
    private String sitePostNo;

    @Element(name = "SITEWHLADDR", required = false)
    private String siteWhlAddr;

    @Element(name = "RDNWHLADDR", required = false)
    private String rdnWhlAddr;

    @Element(name = "RDNPOSTNO", required = false)
    private String rdnPostNo;

    @Element(name = "BPLCNM", required = false)
    private String bplcNm;

    @Element(name = "LASTMODTS", required = false)
    private String lastModTs;

    @Element(name = "UPDATEGBN", required = false)
    private String updateGbn;

    @Element(name = "UPDATEDT", required = false)
    private String updateDt;

    @Element(name = "UPTAENM", required = false)
    private String uptaenm;

    @Element(name = "X", required = false)
    private String x;

    @Element(name = "Y", required = false)
    private String y;

    @Element(name = "CULPHYEDCOBNM", required = false)
    private String culphyEdcObNm;

    @Element(name = "JGONGYMD", required = false)
    private String jgongYmd;

    @Element(name = "OPENYMD", required = false)
    private String openYmd;

    @Element(name = "MNGMBDNM", required = false)
    private String mngmbdnm;

    @Element(name = "MUSEUMSORTNM", required = false)
    private String museumSortNm;

    @Element(name = "MUSEUMKINDNM", required = false)
    private String museumKindNm;

    @Element(name = "CLOSEYMD", required = false)
    private String closeYmd;

    @Element(name = "CLOSEWHY", required = false)
    private String closeWhy;

    @Element(name = "CAPT", required = false)
    private String capt;

    @Element(name = "NDLOAN", required = false)
    private String ndloan;

    @Element(name = "BUPESBMNTOBJ", required = false)
    private String bupesbmntobj;

    @Element(name = "PERMCN", required = false)
    private String permcn;

    @Element(name = "BUPDISORGYMD", required = false)
    private String bupdisorgymd;

    @Element(name = "BUPNM", required = false)
    private String bupnm;

    // Getters & Setters

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

    public String getApvCancelYmd() {
        return apvCancelYmd;
    }

    public void setApvCancelYmd(String apvCancelYmd) {
        this.apvCancelYmd = apvCancelYmd;
    }

    public String getTrdStateGbn() {
        return trdStateGbn;
    }

    public void setTrdStateGbn(String trdStateGbn) {
        this.trdStateGbn = trdStateGbn;
    }

    public String getTrdStateNm() {
        return trdStateNm;
    }

    public void setTrdStateNm(String trdStateNm) {
        this.trdStateNm = trdStateNm;
    }

    public String getDtlStateGbn() {
        return dtlStateGbn;
    }

    public void setDtlStateGbn(String dtlStateGbn) {
        this.dtlStateGbn = dtlStateGbn;
    }

    public String getDtlStateNm() {
        return dtlStateNm;
    }

    public void setDtlStateNm(String dtlStateNm) {
        this.dtlStateNm = dtlStateNm;
    }

    public String getDcbYmd() {
        return dcbYmd;
    }

    public void setDcbYmd(String dcbYmd) {
        this.dcbYmd = dcbYmd;
    }

    public String getClgstDt() {
        return clgstDt;
    }

    public void setClgstDt(String clgstDt) {
        this.clgstDt = clgstDt;
    }

    public String getClgendDt() {
        return clgendDt;
    }

    public void setClgendDt(String clgendDt) {
        this.clgendDt = clgendDt;
    }

    public String getRopnymd() {
        return ropnymd;
    }

    public void setRopnymd(String ropnymd) {
        this.ropnymd = ropnymd;
    }

    public String getSiteTel() {
        return siteTel;
    }

    public void setSiteTel(String siteTel) {
        this.siteTel = siteTel;
    }

    public String getSiteArea() {
        return siteArea;
    }

    public void setSiteArea(String siteArea) {
        this.siteArea = siteArea;
    }

    public String getSitePostNo() {
        return sitePostNo;
    }

    public void setSitePostNo(String sitePostNo) {
        this.sitePostNo = sitePostNo;
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

    public String getRdnPostNo() {
        return rdnPostNo;
    }

    public void setRdnPostNo(String rdnPostNo) {
        this.rdnPostNo = rdnPostNo;
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

    public String getUpdateGbn() {
        return updateGbn;
    }

    public void setUpdateGbn(String updateGbn) {
        this.updateGbn = updateGbn;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getUptaenm() {
        return uptaenm;
    }

    public void setUptaenm(String uptaenm) {
        this.uptaenm = uptaenm;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getCulphyEdcObNm() {
        return culphyEdcObNm;
    }

    public void setCulphyEdcObNm(String culphyEdcObNm) {
        this.culphyEdcObNm = culphyEdcObNm;
    }

    public String getJgongYmd() {
        return jgongYmd;
    }

    public void setJgongYmd(String jgongYmd) {
        this.jgongYmd = jgongYmd;
    }

    public String getOpenYmd() {
        return openYmd;
    }

    public void setOpenYmd(String openYmd) {
        this.openYmd = openYmd;
    }

    public String getMngmbdnm() {
        return mngmbdnm;
    }

    public void setMngmbdnm(String mngmbdnm) {
        this.mngmbdnm = mngmbdnm;
    }

    public String getMuseumSortNm() {
        return museumSortNm;
    }

    public void setMuseumSortNm(String museumSortNm) {
        this.museumSortNm = museumSortNm;
    }

    public String getMuseumKindNm() {
        return museumKindNm;
    }

    public void setMuseumKindNm(String museumKindNm) {
        this.museumKindNm = museumKindNm;
    }

    public String getCloseYmd() {
        return closeYmd;
    }

    public void setCloseYmd(String closeYmd) {
        this.closeYmd = closeYmd;
    }

    public String getCloseWhy() {
        return closeWhy;
    }

    public void setCloseWhy(String closeWhy) {
        this.closeWhy = closeWhy;
    }

    public String getCapt() {
        return capt;
    }

    public void setCapt(String capt) {
        this.capt = capt;
    }

    public String getNdloan() {
        return ndloan;
    }

    public void setNdloan(String ndloan) {
        this.ndloan = ndloan;
    }

    public String getBupesbmntobj() {
        return bupesbmntobj;
    }

    public void setBupesbmntobj(String bupesbmntobj) {
        this.bupesbmntobj = bupesbmntobj;
    }

    public String getPermcn() {
        return permcn;
    }

    public void setPermcn(String permcn) {
        this.permcn = permcn;
    }

    public String getBupdisorgymd() {
        return bupdisorgymd;
    }

    public void setBupdisorgymd(String bupdisorgymd) {
        this.bupdisorgymd = bupdisorgymd;
    }

    public String getBupnm() {
        return bupnm;
    }

    public void setBupnm(String bupnm) {
        this.bupnm = bupnm;
    }

    @Override
    public String toString() {
        return "MuseumData{" +
                "opnsfTeamCode='" + opnsfTeamCode + '\'' +
                ", mgtNo='" + mgtNo + '\'' +
                ", apvPermYmd='" + apvPermYmd + '\'' +
                ", apvCancelYmd='" + apvCancelYmd + '\'' +
                ", trdStateGbn='" + trdStateGbn + '\'' +
                ", trdStateNm='" + trdStateNm + '\'' +
                ", dtlStateGbn='" + dtlStateGbn + '\'' +
                ", dtlStateNm='" + dtlStateNm + '\'' +
                ", dcbYmd='" + dcbYmd + '\'' +
                ", clgstDt='" + clgstDt + '\'' +
                ", clgendDt='" + clgendDt + '\'' +
                ", ropnymd='" + ropnymd + '\'' +
                ", siteTel='" + siteTel + '\'' +
                ", siteArea='" + siteArea + '\'' +
                ", sitePostNo='" + sitePostNo + '\'' +
                ", siteWhlAddr='" + siteWhlAddr + '\'' +
                ", rdnWhlAddr='" + rdnWhlAddr + '\'' +
                ", rdnPostNo='" + rdnPostNo + '\'' +
                ", bplcNm='" + bplcNm + '\'' +
                ", lastModTs='" + lastModTs + '\'' +
                ", updateGbn='" + updateGbn + '\'' +
                ", updateDt='" + updateDt + '\'' +
                ", uptaenm='" + uptaenm + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", culphyEdcObNm='" + culphyEdcObNm + '\'' +
                ", jgongYmd='" + jgongYmd + '\'' +
                ", openYmd='" + openYmd + '\'' +
                ", mngmbdnm='" + mngmbdnm + '\'' +
                ", museumSortNm='" + museumSortNm + '\'' +
                ", museumKindNm='" + museumKindNm + '\'' +
                ", closeYmd='" + closeYmd + '\'' +
                ", closeWhy='" + closeWhy + '\'' +
                ", capt='" + capt + '\'' +
                ", ndloan='" + ndloan + '\'' +
                ", bupesbmntobj='" + bupesbmntobj + '\'' +
                ", permcn='" + permcn + '\'' +
                ", bupdisorgymd='" + bupdisorgymd + '\'' +
                ", bupnm='" + bupnm + '\'' +
                '}';
    }
}