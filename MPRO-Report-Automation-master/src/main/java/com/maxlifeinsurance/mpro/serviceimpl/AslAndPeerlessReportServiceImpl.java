package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.AslAndPeerlessReportDao;
import com.maxlifeinsurance.mpro.daoimpl.AslAndPeerlessReportDaoImpl;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.service.AslAndPeerlessReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.maxlifeinsurance.mpro.utils.StringUtility;

/**
 * 
 * @author Vinay
 *
 */
public class AslAndPeerlessReportServiceImpl implements AslAndPeerlessReportService {

	
	/**
	 * @author Vinay
	 * @param Context
	 * @return flag 
	 * This method will act as service layer for ASL-Peerless-Data
	 * report which will fetch data from database create CSV, mail that
	 * CSV and Save that CSV in S3 Bucket
	 */
	@Override
	public boolean generateAslAndPeerlessReport(Context context) {
		boolean flag = false;
		List<Proposal> proposalDocumentsList = null;
		AslAndPeerlessReportDao aslAndPeerlessReportDao = null;
		List<String[]> rowData = null;
		try {
			aslAndPeerlessReportDao = new AslAndPeerlessReportDaoImpl();
			proposalDocumentsList = aslAndPeerlessReportDao.getAslPeerlessData(context);
			if (proposalDocumentsList != null && !proposalDocumentsList.isEmpty()) {
				rowData = createAslandPeerlessDataRow(proposalDocumentsList,context);
				flag = CsvWriteer.csvWriter(rowData, Constants.ASL_PEERLESS_DATA,Constants.ASL_PEERLESS_FOLDER, "report.file.headers.asl&Perrless", context);
			}
		} catch (Exception ex) {
			context.getLogger().log("Exception occued while processing Asl & PeerLess Data Report :: " + ex);
		}
		return flag;
	}
	
	
	private List<String[]> createAslandPeerlessDataRow(List<Proposal> proposalDocumentList, Context context){
		List<String[]> data = null;
		try{
			data = new ArrayList<>();
				for (Proposal proposal : proposalDocumentList) {
					String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPosvJourneyStatus()) + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getBank().getBankDetails().isEmpty() ? proposal.getBank().getBankDetails().get(0).getBankBranch() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getBank().getBankDetails().isEmpty() ? proposal.getBank().getBankDetails().get(0).getBankName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getBankDetails().size() >=2 ? proposal.getBank().getBankDetails().get(0).getMicr() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getBankDetails().size() >=2 ? proposal.getBank().getBankDetails().get(1).getBankBranch() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getBankDetails().size() >=2 ? proposal.getBank().getBankDetails().get(1).getBankName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getBankDetails().size() >=2 ? proposal.getBank().getBankDetails().get(1).getMicr() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getPaymentRenewedBy()) + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getOriginalChannel()) + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getNomineeDetails().getPartyDetails().isEmpty() ? proposal.getNomineeDetails().getPartyDetails().get(0).getFirstName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getNomineeDetails().getPartyDetails().isEmpty() ? proposal.getNomineeDetails().getPartyDetails().get(0).getLastName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getNomineeDetails().getPartyDetails().isEmpty() ? proposal.getNomineeDetails().getPartyDetails().get(0).getMiddleName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getNomineeDetails().getPartyDetails().isEmpty() ? proposal.getNomineeDetails().getPartyDetails().get(0).getRelationshipWithProposer() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getNomineeDetails().getPartyDetails().size() >= 2 ? proposal.getNomineeDetails().getPartyDetails().get(1).getFirstName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getNomineeDetails().getPartyDetails().size() >= 2 ? proposal.getNomineeDetails().getPartyDetails().get(1).getLastName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getNomineeDetails().getPartyDetails().size() >= 2 ? proposal.getNomineeDetails().getPartyDetails().get(1).getMiddleName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getNomineeDetails().getPartyDetails().size() >= 2 ? proposal.getNomineeDetails().getPartyDetails().get(1).getRelationshipWithProposer() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAnnualIncome() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getDob()+"" : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getEducation() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getFirstName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getGender() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getLastName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getMarriageDetails().getMaritalStatus() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getMiddleName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAnnualIncome() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getDob()+"" : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getEducation() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getFirstName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getGender() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getLastName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getMarriageDetails().getMaritalStatus() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getMiddleName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getRelationshipWithProposer() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getAnnualIncome() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getDob()+"" : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3? proposal.getPartyInformation().get(2).getBasicDetails().getEducation() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getFirstName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getGender() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getLastName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getMarriageDetails().getMaritalStatus() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3? proposal.getPartyInformation().get(2).getBasicDetails().getMiddleName() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getRelationshipWithProposer() : "") + StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getDemandDraftDetails().getDemandDraftNumber() : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).isSIOpted()+"" : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentChequeDetails().getChequeNumber() +"" : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPremiumMode() : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getNeedOfInsurance() : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp() : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp() + "" : "")+ StringConstants.DOLLAR
						    + StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCoverageTerm() : "")+ StringConstants.DOLLAR
						    + StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getEffectiveDate() + "" : "")+ StringConstants.DOLLAR
						    + StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getModalPremium() + "" : "")+ StringConstants.DOLLAR
						    + StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getServiceTax() : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getSumAssured() + "" : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductType() : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId())+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentName())+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getGoCABrokerCode())+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonCode())+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().toString())+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getClientId() : "")+ StringConstants.DOLLAR
							+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getDedupeFlag() : "");
					data.add(row.split(Pattern.quote(StringConstants.DOLLAR)));
				}		
		}
		catch(Exception ex){
			context.getLogger().log("Exception occured while creating rows of CSV file :: " + ex.getMessage());
		}
		return data;
	}
	
	
	
//	private double aslandPeerlessCsvWriter(List<Proposal> proposalDocumentList, Context context){
//		double sizeInMb = 0;
//		FileWriter outputfile = null;
//		CSVWriter writer = null;
//		try{
//			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
//			LocalDateTime toDate = LocalDateTime.now();
//			file = new File(
//					"/tmp" + File.separator + "Asl & Peerless Data Report-" + (dateTimeFormatter.format(toDate)) + ".csv");
//			context.getLogger().log("File Path" + file.getAbsolutePath());
//			outputfile = new FileWriter(file);
//			writer = new CSVWriter(outputfile);
//			List<String[]> data = new ArrayList<>();
//			data.add(res.getString("report.file.headers.asl&Perrless").split(Pattern.quote("||")));
//			for (Proposal proposal : proposalDocumentList) {
//				String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPosvJourneyStatus()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getBank().getBankDetails().isEmpty() ? proposal.getBank().getBankDetails().get(0).getBankBranch() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getBank().getBankDetails().isEmpty() ? proposal.getBank().getBankDetails().get(0).getBankName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getBankDetails().size() >=2 ? proposal.getBank().getBankDetails().get(0).getMicr() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getBankDetails().size() >=2 ? proposal.getBank().getBankDetails().get(1).getBankBranch() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getBankDetails().size() >=2 ? proposal.getBank().getBankDetails().get(1).getBankName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getBankDetails().size() >=2 ? proposal.getBank().getBankDetails().get(1).getMicr() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getPaymentRenewedBy()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getOriginalChannel()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getNomineeDetails().getPartyDetails().isEmpty() ? proposal.getNomineeDetails().getPartyDetails().get(0).getFirstName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getNomineeDetails().getPartyDetails().isEmpty() ? proposal.getNomineeDetails().getPartyDetails().get(0).getLastName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getNomineeDetails().getPartyDetails().isEmpty() ? proposal.getNomineeDetails().getPartyDetails().get(0).getMiddleName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getNomineeDetails().getPartyDetails().isEmpty() ? proposal.getNomineeDetails().getPartyDetails().get(0).getRelationshipWithProposer() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getNomineeDetails().getPartyDetails().size() >= 2 ? proposal.getNomineeDetails().getPartyDetails().get(1).getFirstName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getNomineeDetails().getPartyDetails().size() >= 2 ? proposal.getNomineeDetails().getPartyDetails().get(1).getLastName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getNomineeDetails().getPartyDetails().size() >= 2 ? proposal.getNomineeDetails().getPartyDetails().get(1).getMiddleName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getNomineeDetails().getPartyDetails().size() >= 2 ? proposal.getNomineeDetails().getPartyDetails().get(1).getRelationshipWithProposer() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAnnualIncome() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getDob()+"" : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getEducation() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getFirstName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getGender() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getLastName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getMarriageDetails().getMaritalStatus() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getMiddleName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAnnualIncome() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getDob()+"" : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getEducation() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getFirstName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getGender() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getLastName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getMarriageDetails().getMaritalStatus() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getMiddleName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getBasicDetails().getRelationshipWithProposer() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getAnnualIncome() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getDob()+"" : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3? proposal.getPartyInformation().get(2).getBasicDetails().getEducation() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getFirstName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getGender() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getLastName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getMarriageDetails().getMaritalStatus() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3? proposal.getPartyInformation().get(2).getBasicDetails().getMiddleName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getBasicDetails().getRelationshipWithProposer() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getDemandDraftDetails().getDemandDraftNumber() : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).isSIOpted()+"" : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentChequeDetails().getChequeNumber() +"" : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPremiumMode() : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getNeedOfInsurance() : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp() : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp() + "" : "")+ StringConstants.DOLLAR
//					    + StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCoverageTerm() : "")+ StringConstants.DOLLAR
//					    + StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getEffectiveDate() + "" : "")+ StringConstants.DOLLAR
//					    + StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getModalPremium() + "" : "")+ StringConstants.DOLLAR
//					    + StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getServiceTax() : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getSumAssured() + "" : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductType() : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId())+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentName())+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getGoCABrokerCode())+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonCode())+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().toString())+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getClientId() : "")+ StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getDedupeFlag() : "");
//				data.add(row.split(Pattern.quote(StringConstants.DOLLAR)));
//
//			}
//			writer.writeAll(data);
//			context.getLogger().log("CSV Created Successfully");
//			zipFile = ReportCreationUtility.passwordProtectedZip(file, context);
//			if (zipFile != null) {
//				context.getLogger().log("Converting csv file to password protected zip file :: " + zipFile);
//				xmlFileByteArray = ReportCreationUtility.convertExcelIntoByteArray(zipFile.getFile(), context);
//			}
//			fileData = Base64.encode(xmlFileByteArray);
//			long sizeInByte = fileData.length();
//			sizeInMb = sizeInByte / (1024.00 * 1024.00);
//			context.getLogger().log("Size of file is :: " + sizeInMb);
//
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//			context.getLogger().log("Exception occured while creating report for Asl & Peerless Data Report :: " + ex);
//		} finally {
//			try {
//				if (outputfile != null) {
//					outputfile.close();
//				}
//				if (writer != null) {
//					writer.close();
//				}
//			} catch (Exception ex) {
//				context.getLogger().log("Exception occured while closing object :: " + ex.getMessage());
//			}
//		}
//		return sizeInMb;
//	}

}
