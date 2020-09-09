package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class JourneyFieldsModificationStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	private String communicationAddStatus;
	private String dobStatus;
	private String nameStatus;

	public String getNameStatus() {
		return nameStatus;
	}

	public void setNameStatus(String nameStatus) {
		this.nameStatus = nameStatus;
	}

	public String getDobStatus() {
		return dobStatus;
	}

	public void setDobStatus(String dobStatus) {
		this.dobStatus = dobStatus;
	}

	public String getCommunicationAddStatus() {
		return communicationAddStatus;
	}

	public void setCommunicationAddStatus(String communicationAddStatus) {
		this.communicationAddStatus = communicationAddStatus;
	}

}
