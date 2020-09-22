package com.llr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.text.html.parser.Entity;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpUtil {
//	public static void main(String[] args) {
////		Map<String,String> map = new HashMap<String, String>();
////		map.put("", "18046053413");
////		String result =HttpUtil.getRequest("http://ttapi.research.itcast.cn/app/v1_0/sms/codes",map);
////		System.out.println(result);
//		
////		Map<String,String> map = new HashMap<String, String>();
////		map.put("mobile", "18046053413");
////		map.put("code", "211685");
////		String result = HttpUtil.PostReuqest("http://ttapi.research.itcast.cn/app/v1_0/authorizations", map);
////		System.out.println(result);
//	}
	public static Map<String,String> token = new HashMap<String, String>();
	
	/**
	 * http get请求
	 * @param url 请求地址
	 * @param map 请求参数
	 * @return
	 */
	public static String getRequest(String url,Map<String,String> map){
		
		if(map!=null && map.size()>0){
			Set<String> set =  map.keySet();
			int index = 0;
			for (String string : set) {
				System.out.println(string);
				if(string==null||string.equals(" ")||string==""){
					url += ("/"+ map.get(string));
				}else{
					if(index==0){
						url += ("?" + string+ "="+ map.get(string));
						
					}else{
						url += ("&" + string + "=" + map.get(string));
					}
				}
			}
		}
		
		System.out.println(url);
		HttpGet get = new HttpGet(url);
		
		SetToken(get);
		return CommonResponse(get);
	}
	
	/**
	 * 设置token
	 * @param request
	 */
	private static void SetToken(HttpRequest request) {
		// TODO Auto-generated method stub
		request.setHeader("Content-Type", "application/json;charset=utf-8");
		if(token.get("token")!=""){
			request.setHeader("Authorization", "Bearer "+token.get("token"));
		}
	}

	/**
	 * post请求
	 * @param url
	 * @param map
	 * @return
	 */
	public static String PostReuqest(String url ,Map<String,String> map){
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json;charset=utf-8");
		Set<String> set = map.keySet();
		JSONObject jsonobject = new JSONObject();
		for (String string : set) {
			jsonobject.put(string, map.get(string));
		}
		StringEntity entity;
		try {
			entity = new StringEntity(jsonobject.toString(),"utf-8");
			post.setEntity(entity);
			return CommonResponse(post);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * 根据类型去判断是post还是get请求
	 * @param type
	 * @param url
	 * @param map
	 * @return
	 */
	public static String toSerivce(String type,String url,Map<String,String> map){
		System.out.println(type);
		if(type.toLowerCase().equals("post"))
			return PostReuqest(url,map);
		else if(type.toLowerCase().equals("get"))
			return getRequest(url, map);
		else 
			return "";
	}
	
	/**
	 * 公共响应体
	 * @param request
	 * @return 响应的内容
	 */
	public static String CommonResponse(HttpUriRequest request){
		String result = "";
		HttpClient client = HttpClients.createDefault();
		try {
			HttpResponse response = client.execute(request);
			System.out.println(response.getStatusLine().getStatusCode());
			result =EntityUtils.toString(response.getEntity(), "utf-8");		
			GetToken(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取token
	 * @param response
	 */
	private static void GetToken(String response) {
		// TODO Auto-generated method stub
//		response
		JSONObject jsonobj = JSONObject.parseObject(response.toString());
		Object obj =  jsonobj.get("data");
		Set<Entry<String, Object>>  set=jsonobj.entrySet();
		for (Entry<String, Object> entry : set) {
//			System.out.println("==================");
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
			if(entry.getKey().equals("token")){
				token.put("token", entry.getValue().toString());
			}
			else if(entry.getValue().toString().contains("token")){
				GetToken(entry.getValue().toString());
			}
			
		}
	}
	
	public static void main(String[] args) {
			Map<String,String> map = new HashMap<String, String>();
		map.put("mobile", "18046053413");
		map.put("code", "265517");
		String result = HttpUtil.PostReuqest("http://ttapi.research.itcast.cn/app/v1_0/authorizations", map);
//		String response = "{\"message\": \"OK\", \"data\": {\"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDAzNDI2NjgsInVzZXJfaWQiOjEzMDQzMDU1Mjc3NDgwMzQ1NjAsInJlZnJlc2giOmZhbHNlfQ.VSZKi2OVG2maCEIs25UVVUXLvpKYPent0HnkDsAx74w\", \"refresh_token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDE1NDUwNjgsInVzZXJfaWQiOjEzMDQzMDU1Mjc3NDgwMzQ1NjAsInJlZnJlc2giOnRydWV9.toNpZo6vrIl-ydsIvoev_lawy3cvtVlZ3LCWu4G0msA\"}}";
		GetToken(result);
		System.out.println(token.get("token"));
	}
}
