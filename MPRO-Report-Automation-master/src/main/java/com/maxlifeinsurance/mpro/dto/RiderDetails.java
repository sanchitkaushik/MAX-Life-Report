package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class RiderDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String riderInfo;
	private double amount;
	private boolean isRiderRequired;
	private double riderModalPremium;
	private String riderPlanCode;
	private double riderSumAssured;
	private String riderTerm;
	private String typeOfValue;

	public String getTypeOfValue() {
		return typeOfValue;
	}

	public void setTypeOfValue(String typeOfValue) {
		this.typeOfValue = typeOfValue;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isRiderRequired() {
		return isRiderRequired;
	}

	public void setRiderRequired(boolean isRiderRequired) {
		this.isRiderRequired = isRiderRequired;
	}

	public double getRiderModalPremium() {
		return riderModalPremium;
	}

	public void setRiderModalPremium(double riderModalPremium) {
		this.riderModalPremium = riderModalPremium;
	}

	public String getRiderPlanCode() {
		return riderPlanCode;
	}

	public void setRiderPlanCode(String riderPlanCode) {
		this.riderPlanCode = riderPlanCode;
	}

	public double getRiderSumAssured() {
		return riderSumAssured;
	}

	public void setRiderSumAssured(double riderSumAssured) {
		this.riderSumAssured = riderSumAssured;
	}

	public String getRiderTerm() {
		return riderTerm;
	}

	public void setRiderTerm(String riderTerm) {
		this.riderTerm = riderTerm;
	}

	public String getRiderInfo() {
		return riderInfo;
	}

	public void setRiderInfo(String riderInfo) {
		this.riderInfo = riderInfo;
	}

}
