package com.maxlifeinsurance.mpro.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.AxisMproReportService;
import com.maxlifeinsurance.mpro.serviceimpl.AxisMproReportServiceImpl;

/**
 * 
 * @author Vinay This class act as a lambda handler for Axis-Mpro-Report
 */
public class AxisMproReportGenerationHandler {

	AxisMproReportService axisMproReportService = new AxisMproReportServiceImpl();

	public void handleRequest(Context context) {
		context.getLogger().log("MPRO-AXIS report handler is starting...");
		boolean response = axisMproReportService.axisMproReportGenerationService(context);
		context.getLogger().log("MPRO-AXIS report generation status is :: " + response);
	}

}
