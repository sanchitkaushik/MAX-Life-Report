package com.maxlifeinsurance.mpro.utils;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.bson.internal.Base64;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.CommunicationService;
import com.maxlifeinsurance.mpro.serviceimpl.CommunicationServiceImpl;
import com.opencsv.CSVWriter;

import net.lingala.zip4j.core.ZipFile;

/**
 * 
 * @author Vinay
 * @version 1.0
 */

public class CsvWriteer {
	static ResourceBundle res = ResourceBundle.getBundle("application");
	static File file = null;
	static ZipFile zipFile = null;
	static String fileData = null;
	static byte[] xmlFileByteArray = null;
	static boolean flag = false;

	
	/**
	 * 
	 * @param reportData
	 * @param reportName
	 * @param reportHeaderKey
	 * @param context
	 * @return flag
	 * 
	 *         This method will create CSV from data fetched from database and
	 *         mail that CSV also save that CSV on S3 Bucket respectively with
	 *         there reports
	 * 
	 */
	public static boolean csvWriter(List<String[]> reportData, String reportName, String s3SubFolderName, String reportHeaderKey, Context context) {
		double sizeInMb = 0;
		FileWriter outputfile = null;
		CSVWriter writer = null;
		CommunicationService communicationService = null;
		try {
			communicationService = new CommunicationServiceImpl();
			String bucketName = res.getString(Constants.S3BUCKETNAME);
			context.getLogger().log("S3 Bucket name for storing file is :: "+bucketName);
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
			LocalDateTime toDate = LocalDateTime.now();
			file = new File("/tmp" + File.separator + reportName + (dateTimeFormatter.format(toDate)) + ".csv");
			context.getLogger().log("File Path" + file.getAbsolutePath());
			outputfile = new FileWriter(file);
			writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<>();
			data.add(res.getString(reportHeaderKey).split(Pattern.quote("||")));
			data.addAll(reportData);
			writer.writeAll(data);
			writer.flush();
			context.getLogger().log("CSV Created Successfully");
			zipFile = ReportCreationUtility.passwordProtectedZip(file, context);
			if (zipFile != null) {
				context.getLogger().log("Converting csv file to password protected zip file :: " + zipFile);
				xmlFileByteArray = ReportCreationUtility.convertExcelIntoByteArray(zipFile.getFile(), context);
			}
			fileData = Base64.encode(xmlFileByteArray);
			long sizeInByte = fileData.length();
			sizeInMb = sizeInByte / (1024.00 * 1024.00);
			context.getLogger().log("Size of file is :: " + sizeInMb);
			flag = AWSClients.saveFileToS3(s3SubFolderName,file, bucketName, context);
			if (sizeInMb < 12.0 && communicationService.sendEmailToUser(fileData, System.getenv(Constants.MAIL_TO), reportName, context)) {
				context.getLogger().log("Mail sent successfully");
				flag = true;
			} else if (sizeInMb > 0.0 && bucketName!= null && !bucketName.isEmpty()) {
				flag = AWSClients.saveFileToS3(s3SubFolderName,file, bucketName, context);	
			}
		} catch (Exception ex) {
			context.getLogger().log("Exception occured while creating CSV :: " + ex);
		} finally {
			try {
				if (outputfile != null) {
					outputfile.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (Exception ex) {
				context.getLogger().log("Exception occured while closing object :: " + ex.getMessage());
			}
		}
		return flag;
	}

}
