package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class BancaDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String leadId;
	private String customerId;
	private String customerClassification;
	private String bancaId;
	private CrmBancaCustomerDetails crmBancaCustomerDetails;
	private String isCommAddressModified;
	private boolean isDOBModified;
	private boolean isNameModified;
	private String isPanModified;
	private String ukyc;

	public boolean isDOBModified() {
		return isDOBModified;
	}

	public void setDOBModified(boolean isDOBModified) {
		this.isDOBModified = isDOBModified;
	}

	public boolean isNameModified() {
		return isNameModified;
	}

	public void setNameModified(boolean isNameModified) {
		this.isNameModified = isNameModified;
	}

	public String isPanModified() {
		return isPanModified;
	}

	public void setPanModified(String isPanModified) {
		this.isPanModified = isPanModified;
	}

	public String getUkyc() {
		return ukyc;
	}

	public void setUkyc(String ukyc) {
		this.ukyc = ukyc;
	}

	public String getIsCommAddressModified() {
		return isCommAddressModified;
	}

	public void setIsCommAddressModified(String isCommAddressModified) {
		this.isCommAddressModified = isCommAddressModified;
	}

	public CrmBancaCustomerDetails getCrmBancaCustomerDetails() {
		if (crmBancaCustomerDetails != null) {
			return crmBancaCustomerDetails;
		} else {
			return new CrmBancaCustomerDetails();
		}
	}

	public void setCrmBancaCustomerDetails(CrmBancaCustomerDetails crmBancaCustomerDetails) {
		this.crmBancaCustomerDetails = crmBancaCustomerDetails;
	}

	public String getBancaId() {
		return bancaId;
	}

	public void setBancaId(String bancaId) {
		this.bancaId = bancaId;
	}

	public String getCustomerClassification() {
		return customerClassification;
	}

	public void setCustomerClassification(String customerClassification) {
		this.customerClassification = customerClassification;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

}
