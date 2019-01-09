package com.garage.usagedata.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(PK_UsePtrn3monsRetv.class)
@Table(name = "usePtrn3monsRetv")
public class Data_UsePtrn3monsRetv implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "svcContId")
	private String svcContId;
	
	@Id
	@Column(name = "retvYm")
	private String retvYm;
	
	@Column(name = "vcTlkTotQant")
	private String vcTlkTotQant;
	
	@Column(name = "ntnlVcTlkTotQnt")
	private String ntnlVcTlkTotQnt;
	
	@Column(name = "intlVcTlkTotQnt")
	private String intlVcTlkTotQnt;
	
	@Column(name = "ntnlInnetVcTlkQnt")
	private String ntnlInnetVcTlkQnt;
	
	@Column(name = "ntnlOutnetVcTlkQnt")
	private String ntnlOutnetVcTlkQnt;
	
	@Column(name = "dataPacktQnt")
	private String dataPacktQnt;
	
	@Column(name = "smsUseQnt")
	private String smsUseQnt;
	
	protected Data_UsePtrn3monsRetv() {
	}

	public String getSvcContId() {
		return svcContId;
	}

	public void setSvcContId(String svcContId) {
		this.svcContId = svcContId;
	}

	public String getRetvYm() {
		return retvYm;
	}

	public void setRetvYm(String retvYm) {
		this.retvYm = retvYm;
	}

	public String getVcTlkTotQant() {
		return vcTlkTotQant;
	}

	public void setVcTlkTotQant(String vcTlkTotQant) {
		this.vcTlkTotQant = vcTlkTotQant;
	}

	public String getNtnlVcTlkTotQnt() {
		return ntnlVcTlkTotQnt;
	}

	public void setNtnlVcTlkTotQnt(String ntnlVcTlkTotQnt) {
		this.ntnlVcTlkTotQnt = ntnlVcTlkTotQnt;
	}

	public String getIntlVcTlkTotQnt() {
		return intlVcTlkTotQnt;
	}

	public void setIntlVcTlkTotQnt(String intlVcTlkTotQnt) {
		this.intlVcTlkTotQnt = intlVcTlkTotQnt;
	}

	public String getNtnlInnetVcTlkQnt() {
		return ntnlInnetVcTlkQnt;
	}

	public void setNtnlInnetVcTlkQnt(String ntnlInnetVcTlkQnt) {
		this.ntnlInnetVcTlkQnt = ntnlInnetVcTlkQnt;
	}

	public String getNtnlOutnetVcTlkQnt() {
		return ntnlOutnetVcTlkQnt;
	}

	public void setNtnlOutnetVcTlkQnt(String ntnlOutnetVcTlkQnt) {
		this.ntnlOutnetVcTlkQnt = ntnlOutnetVcTlkQnt;
	}

	public String getDataPacktQnt() {
		return dataPacktQnt;
	}

	public void setDataPacktQnt(String dataPacktQnt) {
		this.dataPacktQnt = dataPacktQnt;
	}

	public String getSmsUseQnt() {
		return smsUseQnt;
	}

	public void setSmsUseQnt(String smsUseQnt) {
		this.smsUseQnt = smsUseQnt;
	}	
	
}
