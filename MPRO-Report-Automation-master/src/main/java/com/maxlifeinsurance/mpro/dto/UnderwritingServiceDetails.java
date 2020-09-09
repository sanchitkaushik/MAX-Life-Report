package com.maxlifeinsurance.mpro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Qualtech
 *
 */
public class UnderwritingServiceDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private CibilDetails cibilDetails;
	private List<DedupeDetails> dedupeDetails;
	private MsaFsaDetails msaFsaDetails;
	private FinancialGridDetails financialGridDetails;
	private MedicalGridDetails medicalGridDetails;
	private MiscellaneousRuleStatus miscellaneousRuleStatus;
	private ProposalFormRuleDetails proposalFormRuleDetails;
	private RiskScoreDetails riskScoreDetails;
	private UnderwritingStatus underwritingStatus;
	private UrmuRuleStatus urmuRuleStatus;

	public UrmuRuleStatus getUrmuRuleStatus() {
		if (urmuRuleStatus != null) {
			return urmuRuleStatus;
		} else {
			return new UrmuRuleStatus();
		}
	}

	public void setUrmuRuleStatus(UrmuRuleStatus urmuRuleStatus) {
		this.urmuRuleStatus = urmuRuleStatus;
	}

	public UnderwritingStatus getUnderwritingStatus() {
		if (underwritingStatus != null) {
			return underwritingStatus;
		} else {
			return new UnderwritingStatus();
		}
	}

	public void setUnderwritingStatus(UnderwritingStatus underwritingStatus) {
		this.underwritingStatus = underwritingStatus;
	}

	public RiskScoreDetails getRiskScoreDetails() {
		if (riskScoreDetails != null) {
			return riskScoreDetails;
		} else {
			return new RiskScoreDetails();
		}
	}

	public void setRiskScoreDetails(RiskScoreDetails riskScoreDetails) {
		this.riskScoreDetails = riskScoreDetails;
	}

	public ProposalFormRuleDetails getProposalFormRuleDetails() {
		if (proposalFormRuleDetails != null) {
			return proposalFormRuleDetails;
		} else {
			return new ProposalFormRuleDetails();
		}
	}

	public void setProposalFormRuleDetails(ProposalFormRuleDetails proposalFormRuleDetails) {
		this.proposalFormRuleDetails = proposalFormRuleDetails;
	}

	public MiscellaneousRuleStatus getMiscellaneousRuleStatus() {
		if (miscellaneousRuleStatus != null) {
			return miscellaneousRuleStatus;
		} else {
			return new MiscellaneousRuleStatus();
		}
	}

	public void setMiscellaneousRuleStatus(MiscellaneousRuleStatus miscellaneousRuleStatus) {
		this.miscellaneousRuleStatus = miscellaneousRuleStatus;
	}

	public MedicalGridDetails getMedicalGridDetails() {
		if (medicalGridDetails != null) {
			return medicalGridDetails;
		} else {
			return new MedicalGridDetails();
		}
	}

	public void setMedicalGridDetails(MedicalGridDetails medicalGridDetails) {
		this.medicalGridDetails = medicalGridDetails;
	}

	public FinancialGridDetails getFinancialGridDetails() {
		if (financialGridDetails != null) {
			return financialGridDetails;
		} else {
			return new FinancialGridDetails();
		}

	}

	public void setFinancialGridDetails(FinancialGridDetails financialGridDetails) {
		this.financialGridDetails = financialGridDetails;
	}

	public MsaFsaDetails getMsaFsaDetails() {
		if (msaFsaDetails != null) {
			return msaFsaDetails;
		} else {
			return new MsaFsaDetails();
		}

	}

	public void setMsaFsaDetails(MsaFsaDetails msaFsaDetails) {
		this.msaFsaDetails = msaFsaDetails;
	}

	public List<DedupeDetails> getDedupeDetails() {
		if (dedupeDetails != null) {
			return dedupeDetails;
		} else {
			return new ArrayList<DedupeDetails>();
		}

	}

	public void setDedupeDetails(List<DedupeDetails> dedupeDetails) {
		this.dedupeDetails = dedupeDetails;
	}

	public CibilDetails getCibilDetails() {
		if (cibilDetails != null) {
			return cibilDetails;
		} else {
			return new CibilDetails();
		}

	}

	public void setCibilDetails(CibilDetails cibilDetails) {
		this.cibilDetails = cibilDetails;
	}

}
