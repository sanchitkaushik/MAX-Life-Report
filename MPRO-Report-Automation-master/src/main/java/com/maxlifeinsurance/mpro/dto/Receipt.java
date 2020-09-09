package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Qualtech
 *
 */
public class Receipt implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean isSIOpted;
	private String paymentType;
	private String modeOfPayment;
	private String premiumMode;
	private Date paymentDate;
	private PaymentChequeDetails paymentChequeDetails;
	private DemandDraftDetails demandDraftDetails;

	public DemandDraftDetails getDemandDraftDetails() {
		if (demandDraftDetails != null) {
			return demandDraftDetails;
		} else {
			return new DemandDraftDetails();
		}
	}

	public void setDemandDraftDetails(DemandDraftDetails demandDraftDetails) {
		this.demandDraftDetails = demandDraftDetails;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentChequeDetails getPaymentChequeDetails() {
		if (paymentChequeDetails != null) {
			return paymentChequeDetails;
		} else {
			return new PaymentChequeDetails();
		}
	}

	public void setPaymentChequeDetails(PaymentChequeDetails paymentChequeDetails) {
		this.paymentChequeDetails = paymentChequeDetails;
	}

	public boolean isSIOpted() {
		return isSIOpted;
	}

	public void setSIOpted(boolean isSIOpted) {
		this.isSIOpted = isSIOpted;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getPremiumMode() {
		return premiumMode;
	}

	public void setPremiumMode(String premiumMode) {
		this.premiumMode = premiumMode;
	}

}
