package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface AxisLeadIdReportService {

	public boolean generateAxisLeadIdReport(Context context);
}
