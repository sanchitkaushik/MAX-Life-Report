package com.maxlifeinsurance.mpro.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.AxisLeadIdReportService;
import com.maxlifeinsurance.mpro.serviceimpl.AxisLeadIdReportServiceImpl;

/**
 * 
 * @author Vinay
 * @version 1.0 Lambda handler for Axis-Mpro-LeadId
 */
public class AxisLeadIdReportGenerationHandler {

	AxisLeadIdReportService axisLeadIdReportService = new AxisLeadIdReportServiceImpl();

	public void handleRequest(Context context) {
		context.getLogger().log("Axis-Mpro-LeadId report handler is starting...");
		boolean response = axisLeadIdReportService.generateAxisLeadIdReport(context);
		context.getLogger().log("Axis-Mpro-LeadId report generation status is :: " + response);
	}
}
