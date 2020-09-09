package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class PersonalIdentification implements Serializable {

	private static final long serialVersionUID = 1L;
	private PanDetails panDetails;

	public PanDetails getPanDetails() {
		if (panDetails != null) {
			return panDetails;
		} else {
			return new PanDetails();
		}
	}

	public void setPanDetails(PanDetails panDetails) {
		this.panDetails = panDetails;
	}

}
