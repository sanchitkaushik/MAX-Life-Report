package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Qualtech
 *
 */
public class NomineeDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<PartyDetails> partyDetails;

	public List<PartyDetails> getPartyDetails() {
		if (partyDetails != null) {
			return partyDetails;
		} else {
			return new ArrayList<PartyDetails>();
		}
	}

	public void setPartyDetails(List<PartyDetails> partyDetails) {
		this.partyDetails = partyDetails;
	}

}
