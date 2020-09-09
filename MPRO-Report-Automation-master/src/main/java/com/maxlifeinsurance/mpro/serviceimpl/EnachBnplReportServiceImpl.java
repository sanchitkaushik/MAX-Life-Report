package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.EnachBnplReportDao;
import com.maxlifeinsurance.mpro.daoimpl.EnachBnplReportDaoImpl;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.service.EnachBnplReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.maxlifeinsurance.mpro.utils.StringUtility;

/**
 * 
 * @author Vinay
 * Service layer for Enach-Bnpl-Data Report
 */
public class EnachBnplReportServiceImpl implements EnachBnplReportService {


	/**
	 * @author Vinay
	 * @param Context
	 * @return flag 
	 * This method will act as service layer for Enach-Bnpl-Data
	 * report which will fetch data from database create CSV, mail that
	 * CSV and Save that CSV in S3 Bucket
	 */
	@Override
	public boolean enachBnplReportGenerationService(Context context) {
		boolean flag = false;
		List<Proposal> proposalDocumentsList = null;
		EnachBnplReportDao enachBnplReportDao = null;
		List<String[]> rowData = null;
		try {
			enachBnplReportDao = new EnachBnplReportDaoImpl();
			proposalDocumentsList = enachBnplReportDao.getEnachBnplReportData(context);
			if (proposalDocumentsList != null && !proposalDocumentsList.isEmpty()) {
				rowData = createEnachBnplDataRow(proposalDocumentsList,context);
				flag = CsvWriteer.csvWriter(rowData, Constants.ENACH_BNPL_DATA_REPORT, Constants.ENACH_BNPL_FOLDER, "report.file.headers.enachBnplData", context);
			}
		} catch (Exception ex) {
			context.getLogger().log("Exception occued while processing Enach-Bnpl-Data-Report :: " + ex);
		}
		return flag;
	}
	
	
	private List<String[]> createEnachBnplDataRow(List<Proposal> proposalDocumentList, Context context){
		List<String[]> data = null;
		try{
			data = new ArrayList<>();
			for(Proposal proposal : proposalDocumentList){
				String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getAdditionalFlags().isPaymentDone()+"") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getAdditionalFlags().isRenewelPaymentDone()+"") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.COMMA 
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.COMMA 
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank() != null ? proposal.getBank().getPaymentRenewedBy() : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.COMMA 
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).isSIOpted()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getModeOfPayment()+"" : "") + StringConstants.COMMA 
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPremiumMode()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "") + StringConstants.COMMA 
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getTransactionId()+"");
				data.add(row.split(Pattern.quote(StringConstants.COMMA)));

			}
			
		}
		catch(Exception ex){
			context.getLogger().log("Exception occured while creating rows of CSV file :: " + ex.getMessage());
		}
		return data;
	}
	
	

//	private double enachBnplCsvWriter(List<Proposal> proposalDocumentList, Context context) {
//		double sizeInMb = 0;
//		FileWriter outputfile = null;
//		CSVWriter writer = null;
//		try {
//			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
//			LocalDateTime toDate = LocalDateTime.now();
//			file = new File("/tmp" + File.separator + "Enach-Bnpl Report" + (dateTimeFormatter.format(toDate)) + ".csv");
//			context.getLogger().log("File Path" + file.getAbsolutePath());
//			outputfile = new FileWriter(file);
//			writer = new CSVWriter(outputfile);
//			List<String[]> data = new ArrayList<>();
//			data.add(res.getString("report.file.headers.enachBnplData").split(Pattern.quote("||")));
//			for (Proposal proposal : proposalDocumentList) {
//				String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getAdditionalFlags().isPaymentDone()+"") + StringConstants.COMMA 
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getAdditionalFlags().isRenewelPaymentDone()+"") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.COMMA 
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.COMMA 
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank() != null ? proposal.getBank().getPaymentRenewedBy() : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.COMMA 
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).isSIOpted()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getModeOfPayment()+"" : "") + StringConstants.COMMA 
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPremiumMode()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "") + StringConstants.COMMA 
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
//			ex.printStackTrace();
//			context.getLogger().log("Exception occured while creating report for Enach-Bnpl Data :: " + ex);
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
