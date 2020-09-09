package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface SMTPReportService {
	
	public boolean generateSMTPReport(Context context);

}
