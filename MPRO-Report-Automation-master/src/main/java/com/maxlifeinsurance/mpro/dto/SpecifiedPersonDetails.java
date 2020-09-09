package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Qualtech
 *
 */
public class SpecifiedPersonDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String spSSNCode;
	private String amlStatus;
	private Date amlTrainingExpirationDate;
	private String spCertificateNumber;
	private String spCode;
	private String spGoCABrokerCode;
	private Date spLicenseExpiryDate;
	private Date spLicenseStartDate;
	private String spLocation;
	private String spMobileNumber;
	private String spName;
	private String ulipStatus;
	private Date ulipTrainingExpirationDate;

	public String getAmlStatus() {
		return amlStatus;
	}

	public void setAmlStatus(String amlStatus) {
		this.amlStatus = amlStatus;
	}

	public Date getAmlTrainingExpirationDate() {
		return amlTrainingExpirationDate;
	}

	public void setAmlTrainingExpirationDate(Date amlTrainingExpirationDate) {
		this.amlTrainingExpirationDate = amlTrainingExpirationDate;
	}

	public String getSpCertificateNumber() {
		return spCertificateNumber;
	}

	public void setSpCertificateNumber(String spCertificateNumber) {
		this.spCertificateNumber = spCertificateNumber;
	}

	public String getSpCode() {
		return spCode;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public String getSpGoCABrokerCode() {
		return spGoCABrokerCode;
	}

	public void setSpGoCABrokerCode(String spGoCABrokerCode) {
		this.spGoCABrokerCode = spGoCABrokerCode;
	}

	public Date getSpLicenseExpiryDate() {
		return spLicenseExpiryDate;
	}

	public void setSpLicenseExpiryDate(Date spLicenseExpiryDate) {
		this.spLicenseExpiryDate = spLicenseExpiryDate;
	}

	public Date getSpLicenseStartDate() {
		return spLicenseStartDate;
	}

	public void setSpLicenseStartDate(Date spLicenseStartDate) {
		this.spLicenseStartDate = spLicenseStartDate;
	}

	public String getSpLocation() {
		return spLocation;
	}

	public void setSpLocation(String spLocation) {
		this.spLocation = spLocation;
	}

	public String getSpMobileNumber() {
		return spMobileNumber;
	}

	public void setSpMobileNumber(String spMobileNumber) {
		this.spMobileNumber = spMobileNumber;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getUlipStatus() {
		return ulipStatus;
	}

	public void setUlipStatus(String ulipStatus) {
		this.ulipStatus = ulipStatus;
	}

	public Date getUlipTrainingExpirationDate() {
		return ulipTrainingExpirationDate;
	}

	public void setUlipTrainingExpirationDate(Date ulipTrainingExpirationDate) {
		this.ulipTrainingExpirationDate = ulipTrainingExpirationDate;
	}

	public String getSpSSNCode() {
		return spSSNCode;
	}

	public void setSpSSNCode(String spSSNCode) {
		this.spSSNCode = spSSNCode;
	}

	@Override
	public String toString() {
		return "SpecifiedPersonDetails [spSSNCode=" + spSSNCode + ", amlStatus=" + amlStatus + ", amlTrainingExpirationDate=" + amlTrainingExpirationDate + ", spCertificateNumber=" + spCertificateNumber + ", spCode=" + spCode + ", spGoCABrokerCode="
				+ spGoCABrokerCode + ", spLicenseExpiryDate=" + spLicenseExpiryDate + ", spLicenseStartDate=" + spLicenseStartDate + ", spLocation=" + spLocation + ", spMobileNumber=" + spMobileNumber + ", spName=" + spName + ", ulipStatus="
				+ ulipStatus + ", ulipTrainingExpirationDate=" + ulipTrainingExpirationDate + "]";
	}

}
