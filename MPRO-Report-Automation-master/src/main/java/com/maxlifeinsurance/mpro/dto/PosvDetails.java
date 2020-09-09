package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class PosvDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private PosvStatus posvStatus;

	public PosvStatus getPosvStatus() {
		if (posvStatus != null) {
			return posvStatus;
		} else {
			return new PosvStatus();
		}

	}

	public void setPosvStatus(PosvStatus posvStatus) {
		this.posvStatus = posvStatus;
	}

}
