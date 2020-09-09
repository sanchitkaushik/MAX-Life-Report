package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * 
 * @author Vinay
 *
 */
public interface AxisMproReportService {

	/**
	 * 
	 * @param context
	 * @return 
	 * Implementation of this method is in AxisMproReportServiceImpl, It
	 * will get data from dao layer than it will create csv file in
	 * respective template for axis-mpro and send on mail as well as
	 * save on s3
	 */
	public boolean axisMproReportGenerationService(Context context);

}
