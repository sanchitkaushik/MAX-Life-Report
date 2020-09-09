package com.maxlifeinsurance.mpro.daoimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.config.DbConfig;
import com.maxlifeinsurance.mpro.dao.YblRaIdReportDao;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author Vinay
 *
 */
public class YblRaIdReportDaoImpl implements YblRaIdReportDao {

	
	/**
	 * @author Qualtech
	 * @param Context
	 * @return List<Proposal>
	 * This method will fetch data from database for YBL-RA-ID Data
	 */
	@Override
	public List<Proposal> getYblRaIdData(Context context) {

		List<Proposal> proposalDocumentList = null;
		try {
			MongoCollection<Document> collection = new DbConfig().getDbConnection(context);
//			Below connectivity is used for testing in local env.
//			MongoCollection<Document> collection = new DbConfigForLocal().getDbConnection(mongoDatabaseName);
			List<String> stages = Arrays.asList(System.getenv(StringConstants.STAGES).split(StringConstants.COMMA));
			context.getLogger().log("Stages configured for YBL-RA-ID-Report is :: " +stages);
			String channel = System.getenv(StringConstants.CHANNEL);
			context.getLogger().log("Channel configured for YBL-RA-ID-Report is :: " +channel);
			Document projection = new Document("_id", 0)
					.append("sourcingDetails.regionalAdvisorId", 1)
					.append("sourcingDetails.specifiedPersonCode", 1)
					.append("applicationDetails.policyNumber", 1)
					.append("sourcingDetails.agentId", 1)
					.append("channelDetails.channel", 1)
					.append("applicationDetails.stage", 1);
					
			Document whereCondition = new Document();
			whereCondition.append("channelDetails.channel", channel)
						  .append("applicationDetails.stage", new Document(StringConstants.DOLLAR_IN,stages));
			proposalDocumentList = collection.find(whereCondition, Proposal.class).projection(projection).into(new ArrayList<>());
			context.getLogger().log("Total record count is :: " +proposalDocumentList.size());
		} catch (Exception ex) {
			context.getLogger().log("Exception occured in getYblRaIdData while fetching data from mongo db :: "+ex);
		}
		return proposalDocumentList;
	}

}
