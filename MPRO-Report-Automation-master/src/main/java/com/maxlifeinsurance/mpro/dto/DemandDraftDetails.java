package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class DemandDraftDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String demandDraftNumber;

	public String getDemandDraftNumber() {
		return demandDraftNumber;
	}

	public void setDemandDraftNumber(String demandDraftNumber) {
		this.demandDraftNumber = demandDraftNumber;
	}

}
