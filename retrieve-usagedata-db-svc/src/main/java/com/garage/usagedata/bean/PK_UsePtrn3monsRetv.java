package com.garage.usagedata.bean;

import java.io.Serializable;

public class PK_UsePtrn3monsRetv implements Serializable {

	private static final long serialVersionUID = 1L;

	private String svcContId;
	
	private String retvYm;
	
	public PK_UsePtrn3monsRetv() {
		
	}
	
	public PK_UsePtrn3monsRetv(String svcContId, String retvYm) {
		this.svcContId = svcContId;
		this.retvYm = retvYm;
	}
	
}
