package com.example.liveguard_app_010.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "row", strict = false)
public class PerformanceMovieData {

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

    @Element(name = "CULWRKRSENM", required = false)
    private String culwrkrsenm;

    @Element(name = "TOTNUMLAY", required = false)
    private String totnumlay;

    @Element(name = "NEARENVNM", required = false)
    private String nearenvnm;

    @Element(name = "MNFACTREARTCLCN", required = false)
    private String mnfactreartclcn;

    @Element(name = "FACILAR", required = false)
    private String facilar;

    @Element(name = "JISGNUMLAY", required = false)
    private String jisgnumlay;

    @Element(name = "UNDERNUMLAY", required = false)
    private String undernumlay;

    @Element(name = "BDNGSRVNM", required = false)
    private String bdngsrvnm;

    @Element(name = "PASGBRETH", required = false)
    private String pasgbreth;

    @Element(name = "LGHTFACILINILLU", required = false)
    private String lghtfacilinillu;

    @Element(name = "NOROOMCNT", required = false)
    private String noroomcnt;

    @Element(name = "BGROOMCNT", required = false)
    private String bgroomcnt;

    @Element(name = "EMERSTAIRYN", required = false)
    private String emerstairyn;

    @Element(name = "EMEXYN", required = false)
    private String emexyn;

    @Element(name = "AUTOCHAAIRYN", required = false)
    private String autochaaairyn;

    @Element(name = "BGROOMYN", required = false)
    private String bgroomyn;

    @Element(name = "SPECLGHTYN", required = false)
    private String speclghtyn;

    @Element(name = "SOUARFACILYN", required = false)
    private String souarfacilyn;

    @Element(name = "VDORETORNM", required = false)
    private String vdoretornm;

    @Element(name = "LGHTFACILYN", required = false)
    private String lghtfacilyn;

    @Element(name = "SOUNDFACILYN", required = false)
    private String soundfacilyn;

    @Element(name = "CNVEFACILYN", required = false)
    private String cnvefacilyn;

    @Element(name = "FIREFACILYN", required = false)
    private String firefacilyn;

    @Element(name = "TOTGASYSCNT", required = false)
    private String totgasyscnt;

    @Element(name = "BFGAMEOCPTECTCOBNM", required = false)
    private String bfgameocptectcobnm;

    @Element(name = "PRVDGATHINNM", required = false)
    private String prvdgathinnm;

    @Element(name = "PERPLAFORMSENM", required = false)
    private String perplaformsenm;

    @Element(name = "ACTLNM", required = false)
    private String actlnm;

    @Element(name = "FRSTREGTS", required = false)
    private String frstregts;

    @Element(name = "REGNSENM", required = false)
    private String regnsenm;

    // 기본 생성자
    public PerformanceMovieData() {
    }

    // Getter/Setter 메서드들
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

    public String getCulwrkrsenm() {
        return culwrkrsenm;
    }

    public void setCulwrkrsenm(String culwrkrsenm) {
        this.culwrkrsenm = culwrkrsenm;
    }

    public String getTotnumlay() {
        return totnumlay;
    }

    public void setTotnumlay(String totnumlay) {
        this.totnumlay = totnumlay;
    }

    public String getNearenvnm() {
        return nearenvnm;
    }

    public void setNearenvnm(String nearenvnm) {
        this.nearenvnm = nearenvnm;
    }

    public String getMnfactreartclcn() {
        return mnfactreartclcn;
    }

    public void setMnfactreartclcn(String mnfactreartclcn) {
        this.mnfactreartclcn = mnfactreartclcn;
    }

    public String getFacilar() {
        return facilar;
    }

    public void setFacilar(String facilar) {
        this.facilar = facilar;
    }

    public String getJisgnumlay() {
        return jisgnumlay;
    }

    public void setJisgnumlay(String jisgnumlay) {
        this.jisgnumlay = jisgnumlay;
    }

    public String getUndernumlay() {
        return undernumlay;
    }

    public void setUndernumlay(String undernumlay) {
        this.undernumlay = undernumlay;
    }

    public String getBdngsrvnm() {
        return bdngsrvnm;
    }

    public void setBdngsrvnm(String bdngsrvnm) {
        this.bdngsrvnm = bdngsrvnm;
    }

    public String getPasgbreth() {
        return pasgbreth;
    }

    public void setPasgbreth(String pasgbreth) {
        this.pasgbreth = pasgbreth;
    }

    public String getLghtfacilinillu() {
        return lghtfacilinillu;
    }

    public void setLghtfacilinillu(String lghtfacilinillu) {
        this.lghtfacilinillu = lghtfacilinillu;
    }

    public String getNoroomcnt() {
        return noroomcnt;
    }

    public void setNoroomcnt(String noroomcnt) {
        this.noroomcnt = noroomcnt;
    }

    public String getBgroomcnt() {
        return bgroomcnt;
    }

    public void setBgroomcnt(String bgroomcnt) {
        this.bgroomcnt = bgroomcnt;
    }

    public String getEmerstairyn() {
        return emerstairyn;
    }

    public void setEmerstairyn(String emerstairyn) {
        this.emerstairyn = emerstairyn;
    }

    public String getEmexyn() {
        return emexyn;
    }

    public void setEmexyn(String emexyn) {
        this.emexyn = emexyn;
    }

    public String getAutochaaairyn() {
        return autochaaairyn;
    }

    public void setAutochaaairyn(String autochaaairyn) {
        this.autochaaairyn = autochaaairyn;
    }

    public String getBgroomyn() {
        return bgroomyn;
    }

    public void setBgroomyn(String bgroomyn) {
        this.bgroomyn = bgroomyn;
    }

    public String getSpeclghtyn() {
        return speclghtyn;
    }

    public void setSpeclghtyn(String speclghtyn) {
        this.speclghtyn = speclghtyn;
    }

    public String getSouarfacilyn() {
        return souarfacilyn;
    }

    public void setSouarfacilyn(String souarfacilyn) {
        this.souarfacilyn = souarfacilyn;
    }

    public String getVdoretornm() {
        return vdoretornm;
    }

    public void setVdoretornm(String vdoretornm) {
        this.vdoretornm = vdoretornm;
    }

    public String getLghtfacilyn() {
        return lghtfacilyn;
    }

    public void setLghtfacilyn(String lghtfacilyn) {
        this.lghtfacilyn = lghtfacilyn;
    }

    public String getSoundfacilyn() {
        return soundfacilyn;
    }

    public void setSoundfacilyn(String soundfacilyn) {
        this.soundfacilyn = soundfacilyn;
    }

    public String getCnvefacilyn() {
        return cnvefacilyn;
    }

    public void setCnvefacilyn(String cnvefacilyn) {
        this.cnvefacilyn = cnvefacilyn;
    }

    public String getFirefacilyn() {
        return firefacilyn;
    }

    public void setFirefacilyn(String firefacilyn) {
        this.firefacilyn = firefacilyn;
    }

    public String getTotgasyscnt() {
        return totgasyscnt;
    }

    public void setTotgasyscnt(String totgasyscnt) {
        this.totgasyscnt = totgasyscnt;
    }

    public String getBfgameocptectcobnm() {
        return bfgameocptectcobnm;
    }

    public void setBfgameocptectcobnm(String bfgameocptectcobnm) {
        this.bfgameocptectcobnm = bfgameocptectcobnm;
    }

    public String getPrvdgathinnm() {
        return prvdgathinnm;
    }

    public void setPrvdgathinnm(String prvdgathinnm) {
        this.prvdgathinnm = prvdgathinnm;
    }

    public String getPerplaformsenm() {
        return perplaformsenm;
    }

    public void setPerplaformsenm(String perplaformsenm) {
        this.perplaformsenm = perplaformsenm;
    }

    public String getActlnm() {
        return actlnm;
    }

    public void setActlnm(String actlnm) {
        this.actlnm = actlnm;
    }

    public String getFrstregts() {
        return frstregts;
    }

    public void setFrstregts(String frstregts) {
        this.frstregts = frstregts;
    }

    public String getRegnsenm() {
        return regnsenm;
    }

    public void setRegnsenm(String regnsenm) {
        this.regnsenm = regnsenm;
    }

    @Override
    public String toString() {
        return "PerformanceMovieData{" +
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
                ", culwrkrsenm='" + culwrkrsenm + '\'' +
                ", totnumlay='" + totnumlay + '\'' +
                ", nearenvnm='" + nearenvnm + '\'' +
                ", mnfactreartclcn='" + mnfactreartclcn + '\'' +
                ", facilar='" + facilar + '\'' +
                ", jisgnumlay='" + jisgnumlay + '\'' +
                ", undernumlay='" + undernumlay + '\'' +
                ", bdngsrvnm='" + bdngsrvnm + '\'' +
                ", pasgbreth='" + pasgbreth + '\'' +
                ", lghtfacilinillu='" + lghtfacilinillu + '\'' +
                ", noroomcnt='" + noroomcnt + '\'' +
                ", bgroomcnt='" + bgroomcnt + '\'' +
                ", emerstairyn='" + emerstairyn + '\'' +
                ", emexyn='" + emexyn + '\'' +
                ", autochaaairyn='" + autochaaairyn + '\'' +
                ", bgroomyn='" + bgroomyn + '\'' +
                ", speclghtyn='" + speclghtyn + '\'' +
                ", souarfacilyn='" + souarfacilyn + '\'' +
                ", vdoretornm='" + vdoretornm + '\'' +
                ", lghtfacilyn='" + lghtfacilyn + '\'' +
                ", soundfacilyn='" + soundfacilyn + '\'' +
                ", cnvefacilyn='" + cnvefacilyn + '\'' +
                ", firefacilyn='" + firefacilyn + '\'' +
                ", totgasyscnt='" + totgasyscnt + '\'' +
                ", bfgameocptectcobnm='" + bfgameocptectcobnm + '\'' +
                ", prvdgathinnm='" + prvdgathinnm + '\'' +
                ", perplaformsenm='" + perplaformsenm + '\'' +
                ", actlnm='" + actlnm + '\'' +
                ", frstregts='" + frstregts + '\'' +
                ", regnsenm='" + regnsenm + '\'' +
                '}';
    }
}