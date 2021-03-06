package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class PanDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String creditScore;
	private String panNumber;
	private String isPanValidated;

	public String getIsPanValidated() {
		return isPanValidated;
	}

	public void setIsPanValidated(String isPanValidated) {
		this.isPanValidated = isPanValidated;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(String creditScore) {
		this.creditScore = creditScore;
	}

}
