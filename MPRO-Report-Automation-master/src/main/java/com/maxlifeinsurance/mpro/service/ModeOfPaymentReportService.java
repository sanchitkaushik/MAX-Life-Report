package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface ModeOfPaymentReportService {

	public boolean generateModeOfPaymentReport(Context contex);

}
