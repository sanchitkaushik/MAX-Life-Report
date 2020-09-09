package com.maxlifeinsurance.mpro.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.config.DbConfig;
import com.maxlifeinsurance.mpro.dao.ModeOfPaymentReportDao;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author Vinay
 *
 */
public class ModeOfPaymentReportDaoImpl implements ModeOfPaymentReportDao {

	
	/**
	 * @author Qualtech
	 * @param Context
	 * @return List<Proposal>
	 * This method will fetch data from database for Mode-Of-Payment Data
	 */
	@Override
	public List<Proposal> getModeOfPaymentData(Context context) {
		List<Proposal> proposalDocumentList = null;
		try {
			MongoCollection<Document> collection = new DbConfig().getDbConnection(context);
//			Below connectivity is used for testing in local env.
//			MongoCollection localCollection = new DbConfigForLocal().getDbConnection(mongoDatabaseName).getCollection(mongoCollectionName);
			Document projection = new Document("_id", 0)
					.append("sourcingDetails.agentId", 1)
					.append("applicationDetails.policyNumber", 1)
					.append("sourcingDetails.goCABrokerCode", 1)
					.append("paymentDetails.receipt.paymentChequeDetails.chequeNumber", 1)
					.append("paymentDetails.receipt.paymentChequeDetails.chequeAmount", 1)
					.append("paymentDetails.receipt.paymentChequeDetails.chequeMicr", 1)
					.append("paymentDetails.receipt.paymentChequeDetails.chequeDate", 1);

			Document whereCondition = new Document();
			whereCondition.append("applicationDetails.createdTime",
					new Document(StringConstants.GREATER_THEN_EQUALS_TO, MultiFormatDate.getPreviousMonthDate(context)));
			
			proposalDocumentList = collection.find(whereCondition, Proposal.class).projection(projection).into(new ArrayList<>());
			context.getLogger().log("Total record count is :: " +proposalDocumentList.size());

		} catch (Exception ex) {
			context.getLogger().log("Exception occured in getModeOfPaymentData while fetching data from mongo db :: "+ex);
		}
		return proposalDocumentList;
	}
}
