package com.maxlifeinsurance.mpro.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.AxismAppReportService;
import com.maxlifeinsurance.mpro.serviceimpl.AxismAppReportServiceImpl;

public class AxismAppReportGenerationHandler {

	AxismAppReportService axismAppReportService = new AxismAppReportServiceImpl();

	public void handleRequest(Context context) {
		context.getLogger().log("All-Grid-Data report handler is starting...");
		Boolean response = axismAppReportService.generateAxismAppReport(context);
		context.getLogger().log("All-Grid-Data report generation status is :: " + response);
	}

}
