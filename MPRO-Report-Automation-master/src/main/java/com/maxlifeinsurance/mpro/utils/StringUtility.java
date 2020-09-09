package com.maxlifeinsurance.mpro.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

import com.amazonaws.services.lambda.runtime.Context;

public class StringUtility {
	static String channelForSelfie=System.getenv("channelForSelfie");
	
	private StringUtility() {}
	public static String checkStringNullOrBlank(String s) {

		if (s != null && !s.equals("") && !s.equalsIgnoreCase("null")) {
			return s.trim().toUpperCase();
		} else {
			return "";
		}
	}


	public static String checkStringNullOrBlankWithoutCase(String s) {
		if (s != null && !s.equals("") && !s.equalsIgnoreCase("null")) {
			return s.trim();
		} else {
			return "";
		}
	}
	public static boolean checkFieldIsNull(String fieldValue) 
	{
		if(fieldValue==null ||"null".equals(fieldValue.trim()) || "".equals(fieldValue.trim())){
			return true;
		}
		return false;
	}


	public static void callHttp(
			String extURL,
			String requestData, 
			StringBuilder result,String posvRefNumber,
			Context context
			){
		try{
			context.getLogger().log("API Processing start with Posv RefNumber "+posvRefNumber);
			String output;
			URL url = new URL(extURL);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			try (AutoCloseable conc = () -> httpUrlConnection.disconnect()) {
				HttpsURLConnection.setFollowRedirects(true);
				httpUrlConnection.setDoInput(true);
				httpUrlConnection.setDoOutput(true);
				httpUrlConnection.setRequestMethod("POST");
				httpUrlConnection.setRequestProperty("Content-Type", "application/json");

				try(OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpUrlConnection.getOutputStream()))
				{
					outputStreamWriter.write(requestData);
					outputStreamWriter.flush();
				}

				int apiResponseCode = httpUrlConnection.getResponseCode();
				try (InputStream ins = apiResponseCode >= 400 ? httpUrlConnection.getErrorStream() : httpUrlConnection.getInputStream();
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ins))) 
				{
					while ((output = bufferedReader.readLine()) != null) {
						result.append(output);
					}
				}
			}
			context.getLogger().log("API RESPONSE -- " + result.toString());
		}catch(Exception e){
			context.getLogger().log("Exception Occoured While Calling API's " + e);
		}
	}

	public static int sendOTPOnCall(String mobile,String otp,Context context) {
		StringBuilder result = new StringBuilder();
		BufferedReader br=null;
		HttpURLConnection conn=null;
		int apiResponseCode=0;
		try {
			context.getLogger().log("ON Call OTP API Process : Start");
			String otpURL = "https://voice.httpapi.zone/voiceapi/mliapi_otp.php?campaignid=42570&account=MLIAPIVOIC&pin=In(ts)76$&msisdn="+mobile+"&msgText=Dear%20Customer,%20OTP%20for%20your%20Max%20Life%20policy%20is%20"+otp+"%20Press%201%20to%20listen%20again";
			context.getLogger().log("token Url  : "+otpURL);
			URL url = new URL(otpURL);
			conn = (HttpURLConnection) url.openConnection();
			HttpsURLConnection.setFollowRedirects(true);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("GET");
			apiResponseCode = conn.getResponseCode();
			context.getLogger().log("ON Call OTP API Response Code :: " + apiResponseCode);
			String output;
			if (apiResponseCode == 200) {
				 br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			}else{
				br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
			  }
			while ((output = br.readLine()) != null){
				result.append(output);
			}
		   context.getLogger().log("Get OTP On Call API Response :"+result);
		} catch (Exception e) {
			context.getLogger().log("Exception ON Call OTP API "+e.getMessage());
		}
		finally{
			try {
				if(br!=null) {
				  br.close();
				 }
				if(conn!=null) {
				conn.disconnect();
				}
			}catch(Exception e) {
				context.getLogger().log("Exception in closing conection object ON Call OTP API "+e.getMessage());
			}
		}
		context.getLogger().log("ON Call OTP API Process : End");
		return apiResponseCode;
	}
	
	public static StringBuilder getRequestData(String posvRefNumber, String soaAppId) {
		StringBuilder requestData = new StringBuilder();
		
		requestData.append("	{	");
		requestData.append("	   \"request\": {	");
		requestData.append("	   \"header\": {	");
		requestData.append("	      \"soaCorrelationId\": \""+posvRefNumber+"\",	");
		requestData.append("	      \"soaAppId\": \""+soaAppId+"\"	");
		requestData.append("	   },	");
		requestData.append("	   \"payload\": {	");
		requestData.append("	      \"unqTokenNo\": \"\",	");
		requestData.append("	      \"stepId\": \"\"	");
		requestData.append("	   }}}");
		return requestData;
	}
	
     public static boolean  getSelfieAccess(String channel,Context context){
		
		boolean status=false;
		try {
		if(channel!=null && !channel.isEmpty()) {
		String channelList=channelForSelfie;
		String channels[]=channelList.split(",");
		for(int i=0;i<channels.length;i++){
			if(channel.equalsIgnoreCase(channels[i])) {
				status=true;
				break;
			}
		}
		System.out.println("status "+status);
		}
		}catch(Exception e) {
			context.getLogger().log("Creating Exception in getSelfieAccess "+e.getMessage());
		}
		
		return status;
     }
     
     public static String sortPlanCode(String inputPlan) 
     { 
         // convert input string to char array 
         char tempArray[] = inputPlan.toCharArray(); 
           
         // sort tempArray 
         Arrays.sort(tempArray); 
           
         // return new sorted string 
         return new String(tempArray); 
     }
	
}
