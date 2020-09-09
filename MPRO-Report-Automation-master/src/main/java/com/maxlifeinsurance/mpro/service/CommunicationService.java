package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface CommunicationService {

	public boolean sendEmailToUser(String xlsFile, String userEmailId, String reportName, Context context);

}
