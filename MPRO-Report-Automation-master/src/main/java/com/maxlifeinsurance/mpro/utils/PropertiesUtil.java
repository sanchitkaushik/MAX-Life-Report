package com.maxlifeinsurance.mpro.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PropertiesUtil {

	private static Map<String, String> lambdaProperties = new HashMap<>();

	public static void readPropertiesFile() {
		ResourceBundle rb = ResourceBundle.getBundle("lambda");
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = rb.getString(key);
			lambdaProperties.put(key, value);
		}
	}

	public static String getProperty(String key) {
		if (lambdaProperties.isEmpty())
			readPropertiesFile();
		return lambdaProperties.get(key);

	}
}
