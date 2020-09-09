package com.maxlifeinsurance.mpro.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.config.DbConfig;
import com.maxlifeinsurance.mpro.dao.AxisLeadIdReportDao;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author Vinay 
 * This class is dao layer for axis lead id report
 */
public class AxisLeadIdReportDaoImpl implements AxisLeadIdReportDao {


	/**
	 * @author Qualtech
	 * @param Context
	 * @return List<Proposal>
	 * This method will fetch data from database for Axis-Mpro-LeadId Data
	 */
	@Override
	public List<Proposal> getAxisLeadIdReportData(Context context) {
		List<Proposal> proposalDocuments = null;
		try {
			MongoCollection<Document> collection = new DbConfig().getDbConnection(context);
			
//          Below connectivity is used for testing in local env.
//			MongoCollection<Document> collection = new DbConfigForLocal().getDbConnection(mongoDatabaseName);
			String channel = System.getenv(StringConstants.CHANNEL);
			context.getLogger().log("Channel configured for Axis-LeadId-Mpro Report is :: "+channel);
			context.getLogger().log("Fetching document from database");
			Document projection = new Document();
			projection.append("applicationDetails.policyNumber", 1)
					  .append("bancaDetails.leadId", 1)
					  .append("transactionId", 1)
					  .append("applicationDetails.createdTime", 1)
					  .append("channelDetails.channel", 1);
			
			Document whereCondition = new Document();
			whereCondition.append("channelDetails.channel", channel)
			              .append("applicationDetails.createdTime", new Document().append(StringConstants.GREATER_THEN_EQUALS_TO, MultiFormatDate.getPreviousMonthDate(context)));
			proposalDocuments = collection.find(whereCondition, Proposal.class).projection(projection).into(new ArrayList<>());
			context.getLogger().log("Total record count is :: " +proposalDocuments.size());
		} catch (Exception ex) {
			ex.printStackTrace();
			context.getLogger().log("Exception occured in getAxisMproReportData while fetching data from mongo db :: " + ex);
		}
		return proposalDocuments;
	}
}
