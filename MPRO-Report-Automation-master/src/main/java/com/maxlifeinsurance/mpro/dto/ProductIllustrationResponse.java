package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Qualtech
 *
 */
public class ProductIllustrationResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String afyp;
	private double atp;
	private String coverageTerm;
	private double modalPremium;
	private String premiumPaymentTerm;
	private String deathBenefit;
	private double sumAssured;
	private double initialPremiumPaid;
	private Date effectiveDate;
	private String serviceTax;
	private String CABRiderPremium;
	private String CABRiderSumAssured;
	private String CABRiderTerm;
	private String acceleratedCriticalIllnessRiderSumAssured;
	private String acceleratedCriticalIllnessRiderTerm;
	private String accidentCoverRiderSumAssured;
	private String accidentCoverRiderTerm;
	private String addRiderSumAssured;
	private String addRiderTerm;
	private String firstYearADDRiderPremiumSummary;
	private String firstYearAcceleratedCriticalIllnessRiderPremiumSummary;
	private String firstYearAccidentCoverRiderPremiumSummary;
	private String firstYearPartnerCareRiderPremiumSummary;
	private String firstYearTermPlusRiderPremiumSummary;
	private String firstYearWOPPlusRiderPremiumSummary;
	private String partnerCareRiderSumAssured;
	private String partnerCareRiderTerm;
	private String requiredModalPremium;
	private String termPlusRiderSumAssured;
	private String termPlusRiderTerm;
	private String wopPlusRiderSumAssured;
	private String wopPlusRiderTerm;

	public String getCABRiderPremium() {
		return CABRiderPremium;
	}

	public void setCABRiderPremium(String cABRiderPremium) {
		CABRiderPremium = cABRiderPremium;
	}

	public String getCABRiderSumAssured() {
		return CABRiderSumAssured;
	}

	public void setCABRiderSumAssured(String cABRiderSumAssured) {
		CABRiderSumAssured = cABRiderSumAssured;
	}

	public String getCABRiderTerm() {
		return CABRiderTerm;
	}

	public void setCABRiderTerm(String cABRiderTerm) {
		CABRiderTerm = cABRiderTerm;
	}

	public String getAcceleratedCriticalIllnessRiderSumAssured() {
		return acceleratedCriticalIllnessRiderSumAssured;
	}

	public void setAcceleratedCriticalIllnessRiderSumAssured(String acceleratedCriticalIllnessRiderSumAssured) {
		this.acceleratedCriticalIllnessRiderSumAssured = acceleratedCriticalIllnessRiderSumAssured;
	}

	public String getAcceleratedCriticalIllnessRiderTerm() {
		return acceleratedCriticalIllnessRiderTerm;
	}

	public void setAcceleratedCriticalIllnessRiderTerm(String acceleratedCriticalIllnessRiderTerm) {
		this.acceleratedCriticalIllnessRiderTerm = acceleratedCriticalIllnessRiderTerm;
	}

	public String getAccidentCoverRiderSumAssured() {
		return accidentCoverRiderSumAssured;
	}

	public void setAccidentCoverRiderSumAssured(String accidentCoverRiderSumAssured) {
		this.accidentCoverRiderSumAssured = accidentCoverRiderSumAssured;
	}

	public String getAccidentCoverRiderTerm() {
		return accidentCoverRiderTerm;
	}

	public void setAccidentCoverRiderTerm(String accidentCoverRiderTerm) {
		this.accidentCoverRiderTerm = accidentCoverRiderTerm;
	}

	public String getAddRiderSumAssured() {
		return addRiderSumAssured;
	}

	public void setAddRiderSumAssured(String addRiderSumAssured) {
		this.addRiderSumAssured = addRiderSumAssured;
	}

	public String getAddRiderTerm() {
		return addRiderTerm;
	}

	public void setAddRiderTerm(String addRiderTerm) {
		this.addRiderTerm = addRiderTerm;
	}

	public String getFirstYearADDRiderPremiumSummary() {
		return firstYearADDRiderPremiumSummary;
	}

	public void setFirstYearADDRiderPremiumSummary(String firstYearADDRiderPremiumSummary) {
		this.firstYearADDRiderPremiumSummary = firstYearADDRiderPremiumSummary;
	}

	public String getFirstYearAcceleratedCriticalIllnessRiderPremiumSummary() {
		return firstYearAcceleratedCriticalIllnessRiderPremiumSummary;
	}

	public void setFirstYearAcceleratedCriticalIllnessRiderPremiumSummary(String firstYearAcceleratedCriticalIllnessRiderPremiumSummary) {
		this.firstYearAcceleratedCriticalIllnessRiderPremiumSummary = firstYearAcceleratedCriticalIllnessRiderPremiumSummary;
	}

	public String getFirstYearAccidentCoverRiderPremiumSummary() {
		return firstYearAccidentCoverRiderPremiumSummary;
	}

	public void setFirstYearAccidentCoverRiderPremiumSummary(String firstYearAccidentCoverRiderPremiumSummary) {
		this.firstYearAccidentCoverRiderPremiumSummary = firstYearAccidentCoverRiderPremiumSummary;
	}

	public String getFirstYearPartnerCareRiderPremiumSummary() {
		return firstYearPartnerCareRiderPremiumSummary;
	}

	public void setFirstYearPartnerCareRiderPremiumSummary(String firstYearPartnerCareRiderPremiumSummary) {
		this.firstYearPartnerCareRiderPremiumSummary = firstYearPartnerCareRiderPremiumSummary;
	}

	public String getFirstYearTermPlusRiderPremiumSummary() {
		return firstYearTermPlusRiderPremiumSummary;
	}

	public void setFirstYearTermPlusRiderPremiumSummary(String firstYearTermPlusRiderPremiumSummary) {
		this.firstYearTermPlusRiderPremiumSummary = firstYearTermPlusRiderPremiumSummary;
	}

	public String getFirstYearWOPPlusRiderPremiumSummary() {
		return firstYearWOPPlusRiderPremiumSummary;
	}

	public void setFirstYearWOPPlusRiderPremiumSummary(String firstYearWOPPlusRiderPremiumSummary) {
		this.firstYearWOPPlusRiderPremiumSummary = firstYearWOPPlusRiderPremiumSummary;
	}

	public String getPartnerCareRiderSumAssured() {
		return partnerCareRiderSumAssured;
	}

	public void setPartnerCareRiderSumAssured(String partnerCareRiderSumAssured) {
		this.partnerCareRiderSumAssured = partnerCareRiderSumAssured;
	}

	public String getPartnerCareRiderTerm() {
		return partnerCareRiderTerm;
	}

	public void setPartnerCareRiderTerm(String partnerCareRiderTerm) {
		this.partnerCareRiderTerm = partnerCareRiderTerm;
	}

	public String getRequiredModalPremium() {
		return requiredModalPremium;
	}

	public void setRequiredModalPremium(String requiredModalPremium) {
		this.requiredModalPremium = requiredModalPremium;
	}

	public String getTermPlusRiderSumAssured() {
		return termPlusRiderSumAssured;
	}

	public void setTermPlusRiderSumAssured(String termPlusRiderSumAssured) {
		this.termPlusRiderSumAssured = termPlusRiderSumAssured;
	}

	public String getTermPlusRiderTerm() {
		return termPlusRiderTerm;
	}

	public void setTermPlusRiderTerm(String termPlusRiderTerm) {
		this.termPlusRiderTerm = termPlusRiderTerm;
	}

	public String getWopPlusRiderSumAssured() {
		return wopPlusRiderSumAssured;
	}

	public void setWopPlusRiderSumAssured(String wopPlusRiderSumAssured) {
		this.wopPlusRiderSumAssured = wopPlusRiderSumAssured;
	}

	public String getWopPlusRiderTerm() {
		return wopPlusRiderTerm;
	}

	public void setWopPlusRiderTerm(String wopPlusRiderTerm) {
		this.wopPlusRiderTerm = wopPlusRiderTerm;
	}

	public String getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public double getInitialPremiumPaid() {
		return initialPremiumPaid;
	}

	public void setInitialPremiumPaid(double initialPremiumPaid) {
		this.initialPremiumPaid = initialPremiumPaid;
	}

	public double getSumAssured() {
		return sumAssured;
	}

	public void setSumAssured(double sumAssured) {
		this.sumAssured = sumAssured;
	}

	public String getDeathBenefit() {
		return deathBenefit;
	}

	public void setDeathBenefit(String deathBenefit) {
		this.deathBenefit = deathBenefit;
	}

	public String getAfyp() {
		return afyp;
	}

	public void setAfyp(String afyp) {
		this.afyp = afyp;
	}

	public double getAtp() {
		return atp;
	}

	public void setAtp(double atp) {
		this.atp = atp;
	}

	public String getCoverageTerm() {
		return coverageTerm;
	}

	public void setCoverageTerm(String coverageTerm) {
		this.coverageTerm = coverageTerm;
	}

	public double getModalPremium() {
		return modalPremium;
	}

	public void setModalPremium(double modalPremium) {
		this.modalPremium = modalPremium;
	}

	public String getPremiumPaymentTerm() {
		return premiumPaymentTerm;
	}

	public void setPremiumPaymentTerm(String premiumPaymentTerm) {
		this.premiumPaymentTerm = premiumPaymentTerm;
	}

}
