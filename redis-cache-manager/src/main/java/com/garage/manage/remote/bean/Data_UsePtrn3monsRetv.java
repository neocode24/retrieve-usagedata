package com.garage.manage.remote.bean;

import java.io.Serializable;

public class Data_UsePtrn3monsRetv implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String svcContId;
	
	private String retvYm;
	
	private String vcTlkTotQant;
	
	private String ntnlVcTlkTotQnt;
	
	private String intlVcTlkTotQnt;
	
	private String ntnlInnetVcTlkQnt;
	
	private String ntnlOutnetVcTlkQnt;
	
	private String dataPacktQnt;
	
	private String smsUseQnt;
	
	protected Data_UsePtrn3monsRetv() {
	}
	
	public Data_UsePtrn3monsRetv(String svcContId, String retvYm) {
		this.svcContId = svcContId;
		this.retvYm = retvYm;
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
