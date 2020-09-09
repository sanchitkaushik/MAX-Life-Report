package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface ScbReportService {
	
	public boolean generateScbReport(Context context);

}
