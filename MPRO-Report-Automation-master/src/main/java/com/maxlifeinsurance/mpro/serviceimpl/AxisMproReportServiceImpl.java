package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.AxisMproReportDao;
import com.maxlifeinsurance.mpro.daoimpl.AxisMproReportDaoImpl;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.service.AxisMproReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.maxlifeinsurance.mpro.utils.StringUtility;

/**
 * 
 * @author Vinay
 * This class is service layer for Axis-Mpro-Report
 */
public class AxisMproReportServiceImpl implements AxisMproReportService {

	/**
	 * @author Vinay
	 * @param Context
	 * @return flag 
	 * This method will act as service layer for Axis-Mpro-Data
	 * report which will fetch data from database create CSV, mail that
	 * CSV and Save that CSV in S3 Bucket
	 */
	@Override
	public boolean axisMproReportGenerationService(Context context) {
		boolean flag = false;
		List<Proposal> proposalDocumentsList = null;
		AxisMproReportDao axisMproReportDao = null;
		List<String[]> rowData = null;
		try {
			axisMproReportDao = new AxisMproReportDaoImpl();
			proposalDocumentsList = axisMproReportDao.getAxisMproReportData(context);
			if (proposalDocumentsList != null && !proposalDocumentsList.isEmpty()) {
				rowData = createAxisMproRow(proposalDocumentsList,context);
				flag = CsvWriteer.csvWriter(rowData, Constants.AXIS_MPRO_REPORT, Constants.AXIS_MPRO_FOLDER, "report.file.headers.axisMpro", context);
			}
		} catch (Exception ex) {
			context.getLogger().log("Exception occued while processing Axis-Mpro-Report :: " + ex);
		}
		return flag;
	}
	
	
	
	
	private List<String[]> createAxisMproRow(List<Proposal> proposalDocumentList, Context context){
		List<String[]> data = null;
		try{
			data = new ArrayList<>();
			for(Proposal proposal : proposalDocumentList){
				String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getAdditionalFlags().isPaymentDone()+"") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPosvJourneyStatus()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCustomerId()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getLeadId()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getPaymentRenewedBy()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getBranchCode()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getFirstName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getLastName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getFirstName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getLastName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getFirstName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getLastName() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPremiumMode() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCode() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodeTPP() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp() + "" : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCoverageTerm() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getModalPremium() + "" : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getPremiumPaymentTerm() : "")+ StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "")+ StringConstants.COMMA
					    + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId())+ StringConstants.COMMA
					    + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonCode())+ StringConstants.COMMA
					    + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpSSNCode())+ StringConstants.COMMA
					    + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getTransactionId()+"");
				data.add(row.split(Pattern.quote(StringConstants.COMMA)));
			}
			
		}
		catch(Exception ex){
			context.getLogger().log("Exception occured while creating rows of CSV file :: " + ex.getMessage());
		}
		return data;
	}
	
	
	
	/**
	 * 
	 * @param listAxisMproReport
	 * @param context
	 * @return fileSize 
	 * This is private method which will create CSV file in respective
	 * template for axis-mpro report and than convert the CSV in
	 * password protected zip file
	 */
//	private double axisMproReportCsvWriter(List<Proposal> proposalDocumentsList, Context context){
//		double sizeInMb = 0;
//		FileWriter outputfile = null;
//		CSVWriter writer = null;
//		try{
//			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
//			LocalDateTime toDate = LocalDateTime.now();
//			file = new File(
//					"/tmp" + File.separator + "Axis-Mpro-YTD Report_" + (dateTimeFormatter.format(toDate)) + ".csv");
//			context.getLogger().log("File Path" + file.getAbsolutePath());
//			outputfile = new FileWriter(file);
//			writer = new CSVWriter(outputfile);
//			List<String[]> data = new ArrayList<>();
//			data.add(res.getString("report.file.headers.axisMproYtd").split(Pattern.quote("||")));
//			for (Proposal proposal : proposalDocumentsList) {
//				String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getAdditionalFlags().isPaymentDone()+"") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPosvJourneyStatus()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCustomerId()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getLeadId()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getPaymentRenewedBy()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getBranchCode()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getFirstName() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getLastName() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getFirstName() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getLastName() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getFirstName() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 3 ? proposal.getPartyInformation().get(2).getBasicDetails().getLastName() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPremiumMode() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCode() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodeTPP() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp() + "" : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getCoverageTerm() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getModalPremium() + "" : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getPremiumPaymentTerm() : "")+ StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "")+ StringConstants.COMMA
//					    + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId())+ StringConstants.COMMA
//					    + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonCode())+ StringConstants.COMMA
//					    + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpSSNCode())+ StringConstants.COMMA
//					    + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getTransactionId()+"");
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
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//			context.getLogger().log("Exception occured while creating report for MPRO-AXIS Report :: " + ex);
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
