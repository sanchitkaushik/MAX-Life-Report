package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface AslAndPeerlessReportService {
	
	public boolean generateAslAndPeerlessReport(Context context);

}
