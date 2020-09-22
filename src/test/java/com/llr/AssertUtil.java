package com.llr;


import org.apache.log4j.Logger;
import org.testng.Assert;

public class AssertUtil {

	public static Logger logger= Logger.getLogger(AssertUtil.class);
	
	public static void main(String[] args) {
		AssertResult("{\"message\": {\"code\": \"Invalid params.\"}}","{\"message\": {\"code\": \"Invalid params.\"}}");
	}
	
	public static String AssertResult(String expctedResponseData, String response) {
		// TODO Auto-generated method stub
		String result = "";
		logger.info("expctedResponseData:"+expctedResponseData.trim());
		logger.info("response:"+response.trim());
//		System.out.println("expctedResponseData:"+expctedResponseData.trim());
//		System.out.println("response:"+response.trim());
		boolean flag = expctedResponseData.trim().equals(response.trim());
		
		try {
			if(flag)
				result = "pass";
			else
				result = response;
		} catch (Error e) {
			// TODO: handle exception
			result = response;
		}
		logger.info("比对结果1："+result);
//		System.out.println("比对结果1："+result);
		return result;
	}

}
