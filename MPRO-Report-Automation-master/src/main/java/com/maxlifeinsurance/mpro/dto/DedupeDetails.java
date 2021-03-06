package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Qualtech
 *
 */
public class DedupeDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String dedupeFlag;
	private String previousPolicyNumber;
	private String clientId;
	private String clientType;
	private String policyStatus;
	private Date prevpolicydate;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}

	public Date getPrevpolicydate() {
		return prevpolicydate;
	}

	public void setPrevpolicydate(Date prevpolicydate) {
		this.prevpolicydate = prevpolicydate;
	}

	public String getPreviousPolicyNumber() {
		return previousPolicyNumber;
	}

	public void setPreviousPolicyNumber(String previousPolicyNumber) {
		this.previousPolicyNumber = previousPolicyNumber;
	}

	public String getDedupeFlag() {
		return dedupeFlag;
	}

	public void setDedupeFlag(String dedupeFlag) {
		this.dedupeFlag = dedupeFlag;
	}

}
