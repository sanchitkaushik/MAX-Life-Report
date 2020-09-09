package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class ProductDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private ProductInfo productInfo;
	private String needOfInsurance;
	private String productType;

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getNeedOfInsurance() {
		return needOfInsurance;
	}

	public void setNeedOfInsurance(String needOfInsurance) {
		this.needOfInsurance = needOfInsurance;
	}

	public ProductInfo getProductInfo() {
		if (productInfo != null) {
			return productInfo;
		} else {
			return new ProductInfo();
		}
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

}
