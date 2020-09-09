package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class FinancialGridDetails implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String underwritingResult;

	public String getUnderwritingResult() {
		return underwritingResult;
	}

	public void setUnderwritingResult(String underwritingResult) {
		this.underwritingResult = underwritingResult;
	}

}
