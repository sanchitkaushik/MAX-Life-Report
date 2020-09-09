package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class PartyInformation implements Serializable {

	private static final long serialVersionUID = 1L;
	private String partyType;

	private BasicDetails basicDetails;
	private PersonalIdentification personalIdentification;

	public PersonalIdentification getPersonalIdentification() {
		if (personalIdentification != null) {
			return personalIdentification;
		} else {
			return new PersonalIdentification();
		}

	}

	public void setPersonalIdentification(PersonalIdentification personalIdentification) {
		this.personalIdentification = personalIdentification;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public BasicDetails getBasicDetails() {
		if (basicDetails != null) {
			return basicDetails;
		} else {
			return new BasicDetails();
		}
	}

	public void setBasicDetails(BasicDetails basicDetails) {
		this.basicDetails = basicDetails;
	}

}
