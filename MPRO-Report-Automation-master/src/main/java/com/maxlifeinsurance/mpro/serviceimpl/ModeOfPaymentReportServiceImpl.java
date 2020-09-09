package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.ModeOfPaymentReportDao;
import com.maxlifeinsurance.mpro.daoimpl.ModeOfPaymentReportDaoImpl;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.service.ModeOfPaymentReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.maxlifeinsurance.mpro.utils.StringUtility;

/**
 * 
 * @author Vinay
 * Service layer for Mode-Of-Payment
 */
public class ModeOfPaymentReportServiceImpl implements ModeOfPaymentReportService {

	/**
	 * @author Vinay
	 * @param Context
	 * @return flag 
	 * This method will act as service layer for Mode-Of-Payment-Data
	 * report which will fetch data from database create CSV, mail that
	 * CSV and Save that CSV in S3 Bucket
	 */
	@Override
	public boolean generateModeOfPaymentReport(Context context) {
		boolean flag = false;
		List<Proposal> proposalDocumentsList = null;
		ModeOfPaymentReportDao modeOfPaymentReportDao = null;
		List<String[]> rowData = null;
		try {
			modeOfPaymentReportDao = new ModeOfPaymentReportDaoImpl();
			proposalDocumentsList = modeOfPaymentReportDao.getModeOfPaymentData(context);
			if (proposalDocumentsList != null && !proposalDocumentsList.isEmpty()) {
				rowData = createModeOfPaymentDataRow(proposalDocumentsList,context);
				flag = CsvWriteer.csvWriter(rowData, Constants.MODE_OF_PAYMENT, Constants.MODE_OF_PAYMENT_FOLDER, "report.file.headers.modeOfPayment", context);
			}
		} catch (Exception ex) {
			context.getLogger().log("Exception occued while processing Mode-Of-Payment-Data-Report :: " + ex);
		}
		return flag;
	}
	
	
	private List<String[]> createModeOfPaymentDataRow(List<Proposal> proposalDocumentList, Context context){
		List<String[]> data = null;
		try{
			data = new ArrayList<>();
			for(Proposal proposal : proposalDocumentList){
				String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentChequeDetails().getChequeAmount()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentChequeDetails().getChequeDate()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentChequeDetails().getChequeMicr()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentChequeDetails().getChequeNumber()+"" : "") + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getGoCABrokerCode());
				data.add(row.split(Pattern.quote(StringConstants.COMMA)));

			}
			
		}
		catch(Exception ex){
			context.getLogger().log("Exception occured while creating rows of CSV file :: " + ex.getMessage());
		}
		return data;
	}
	
	
//	private double modeOfPaymentCsvWriter(List<Proposal> proposalDocumentList, Context context) {
//		double sizeInMb = 0;
//		FileWriter outputfile = null;
//		CSVWriter writer = null;
//		try {
//			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
//			LocalDateTime toDate = LocalDateTime.now();
//			file = new File(
//					"/tmp" + File.separator + "Mode-Of-Payment Report" + (dateTimeFormatter.format(toDate)) + ".csv");
//			context.getLogger().log("File Path" + file.getAbsolutePath());
//			outputfile = new FileWriter(file);
//			writer = new CSVWriter(outputfile);
//			List<String[]> data = new ArrayList<>();
//			data.add(res.getString("report.file.headers.modeOfPayment").split(Pattern.quote("||")));
//			for (Proposal proposal : proposalDocumentList) {
//				String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentChequeDetails().getChequeAmount()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentChequeDetails().getChequeDate()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentChequeDetails().getChequeMicr()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentChequeDetails().getChequeNumber()+"" : "") + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getGoCABrokerCode());
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
//			context.getLogger().log("Exception occured while creating report for Mode-Of-Payment Data :: " + ex);
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
