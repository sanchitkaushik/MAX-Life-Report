package com.maxlifeinsurance.mpro.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * This class contains static utility methods which are used to format date as
 * per requirement
 * 
 * @author Qualtech
 *
 */
public class MultiFormatDate {

	/**
	 * This is static method used to format date in this format
	 * yyyy-MM-dd'T'HH:mm:ss.SSS'Z' example 2020-07-01T15:04:00.000Z
	 * 
	 * @param requestDate
	 * @return
	 */
	public static String formatDateInMongoDateFormat(Date requestDate) {
		String mongoDate = "";
		try {
			if (requestDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
				Date date = sdf.parse(requestDate.toString());
				SimpleDateFormat mongpDtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				mongoDate = mongpDtFormat.format(date);
			}

		} catch (Exception e) {

		}
		return mongoDate;
	}

	/**
	 * This is static method method used to format string date in this format
	 * 2020/03/13
	 * 
	 * @param context
	 * @param dateInString
	 * @return
	 */
	public static Date formatDateForQuery(Context context, String dateInString) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			date = sdf.parse(dateInString);
			// context.getLogger().log("Date after parsing is ::
			// "+date.toString());
		} catch (Exception e) {
			context.getLogger().log("Exception occured in while formating  date " + e + "Input date is :: " + dateInString);
		}
		return date;
	}

	/**
	 * This static method used to format date string in date in this format
	 * 2020/09/28
	 * 
	 * @param context
	 * @param minusDate
	 * @return date
	 */
	public static Date getPreviousDate(Context context, int minusDate) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(LocalDate.now().minusDays(minusDate).toString());
		} catch (Exception e) {

		}
		return date;
	}

	/**
	 * This is static method used get 7 day previous date in format 2020/09/12
	 * 
	 * @param context
	 * @return date
	 */
	public static Date getPreviousMonthDate(Context context) {
		Date date = null;
		try {

			Calendar clr = Calendar.getInstance();
			int year = clr.get(Calendar.YEAR);
			int month = clr.get(Calendar.MONTH);
			if (month == 0) {
				month = 1;
			}
			date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String dateInString = year + "/" + (month) + "/" + "1";
			date = sdf.parse(dateInString);
			context.getLogger().log("Date for previous month is :: " + date.toString());
		} catch (Exception ex) {
			context.getLogger().log("Exception occured while fetching previous month date :: " + ex);
		}
		return date;
	}

	/**
	 * This is static method used get 7 day previous date in format 2020/09/12
	 * 
	 * @param context
	 * @return date
	 */
	public static Date get7DaysPreviousDate(Context context) {
		Date date = null;
		try {

			Calendar clr = Calendar.getInstance();
			clr.add(Calendar.DATE, -7);
			int year = clr.get(Calendar.YEAR);
			int month = clr.get(Calendar.MONTH);
			int day = clr.get(Calendar.DATE);
			date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String dateInString = year + "/" + (month + 1) + "/" + (day + 1);
			date = sdf.parse(dateInString);
		} catch (Exception ex) {

		}
		return date;
	}

}
