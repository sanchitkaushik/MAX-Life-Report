package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface EnachBnplReportService {

	public boolean enachBnplReportGenerationService(Context context);

}
