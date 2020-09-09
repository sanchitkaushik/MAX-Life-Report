package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface AxisEbccReportService {
	
	public boolean generateAxisEbccReport(Context contex);

}
