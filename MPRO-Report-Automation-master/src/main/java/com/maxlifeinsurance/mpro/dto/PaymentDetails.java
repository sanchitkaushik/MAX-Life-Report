package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Qualtech
 *
 */
public class PaymentDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Receipt> receipt;

	public List<Receipt> getReceipt() {
		if(receipt != null){
			return receipt;
		}
		else{
			return new ArrayList<Receipt>();
		}
		
	}

	public void setReceipt(List<Receipt> receipt) {
		this.receipt = receipt;
	}

}
