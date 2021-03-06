package com.maxlifeinsurance.mpro.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.amazonaws.services.lambda.runtime.Context;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * This class contains utility method which are used to convert file into byte
 * array, convert file in password protected zip file
 * 
 * @author Vinay
 * 
 */
public class ReportCreationUtility {

	/**
	 * This is static method used to convert file in byte array.
	 * 
	 * @param source
	 * @param context
	 * @return byte[] file byte array
	 */
	public static byte[] convertExcelIntoByteArray(File source, Context context) {
		InputStream inputStream = null;
		ByteArrayOutputStream outputStream;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new ByteArrayOutputStream();

			int data;
			while ((data = inputStream.read()) >= 0) {
				outputStream.write(data);
			}

		} catch (Exception e) {
			context.getLogger().log("Exception in converting csv to byte array :: " + e.getMessage());
			return null;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception ex) {
				context.getLogger().log("Exception in closing connection :: " + ex.getMessage());
			}
		}
		return outputStream.toByteArray();
	}

	/**
	 * This method will convert the CSV file in zip file so that it can be send
	 * on mail
	 * 
	 * @param xlsFile
	 * @param context
	 * @return zipFile
	 */
	public static ZipFile passwordProtectedZip(File xlsFile, Context context) {
		File file = xlsFile;
		String zipFilePath = null;
		ZipFile zipFile = null;
		String fileName = null;
		String zipFileName = null;
		ZipParameters zipParameters = new ZipParameters();
		try {
			if (file.exists() && file != null) {
				fileName = file.getName();
				zipFileName = fileName.replace("csv", "zip");
				context.getLogger().log("Received valid CSV file");
				zipFilePath = "/tmp" + File.separator + zipFileName;
				zipFile = new ZipFile(zipFilePath);
				setZipParams(zipParameters, context);
				zipFile.addFile(file, zipParameters);
			} else {
				context.getLogger().log("Exception :: Received invalid csv file");
			}
		} catch (Exception e) {
			context.getLogger().log("Exception :: while making password protected zip file " + e.getMessage());
		}
		return zipFile;
	}

	/**
	 * This is private method used by passwordProtectedMethod to create password
	 * on zip file
	 * 
	 * @param zipParameters
	 * @param context
	 * 
	 */
	private static void setZipParams(ZipParameters zipParameters, Context context) {
		zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		zipParameters.setEncryptFiles(true);
		zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		zipParameters.setPassword("max2020");
		context.getLogger().log("added zip parameters");
	}

}
