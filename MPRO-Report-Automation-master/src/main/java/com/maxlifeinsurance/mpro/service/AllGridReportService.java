package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface AllGridReportService {
	
	public boolean generateAllGridDataReport(Context context);

}
