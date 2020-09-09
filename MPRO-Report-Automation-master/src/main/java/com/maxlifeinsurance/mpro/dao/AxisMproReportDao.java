package com.maxlifeinsurance.mpro.dao;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dto.Proposal;

/**
 * 
 * @author Vinay
 *
 */
public interface AxisMproReportDao {

	/**
	 * 
	 * @param context
	 * @return Implememtation on this method is done AxisMproReportDaoImpl
	 *         class, this method will fetch data from mongo database against
	 *         query for axis mpro report
	 */
	public List<Proposal> getAxisMproReportData(Context context);

}
