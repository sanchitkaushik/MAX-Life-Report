package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;

/**
 * 
 * @author Qualtech
 *
 */
public class PepDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String affiliationsToPoliticalparty;
	private String convictionDetails;
	private String foreignOfficeDetails;
	private String incomeSources;
	private boolean isFamilyMemberPEP;
	private boolean isLIOrNomineePEP;
	private boolean isLIPEP;
	private boolean isPayorPep;
	private boolean isProposerPEP;
	private String partyInPower;
	private String pepConvicted;
	private String pepEverPostedInForeignOffice;
	private String politicalExperience;
	private String portfolioHandled;
	private String roleInPoliticalParty;
	private String roleOthers;
	private String specifyFamilyMembers;

	public String getAffiliationsToPoliticalparty() {
		return affiliationsToPoliticalparty;
	}

	public void setAffiliationsToPoliticalparty(String affiliationsToPoliticalparty) {
		this.affiliationsToPoliticalparty = affiliationsToPoliticalparty;
	}

	public String getConvictionDetails() {
		return convictionDetails;
	}

	public void setConvictionDetails(String convictionDetails) {
		this.convictionDetails = convictionDetails;
	}

	public String getForeignOfficeDetails() {
		return foreignOfficeDetails;
	}

	public void setForeignOfficeDetails(String foreignOfficeDetails) {
		this.foreignOfficeDetails = foreignOfficeDetails;
	}

	public String getIncomeSources() {
		return incomeSources;
	}

	public void setIncomeSources(String incomeSources) {
		this.incomeSources = incomeSources;
	}

	public boolean isFamilyMemberPEP() {
		return isFamilyMemberPEP;
	}

	public void setFamilyMemberPEP(boolean isFamilyMemberPEP) {
		this.isFamilyMemberPEP = isFamilyMemberPEP;
	}

	public boolean isLIOrNomineePEP() {
		return isLIOrNomineePEP;
	}

	public void setLIOrNomineePEP(boolean isLIOrNomineePEP) {
		this.isLIOrNomineePEP = isLIOrNomineePEP;
	}

	public boolean isLIPEP() {
		return isLIPEP;
	}

	public void setLIPEP(boolean isLIPEP) {
		this.isLIPEP = isLIPEP;
	}

	public boolean isPayorPep() {
		return isPayorPep;
	}

	public void setPayorPep(boolean isPayorPep) {
		this.isPayorPep = isPayorPep;
	}

	public boolean isProposerPEP() {
		return isProposerPEP;
	}

	public void setProposerPEP(boolean isProposerPEP) {
		this.isProposerPEP = isProposerPEP;
	}

	public String getPartyInPower() {
		return partyInPower;
	}

	public void setPartyInPower(String partyInPower) {
		this.partyInPower = partyInPower;
	}

	public String getPepConvicted() {
		return pepConvicted;
	}

	public void setPepConvicted(String pepConvicted) {
		this.pepConvicted = pepConvicted;
	}

	public String getPepEverPostedInForeignOffice() {
		return pepEverPostedInForeignOffice;
	}

	public void setPepEverPostedInForeignOffice(String pepEverPostedInForeignOffice) {
		this.pepEverPostedInForeignOffice = pepEverPostedInForeignOffice;
	}

	public String getPoliticalExperience() {
		return politicalExperience;
	}

	public void setPoliticalExperience(String politicalExperience) {
		this.politicalExperience = politicalExperience;
	}

	public String getPortfolioHandled() {
		return portfolioHandled;
	}

	public void setPortfolioHandled(String portfolioHandled) {
		this.portfolioHandled = portfolioHandled;
	}

	public String getRoleInPoliticalParty() {
		return roleInPoliticalParty;
	}

	public void setRoleInPoliticalParty(String roleInPoliticalParty) {
		this.roleInPoliticalParty = roleInPoliticalParty;
	}

	public String getRoleOthers() {
		return roleOthers;
	}

	public void setRoleOthers(String roleOthers) {
		this.roleOthers = roleOthers;
	}

	public String getSpecifyFamilyMembers() {
		return specifyFamilyMembers;
	}

	public void setSpecifyFamilyMembers(String specifyFamilyMembers) {
		this.specifyFamilyMembers = specifyFamilyMembers;
	}

}
