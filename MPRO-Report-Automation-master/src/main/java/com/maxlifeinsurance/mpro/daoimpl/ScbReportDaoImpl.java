package com.maxlifeinsurance.mpro.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.config.DbConfig;
import com.maxlifeinsurance.mpro.dao.ScbReportDao;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author Vinay
 *
 */
public class ScbReportDaoImpl implements ScbReportDao {

	
	/**
	 * @author Qualtech
	 * @param Context
	 * @return List<Proposal>
	 * This method will fetch data from database for SCB Data
	 */
	@Override
	public List<Proposal> getScbReportData(Context context) {
		List<Proposal> proposalDocumentList = null;
		try {
			MongoCollection<Document> collection = new DbConfig().getDbConnection(context);
//			Below connectivity is used for testing in local env.
//			MongoCollection localCollection = new DbConfigForLocal().getDbConnection(mongoDatabaseName).getCollection(mongoCollectionName);
			Document projection = new Document("_id", 0)
					.append("applicationDetails.policyNumber", 1)
					.append("transactionId", 1)
					.append("partyInformation.basicDetails.dob", 1)
					.append("productDetails.productInfo.productName", 1)
					.append("applicationDetails.createdTime", 1)
					.append("sourcingDetails.goCABrokerCode", 1)
					.append("productDetails.productInfo.productId", 1)
					.append("productDetails.productInfo.planCode", 1)
					.append("productDetails.productInfo.productIllustrationResponse.afyp", 1)
					.append("productDetails.productInfo.productIllustrationResponse.atp", 1)
					.append("channelDetails.channel", 1)
					.append("sourcingDetails.agentId", 1)
					.append("salesStoriesProductDetails.salesReferenceId", 1)
					.append("salesStoriesProductDetails.isSalesProduct", 1);

			Document whereCondition = new Document();
			whereCondition.append("salesStoriesProductDetails.isSalesProduct", "yes");
			
			proposalDocumentList = collection.find(whereCondition, Proposal.class).projection(projection).into(new ArrayList<>());
			context.getLogger().log("Total record count is :: " +proposalDocumentList.size());

		} catch (Exception ex) {
			context.getLogger().log("Exception occured in getScbReportData while fetching data from mongo db :: "+ex);
		}
		return proposalDocumentList;
	}

}
