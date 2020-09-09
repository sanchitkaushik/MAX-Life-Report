package com.maxlifeinsurance.mpro.config;

import com.maxlifeinsurance.mpro.dao.AxisMproYtdReportDao;
import com.maxlifeinsurance.mpro.daoimpl.AxisMproYtdReportDaoImpl;

public class ApplicationStartUp {
	
	public static void main(String[] args) {
		AxisMproYtdReportDao YblRaIdReportDao = new AxisMproYtdReportDaoImpl();
		YblRaIdReportDao.getAxisYtdReportData(null);
	}
}
