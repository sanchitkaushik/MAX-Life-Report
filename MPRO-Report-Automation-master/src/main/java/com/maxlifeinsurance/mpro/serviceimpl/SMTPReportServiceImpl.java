package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.SMTPReportDao;
import com.maxlifeinsurance.mpro.daoimpl.SMTPReportDaoImpl;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.service.SMTPReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.maxlifeinsurance.mpro.utils.StringUtility;

/**
 * 
 * @author Vinay
 * Service layer for SMTP Report
 */
public class SMTPReportServiceImpl implements SMTPReportService{

	/**
	 * @author Vinay
	 * @param Context
	 * @return flag 
	 * This method will act as service layer for SMTP-Data
	 * report which will fetch data from database create CSV, mail that
	 * CSV and Save that CSV in S3 Bucket
	 */
	@Override
	public boolean generateSMTPReport(Context context) {
		boolean flag = false;
		List<Proposal> proposalDocumentsList = null;
		SMTPReportDao smtpReportDao = null;
		List<String[]> rowData = null;
		try {
			smtpReportDao = new SMTPReportDaoImpl();
			proposalDocumentsList = smtpReportDao.getSMTPReportData(context);
			if (proposalDocumentsList != null && !proposalDocumentsList.isEmpty()) {
				rowData = createSMTPRowData(proposalDocumentsList,context);
				flag = CsvWriteer.csvWriter(rowData, Constants.SMTP, Constants.SMTP_FOLDER, "report.file.headers.smtp", context);
			}
		} catch (Exception ex) {
			context.getLogger().log("Exception occued while processing SMTP-Data-Report :: " + ex);
		}
		return flag;
	}
	
	
	private List<String[]> createSMTPRowData(List<Proposal> proposalDocumentList, Context context){
		List<String[]> data = null;
		try{
			data = new ArrayList<>();
			for(Proposal proposal : proposalDocumentList){
				String row = StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getFormType()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCustomerClassification()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getAffiliationsToPoliticalparty()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getConvictionDetails())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getForeignOfficeDetails())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getIncomeSources())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().isFamilyMemberPEP()+ "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().isLIOrNomineePEP() +"")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().isLIPEP() + "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().isPayorPep() + "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().isProposerPEP() + "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getPartyInPower())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getPepConvicted())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getPepEverPostedInForeignOffice())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getPoliticalExperience())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getPortfolioHandled())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getRoleInPoliticalParty())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getRoleOthers())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getSpecifyFamilyMembers())+ StringConstants.COMMA
						
//						AddressDetails
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()  ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()  ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()  ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()  ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAnnualIncome() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getDob()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getEducation() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getOccupation(): "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().getCreditScore() : "") + StringConstants.COMMA
						
						
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ?(proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAnnualIncome() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getDob()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getEducation() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getOccupation(): "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getPersonalIdentification().getPanDetails().getCreditScore() : "") + StringConstants.COMMA
						
						
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ?(proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getAnnualIncome() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getDob()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getEducation() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getOccupation(): "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getPersonalIdentification().getPanDetails().getCreditScore() : "") + StringConstants.COMMA
						
						
		
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPosvDetails().getPosvStatus().getOverallHealthStatus())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCode() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPremiumType() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp()+"" : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCoverageTerm() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getDeathBenefit() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getSumAssured()+"" : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderInfo() : "" ) : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderInfo() : "" ) : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 3 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderInfo() : "" ) : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 4 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderInfo() : "" ) : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 5 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderInfo() : "" ) : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 6 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderInfo() : "" ) : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 7 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderInfo() : "" ) : "") + StringConstants.COMMA
																				
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getGoCABrokerCode())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getAffulentOrNot())+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScore() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScoreDate() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScoreName() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >=2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScore() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >=2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScoreDate() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >=2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScoreName() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getDedupeFlag() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfFSA() + "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfMSA() + "");
				data.add(row.split(Pattern.quote(StringConstants.COMMA)));

			}
			
		}
		catch(Exception ex){
			context.getLogger().log("Exception occured while creating rows of CSV file :: " + ex.getMessage());
		}
		return data;
	}
	
	
	
//	private double smtpCsvWriter(List<Proposal> proposalDocumentList, Context context){
//
//		double sizeInMb = 0;
//		FileWriter outputfile = null;
//		CSVWriter writer = null;
//		try {
//			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
//			LocalDateTime toDate = LocalDateTime.now();
//			file = new File("/tmp" + File.separator + "SMTP-Data Report" + (dateTimeFormatter.format(toDate)) + ".csv");
////			file = new File("E://"+File.separator+"SMTP.csv");
//			context.getLogger().log("File Path" + file.getAbsolutePath());
//			outputfile = new FileWriter(file);
//			writer = new CSVWriter(outputfile);
//			List<String[]> data = new ArrayList<>();
//			data.add(res.getString("report.file.headers.smtp").split(Pattern.quote("||")));
//			for (Proposal proposal : proposalDocumentList) {
//				String row = StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getFormType()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCustomerClassification()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getAffiliationsToPoliticalparty()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getConvictionDetails())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getForeignOfficeDetails())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getIncomeSources())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().isFamilyMemberPEP()+ "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().isLIOrNomineePEP() +"")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().isLIPEP() + "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().isPayorPep() + "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().isProposerPEP() + "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getPartyInPower())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getPepConvicted())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getPepEverPostedInForeignOffice())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getPoliticalExperience())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getPortfolioHandled())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getRoleInPoliticalParty())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getRoleOthers())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getEmploymentDetails().getPepDetails().getSpecifyFamilyMembers())+ StringConstants.COMMA
//						
////						AddressDetails
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()  ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()  ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()  ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty()  ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAnnualIncome() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getDob()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getEducation() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getOccupation(): "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().getCreditScore() : "") + StringConstants.COMMA
//						
//						
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ?(proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAnnualIncome() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getDob()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getEducation() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getOccupation(): "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getPersonalIdentification().getPanDetails().getCreditScore() : "") + StringConstants.COMMA
//						
//						
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ?(proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getCity() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getPinCode() : "") : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getAnnualIncome() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getDob()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getEducation() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getNationalityDetails().getNationality() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getOccupation(): "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getPersonalIdentification().getPanDetails().getCreditScore() : "") + StringConstants.COMMA
//						
//						
//		
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPosvDetails().getPosvStatus().getOverallHealthStatus())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCode() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPremiumType() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp()+"" : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCoverageTerm() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getDeathBenefit() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getSumAssured()+"" : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (!proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(0).getRiderInfo() : "" ) : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 2 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(1).getRiderInfo() : "" ) : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 3 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(2).getRiderInfo() : "" ) : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 4 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(3).getRiderInfo() : "" ) : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 5 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(4).getRiderInfo() : "" ) : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 6 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(5).getRiderInfo() : "" ) : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? (proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().size() >= 7 ? proposal.getProductDetails().get(0).getProductInfo().getRiderDetails().get(6).getRiderInfo() : "" ) : "") + StringConstants.COMMA
//																				
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getGoCABrokerCode())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getAffulentOrNot())+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScore() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScoreDate() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScoreName() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >=2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScore() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >=2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScoreDate() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >=2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScoreName() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getDedupeFlag() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfFSA() + "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfMSA() + "");
//				data.add(row.split(Pattern.quote(",")));
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
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			context.getLogger().log("Exception occured while creating report for SMTP-Data Data :: " + ex);
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
//	
//	}

}
