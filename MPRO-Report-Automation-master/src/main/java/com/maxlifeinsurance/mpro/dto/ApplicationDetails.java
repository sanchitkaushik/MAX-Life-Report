package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Qualtech
 *
 */
public class ApplicationDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date createdTime;
	private String applicationStatus;
	private String stage;
	private String policyNumber;
	private String posvJourneyStatus;
	private String formType;
	private String policyProcessingJourneyStatus;

	public String getPolicyProcessingJourneyStatus() {
		return policyProcessingJourneyStatus;
	}

	public void setPolicyProcessingJourneyStatus(String policyProcessingJourneyStatus) {
		this.policyProcessingJourneyStatus = policyProcessingJourneyStatus;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getPosvJourneyStatus() {
		return posvJourneyStatus;
	}

	public void setPosvJourneyStatus(String posvJourneyStatus) {
		this.posvJourneyStatus = posvJourneyStatus;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

}
