package com.maxlifeinsurance.mpro.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.ModeOfPaymentReportService;
import com.maxlifeinsurance.mpro.serviceimpl.ModeOfPaymentReportServiceImpl;

/**
 * 
 * @author Vinay
 * @version 1.0 Lambda handler for Mode-Of-Payment
 */
public class ModeOfPaymentReportGenerationHandler {

	ModeOfPaymentReportService modeOfPaymentReportService = new ModeOfPaymentReportServiceImpl();

	public void handleRequest(Context context) {
		context.getLogger().log("Mode-Of-Payment report handler is starting...");
		boolean response = modeOfPaymentReportService.generateModeOfPaymentReport(context);
		context.getLogger().log("Mode-Of-Payment report generation status is :: " + response);
	}

}
