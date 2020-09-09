package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class RequiredDocuments implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String brmsId;

	public String getBrmsId() {
		return brmsId;
	}

	public void setBrmsId(String brmsId) {
		this.brmsId = brmsId;
	}

}
