package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface AxisMproYtdReportService {
	
	public boolean generateAxisMproYtdReport(Context context);

}
