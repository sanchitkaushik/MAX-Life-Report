package com.maxlifeinsurance.mpro.serviceimpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxlifeinsurance.mpro.service.CommunicationService;
import com.maxlifeinsurance.mpro.utils.Constants;

/**
 * 
 * @author Vinay
 *
 */
public class CommunicationServiceImpl implements CommunicationService {

	static ResourceBundle res = ResourceBundle.getBundle("application");
	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * @param PIMproSellerTransaction
	 * @param context
	 *            This method will send short link to customer over Email return
	 *            boolean
	 */
	@Override
	public boolean sendEmailToUser(String xlsFile, String userEmailId, String reportName, Context context) {
		String output = "";
		StringBuilder result = new StringBuilder();
		OutputStreamWriter writer = null;
		String mailUrl = "";
		URL url = null;
		HttpURLConnection conn = null;
		String mailBody = "";
		BufferedReader br = null;
		try {
			mailBody = createEmailBodyText(xlsFile, userEmailId, reportName);
			context.getLogger().log("SOA Mail API Request " + mailBody);
			mailUrl = res.getString(Constants.EMAIL_API_URL);
			url = new URL(mailUrl);
			conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");

			writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(mailBody);
			writer.flush();
			int apiResponseCode = conn.getResponseCode();
			if (apiResponseCode == 200) {
				br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				while ((output = br.readLine()) != null) {
					result.append(output);
				}
				conn.disconnect();
				br.close();
			} else {
				br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
				while ((output = br.readLine()) != null) {
					result.append(output);
				}
				conn.disconnect();
				br.close();
			}
			context.getLogger().log("SOA MAIL API Response " + result);
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e1) {
				context.getLogger().log("Exception while closing writer object " + e1.getMessage());
			}
		}
		return true;
	}

	private String createEmailBodyText(String xlsFile, String userEmailId, String reportName) {

		double randomNumber = Math.random();
		xlsFile = xlsFile.replaceAll("\n", "");
		StringBuilder mailReq = new StringBuilder();
		mailReq.append("{");
		mailReq.append("\"request\": {");
		mailReq.append("\"header\": {");
		mailReq.append("\"soaCorrelationId\": \"" + randomNumber + "\",");
		mailReq.append("\"soaAppId\": \"POS\"");
		mailReq.append("},");
		mailReq.append("\"requestData\": {");
		mailReq.append("\"mailIdTo\": \"" + userEmailId.toLowerCase() + "\",");
		mailReq.append("\"mailSubject\": \"mPRO- " + reportName + "\",");
		mailReq.append("\"fromName\": \"Maxlife Insurance\",");
		mailReq.append("\"attachments\": [{");
		mailReq.append("\"fileName\": \"" + reportName + ".zip\",");
		mailReq.append("\"byteArrayBase64\": \"" + xlsFile + "\"}");
		mailReq.append("],");
		mailReq.append("\"isConsolidate\":false,");
		mailReq.append("\"isFileAttached\":true,");
		mailReq.append("\"fromEmail\": \"DoNotReply@maxlifeinsurance.com\",");

		mailReq.append("\"mailBody\": \"").append("<html><body>Hi All, <br><br/>Please find the attachment of mPRO-"
				+ reportName + "." + "<br/><br><br/><br>Regards,<br/><br>Max Life Insurance<br/></body></html>")
				.append("\"");

		mailReq.append("}");
		mailReq.append("}");
		mailReq.append("}");
		return mailReq.toString();
	}
}
