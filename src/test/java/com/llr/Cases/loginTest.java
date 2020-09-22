package com.llr.Cases;

import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.llr.GetDataUtil;
import com.llr.HttpUtil;

public class loginTest extends NewTest{
//  @Test(dataProvider="datas")
//  public void f(String params, String apiid,String caseId) {
////	  System.out.println(params);
//	  String[] param = {"url","type"};
//	  String[] result = GetDataUtil.getInfoByApiId(apiid, param);
//	  String url = result[0];
//	  String type = result[1];
//	  System.out.println(url +""+ type);
//	  Map<String, String> map= (Map<String, String>) JSONObject.parse(params);
//	  String response = HttpUtil.toSerivce(type, url, map);
//	  System.out.println(response);
////	  HttpUtil.
//  }
  
  @DataProvider
  public Object[][] datas(){
	 
	  return GetDataUtil.getDataByApiId("1",params);
  }
}
