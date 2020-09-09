package com.maxlifeinsurance.mpro.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.ScbReportService;
import com.maxlifeinsurance.mpro.serviceimpl.ScbReportServiceImpl;

/**
 * 
 * @author Vinay
 * @version 1.0 Lambda handler for Scb Data
 */
public class ScbReportGenerationHandler {
	ScbReportService scbReportService = new ScbReportServiceImpl();

	public void handleRequest(Context context) {
		context.getLogger().log("SCB report handler is starting...");
		boolean response = scbReportService.generateScbReport(context);
		context.getLogger().log("SCB report generation status is :: " + response);
	}

}
