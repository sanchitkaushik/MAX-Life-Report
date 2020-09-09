//package com.maxlifeinsurance.mpro.serviceimpl;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//import java.util.regex.Pattern;
//
//import org.bson.internal.Base64;
//
//import com.amazonaws.services.lambda.runtime.Context;
//import com.maxlifeinsurance.mpro.dao.ModeOfPaymentReportDao;
//import com.maxlifeinsurance.mpro.daoimpl.ModeOfPaymentReportDaoImpl;
//import com.maxlifeinsurance.mpro.dto.Proposal;
//import com.maxlifeinsurance.mpro.service.AxisEbccReportService;
//import com.maxlifeinsurance.mpro.service.CommunicationService;
//import com.maxlifeinsurance.mpro.utils.AWSClients;
//import com.maxlifeinsurance.mpro.utils.Constants;
//import com.maxlifeinsurance.mpro.utils.MultiFormatDate;
//import com.maxlifeinsurance.mpro.utils.ReportCreationUtility;
//import com.maxlifeinsurance.mpro.utils.StringUtility;
//import com.opencsv.CSVWriter;
//
//import net.lingala.zip4j.core.ZipFile;
//
///**
// * 
// * @author Vinay
// * Service layer for Axis-Ebcc-Report
// */
//public class AxisEbccReportServiceImpl implements AxisEbccReportService {
//
//	static ResourceBundle res = ResourceBundle.getBundle("application");
//
//	File file = null;
//	ZipFile zipFile = null;
//	String fileData = null;
//	byte[] xmlFileByteArray = null;
//	double fileSize;
//	static boolean flag = false;
//
//	@Override
//	public boolean generateAxisEbccReport(Context context) {
//		ModeOfPaymentReportDao modeOfPaymentReportDao = null;
//		List<Proposal> proposalDocumentList = null;
//		CommunicationService communicationService = null;
//		try {
//			modeOfPaymentReportDao = new ModeOfPaymentReportDaoImpl();
//			communicationService = new CommunicationServiceImpl();
//			proposalDocumentList = modeOfPaymentReportDao.getModeOfPaymentData(context);
//			if (proposalDocumentList != null && !proposalDocumentList.isEmpty()) {
//				fileSize = axisEbccCsvWriter(proposalDocumentList, context);
//				if (fileSize < 12.0 && communicationService.sendEmailToUser(fileData, System.getenv(Constants.MAIL_TO), Constants.MODE_OF_PAYMENT, context)) {
//					context.getLogger().log("Mail sent successfully");
//					flag = true;
//				} else if (fileSize > 0.0) {
//					flag = AWSClients.saveFileToS3(file, res.getString(Constants.S3BUCKETNAME), context);
//					context.getLogger().log("File is saved on S3 bucket with name :: " + res.getString(Constants.S3BUCKETNAME));
//				}
//			}
//
//		} catch (Exception ex) {
//			context.getLogger().log("Exception occued while processing Mode-Of-Payment report :: " + ex);
//		}
//		return flag;
//	}
//	
//	private double axisEbccCsvWriter(List<Proposal> proposalDocumentList, Context context) {
//		double sizeInMb = 0;
//		FileWriter outputfile = null;
//		CSVWriter writer = null;
//		try {
//			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
//			LocalDateTime toDate = LocalDateTime.now();
//			file = new File(
//					"/tmp" + File.separator + "Axis-Ebcc Report" + (dateTimeFormatter.format(toDate)) + ".csv");
//			context.getLogger().log("File Path" + file.getAbsolutePath());
//			outputfile = new FileWriter(file);
//			writer = new CSVWriter(outputfile);
//			List<String[]> data = new ArrayList<>();
//			data.add(res.getString("report.file.headers.axisEbcc").split(Pattern.quote("||")));
//			for (Proposal proposal : proposalDocumentList) {
//				String row = StringUtility.checkStringNullOrBlankWithoutCase(proposal.getAdditionalFlags().isPaymentDone()+"") + ","
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getAdditionalFlags().getJourneyFieldsModificationStatus().getCommunicationAddStatus()) + ","
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getAdditionalFlags().getJourneyFieldsModificationStatus().getDobStatus()) + ","
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getAdditionalFlags().getJourneyFieldsModificationStatus().getNameStatus()) + ","
//						+ StringUtility.checkStringNullOrBlankWithoutCase(MultiFormatDate.formatDateInMongoDateFormat(proposal.getApplicationDetails().getCreatedTime())) + ","
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyNumber()) + ","
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPolicyProcessingJourneyStatus())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getPosvJourneyStatus())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getApplicationDetails().getStage())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getBancaId())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmAccountNo()+"")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmAccountOpendate()+"")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmArea())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmBankBranchName())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmCity())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmCountry())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmCustomerFirstName())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmCustomerLastName())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmCustomerMiddleName())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmDob()+"")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmHouseNo())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmLandmark())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmPanCard())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmPinCode())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmState())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCrmBancaCustomerDetails().getCrmtitle())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCustomerClassification())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getCustomerId())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getIsCommAddressModified())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().isDOBModified()+"")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().isNameModified()+"")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().isPanModified())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getLeadId())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBancaDetails().getUkyc())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getBank().getPaymentRenewedBy())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getBranchCode())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getChannelDetails().getChannel())
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getAadhaarOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getArea() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCity() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getCountry() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getHouseNo() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getLandmark() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getState() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getVillage() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (!proposal.getPartyInformation().get(0).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(0).getAddressDetails().getVoterIdOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getAadhaarOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getArea() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getCity() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getCountry() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getHouseNo() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getLandmark() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getPinCode() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getState() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getVillage() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(0).getBasicDetails().getAddress().get(1).getAddressDetails().getVoterIdOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPartyInformation().isEmpty() ? (proposal.getPartyInformation().get(0).getPersonalIdentification().getPanDetails().getIsPanValidated()) : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getAadhaarOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getArea() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getCity() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getCountry() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getHouseNo() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getLandmark() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getState() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getVillage() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (!proposal.getPartyInformation().get(1).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(0).getAddressDetails().getVoterIdOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getAadhaarOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getArea() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getCity() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getCountry() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getHouseNo() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getLandmark() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getPinCode() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getState() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getVillage() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(1).getBasicDetails().getAddress().get(1).getAddressDetails().getVoterIdOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=2 ? (proposal.getPartyInformation().get(1).getPersonalIdentification().getPanDetails().getIsPanValidated()) : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getAadhaarOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getArea() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getCity() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getCountry() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getHouseNo() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getLandmark() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getPinCode() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getState() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getVillage() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (!proposal.getPartyInformation().get(2).getBasicDetails().getAddress().isEmpty() ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(0).getAddressDetails().getVoterIdOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getAadhaarOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getArea() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getCity() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getCountry() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getHouseNo() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getLandmark() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getPinCode() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getState() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getVillage() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getBasicDetails().getAddress().size() >= 2 ? proposal.getPartyInformation().get(2).getBasicDetails().getAddress().get(1).getAddressDetails().getVoterIdOcrStatus() :  "") : "")
//						+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getPartyInformation().size() >=3 ? (proposal.getPartyInformation().get(2).getPersonalIdentification().getPanDetails().getIsPanValidated()) : "")
//				
//				
//				
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).isSIOpted()+"" : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPaymentDate()+""+"" : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getPaymentDetails().getReceipt().isEmpty() ? proposal.getPaymentDetails().getReceipt().get(0).getPremiumMode()+""+"" : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCode() : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodeMFSA() : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodePOSV() : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getPlanCodeTPP() : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAfyp() : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getAtp()+"" : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductIllustrationResponse().getInitialPremiumPaid()+"" : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getProductDetails().isEmpty() ? proposal.getProductDetails().get(0).getProductInfo().getProductName() : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentBranchData())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentCode())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentEmail())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentId())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentJoiningDate()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentLicenseStartdate()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentLocation())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentMobileNumber()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentName())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentRole())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getAgentStatus())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getDesignation())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getGoCABrokerCode())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().isAgentValidated()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getPersistency()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getRegionalAdvisorId())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getReportingManagerCode())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getReportingManagerName()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSourcingBranchCode())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpStatus())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonCode())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getAmlStatus())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getAmlTrainingExpirationDate()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpCertificateNumber())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpCode())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpGoCABrokerCode())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpLicenseExpiryDate()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpLicenseStartDate()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpLocation())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpMobileNumber())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpName())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getSpSSNCode())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getUlipStatus())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonDetails().getUlipTrainingExpirationDate()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSpecifiedPersonName())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getSourcingDetails().getSsnCode())
//				+ StringUtility.checkStringNullOrBlankWithoutCase(proposal.getTransactionId()+"")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getClientId() : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getClientType() : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getDedupeFlag() : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPolicyStatus() : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPreviousPolicyNumber() + "" : "")
//				+ StringUtility.checkStringNullOrBlankWithoutCase(!proposal.getUnderwritingServiceDetails().getDedupeDetails().isEmpty() ? proposal.getUnderwritingServiceDetails().getDedupeDetails().get(0).getPrevpolicydate() + "" : "");
//				
//				
//				
//				data.add(row.split(Pattern.quote(",")));
//
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
//		} catch (Exception ex) {
//			context.getLogger().log("Exception occured while creating report for Axis Ebcc Report Data :: " + ex);
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
//
//}
