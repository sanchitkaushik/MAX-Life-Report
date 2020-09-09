package com.maxlifeinsurance.mpro.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.SMTPReportService;
import com.maxlifeinsurance.mpro.serviceimpl.SMTPReportServiceImpl;

/**
 * 
 * @author Vinay
 * @version 1.0 Lambda handler for SMTP
 */
public class SMTPReportGenerationHandler {

	SMTPReportService smtpReportService = new SMTPReportServiceImpl();

	public void handleRequest(Context context) {
		context.getLogger().log("SMTP report handler is starting...");
		boolean response = smtpReportService.generateSMTPReport(context);
		context.getLogger().log("SMTP report generation status is :: " + response);
	}

}
