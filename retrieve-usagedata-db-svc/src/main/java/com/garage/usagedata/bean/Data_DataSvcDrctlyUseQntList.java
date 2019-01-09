package com.garage.usagedata.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(PK_DataSvcDrctlyUseQntList.class)
@Table(name = "dataSvcDrctlyUseQntList")
public class Data_DataSvcDrctlyUseQntList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "svcContId")
	private String svcContId;
	
	@Id
	@Column(name = "retvDt")
	private String retvDt;
	
	@Column(name = "prodEfctStDate")
	private String prodEfctStDate;
	
	@Column(name = "prodEfctFnsDate")
	private String prodEfctFnsDate;
	
	@Column(name = "prodId")
	private String prodId;
	
	@Column(name = "prodNm")
	private String prodNm;
	
	@Column(name = "ntnlOutnetVcTlkQnt")
	private String ntnlOutnetVcTlkQnt;
	
	@Column(name = "billMidCtgCd")
	private String billMidCtgCd;
	
	@Column(name = "billMidCtgNm")
	private String billMidCtgNm;
	
	@Column(name = "totUseQnt")
	private String totUseQnt;
	
	@Column(name = "nnRatUseQnt")
	private String nnRatUseQnt;
	
	@Column(name = "ratUseQnt")
	private String ratUseQnt;
	
	@Column(name = "ratAmt")
	private String ratAmt;
	
	@Column(name = "svcTarif")
	private String svcTarif;
	
	@Column(name = "bigiDiv")
	private String bigiDiv;
	
	protected Data_DataSvcDrctlyUseQntList() {
	}

	public String getSvcContId() {
		return svcContId;
	}

	public void setSvcContId(String svcContId) {
		this.svcContId = svcContId;
	}

	public String getRetvDt() {
		return retvDt;
	}

	public void setRetvDt(String retvDt) {
		this.retvDt = retvDt;
	}

	public String getProdEfctStDate() {
		return prodEfctStDate;
	}

	public void setProdEfctStDate(String prodEfctStDate) {
		this.prodEfctStDate = prodEfctStDate;
	}

	public String getProdEfctFnsDate() {
		return prodEfctFnsDate;
	}

	public void setProdEfctFnsDate(String prodEfctFnsDate) {
		this.prodEfctFnsDate = prodEfctFnsDate;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getNtnlOutnetVcTlkQnt() {
		return ntnlOutnetVcTlkQnt;
	}

	public void setNtnlOutnetVcTlkQnt(String ntnlOutnetVcTlkQnt) {
		this.ntnlOutnetVcTlkQnt = ntnlOutnetVcTlkQnt;
	}

	public String getBillMidCtgCd() {
		return billMidCtgCd;
	}

	public void setBillMidCtgCd(String billMidCtgCd) {
		this.billMidCtgCd = billMidCtgCd;
	}

	public String getBillMidCtgNm() {
		return billMidCtgNm;
	}

	public void setBillMidCtgNm(String billMidCtgNm) {
		this.billMidCtgNm = billMidCtgNm;
	}

	public String getTotUseQnt() {
		return totUseQnt;
	}

	public void setTotUseQnt(String totUseQnt) {
		this.totUseQnt = totUseQnt;
	}

	public String getNnRatUseQnt() {
		return nnRatUseQnt;
	}

	public void setNnRatUseQnt(String nnRatUseQnt) {
		this.nnRatUseQnt = nnRatUseQnt;
	}

	public String getRatUseQnt() {
		return ratUseQnt;
	}

	public void setRatUseQnt(String ratUseQnt) {
		this.ratUseQnt = ratUseQnt;
	}

	public String getRatAmt() {
		return ratAmt;
	}

	public void setRatAmt(String ratAmt) {
		this.ratAmt = ratAmt;
	}

	public String getSvcTarif() {
		return svcTarif;
	}

	public void setSvcTarif(String svcTarif) {
		this.svcTarif = svcTarif;
	}

	public String getBigiDiv() {
		return bigiDiv;
	}

	public void setBigiDiv(String bigiDiv) {
		this.bigiDiv = bigiDiv;
	}

	
}
