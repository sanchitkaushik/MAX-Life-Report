package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class PosvStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	private String overallHealthStatus;

	public String getOverallHealthStatus() {
		return overallHealthStatus;
	}

	public void setOverallHealthStatus(String overallHealthStatus) {
		this.overallHealthStatus = overallHealthStatus;
	}

}
