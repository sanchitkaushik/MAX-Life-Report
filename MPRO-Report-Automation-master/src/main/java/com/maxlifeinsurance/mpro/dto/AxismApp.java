package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

public class AxismApp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long proposalNo;
	private long clientId;
	private long agentCode;
	private String creationTime;
	private String productCode;
	private long premiumPayingTerm;
	private long policyTerm;
	private String spCode;
	private long modalPremium;
	private long branchCode;
	private String proposerName;
	private String paymentMode;
	private float afyp;
	private long ssnId;
	private String planName;
	private String caseStatus;

	public long getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(long proposalNo) {
		this.proposalNo = proposalNo;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public long getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(long agentCode) {
		this.agentCode = agentCode;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public long getPremiumPayingTerm() {
		return premiumPayingTerm;
	}

	public void setPremiumPayingTerm(long premiumPayingTerm) {
		this.premiumPayingTerm = premiumPayingTerm;
	}

	public long getPolicyTerm() {
		return policyTerm;
	}

	public void setPolicyTerm(long policyTerm) {
		this.policyTerm = policyTerm;
	}

	public String getSpCode() {
		return spCode;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public long getModalPremium() {
		return modalPremium;
	}

	public void setModalPremium(long modalPremium) {
		this.modalPremium = modalPremium;
	}

	public long getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(long branchCode) {
		this.branchCode = branchCode;
	}

	public String getProposerName() {
		return proposerName;
	}

	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public float getAfyp() {
		return afyp;
	}

	public void setAfyp(float afyp) {
		this.afyp = afyp;
	}

	public long getSsnId() {
		return ssnId;
	}

	public void setSsnId(long ssnId) {
		this.ssnId = ssnId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

}
