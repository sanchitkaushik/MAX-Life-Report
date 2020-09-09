package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Qualtech
 *
 */
public class Proposal implements Serializable {

	private static final long serialVersionUID = 1L;
	private long transactionId;
	private ApplicationDetails applicationDetails;
	private SourcingDetails sourcingDetails;
	private ChannelDetails channelDetails;
	private BancaDetails bancaDetails;
	private List<ProductDetails> productDetails;
	private Bank bank;
	private PaymentDetails paymentDetails;
	private AdditionalFlags additionalFlags;
	private List<PartyInformation> partyInformation;
	private EmploymentDetails employmentDetails;
	private PosvDetails posvDetails;
	private UnderwritingServiceDetails underwritingServiceDetails;
	private SalesStoriesProductDetails salesStoriesProductDetails;
	private NomineeDetails nomineeDetails;

	public NomineeDetails getNomineeDetails() {
		if (nomineeDetails != null) {
			return nomineeDetails;
		} else {
			return new NomineeDetails();
		}
	}

	public void setNomineeDetails(NomineeDetails nomineeDetails) {
		this.nomineeDetails = nomineeDetails;
	}

	public SalesStoriesProductDetails getSalesStoriesProductDetails() {
		if (salesStoriesProductDetails != null) {
			return salesStoriesProductDetails;
		} else {
			return new SalesStoriesProductDetails();
		}
	}

	public void setSalesStoriesProductDetails(SalesStoriesProductDetails salesStoriesProductDetails) {
		this.salesStoriesProductDetails = salesStoriesProductDetails;
	}

	public UnderwritingServiceDetails getUnderwritingServiceDetails() {
		if (underwritingServiceDetails != null) {
			return underwritingServiceDetails;
		} else {
			return new UnderwritingServiceDetails();
		}

	}

	public void setUnderwritingServiceDetails(UnderwritingServiceDetails underwritingServiceDetails) {
		this.underwritingServiceDetails = underwritingServiceDetails;
	}

	public PosvDetails getPosvDetails() {
		if (posvDetails != null) {
			return posvDetails;
		} else {
			return new PosvDetails();
		}

	}

	public void setPosvDetails(PosvDetails posvDetails) {
		this.posvDetails = posvDetails;
	}

	public EmploymentDetails getEmploymentDetails() {
		if (employmentDetails != null) {
			return employmentDetails;
		} else {
			return new EmploymentDetails();
		}

	}

	public void setEmploymentDetails(EmploymentDetails employmentDetails) {
		this.employmentDetails = employmentDetails;
	}

	public List<PartyInformation> getPartyInformation() {
		if (partyInformation != null) {
			return partyInformation;
		} else {
			return new ArrayList<PartyInformation>();
		}
	}

	public void setPartyInformation(List<PartyInformation> partyInformation) {
		this.partyInformation = partyInformation;
	}

	public AdditionalFlags getAdditionalFlags() {
		if (additionalFlags != null) {
			return additionalFlags;
		} else {
			return new AdditionalFlags();
		}

	}

	public void setAdditionalFlags(AdditionalFlags additionalFlags) {
		this.additionalFlags = additionalFlags;
	}

	public List<ProductDetails> getProductDetails() {
		if (productDetails != null) {
			return productDetails;
		} else {
			return new ArrayList<ProductDetails>();
		}
	}

	public void setProductDetails(List<ProductDetails> productDetails) {
		this.productDetails = productDetails;
	}

	public Bank getBank() {
		if (bank != null) {
			return bank;
		} else {
			return new Bank();
		}
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public PaymentDetails getPaymentDetails() {
		if (paymentDetails != null) {
			return paymentDetails;
		} else {
			return new PaymentDetails();
		}

	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public ApplicationDetails getApplicationDetails() {
		if (applicationDetails != null) {
			return applicationDetails;
		} else {
			return new ApplicationDetails();
		}
	}

	public void setApplicationDetails(ApplicationDetails applicationDetails) {
		this.applicationDetails = applicationDetails;
	}

	public SourcingDetails getSourcingDetails() {

		if (sourcingDetails != null) {
			return sourcingDetails;
		} else {
			return new SourcingDetails();
		}
	}

	public void setSourcingDetails(SourcingDetails sourcingDetails) {
		this.sourcingDetails = sourcingDetails;
	}

	public ChannelDetails getChannelDetails() {
		if (channelDetails != null) {
			return channelDetails;
		} else {
			return new ChannelDetails();
		}

	}

	public void setChannelDetails(ChannelDetails channelDetails) {
		this.channelDetails = channelDetails;
	}

	public BancaDetails getBancaDetails() {
		if (bancaDetails != null) {
			return bancaDetails;
		} else {
			return new BancaDetails();
		}
	}

	public void setBancaDetails(BancaDetails bancaDetails) {
		this.bancaDetails = bancaDetails;
	}

}
