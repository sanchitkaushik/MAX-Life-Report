package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.AxisMproYtdReportDao;
import com.maxlifeinsurance.mpro.daoimpl.AxisMproYtdReportDaoImpl;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.service.AxisMproYtdReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.maxlifeinsurance.mpro.utils.StringUtility;

/**
 * 
 * @author Vinay Service layer for Axis-Mpro-Ytd Report
 */
public class AxisMproYtdReportServiceImpl implements AxisMproYtdReportService {

	/**
	 * @author Vinay
	 * @param Context
	 * @return flag This method will act as service layer for Axis-Mpro-YTD-Data
	 *         report which will fetch data from database create CSV, mail that
	 *         CSV and Save that CSV in S3 Bucket
	 */
	@Override
	public boolean generateAxisMproYtdReport(Context context) {
		boolean flag = false;
		List<Proposal> proposalDocumentsList = null;
		AxisMproYtdReportDao axisMproYtdReportDao = null;
		List<String[]> rowData = null;
		try {
			axisMproYtdReportDao = new AxisMproYtdReportDaoImpl();
			proposalDocumentsList = axisMproYtdReportDao.getAxisYtdReportData(context);
			if (proposalDocumentsList != null && !proposalDocumentsList.isEmpty()) {
				rowData = createAxisMproYtdRow(proposalDocumentsList, context);
				flag = CsvWriteer.csvWriter(rowData, Constants.AXIS_MPRO_REPORT_YTD, Constants.AXIS_MPRO_REPORT_YTD, "report.file.headers.axisMproYtd", context);
			}
		} catch (Exception ex) {
			context.getLogger().log("Exception occued while processing Axis-Mpro-YTD-Report :: " + ex);
		}
		return flag;
	}

	private List<String[]> createAxisMproYtdRow(List<Proposal> proposalDocumentList, Context context) {
		List<String[]> data = null;
		try {
			data = new ArrayList<>();
			for (Proposal proposal : proposalDocumentList) {
				String row = StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage())
						+ StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCustomerClassification()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getPaymentRenewedBy()) + StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getBranchCode())
						+ StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAnnualIncome() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getDob() + "" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getFirstName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getGender() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getLastName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getOccupation() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAnnualIncome() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getDob() + "" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getFirstName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getGender() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getLastName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getOccupation() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getAnnualIncome() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getDob() + "" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getFirstName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getGender() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getLastName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getOccupation() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getModeOfPayment() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPremiumMode() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getModeOfPayment() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCode() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodeMFSA() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodePOSV() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodeTPP() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCABRiderPremium() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCABRiderSumAssured() : "")
						+ StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCABRiderTerm() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(
								proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAcceleratedCriticalIllnessRiderSumAssured() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAcceleratedCriticalIllnessRiderTerm() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAccidentCoverRiderSumAssured() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAccidentCoverRiderTerm() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAddRiderSumAssured() : "")
						+ StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAddRiderTerm() : "")
						+ StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp() : "")
						+ StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp() + "" : "")
						+ StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCoverageTerm() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getEffectiveDate() + "" : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearADDRiderPremiumSummary() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(
								proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearAcceleratedCriticalIllnessRiderPremiumSummary() : "")
						+ StringConstants.COMMA
						+ StringUtility
								.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearAccidentCoverRiderPremiumSummary() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(
								proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearPartnerCareRiderPremiumSummary() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearTermPlusRiderPremiumSummary() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearWOPPlusRiderPremiumSummary() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getModalPremium() + "" : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getPartnerCareRiderSumAssured() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getPartnerCareRiderTerm() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getPremiumPaymentTerm() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getRequiredModalPremium() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getSumAssured() + "" : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getTermPlusRiderSumAssured() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getTermPlusRiderTerm() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getWopPlusRiderSumAssured() : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getWopPlusRiderTerm() : "")
						+ StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getAmount() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).isRiderRequired() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderInfo() : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderModalPremium() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderPlanCode() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderSumAssured() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderTerm() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getTypeOfValue() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getAmount() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).isRiderRequired() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderInfo() : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderModalPremium() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderPlanCode() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderSumAssured() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderTerm() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getTypeOfValue() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 3 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getAmount() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 3 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).isRiderRequired() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 3 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderInfo() : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 3 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderModalPremium() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 3 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderPlanCode() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 3 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderSumAssured() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 3 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderTerm() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 3 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getTypeOfValue() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 4 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getAmount() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 4 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).isRiderRequired() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 4 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderInfo() : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 4 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderModalPremium() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 4 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderPlanCode() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 4 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderSumAssured() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 4 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderTerm() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 4 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getTypeOfValue() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 5 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getAmount() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 5 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).isRiderRequired() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 5 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderInfo() : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 5 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderModalPremium() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 5 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderPlanCode() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 5 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderSumAssured() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 5 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderTerm() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 5 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getTypeOfValue() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 6 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getAmount() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 6 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).isRiderRequired() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 6 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderInfo() : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 6 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderModalPremium() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 6 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderPlanCode() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 6 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderSumAssured() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 6 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderTerm() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 6 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getTypeOfValue() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 7 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getAmount() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 7 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).isRiderRequired() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 7 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderInfo() : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 7 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderModalPremium() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 7 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderPlanCode() : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 7 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderSumAssured() + "" : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 7 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderTerm() : "") : "")
						+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
								? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 7 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getTypeOfValue() : "") : "")
						+ StringConstants.COMMA + StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductType() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().toString());

				data.add(row.split(Pattern.quote(StringConstants.COMMA)));

			}

		} catch (Exception ex) {
			context.getLogger().log("Exception occured while creating rows of CSV file :: " + ex.getMessage());
		}
		return data;
	}

	/**
	 * 
	 * @param listAxisMproReport
	 * @param context
	 * @return fileSize This is private method which will create CSV file in
	 *         respective template for axis-mpro report and than convert the CSV
	 *         in password protected zip file
	 */
	// private double axisMproYtdCsvWriter(List<Proposal> proposalDocumentsList,
	// Context context){
	// double sizeInMb = 0;
	// FileWriter outputfile = null;
	// CSVWriter writer = null;
	// try{
	// DateTimeFormatter dateTimeFormatter =
	// DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
	// LocalDateTime toDate = LocalDateTime.now();
	// file = new File(
	// "/tmp" + File.separator + "Axis-Mpro-YTD Report_" +
	// (dateTimeFormatter.format(toDate)) + ".csv");
	// context.getLogger().log("File Path" + file.getAbsolutePath());
	// outputfile = new FileWriter(file);
	// writer = new CSVWriter(outputfile);
	// List<String[]> data = new ArrayList<>();
	// data.add(res.getString("report.file.headers.axisMproYtd").split(Pattern.quote("||")));
	// for (Proposal proposal : proposalDocumentsList) {
	// String row =
	// StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime()))
	// + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber())
	// + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage())
	// + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCustomerClassification())
	// + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getPaymentRenewedBy())
	// + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getBranchCode())
	// + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()
	// ?
	// proposal.getPartyInformation().get(0).getBasicDetails().getAnnualIncome()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()
	// ? proposal.getPartyInformation().get(0).getBasicDetails().getDob() + "" :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()
	// ? proposal.getPartyInformation().get(0).getBasicDetails().getFirstName()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()
	// ? proposal.getPartyInformation().get(0).getBasicDetails().getGender() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()
	// ? proposal.getPartyInformation().get(0).getBasicDetails().getLastName() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()
	// ?
	// proposal.getPartyInformation().get(0).getBasicDetails().getNationalityDetails().getNationality()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()
	// ? proposal.getPartyInformation().get(0).getBasicDetails().getOccupation()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 2 ?
	// proposal.getPartyInformation().get(1).getBasicDetails().getAnnualIncome()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getDob() +
	// "" : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 2 ?
	// proposal.getPartyInformation().get(1).getBasicDetails().getFirstName() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 2 ?
	// proposal.getPartyInformation().get(1).getBasicDetails().getGender() : "")
	// + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 2 ?
	// proposal.getPartyInformation().get(1).getBasicDetails().getLastName() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 2 ?
	// proposal.getPartyInformation().get(1).getBasicDetails().getNationalityDetails().getNationality()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 2 ?
	// proposal.getPartyInformation().get(1).getBasicDetails().getOccupation() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 3 ?
	// proposal.getPartyInformation().get(2).getBasicDetails().getAnnualIncome()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getDob() +
	// "" : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 3 ?
	// proposal.getPartyInformation().get(2).getBasicDetails().getFirstName() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 3 ?
	// proposal.getPartyInformation().get(2).getBasicDetails().getGender() : "")
	// + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 3 ?
	// proposal.getPartyInformation().get(2).getBasicDetails().getLastName() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 3 ?
	// proposal.getPartyInformation().get(2).getBasicDetails().getNationalityDetails().getNationality()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size()
	// >= 3 ?
	// proposal.getPartyInformation().get(2).getBasicDetails().getOccupation() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty()
	// ? proposal.getPaymentDetails().getReceipt().get(0).getModeOfPayment() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty()
	// ? proposal.getPaymentDetails().getReceipt().get(0).getPremiumMode() : "")
	// + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ? proposal.getProductDetails().get(0).getProductInfo().getModeOfPayment()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ? proposal.getProductDetails().get(0).getProductInfo().getPlanCode() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodeMFSA()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodePOSV()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodeTPP() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCABRiderPremium()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCABRiderSumAssured()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCABRiderTerm()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAcceleratedCriticalIllnessRiderSumAssured()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAcceleratedCriticalIllnessRiderTerm()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAccidentCoverRiderSumAssured()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAccidentCoverRiderTerm()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAddRiderSumAssured()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAddRiderTerm()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp()
	// +"" : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCoverageTerm()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getEffectiveDate()
	// + "" : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearADDRiderPremiumSummary()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearAcceleratedCriticalIllnessRiderPremiumSummary()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearAccidentCoverRiderPremiumSummary()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearPartnerCareRiderPremiumSummary()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearTermPlusRiderPremiumSummary()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getFirstYearWOPPlusRiderPremiumSummary()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getModalPremium()
	// + "" : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getPartnerCareRiderSumAssured()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getPartnerCareRiderTerm()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getPremiumPaymentTerm()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getRequiredModalPremium()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getSumAssured()
	// +"" : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getTermPlusRiderSumAssured()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getTermPlusRiderTerm()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getWopPlusRiderSumAssured()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getWopPlusRiderTerm()
	// : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().isEmpty()
	// ? proposal.getProductDetails().get(0).getProductInfo().getProductName() :
	// "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getAmount()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).isRiderRequired()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderInfo()
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderModalPremium()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderPlanCode()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderSumAssured()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderTerm()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty()
	// ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getTypeOfValue()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 2 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getAmount()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 2 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).isRiderRequired()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 2 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderInfo()
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 2 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderModalPremium()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 2 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderPlanCode()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 2 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderSumAssured()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 2 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderTerm()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 2 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getTypeOfValue()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 3 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getAmount()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 3 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).isRiderRequired()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 3 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderInfo()
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 3 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderModalPremium()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 3 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderPlanCode()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 3 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderSumAssured()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 3 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderTerm()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 3 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getTypeOfValue()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 4 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getAmount()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 4 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).isRiderRequired()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 4 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderInfo()
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 4 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderModalPremium()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 4 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderPlanCode()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 4 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderSumAssured()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 4 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderTerm()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 4 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getTypeOfValue()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 5 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getAmount()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 5 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).isRiderRequired()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 5 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderInfo()
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 5 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderModalPremium()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 5 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderPlanCode()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 5 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderSumAssured()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 5 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderTerm()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 5 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getTypeOfValue()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 6 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getAmount()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 6 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).isRiderRequired()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 6 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderInfo()
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 6 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderModalPremium()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 6 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderPlanCode()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 6 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderSumAssured()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 6 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderTerm()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 6 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getTypeOfValue()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 7 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getAmount()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 7 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).isRiderRequired()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 7 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderInfo()
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 7 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderModalPremium()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 7 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderPlanCode()
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 7 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderSumAssured()+""
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 7 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderTerm()
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ?
	// (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size()
	// >= 7 ?
	// proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getTypeOfValue()
	// : "") : "") + StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty()
	// ? proposal.getProductDetails().get(0).getProductType() : "") +
	// StringConstants.COMMA
	// +
	// StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().toString());
	//
	// data.add(row.split(Pattern.quote(",")));
	//
	// }
	// writer.writeAll(data);
	// context.getLogger().log("CSV Created Successfully");
	// zipFile = ReportCreationUtility.passwordProtectedZip(file, context);
	// if (zipFile != null) {
	// context.getLogger().log("Converting csv file to password protected zip
	// file :: " + zipFile);
	// xmlFileByteArray =
	// ReportCreationUtility.convertExcelIntoByteArray(zipFile.getFile(),
	// context);
	// }
	// fileData = Base64.encode(xmlFileByteArray);
	// long sizeInByte = fileData.length();
	// sizeInMb = sizeInByte / (1024.00 * 1024.00);
	// context.getLogger().log("Size of file is :: " + sizeInMb);
	//
	// }
	// catch (Exception ex) {
	// ex.printStackTrace();
	// context.getLogger().log("Exception occured while creating report for
	// MPRO-AXIS Report :: " + ex);
	// } finally {
	// try {
	// if (outputfile != null) {
	// outputfile.close();
	// }
	// if (writer != null) {
	// writer.close();
	// }
	// } catch (Exception ex) {
	// context.getLogger().log("Exception occured while closing object :: " +
	// ex.getMessage());
	// }
	// }
	// return sizeInMb;
	// }

}
