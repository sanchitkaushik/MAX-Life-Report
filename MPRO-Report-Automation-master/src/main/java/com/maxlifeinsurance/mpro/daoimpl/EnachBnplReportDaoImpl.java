package com.maxlifeinsurance.mpro.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.config.DbConfig;
import com.maxlifeinsurance.mpro.dao.EnachBnplReportDao;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author Vinay
 *
 */
public class EnachBnplReportDaoImpl implements EnachBnplReportDao {


	/**
	 * @author Qualtech
	 * @param Context
	 * @return List<Proposal>
	 * This method will fetch data from database for Enach-Bnpl-Data 
	 */
	@Override
	public List<Proposal> getEnachBnplReportData(Context context) {
		List<Proposal> proposalDocuments = null;
		try {
			MongoCollection<Document> collection = new DbConfig().getDbConnection(context);
			// Below connectivity is used for testing in local env.
			// MongoCollection localCollection = new DbConfigForLocal().getDbConnection(mongoDatabaseName).getCollection(mongoCollectionName);
			Document projection = new Document("_id", 0)
					.append("applicationDetails.policyNumber", 1)
					.append("transactionId", 1)
					.append("applicationDetails.createdTime", 1)
					.append("applicationDetails.stage", 1)
					.append("productDetails.productInfo.productName", 1)
					.append("paymentDetails.receipt.premiumMode", 1)
					.append("additionalFlags.isPaymentDone", 1)
					.append("additionalFlags.isRenewelPaymentDone", 1)
					.append("bank.paymentRenewedBy", 1)
					.append("channelDetails.channel", 1)
					.append("paymentDetails.receipt.modeOfPayment", 1)
					.append("paymentDetails.receipt.isSIOpted", 1);

			Document whereCondition = new Document();
			whereCondition.append("applicationDetails.createdTime", new Document(StringConstants.GREATER_THEN_EQUALS_TO, MultiFormatDate.getPreviousMonthDate(context)));
			proposalDocuments = collection.find(whereCondition, Proposal.class).projection(projection).into(new ArrayList<>());
			context.getLogger().log("Total record count is :: " + proposalDocuments.size());
		} catch (Exception ex) {
			context.getLogger().log("Exception occured in getEnachBnplReportData while fetching data from mongo db :: " + ex);
		}
		return proposalDocuments;
	}
}
