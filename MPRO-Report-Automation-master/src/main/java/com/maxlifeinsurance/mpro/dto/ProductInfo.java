package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Qualtech
 *
 */
public class ProductInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String productName;
	private String planCode;
	private String planCodeTPP;
	private String premiumType;
	private String productId;
	private String planCodeMFSA;
	private String planCodePOSV;
	private String modeOfPayment;

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getPlanCodeMFSA() {
		return planCodeMFSA;
	}

	public void setPlanCodeMFSA(String planCodeMFSA) {
		this.planCodeMFSA = planCodeMFSA;
	}

	public String getPlanCodePOSV() {
		return planCodePOSV;
	}

	public void setPlanCodePOSV(String planCodePOSV) {
		this.planCodePOSV = planCodePOSV;
	}

	private List<RiderDetails> riderDetails;

	private ProductIllustrationResponse productIllustrationResponse;

	public List<RiderDetails> getRiderDetails() {
		if (riderDetails != null) {
			return riderDetails;
		} else {
			return new ArrayList<RiderDetails>();
		}
	}

	public void setRiderDetails(List<RiderDetails> riderDetails) {
		this.riderDetails = riderDetails;
	}

	public String getPremiumType() {
		return premiumType;
	}

	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}

	public ProductIllustrationResponse getProductIllustrationResponse() {
		if (productIllustrationResponse != null) {
			return productIllustrationResponse;
		} else {
			return new ProductIllustrationResponse();
		}
	}

	public void setProductIllustrationResponse(ProductIllustrationResponse productIllustrationResponse) {
		this.productIllustrationResponse = productIllustrationResponse;
	}

	public String getPlanCodeTPP() {
		return planCodeTPP;
	}

	public void setPlanCodeTPP(String planCodeTPP) {
		this.planCodeTPP = planCodeTPP;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
