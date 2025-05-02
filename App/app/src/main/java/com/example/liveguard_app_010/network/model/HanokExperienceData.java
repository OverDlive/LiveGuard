package com.example.liveguard_app_010.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.io.Serializable;

/**
 * XML <row> 하나의 한옥 체험 정보를 나타내는 모델 클래스
 */
@Root(name = "row", strict = false)
public class HanokExperienceData implements Serializable {

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
    private String dcbyMd;

    @Element(name = "CLGSTDT", required = false)
    private String clgstDt;

    @Element(name = "CLGENDDT", required = false)
    private String clgendDt;

    @Element(name = "ROPNYMD", required = false)
    private String ropnyMd;

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
    private String upTaenm;

    @Element(name = "X", required = false)
    private String x;

    @Element(name = "Y", required = false)
    private String y;

    @Element(name = "CULPHYEDCOBNM", required = false)
    private String culphyEdcObNm;

    @Element(name = "CULTWRKRSENM", required = false)
    private String cultWrkRsenm;

    @Element(name = "REGNSENM", required = false)
    private String regnSenm;

    @Element(name = "TOTNUMLAY", required = false)
    private String totNumLay;

    @Element(name = "NEARENVNM", required = false)
    private String nearEnvNm;

    @Element(name = "MNFACTREARTCLCN", required = false)
    private String mnFactReArtClcn;

    @Element(name = "INSURORGNM", required = false)
    private String insurOrgnm;

    @Element(name = "BDNGSRVNM", required = false)
    private String bdngSrvNm;

    @Element(name = "JISGNUMLAY", required = false)
    private String jisgnUmlay;

    @Element(name = "UNDERNUMLAY", required = false)
    private String underNumLay;

    @Element(name = "STROOMCNT", required = false)
    private String strOomCnt;

    @Element(name = "CNSTYAREA", required = false)
    private String cnstyArea;

    @Element(name = "ENGSTNTRNMNM", required = false)
    private String engStnTrnMnm;

    @Element(name = "ENGSTNTRNMADDR", required = false)
    private String engStnTrnMnmAddr;

    @Element(name = "SHPTOTTONS", required = false)
    private String shpTotTons;

    @Element(name = "SHPCNT", required = false)
    private String shpCnt;

    @Element(name = "SHPINFO", required = false)
    private String shpInfo;

    @Element(name = "STAGEAR", required = false)
    private String stageAr;

    @Element(name = "CHAIRCNT", required = false)
    private String chairCnt;

    @Element(name = "SVNSR", required = false)
    private String svnsr;

    @Element(name = "MEETSAMTIMESYGSTF", required = false)
    private String meetSamTimeSygstf;

    @Element(name = "FACILAR", required = false)
    private String facilAr;

    @Element(name = "PLAYUTSCNTDTL", required = false)
    private String playUtsCntDtl;

    @Element(name = "PLAYFACILCNT", required = false)
    private String playFacilCnt;

    @Element(name = "BCFACILEN", required = false)
    private String bcFacilEn;

    @Element(name = "GEICPFACILEN", required = false)
    private String geicpFacilEn;

    @Element(name = "DISPENEN", required = false)
    private String dispenEn;

    @Element(name = "INFOBEN", required = false)
    private String infoBen;

    @Element(name = "PLNINSURSTDT", required = false)
    private String plnInsurStDt;

    @Element(name = "PLNINSURENDDT", required = false)
    private String plnInsureEndDt;

    @Element(name = "CAPT", required = false)
    private String capt;

    @Element(name = "INSURSTDT", required = false)
    private String insurStDt;

    @Element(name = "INSURENDDT", required = false)
    private String insureEndDt;

    @Element(name = "AFC", required = false)
    private String afc;

    @Element(name = "FACILSCP", required = false)
    private String facilScp;

    // 기본 생성자
    public HanokExperienceData() {}

    // Getter and Setter methods
    public String getOpnsfTeamCode() { return opnsfTeamCode; }
    public void setOpnsfTeamCode(String opnsfTeamCode) { this.opnsfTeamCode = opnsfTeamCode; }
    public String getMgtNo() { return mgtNo; }
    public void setMgtNo(String mgtNo) { this.mgtNo = mgtNo; }
    public String getApvPermYmd() { return apvPermYmd; }
    public void setApvPermYmd(String apvPermYmd) { this.apvPermYmd = apvPermYmd; }
    public String getApvCancelYmd() { return apvCancelYmd; }
    public void setApvCancelYmd(String apvCancelYmd) { this.apvCancelYmd = apvCancelYmd; }
    public String getTrdStateGbn() { return trdStateGbn; }
    public void setTrdStateGbn(String trdStateGbn) { this.trdStateGbn = trdStateGbn; }
    public String getTrdStateNm() { return trdStateNm; }
    public void setTrdStateNm(String trdStateNm) { this.trdStateNm = trdStateNm; }
    public String getDtlStateGbn() { return dtlStateGbn; }
    public void setDtlStateGbn(String dtlStateGbn) { this.dtlStateGbn = dtlStateGbn; }
    public String getDtlStateNm() { return dtlStateNm; }
    public void setDtlStateNm(String dtlStateNm) { this.dtlStateNm = dtlStateNm; }
    public String getDcbyMd() { return dcbyMd; }
    public void setDcbyMd(String dcbyMd) { this.dcbyMd = dcbyMd; }
    public String getClgstDt() { return clgstDt; }
    public void setClgstDt(String clgstDt) { this.clgstDt = clgstDt; }
    public String getClgendDt() { return clgendDt; }
    public void setClgendDt(String clgendDt) { this.clgendDt = clgendDt; }
    public String getRopnyMd() { return ropnyMd; }
    public void setRopnyMd(String ropnyMd) { this.ropnyMd = ropnyMd; }
    public String getSiteTel() { return siteTel; }
    public void setSiteTel(String siteTel) { this.siteTel = siteTel; }
    public String getSiteArea() { return siteArea; }
    public void setSiteArea(String siteArea) { this.siteArea = siteArea; }
    public String getSitePostNo() { return sitePostNo; }
    public void setSitePostNo(String sitePostNo) { this.sitePostNo = sitePostNo; }
    public String getSiteWhlAddr() { return siteWhlAddr; }
    public void setSiteWhlAddr(String siteWhlAddr) { this.siteWhlAddr = siteWhlAddr; }
    public String getRdnWhlAddr() { return rdnWhlAddr; }
    public void setRdnWhlAddr(String rdnWhlAddr) { this.rdnWhlAddr = rdnWhlAddr; }
    public String getRdnPostNo() { return rdnPostNo; }
    public void setRdnPostNo(String rdnPostNo) { this.rdnPostNo = rdnPostNo; }
    public String getBplcNm() { return bplcNm; }
    public void setBplcNm(String bplcNm) { this.bplcNm = bplcNm; }
    public String getLastModTs() { return lastModTs; }
    public void setLastModTs(String lastModTs) { this.lastModTs = lastModTs; }
    public String getUpdateGbn() { return updateGbn; }
    public void setUpdateGbn(String updateGbn) { this.updateGbn = updateGbn; }
    public String getUpdateDt() { return updateDt; }
    public void setUpdateDt(String updateDt) { this.updateDt = updateDt; }
    public String getUpTaenm() { return upTaenm; }
    public void setUpTaenm(String upTaenm) { this.upTaenm = upTaenm; }
    public String getX() { return x; }
    public void setX(String x) { this.x = x; }
    public String getY() { return y; }
    public void setY(String y) { this.y = y; }
    public String getCulphyEdcObNm() { return culphyEdcObNm; }
    public void setCulphyEdcObNm(String culphyEdcObNm) { this.culphyEdcObNm = culphyEdcObNm; }
    public String getCultWrkRsenm() { return cultWrkRsenm; }
    public void setCultWrkRsenm(String cultWrkRsenm) { this.cultWrkRsenm = cultWrkRsenm; }
    public String getRegnSenm() { return regnSenm; }
    public void setRegnSenm(String regnSenm) { this.regnSenm = regnSenm; }
    public String getTotNumLay() { return totNumLay; }
    public void setTotNumLay(String totNumLay) { this.totNumLay = totNumLay; }
    public String getNearEnvNm() { return nearEnvNm; }
    public void setNearEnvNm(String nearEnvNm) { this.nearEnvNm = nearEnvNm; }
    public String getMnFactReArtClcn() { return mnFactReArtClcn; }
    public void setMnFactReArtClcn(String mnFactReArtClcn) { this.mnFactReArtClcn = mnFactReArtClcn; }
    public String getInsurOrgnm() { return insurOrgnm; }
    public void setInsurOrgnm(String insurOrgnm) { this.insurOrgnm = insurOrgnm; }
    public String getBdngSrvNm() { return bdngSrvNm; }
    public void setBdngSrvNm(String bdngSrvNm) { this.bdngSrvNm = bdngSrvNm; }
    public String getJisgnUmlay() { return jisgnUmlay; }
    public void setJisgnUmlay(String jisgnUmlay) { this.jisgnUmlay = jisgnUmlay; }
    public String getUnderNumLay() { return underNumLay; }
    public void setUnderNumLay(String underNumLay) { this.underNumLay = underNumLay; }
    public String getStrOomCnt() { return strOomCnt; }
    public void setStrOomCnt(String strOomCnt) { this.strOomCnt = strOomCnt; }
    public String getCnstyArea() { return cnstyArea; }
    public void setCnstyArea(String cnstyArea) { this.cnstyArea = cnstyArea; }
    public String getEngStnTrnMnm() { return engStnTrnMnm; }
    public void setEngStnTrnMnm(String engStnTrnMnm) { this.engStnTrnMnm = engStnTrnMnm; }
    public String getEngStnTrnMnmAddr() { return engStnTrnMnmAddr; }
    public void setEngStnTrnMnmAddr(String engStnTrnMnmAddr) { this.engStnTrnMnmAddr = engStnTrnMnmAddr; }
    public String getShpTotTons() { return shpTotTons; }
    public void setShpTotTons(String shpTotTons) { this.shpTotTons = shpTotTons; }
    public String getShpCnt() { return shpCnt; }
    public void setShpCnt(String shpCnt) { this.shpCnt = shpCnt; }
    public String getShpInfo() { return shpInfo; }
    public void setShpInfo(String shpInfo) { this.shpInfo = shpInfo; }
    public String getStageAr() { return stageAr; }
    public void setStageAr(String stageAr) { this.stageAr = stageAr; }
    public String getChairCnt() { return chairCnt; }
    public void setChairCnt(String chairCnt) { this.chairCnt = chairCnt; }
    public String getSvnsr() { return svnsr; }
    public void setSvnsr(String svnsr) { this.svnsr = svnsr; }
    public String getMeetSamTimeSygstf() { return meetSamTimeSygstf; }
    public void setMeetSamTimeSygstf(String meetSamTimeSygstf) { this.meetSamTimeSygstf = meetSamTimeSygstf; }
    public String getFacilAr() { return facilAr; }
    public void setFacilAr(String facilAr) { this.facilAr = facilAr; }
    public String getPlayUtsCntDtl() { return playUtsCntDtl; }
    public void setPlayUtsCntDtl(String playUtsCntDtl) { this.playUtsCntDtl = playUtsCntDtl; }
    public String getPlayFacilCnt() { return playFacilCnt; }
    public void setPlayFacilCnt(String playFacilCnt) { this.playFacilCnt = playFacilCnt; }
    public String getBcFacilEn() { return bcFacilEn; }
    public void setBcFacilEn(String bcFacilEn) { this.bcFacilEn = bcFacilEn; }
    public String getGeicpFacilEn() { return geicpFacilEn; }
    public void setGeicpFacilEn(String geicpFacilEn) { this.geicpFacilEn = geicpFacilEn; }
    public String getDispenEn() { return dispenEn; }
    public void setDispenEn(String dispenEn) { this.dispenEn = dispenEn; }
    public String getInfoBen() { return infoBen; }
    public void setInfoBen(String infoBen) { this.infoBen = infoBen; }
    public String getPlnInsurStDt() { return plnInsurStDt; }
    public void setPlnInsurStDt(String plnInsurStDt) { this.plnInsurStDt = plnInsurStDt; }
    public String getPlnInsureEndDt() { return plnInsureEndDt; }
    public void setPlnInsureEndDt(String plnInsureEndDt) { this.plnInsureEndDt = plnInsureEndDt; }
    public String getCapt() { return capt; }
    public void setCapt(String capt) { this.capt = capt; }
    public String getInsurStDt() { return insurStDt; }
    public void setInsurStDt(String insurStDt) { this.insurStDt = insurStDt; }
    public String getInsureEndDt() { return insureEndDt; }
    public void setInsureEndDt(String insureEndDt) { this.insureEndDt = insureEndDt; }
    public String getAfc() { return afc; }
    public void setAfc(String afc) { this.afc = afc; }
    public String getFacilScp() { return facilScp; }
    public void setFacilScp(String facilScp) { this.facilScp = facilScp; }
}