package com.llr.Cases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.llr.AssertUtil;
import com.llr.DataBaseUtil;
import com.llr.ExcelUtil;
import com.llr.GetDataUtil;
import com.llr.HttpUtil;
import com.llr.WriteDataToExcel;
import com.llr.Inteface.AfterVerfityResult;
import com.llr.Inteface.VerifySql;
import com.llr.Inteface.WriteData;

public class NewTest {
	 public static String[] params = {
				"Mobile","ApiId","CaseId","ExpctedResponseData","VerifySql","AfterVerifySql" 
			  };
	
	@Test(dataProvider="datas")
	  public void f(String params, String apiid,String caseId,String expctedResponseData,String verifySql,String afterVerifySql) {
		  //��ȡsql��䣬ִ��sql���
//		DataBaseUtil.Query(sql)
//		 System.out.println("======================");
//		  System.out.println(verifySql);
//		  System.out.println("======================");
//		  System.out.println(afterVerifySql);
//		  System.out.println("params:"+params);
		
//		  QueryAndWrite(verifySql, apiid, "VerifySqlResult","��������");
//		  QueryAndWrite(verifySql);
		  ExcelUtil.writeDataList.add(new WriteData("VerifySqlResult",caseId,QueryAndWrite(verifySql),"��������"));
		  String[] param = {"url","type"};
		  String[] result = GetDataUtil.getInfoByApiId(apiid, param);
		  String url = result[0];
		  String type = result[1];
//		  System.out.println("url:"+url +",type:"+ type);
		 
		  for (int i = 0; i < ExcelUtil.paramList.size(); i++) {
			if(params.contains(ExcelUtil.paramList.get(i).getKey())){
				params =params.replace(ExcelUtil.paramList.get(i).getKey(), ExcelUtil.paramList.get(i).getValue());
				expctedResponseData =expctedResponseData.replace(ExcelUtil.paramList.get(i).getKey(), ExcelUtil.paramList.get(i).getValue());
				 
			}
		  }
		  System.out.println(params);
		  Map<String, String> map= (Map<String, String>) JSONObject.parse(params);
		  String response = HttpUtil.toSerivce(type, url, map);
		  System.out.println("response:"+response);
		  response = AssertUtil.AssertResult(expctedResponseData,response);
		  
		
		  System.out.println("�ȶԽ����"+response);
		  ExcelUtil.writeDataList.add(new WriteData("ActuallResponseData",caseId,response,"��������"));
//		  QueryAndWrite(afterVerifySql);
		  ExcelUtil.writeDataList.add(new WriteData("AfterVerifySqlResult",caseId,QueryAndWrite(afterVerifySql),"��������"));
	  }
	
	
	public String QueryAndWrite(String verifySql){
		String result="";
		if(verifySql!=null && verifySql!=""){
			 List<VerifySql> listsql =JSONObject.parseArray(verifySql, VerifySql.class);
			 List<AfterVerfityResult> resultList = new ArrayList<AfterVerfityResult>();
			 for (VerifySql verifySql2 : listsql) {				
				List<Map<String, String>> list = DataBaseUtil.Query(verifySql2.getSql());
				String json =  JSONObject.toJSONString(list);
				resultList.add(new AfterVerfityResult(verifySql2.getNo(),json));
//				ExcelUtil.resultList.add(new AfterVerfityResult(verifySql2.getNo(),json.toString(),apiid,cellName,sheetName));

//				if(ExcelUtil.resultList==null){
//									}
//				System.out.println(json.toString());
//				result = json.toString();
			}
			 result = JSONObject.toJSONString(resultList);
		}
		System.out.println(result);
		return result;
	}
	
	@AfterSuite
	public void RollBackToExcel(){
		WriteDataToExcel.WriteDataList();
	}
}
