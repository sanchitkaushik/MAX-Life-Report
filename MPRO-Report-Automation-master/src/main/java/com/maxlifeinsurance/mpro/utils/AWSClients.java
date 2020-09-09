package com.maxlifeinsurance.mpro.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

public class AWSClients {

	public static final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();

	public static AmazonS3 getS3client() {
		return s3Client;
	}

	public static String uploadFileToS3(String objectkey, String bucketName, InputStream input,
			ObjectMetadata metadata) {

		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectkey, input, metadata);
		PutObjectResult result = s3Client.putObject(putObjectRequest);
		return result.getETag();
	}

	public static boolean saveFileToS3(String subFolderName,File xlsFile, String bucketName, Context context) {
		boolean flag = false;
		try {
			context.getLogger().log("File Name :: " +xlsFile.getName());
			String key = AWSClients.uploadFileToS3(subFolderName + File.separator + xlsFile.getName(), bucketName, new FileInputStream(xlsFile), null);
//			String key = AWSClients.uploadFileToS3(xlsFile.getName(), bucketName, new FileInputStream(xlsFile), null);
			context.getLogger().log("Key -> " + key);
			Boolean deletedStatus = xlsFile.delete();
			flag = true;
			context.getLogger().log("File Deletion Status :: " + deletedStatus);
			context.getLogger().log("File is saved on S3 " + bucketName);
		} catch (Exception e) {
			context.getLogger().log("Exception while uploading file to S3" + e);
		}
		return flag;

	}

}
