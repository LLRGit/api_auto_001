package com.llr;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.llr.Inteface.Case;
import com.llr.Inteface.InterfaceInfo;
import com.llr.Inteface.VerifySql;

public class GetDataUtil {
	
	public static void main(String[] args) {
//		String[] params =  {"Mobile","ApiId","CaseId","ExpctedResponseData","VerifySql","AfterVerifySql" };
//		Object[][] oj = getDataByApiId("1",params);
//		
//		System.out.println("================");
//		String[] param = {
//			"url","type"
//		};
//		String[] out = getInfoByApiId("1",param);
//		for (int i = 0; i < out.length; i++) {
//			System.out.println(out[i]);
//		}
//		String jsonString="[{\"no\":\"01\",\"sql\":\"select count(*) from students;\"},{\"no\":\"02\",\"sql\":\"select  count(*) from students where studentNo=\'001\';\"}]";
//		List<VerifySql> jsonArray=JSONObject.parseArray(jsonString,VerifySql.class);
//		for(int i=0;i<jsonArray.size();i++){
//			VerifySql object= jsonArray.get(i);
//		    System.out.println(object.getNo());
//		}
	}
	
	/**
	 * 根据指定apiid获取数据中指定的列表的数据
	 * @param ApiId
	 * @param params
	 */
	public static Object[][] getDataByApiId(String ApiId,String[] params){
		int size =ExcelUtil.list.size();
		List<List> map = new ArrayList<List>();
		Class<Case> clazz = Case.class;
		for (int i = 0; i < size; i++) {		
			Case caseObj = ExcelUtil.list.get(i);
			if(ExcelUtil.list.get(i).getApiId().equals(ApiId)){
//				 Case caseObj;
				List list = new ArrayList();
				try {
//					Case caseObj = clazz.newInstance();
					 for (int j = 0; j < params.length; j++) {
//						 System.out.println(params[j]);
						 Method method = clazz.getMethod("get"+params[j].substring(0,1).toUpperCase()+params[j].substring(1));
//						 System.out.println(method.invoke(caseObj));
						 list.add( method.invoke(caseObj));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.add(list);
			}
		}
		Object[][] object = new Object[map.size()][params.length];
		for (int i = 0; i < map.size(); i++) {
			List jsonList =  map.get(i);
			for (int j = 0; j < jsonList.size(); j++) {
//				System.out.println(jsonList.get(j));
				object[i][j] = jsonList.get(j);
			}
		}
		return object;
	}

	/**
	 * 根据apiId获取要的信息；
	 * @param ApiId
	 * @param param
	 * @return
	 */
	public static String[] getInfoByApiId(String ApiId,String[] param){
		List<InterfaceInfo> infos =  ExcelUtil.infos;
		String[] outParams =  new String[param.length];
		Class clazz = InterfaceInfo.class;
		for (int i = 0; i < infos.size(); i++) {
			if(infos.get(i).getApiId().equals(ApiId)){
				for (int j = 0; j < param.length; j++) {
					Method method;
					try {
						method = clazz.getMethod("get"+param[j].substring(0,1).toUpperCase()+param[j].substring(1));
						outParams[j] = (String)method.invoke(infos.get(i));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}	
				break;
			}
		}
		return outParams;
	}
	
	
}
