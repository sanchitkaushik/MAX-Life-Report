package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Qualtech
 *
 */
public class BasicDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private Date dob;
	private List<Address> address;
	private String annualIncome;
	private String education;
	private NationalityDetails nationalityDetails;
	private String occupation;
	private MarriageDetails marriageDetails;
	private String relationshipWithProposer;

	public String getRelationshipWithProposer() {
		return relationshipWithProposer;
	}

	public void setRelationshipWithProposer(String relationshipWithProposer) {
		this.relationshipWithProposer = relationshipWithProposer;
	}

	public MarriageDetails getMarriageDetails() {
		if (marriageDetails != null) {
			return marriageDetails;
		} else {
			return new MarriageDetails();
		}
	}

	public void setMarriageDetails(MarriageDetails marriageDetails) {
		this.marriageDetails = marriageDetails;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public NationalityDetails getNationalityDetails() {
		if (nationalityDetails != null) {
			return nationalityDetails;
		} else {
			return new NationalityDetails();
		}
	}

	public void setNationalityDetails(NationalityDetails nationalityDetails) {
		this.nationalityDetails = nationalityDetails;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public List<Address> getAddress() {
		if (address != null) {
			return address;
		} else {
			return new ArrayList<Address>();
		}
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

}
