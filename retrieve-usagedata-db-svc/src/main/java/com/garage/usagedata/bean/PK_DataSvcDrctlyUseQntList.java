package com.garage.usagedata.bean;

import java.io.Serializable;

public class PK_DataSvcDrctlyUseQntList implements Serializable {

	private static final long serialVersionUID = 1L;

	private String svcContId;
	
	private String retvDt;
	
	public PK_DataSvcDrctlyUseQntList() {
		
	}
	
	public PK_DataSvcDrctlyUseQntList(String svcContId, String retvDt) {
		this.svcContId = svcContId;
		this.retvDt = retvDt;
	}
	
}
