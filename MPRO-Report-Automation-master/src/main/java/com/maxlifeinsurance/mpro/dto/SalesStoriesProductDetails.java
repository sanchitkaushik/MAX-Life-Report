package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class SalesStoriesProductDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String isSalesProduct;
	private String salesReferenceId;

	public String getIsSalesProduct() {
		return isSalesProduct;
	}

	public void setIsSalesProduct(String isSalesProduct) {
		this.isSalesProduct = isSalesProduct;
	}

	public String getSalesReferenceId() {
		return salesReferenceId;
	}

	public void setSalesReferenceId(String salesReferenceId) {
		this.salesReferenceId = salesReferenceId;
	}

}
