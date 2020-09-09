package com.maxlifeinsurance.mpro.service;

import com.amazonaws.services.lambda.runtime.Context;

public interface AxismAppReportService {
	
	public boolean generateAxismAppReport(Context context);

}
