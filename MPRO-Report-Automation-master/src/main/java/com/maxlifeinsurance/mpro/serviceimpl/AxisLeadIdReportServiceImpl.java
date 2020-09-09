package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.AxisLeadIdReportDao;
import com.maxlifeinsurance.mpro.daoimpl.AxisLeadIdReportDaoImpl;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.service.AxisLeadIdReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.maxlifeinsurance.mpro.utils.StringUtility;

/**
 * 
 * @author Vinay
 * Service layer for Axis-Mpro-LeadId-Report
 */
public class AxisLeadIdReportServiceImpl implements AxisLeadIdReportService {

	/**
	 * @author Vinay
	 * @param Context
	 * @return flag 
	 * This method will act as service layer for Axis-LeadId-Data
	 * report which will fetch data from database create CSV, mail that
	 * CSV and Save that CSV in S3 Bucket
	 */
	@Override
	public boolean generateAxisLeadIdReport(Context context) {
		boolean flag = false;
		AxisLeadIdReportDao axisLeadIdReportDao = null;
		List<Proposal> proposalDocument = null;
		List<String[]> reportData = null;
		try {
			axisLeadIdReportDao = new AxisLeadIdReportDaoImpl();
			proposalDocument = axisLeadIdReportDao.getAxisLeadIdReportData(context);
			if (proposalDocument != null && !proposalDocument.isEmpty()) {
				reportData = createAxisMproLeadIdRow(proposalDocument,context);
				flag = CsvWriteer.csvWriter(reportData,Constants.AXIS_LEAD_ID_REPORT,Constants.AXIS_LEAD_ID_FOLDER,"report.file.headers.axisLeadId",context);
			}

		} catch (Exception ex) {
			context.getLogger().log("Exception occued while processing Axis-Mpro-LeadId report :: " + ex);
		}
		return flag;
	}
	
	private List<String[]> createAxisMproLeadIdRow(List<Proposal> proposalDocumentList, Context context){
		List<String[]> data = null;
		try{
			data = new ArrayList<>();
			for(Proposal proposal : proposalDocumentList){
				String row = StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + StringConstants.COMMA 
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getLeadId()) + StringConstants.COMMA
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel()) + StringConstants.COMMA 
						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getTransactionId() + "");
				data.add(row.split(Pattern.quote(StringConstants.COMMA)));
			}
			
		}
		catch(Exception ex){
			context.getLogger().log("Exception occured while creating rows of CSV file :: " + ex.getMessage());
		}
		return data;
	}
}
