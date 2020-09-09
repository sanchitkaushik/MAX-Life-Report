package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.YblRaIdReportDao;
import com.maxlifeinsurance.mpro.daoimpl.YblRaIdReportDaoImpl;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.service.YblRaIdReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.maxlifeinsurance.mpro.utils.StringUtility;

/**
 * 
 * @author Vinay
 * Service layer for YBL-RA-ID Report
 */

public class YblRaIdReportServiceImpl implements YblRaIdReportService {

	
	/**
	 * @author Vinay
	 * @param Context
	 * @return flag 
	 * This method will act as service layer for YBL-RA-ID-Data
	 * report which will fetch data from database create CSV, mail that
	 * CSV and Save that CSV in S3 Bucket
	 */
	@Override
	public boolean generateYblRaIdReport(Context context) {
		boolean flag = false;
		List<Proposal> proposalDocumentsList = null;
		YblRaIdReportDao yblRaIdReportDao = null;
		List<String[]> rowData = null;
		try {
			yblRaIdReportDao = new YblRaIdReportDaoImpl();
			proposalDocumentsList = yblRaIdReportDao.getYblRaIdData(context);
			if (proposalDocumentsList != null && !proposalDocumentsList.isEmpty()) {
				rowData = createYblRaIdDataRow(proposalDocumentsList,context);
				flag = CsvWriteer.csvWriter(rowData, Constants.YBL_RA_ID_MPRO, Constants.YBL_RA_ID_FOLDER, "report.file.headers.yblRaId", context);
			}
		} catch (Exception ex) {
			context.getLogger().log("Exception occued while processing YBL-RA-ID-Report :: " + ex);
		}
		return flag;
	}
	
	
	private List<String[]> createYblRaIdDataRow(List<Proposal> proposalDocumentList, Context context){
		List<String[]> data = null;
		try{
			data = new ArrayList<>();
			for(Proposal proposal : proposalDocumentList){
				String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getRegionalAdvisorId()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonCode());
				data.add(row.split(Pattern.quote(StringConstants.COMMA)));

			}
			
		}
		catch(Exception ex){
			context.getLogger().log("Exception occured while creating rows of CSV file :: " + ex.getMessage());
		}
		return data;
	}
	
	
	
//	private double yblRaIdCsvWriter(List<Proposal> proposalDocumentList, Context context) {
//		double sizeInMb = 0;
//		FileWriter outputfile = null;
//		CSVWriter writer = null;
//		try {
//			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
//			LocalDateTime toDate = LocalDateTime.now();
//			file = new File(
//					"/tmp" + File.separator + "Ybl-Ra-Id-Mpro" + (dateTimeFormatter.format(toDate)) + ".csv");
//			context.getLogger().log("File Path" + file.getAbsolutePath());
//			outputfile = new FileWriter(file);
//			writer = new CSVWriter(outputfile);
//			List<String[]> data = new ArrayList<>();
//			data.add(res.getString("report.file.headers.yblRaId").split(Pattern.quote("||")));
//			for (Proposal proposal : proposalDocumentList) {
//				String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getRegionalAdvisorId()) + StringConstants.COMMA
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonCode());
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
//			context.getLogger().log("Exception occured while creating report for YBL-RA-ID-MPRO  :: " + ex);
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
