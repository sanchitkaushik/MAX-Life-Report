package com.maxlifeinsurance.mpro.utils;

public class StringUtils {

	private StringUtils() {}
	public static String isNullOrEmpty(String object) {
		if(object!=null&&!object.isEmpty()) {
			return  object;
		}else {
			return "";
		}
	}
	
	
	
}
