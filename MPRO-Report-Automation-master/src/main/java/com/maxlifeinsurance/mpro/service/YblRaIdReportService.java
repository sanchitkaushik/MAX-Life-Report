package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface YblRaIdReportService {
	
	public boolean generateYblRaIdReport(Context context);

}
