package com.maxlifeinsurance.mpro.dao;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;

public interface AxismAppReportDao {

	public List getAllGridReportData(Context context);

}
