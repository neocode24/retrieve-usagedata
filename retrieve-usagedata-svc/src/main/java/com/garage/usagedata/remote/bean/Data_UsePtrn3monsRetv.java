package com.garage.usagedata.remote.bean;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
	@Builder
	public Data_UsePtrn3monsRetv(String svcContId, String retvYm) {
		this.svcContId = svcContId;
		this.retvYm = retvYm;
	}

}
