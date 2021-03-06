package com.maxlifeinsurance.mpro.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.config.DbConfig;
import com.maxlifeinsurance.mpro.dao.AllGridReportDao;
import com.maxlifeinsurance.mpro.dto.Proposal;
import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
import com.maxlifeinsurance.mpro.utils.StringConstants;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author Vinay
 * Dao layer for All-Grid-Data Report
 */
public class AllGridReportDaoImpl implements AllGridReportDao {

	/**
	 * @author Vinay
	 * @param Context
	 * @return List<Proposal>
	 * This method will fetch data from database for All-Grid-Data Report
	 */
	@Override
	public List<Proposal> getAllGridReportData(Context context) {
		List<Proposal> proposalDocumentList = null;
		try {
			MongoCollection<Document> collection = new DbConfig().getDbConnection(context);
//			MongoCollection collection = new DbConfigForLocal().getDbConnection(mongoDatabaseName).getCollection(mongoCollectionName);
			Document projection = new Document("_id", 0)
						.append("applicationDetails.createdTime",1)
						.append("applicationDetails.policyNumber",1)
						.append("applicationDetails.stage", 1)
						.append("partyInformation.personalIdentification.panDetails.panNumber", 1)
						.append("underwritingServiceDetails.dedupeDetails.dedupeFlag", 1)
						.append("underwritingServiceDetails.medicalGridDetails.result", 1)
						.append("underwritingServiceDetails.financialGridDetails.underwritingResult",1)
						.append("underwritingServiceDetails.underwritingStatus.medicalGridStatus.financialGridStatus", 1)
						.append("underwritingServiceDetails.riskScoreDetails.score", 1)
						.append("underwritingServiceDetails.urmuRuleStatus.result", 1)
						.append("underwritingServiceDetails.cibilDetails.bureauResponse", 1)
						.append("productDetails.productInfo.productName", 1)
						.append("channelDetails.channel", 1)
						.append("underwritingServiceDetails.msaFsaDetails", 1)
						.append("medicalShedulingDetails.scheduleStatus", 1)
						.append("underwritingServiceDetails.miscellaneousRuleStatus.status", 1)
						.append("underwritingServiceDetails.cibilDetails.affulentOrNot", 1)
						.append("underwritingServiceDetails.proposalFormRuleDetails", 1)
						.append("underwritingServiceDetails.riskScoreDetails.riskyTagURMU", 1)
						.append("underwritingServiceDetails.riskScoreDetails.normalisedScoreURMU", 1);

			 Document whereCondition=new Document();
			 whereCondition.append("applicationDetails.createdTime", new Document(StringConstants.GREATER_THEN_EQUALS_TO, MultiFormatDate.getPreviousMonthDate(context)));
						  
			 proposalDocumentList = collection.find(whereCondition, Proposal.class).projection(projection).into(new ArrayList<>());
			 context.getLogger().log("Total record count is :: " +proposalDocumentList.size());

		} catch (Exception ex) {
			context.getLogger().log("Exception occured in getAllGridReportData while fetching data from mongo db :: " + ex);
		}
		return proposalDocumentList;
	}
}
