package com.maxlifeinsurance.mpro.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.service.AslAndPeerlessReportService;
import com.maxlifeinsurance.mpro.serviceimpl.AslAndPeerlessReportServiceImpl;

/**
 * 
 * @author Vinay
 * @version 1.0 Lambda handler for Asl-Peerless Data
 */
public class AslAndPeerlessReportGenerationHandler {

	AslAndPeerlessReportService aslAndPeerlessReportService = new AslAndPeerlessReportServiceImpl();

	public void handleRequest(Context context) {
		context.getLogger().log("Asl & PeerLess report handler is starting...");
		boolean status = aslAndPeerlessReportService.generateAslAndPeerlessReport(context);
		context.getLogger().log("Asl & PeerLess report generation status is :: " + status);
	}

}
