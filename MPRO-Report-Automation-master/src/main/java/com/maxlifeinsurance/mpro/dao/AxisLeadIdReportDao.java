package com.maxlifeinsurance.mpro.dao;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dto.Proposal;
/**
 * 
 * @author Vinay
 *
 */
public interface AxisLeadIdReportDao {
	public List<Proposal> getAxisLeadIdReportData(Context context);
}
