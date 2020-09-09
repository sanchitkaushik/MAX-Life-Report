package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class AddressDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String city;
	private String pinCode;
	private String aadhaarOcrStatus;
	private String area;
	private String houseNo;
	private String landmark;
	private String state;
	private String village;
	private String voterIdOcrStatus;
	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAadhaarOcrStatus() {
		return aadhaarOcrStatus;
	}

	public void setAadhaarOcrStatus(String aadhaarOcrStatus) {
		this.aadhaarOcrStatus = aadhaarOcrStatus;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getVoterIdOcrStatus() {
		return voterIdOcrStatus;
	}

	public void setVoterIdOcrStatus(String voterIdOcrStatus) {
		this.voterIdOcrStatus = voterIdOcrStatus;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

}
