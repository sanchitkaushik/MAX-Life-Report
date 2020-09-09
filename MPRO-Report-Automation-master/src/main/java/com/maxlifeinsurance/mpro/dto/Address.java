package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
/**
 * 
 * @author Qualtech
 *
 */
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String proofType;
	private String addressType;
	private AddressDetails addressDetails;

	public String getProofType() {
		return proofType;
	}

	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public AddressDetails getAddressDetails() {
		if (addressDetails != null) {
			return addressDetails;
		} else {
			return new AddressDetails();
		}

	}

	public void setAddressDetails(AddressDetails addressDetails) {
		this.addressDetails = addressDetails;
	}

}
