package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class EmploymentDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private PepDetails pepDetails;

	public PepDetails getPepDetails() {
		if (pepDetails != null) {
			return pepDetails;
		} else {
			return new PepDetails();
		}

	}

	public void setPepDetails(PepDetails pepDetails) {
		this.pepDetails = pepDetails;
	}

}
