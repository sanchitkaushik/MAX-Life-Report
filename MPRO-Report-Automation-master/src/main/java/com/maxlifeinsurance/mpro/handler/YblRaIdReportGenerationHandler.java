package com.maxlifeinsurance.mpro.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.YblRaIdReportService;
import com.maxlifeinsurance.mpro.serviceimpl.YblRaIdReportServiceImpl;

/**
 * 
 * @author Vinay
 * @version 1.0 Lambda handler for YBL-RA-ID-Report
 */
public class YblRaIdReportGenerationHandler {
	YblRaIdReportService yblRaIdReportService = new YblRaIdReportServiceImpl();

	public void handleRequest(Context context) {
		context.getLogger().log("YBL-RA-ID-MPRO handler is starting...");
		boolean response = yblRaIdReportService.generateYblRaIdReport(context);
		context.getLogger().log("YBL-RA-ID-MPRO report generation status is :: " + response);
	}

}
