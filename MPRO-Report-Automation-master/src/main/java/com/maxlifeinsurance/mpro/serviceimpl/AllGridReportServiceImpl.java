package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.AllGridReportDao;
import com.maxlifeinsurance.mpro.daoimpl.AllGridReportDaoImpl;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.service.AllGridReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.maxlifeinsurance.mpro.utils.StringUtility;

/**
 * 
 * @author Vinay
 * Service layer for All-Grid-Data Report
 *
 */
public class AllGridReportServiceImpl implements AllGridReportService {

	@Override
	/**
	 * @author Vinay
	 * @param Context
	 * @return flag 
	 * This method will act as service layer for All-Grid-Data
	 * report which will fetch data from database create CSV, mail that
	 * CSV and Save that CSV in S3 Bucket
	 */
	public boolean generateAllGridDataReport(Context context) {
		boolean flag = false;
		List<Proposal> proposalDocumentsList = null;
		AllGridReportDao allGridReportDao = null;
		List<String[]> rowData = null;
		try {
			allGridReportDao = new AllGridReportDaoImpl();
			proposalDocumentsList = allGridReportDao.getAllGridReportData(context);
			if (proposalDocumentsList != null && !proposalDocumentsList.isEmpty()) {
				rowData = createAllGridDataRow(proposalDocumentsList,context);
				flag = CsvWriteer.csvWriter(rowData, Constants.ALL_GRID_DATA, Constants.ALL_GRID_FOLDER, "report.file.headers.allGridData", context);
			}
		} catch (Exception ex) {
			context.getLogger().log("Exception occued while processing All-Grid-Data report :: " + ex);
		}
		return flag;

	}
	
//	/**
//	 * 
//	 * @param listAxisMproReport
//	 * @param context
//	 * @return fileSize 
//	 * This is private method which will create CSV file in respective
//	 * template for axis-mpro report and than convert the CSV in
//	 * password protected zip file
//	 */
//	private double allGridDataCsvWriter(List<Proposal> proposalDocumentsList, Context context){
//		double sizeInMb = 0;
//		FileWriter outputfile = null;
//		CSVWriter writer = null;
//		try{
//			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
//			LocalDateTime toDate = LocalDateTime.now();
//			file = new File("/tmp" + File.separator + "All GRID DATA Report_" + (dateTimeFormatter.format(toDate)) + ".csv");
////			file = new File("E://"+File.separator+"Axis Lead Report.csv");
//			context.getLogger().log("File Path" + file.getAbsolutePath());
//			outputfile = new FileWriter(file);
//			writer = new CSVWriter(outputfile);
//			List<String[]> data = new ArrayList<>();
//			data.add(res.getString("report.file.headers.allGridData").split(Pattern.quote("||")));
//			for (Proposal proposal : proposalDocumentsList) {
//				
//				String row = StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().getPanNumber() : "") + StringConstants.DOLLAR
//					    + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getPersonalIdentification().getPanDetails().getPanNumber() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getPersonalIdentification().getPanDetails().getPanNumber() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getAffulentOrNot()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScore() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScoreDate() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScoreName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >= 2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScore() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >= 2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScoreDate() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >= 2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScoreName() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getDedupeFlag() : "") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getFinancialGridDetails().getUnderwritingResult()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMedicalGridDetails().getResult()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMiscellaneousRuleStatus().getStatus()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfAFYP()+"") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfDD()+"") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfFSA()+"") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfMSA()+"") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfSUC()+"") + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getProposalFormRuleDetails().getKickoutMsg()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getProposalFormRuleDetails().getResultFlag()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getRiskScoreDetails().getNormalisedScoreURMU()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getRiskScoreDetails().getRiskyTagURMU()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getUnderwritingStatus().toString()) + StringConstants.DOLLAR
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getUrmuRuleStatus().getResult());
//				data.add(row.split(Pattern.quote(StringConstants.DOLLAR))); 	
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
////			System.out.println(ex);
//			context.getLogger().log("Exception occured while creating report for ALL-GRID-DATA Report :: " + ex);
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
	
	private List<String[]> createAllGridDataRow(List<Proposal> proposalDocumentList, Context context){
		List<String[]> data = null;
		try{
			data = new ArrayList<>();
				for (Proposal proposal : proposalDocumentList) {
				String row = StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? proposal.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().getPanNumber() : "") + StringConstants.DOLLAR
					    + StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? proposal.getPartyInformation().get(1).getPersonalIdentification().getPanDetails().getPanNumber() : "") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? proposal.getPartyInformation().get(2).getPersonalIdentification().getPanDetails().getPanNumber() : "") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getAffulentOrNot()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScore() : "") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScoreDate() : "") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().isEmpty() ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(0).getScoreName() : "") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >= 2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScore() : "") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >= 2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScoreDate() : "") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().size() >= 2 ? proposal.getUnderwritingServiceDetails().getCibilDetails().getBureauResponse().get(1).getScoreName() : "") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getDedupeFlag() : "") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getFinancialGridDetails().getUnderwritingResult()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMedicalGridDetails().getResult()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMiscellaneousRuleStatus().getStatus()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfAFYP()+"") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfDD()+"") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfFSA()+"") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfMSA()+"") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getMsaFsaDetails().getValueOfSUC()+"") + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getProposalFormRuleDetails().getKickoutMsg()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getProposalFormRuleDetails().getResultFlag()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getRiskScoreDetails().getNormalisedScoreURMU()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getRiskScoreDetails().getRiskyTagURMU()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getUnderwritingStatus().toString()) + StringConstants.DOLLAR
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getUnderwritingServiceDetails().getUrmuRuleStatus().getResult());
				data.add(row.split(Pattern.quote(StringConstants.DOLLAR))); 	
			}		
		}
		catch(Exception ex){
			context.getLogger().log("Exception occured while creating rows of CSV file :: " + ex.getMessage());
		}
		return data;
	}
}
