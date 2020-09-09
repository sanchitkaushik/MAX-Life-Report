package com.maxlifeinsurance.mpro.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.config.DbConfig;
import com.maxlifeinsurance.mpro.dao.AxisMproReportDao;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author Vinay 
 * This class is dao layer for axis mpro report
 */

public class AxisMproReportDaoImpl implements AxisMproReportDao {


	@Override
	/**
	 * @author Qualtech
	 * @param Context
	 * @return List<Proposal>
	 * This method will fetch data from database for Axis-Mpro-Data
	 */
	public List<Proposal> getAxisMproReportData(Context context) {
		List<Proposal> proposalDocumentList = null;
		try {

			MongoCollection<Document> collection = new DbConfig().getDbConnection(context);
//          Below connectivity is used for testing in local env.
//			MongoCollection<Document> collection = new DbConfigForLocal().getDbConnection(mongoDatabaseName);
			String channel = System.getenv(StringConstants.CHANNEL);
			context.getLogger().log("Channel configured for Axis-Mpro Report is :: "+channel);
			Document projection = new Document("_id", 0)
					.append("applicationDetails.policyNumber", 1)
					.append("transactionId", 1)
					.append("applicationDetails.createdTime", 1)
					.append("applicationDetails.stage", 1)
					.append("channelDetails.branchCode", 1)
					.append("partyInformation.basicDetails.firstName", 1)
					.append("applicationDetails.posvJourneyStatus", 1)
					.append("partyInformation.basicDetails.lastName", 1)
					.append("paymentDetails.receipt.premiumMode", 1)
					.append("bank.paymentRenewedBy", 1)
					.append("additionalFlags.isPaymentDone", 1)
					.append("sourcingDetails.specifiedPersonDetails.spSSNCode", 1)
					.append("productDetails.productInfo.productIllustrationResponse.afyp", 1)
					.append("productDetails.productInfo.productIllustrationResponse.atp", 1)
					.append("productDetails.productInfo.productName", 1)
					.append("productDetails.productInfo.planCode", 1)
					.append("productDetails.productInfo.planCodeTPP", 1)
					.append("bancaDetails.customerId", 1)
					.append("bancaDetails.leadId", 1)
					.append("productDetails.productInfo.productIllustrationResponse.modalPremium", 1)
					.append("productDetails.productInfo.productIllustrationResponse.premiumPaymentTerm", 1)
					.append("productDetails.productInfo.productIllustrationResponse.coverageTerm", 1)
					.append("sourcingDetails.specifiedPersonCode", 1)
					.append("sourcingDetails.agentId", 1);

			Document whereCondition = new Document();
			whereCondition.append("applicationDetails.createdTime", new Document(StringConstants.GREATER_THEN_EQUALS_TO,MultiFormatDate.getPreviousMonthDate(context)))
			              .append("channelDetails.channel", channel);

			proposalDocumentList = collection.find(whereCondition, Proposal.class).projection(projection).into(new ArrayList<>());
			
			context.getLogger().log("Total record count is :: "+proposalDocumentList.size());

		} catch (Exception ex) {
			context.getLogger().log("Exception occured in getAxisMproReportData while fetching data from mongo db :: " + ex);
		}
		return proposalDocumentList;
	}
}
