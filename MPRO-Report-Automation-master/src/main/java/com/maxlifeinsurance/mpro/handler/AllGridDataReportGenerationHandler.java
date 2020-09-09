package com.maxlifeinsurance.mpro.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.AllGridReportService;
import com.maxlifeinsurance.mpro.serviceimpl.AllGridReportServiceImpl;

/**
 * 
 * @author Sanchit
 * @version 1.0 Lambda handler for All-Grid-Data-Report
 */
public class AllGridDataReportGenerationHandler {

	AllGridReportService allGridReportService = new AllGridReportServiceImpl();

	public void handleRequest(Context context) {
		context.getLogger().log("Axis-mApp report handler is starting...");
		Boolean response = allGridReportService.generateAllGridDataReport(context);
		context.getLogger().log("Axis-mApp report generation status is :: " + response);
	}

}
