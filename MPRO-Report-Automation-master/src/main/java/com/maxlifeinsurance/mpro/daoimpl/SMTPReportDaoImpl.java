package com.maxlifeinsurance.mpro.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.config.DbConfig;
import com.maxlifeinsurance.mpro.dao.SMTPReportDao;
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
public class SMTPReportDaoImpl implements SMTPReportDao{

	/**
	 * @author Qualtech
	 * @param Context
	 * @return List<Proposal>
	 * This method will fetch data from database for SMTP Data
	 */
	@Override
	public List<Proposal> getSMTPReportData(Context context) {

		List<Proposal> proposalDocumentList = null;
		try {
			MongoCollection<Document> collection = new DbConfig().getDbConnection(context);
//			Below connectivity is used for testing in local env.
//			MongoCollection collection = new DbConfigForLocal().getDbConnection(mongoDatabaseName).getCollection(mongoCollectionName);
			Document projection = new Document("_id", 0)
					.append("applicationDetails.createdTime", 1)
					.append("applicationDetails.stage", 1)
					.append("applicationDetails.policyNumber", 1)
					.append("applicationDetails.formType", 1)
					.append("channelDetails.channel", 1)
					.append("sourcingDetails.agentId", 1)
					.append("productDetails.productInfo.productName", 1)
					.append("productDetails.productInfo.planCode", 1)
					.append("productDetails.productInfo.productIllustrationResponse.coverageTerm", 1)
					.append("productDetails.productInfo.productIllustrationResponse.sumAssured", 1)
					.append("productDetails.productInfo.productIllustrationResponsepremiumPaymentTerm", 1)
					.append("productDetails.productInfo.productIllustrationResponse.afyp", 1)
					.append("productDetails.productInfo.productIllustrationResponse.atp", 1)
					.append("productDetails.productInfo.riderDetails.riderInfo", 1)
					.append("productDetails.productInfo.premiumType", 1)
					.append("bancaDetails.customerClassification", 1)
					.append("underwritingServiceDetails.dedupeDetails.dedupeFlag", 1)
					.append("underwritingServiceDetails.dedupeDetails.previousPolicyNumber", 1)
					.append("underwritingServiceDetails.cibilDetails.affulentOrNot", 1)
					.append("underwritingServiceDetails.cibilDetails.bureauResponse", 1)
					.append("sourcingDetails.goCABrokerCode", 1)
					.append("partyInformation.basicDetails", 1)
					.append("partyInformation.personalIdentification.panDetails.creditScore", 1)
					.append("underwritingServiceDetails.msaFsaDetails.valueOfMSA", 1)
					.append("underwritingServiceDetails.msaFsaDetails.valueOfFSA", 1)
					.append("posvDetails.posvStatus.overallHealthStatus", 1)
					.append("employmentDetails.pepDetails", 1)
					.append("productDetails.productInfo.productIllustrationResponse.deathBenefit", 1);
			
			Document whereCondition = new Document();
			whereCondition.append("applicationDetails.createdTime",new Document(StringConstants.GREATER_THEN_EQUALS_TO, MultiFormatDate.getPreviousMonthDate(context)));
			proposalDocumentList = collection.find(whereCondition, Proposal.class).projection(projection).into(new ArrayList<>());
			context.getLogger().log("Total record count is :: " +proposalDocumentList.size());
		} catch (Exception ex) {
			context.getLogger().log("Exception occured in getSMTPReportData while fetching data from mongo db :: "+ex);
		}
		return proposalDocumentList;
	}
}
