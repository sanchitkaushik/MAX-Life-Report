package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.ScbReportDao;
import com.maxlifeinsurance.mpro.daoimpl.ScbReportDaoImpl;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.service.ScbReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.maxlifeinsurance.mpro.utils.StringUtility;

/**
 * 
 * @author Vinay
 * Service layer for SCB Report
 */
public class ScbReportServiceImpl implements ScbReportService {

	/**
	 * @author Vinay
	 * @param Context
	 * @return flag 
	 * This method will act as service layer for SCB-Data
	 * report which will fetch data from database create CSV, mail that
	 * CSV and Save that CSV in S3 Bucket
	 */
	@Override
	public boolean generateScbReport(Context context) {
		boolean flag = false;
		List<Proposal> proposalDocumentsList = null;
		ScbReportDao scbReportDao = null;
		List<String[]> rowData = null;
		try {
			scbReportDao = new ScbReportDaoImpl();
			proposalDocumentsList = scbReportDao.getScbReportData(context);
			if (proposalDocumentsList != null && !proposalDocumentsList.isEmpty()) {
				rowData = createScbDataRow(proposalDocumentsList,context);
				flag = CsvWriteer.csvWriter(rowData, Constants.SCB_REPORT, Constants.SCB_FOLDER, "report.file.headers.scb", context);
			}
		} catch (Exception ex) {
			context.getLogger().log("Exception occued while processing SCB-Data-Report :: " + ex);
		}
		return flag;
	}
	
	
	private List<String[]> createScbDataRow(List<Proposal> proposalDocumentList, Context context){
		List<String[]> data = null;
		try{
			data = new ArrayList<>();
			for(Proposal proposal : proposalDocumentList){
				String row = StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getDob()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2  ? proposal.getPartyInformation().get(1).getBasicDetails().getDob()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCode() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductId() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp() + "" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName(): "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().size() >=2 ? proposal.getProductDetails().get(1).getProductInfo().getPlanCode() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().size() >=2 ? proposal.getProductDetails().get(1).getProductInfo().getProductId() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().size() >=2 ? proposal.getProductDetails().get(1).getProductInfo().getProductIllustrationResponse().getAfyp() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().size() >=2 ? proposal.getProductDetails().get(1).getProductInfo().getProductIllustrationResponse().getAtp() + "" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().size() >=2 ? proposal.getProductDetails().get(1).getProductInfo().getProductName(): "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSalesStoriesProductDetails().getIsSalesProduct()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSalesStoriesProductDetails().getSalesReferenceId()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getGoCABrokerCode()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getTransactionId()+"");
				data.add(row.split(Pattern.quote(StringConstants.COMMA)));

			}
			
		}
		catch(Exception ex){
			context.getLogger().log("Exception occured while creating rows of CSV file :: " + ex.getMessage());
		}
		return data;
	}
	
	
//	private double scbReportCsvWriter(List<Proposal> proposalDocumentList, Context context) {
//		double sizeInMb = 0;
//		FileWriter outputfile = null;
//		CSVWriter writer = null;
//		try {
//			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
//			LocalDateTime toDate = LocalDateTime.now();
//			file = new File(
//					"/tmp" + File.separator + "SCB Report" + (dateTimeFormatter.format(toDate)) + ".csv");
//			context.getLogger().log("File Path" + file.getAbsolutePath());
//			outputfile = new FileWriter(file);
//			writer = new CSVWriter(outputfile);
//			List<String[]> data = new ArrayList<>();
//			data.add(res.getString("report.file.headers.scb").split(Pattern.quote("||")));
//			for (Proposal proposal : proposalDocumentList) {
//				String row = StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getDob()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >= 2  ? proposal.getPartyInformation().get(1).getBasicDetails().getDob()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCode() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductId() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp() + "" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName(): "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().size() >=2 ? proposal.getProductDetails().get(1).getProductInfo().getPlanCode() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().size() >=2 ? proposal.getProductDetails().get(1).getProductInfo().getProductId() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().size() >=2 ? proposal.getProductDetails().get(1).getProductInfo().getProductIllustrationResponse().getAfyp() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().size() >=2 ? proposal.getProductDetails().get(1).getProductInfo().getProductIllustrationResponse().getAtp() + "" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getProductDetails().size() >=2 ? proposal.getProductDetails().get(1).getProductInfo().getProductName(): "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSalesStoriesProductDetails().getIsSalesProduct()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSalesStoriesProductDetails().getSalesReferenceId()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getGoCABrokerCode()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getTransactionId()+"");
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
//			context.getLogger().log("Exception occured while creating report for SCB Data Report :: " + ex);
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
