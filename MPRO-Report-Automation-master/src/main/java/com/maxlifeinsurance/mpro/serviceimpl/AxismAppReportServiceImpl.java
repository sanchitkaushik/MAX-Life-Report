package com.maxlifeinsurance.mpro.serviceimpl;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.AxismAppReportDao;
import com.maxlifeinsurance.mpro.daoimpl.AxismAppReportDaoImpl;
import com.maxlifeinsurance.mpro.service.AxismAppReportService;
import com.maxlifeinsurance.mpro.utils.Constants;
import com.maxlifeinsurance.mpro.utils.CsvWriteer;

public class AxismAppReportServiceImpl implements AxismAppReportService {

	@Override
	public boolean generateAxismAppReport(Context context) {

		boolean flag = false;
		AxismAppReportDao axismAppReportDao = null;
		// List<String[]> result = null;
		List<String[]> rowData = null;
		try {
			axismAppReportDao = new AxismAppReportDaoImpl();
			rowData = axismAppReportDao.getAllGridReportData(context);

			flag = CsvWriteer.csvWriter(rowData, Constants.ALL_GRID_DATA, Constants.ALL_GRID_FOLDER,
					"report.file.headers.allGridData", context);

		} catch (Exception e) {
		}
		return false;
	}

}
