package com.maxlifeinsurance.mpro.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.EnachBnplReportService;
import com.maxlifeinsurance.mpro.serviceimpl.EnachBnplReportServiceImpl;

/**
 * 
 * @author Vinay
 * @version 1.0 Lambda handler for Enach-Bnpl
 */
public class EnachBnplReportGenerationHandler {

	EnachBnplReportService enachBnplReportService = new EnachBnplReportServiceImpl();

	public void handleRequest(Context context) {
		context.getLogger().log("ENACH-BNPL report handler is starting...");
		boolean response = enachBnplReportService.enachBnplReportGenerationService(context);
		context.getLogger().log("ENACH-BNPL report generation status is :: " + response);
	}

}
