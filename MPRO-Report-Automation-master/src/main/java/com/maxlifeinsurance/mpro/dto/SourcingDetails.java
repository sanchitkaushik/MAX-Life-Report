package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Qualtech
 *
 */
public class SourcingDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean isAgentValidated;
	private String agentId;
	private String agentName;
	private String specifiedPersonCode;
	private String goCABrokerCode;
	private SpecifiedPersonDetails specifiedPersonDetails;
	private String regionalAdvisorId;
	private String agentBranchData;
	private String agentCode;
	private String agentEmail;
	private Date agentJoiningDate;
	private Date agentLicenseStartdate;
	private String agentLocation;
	private long agentMobileNumber;
	private String agentRole;
	private String agentStatus;
	private String designation;
	private double persistency;
	private String reportingManagerCode;
	private String reportingManagerName;
	private String sourcingBranchCode;
	private String spStatus;
	private String specifiedPersonName;
	private String ssnCode;

	public String getAgentBranchData() {
		return agentBranchData;
	}

	public void setAgentBranchData(String agentBranchData) {
		this.agentBranchData = agentBranchData;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentEmail() {
		return agentEmail;
	}

	public void setAgentEmail(String agentEmail) {
		this.agentEmail = agentEmail;
	}

	public Date getAgentJoiningDate() {
		return agentJoiningDate;
	}

	public void setAgentJoiningDate(Date agentJoiningDate) {
		this.agentJoiningDate = agentJoiningDate;
	}

	public Date getAgentLicenseStartdate() {
		return agentLicenseStartdate;
	}

	public void setAgentLicenseStartdate(Date agentLicenseStartdate) {
		this.agentLicenseStartdate = agentLicenseStartdate;
	}

	public String getAgentLocation() {
		return agentLocation;
	}

	public void setAgentLocation(String agentLocation) {
		this.agentLocation = agentLocation;
	}

	public long getAgentMobileNumber() {
		return agentMobileNumber;
	}

	public void setAgentMobileNumber(long agentMobileNumber) {
		this.agentMobileNumber = agentMobileNumber;
	}

	public String getAgentRole() {
		return agentRole;
	}

	public void setAgentRole(String agentRole) {
		this.agentRole = agentRole;
	}

	public String getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(String agentStatus) {
		this.agentStatus = agentStatus;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getPersistency() {
		return persistency;
	}

	public void setPersistency(double persistency) {
		this.persistency = persistency;
	}

	public String getReportingManagerCode() {
		return reportingManagerCode;
	}

	public void setReportingManagerCode(String reportingManagerCode) {
		this.reportingManagerCode = reportingManagerCode;
	}

	public String getReportingManagerName() {
		return reportingManagerName;
	}

	public void setReportingManagerName(String reportingManagerName) {
		this.reportingManagerName = reportingManagerName;
	}

	public String getSourcingBranchCode() {
		return sourcingBranchCode;
	}

	public void setSourcingBranchCode(String sourcingBranchCode) {
		this.sourcingBranchCode = sourcingBranchCode;
	}

	public String getSpStatus() {
		return spStatus;
	}

	public void setSpStatus(String spStatus) {
		this.spStatus = spStatus;
	}

	public String getSpecifiedPersonName() {
		return specifiedPersonName;
	}

	public void setSpecifiedPersonName(String specifiedPersonName) {
		this.specifiedPersonName = specifiedPersonName;
	}

	public String getSsnCode() {
		return ssnCode;
	}

	public void setSsnCode(String ssnCode) {
		this.ssnCode = ssnCode;
	}

	public String getRegionalAdvisorId() {
		return regionalAdvisorId;
	}

	public void setRegionalAdvisorId(String regionalAdvisorId) {
		this.regionalAdvisorId = regionalAdvisorId;
	}

	public SpecifiedPersonDetails getSpecifiedPersonDetails() {
		if (specifiedPersonDetails != null) {
			return specifiedPersonDetails;
		} else {
			return new SpecifiedPersonDetails();
		}

	}

	public String getGoCABrokerCode() {
		return goCABrokerCode;
	}

	public void setGoCABrokerCode(String goCABrokerCode) {
		this.goCABrokerCode = goCABrokerCode;
	}

	public void setSpecifiedPersonDetails(SpecifiedPersonDetails specifiedPersonDetails) {
		this.specifiedPersonDetails = specifiedPersonDetails;
	}

	public String getSpecifiedPersonCode() {
		return specifiedPersonCode;
	}

	public void setSpecifiedPersonCode(String specifiedPersonCode) {
		this.specifiedPersonCode = specifiedPersonCode;
	}

	public boolean isAgentValidated() {
		return isAgentValidated;
	}

	public void setAgentValidated(boolean isAgentValidated) {
		this.isAgentValidated = isAgentValidated;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

}
