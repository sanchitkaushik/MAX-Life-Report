package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class RiskScoreDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String normalisedScoreURMU;
	private String riskyTagURMU;

	public String getNormalisedScoreURMU() {
		return normalisedScoreURMU;
	}

	public void setNormalisedScoreURMU(String normalisedScoreURMU) {
		this.normalisedScoreURMU = normalisedScoreURMU;
	}

	public String getRiskyTagURMU() {
		return riskyTagURMU;
	}

	public void setRiskyTagURMU(String riskyTagURMU) {
		this.riskyTagURMU = riskyTagURMU;
	}

}
