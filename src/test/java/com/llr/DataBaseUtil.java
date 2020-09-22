package com.llr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;


public class DataBaseUtil {
	private static String url = "jdbc:mysql://120.78.128.25:3306/test?characterEncoding=utf8&useSSL=true";
	
	private static String Username = "lemon";
	
	private static String Password = "123456";
	
	private static String driver = "com.mysql.jdbc.Driver";
	
	public static Logger logger = Logger.getLogger(DataBaseUtil.class);
	
	static{
		Properties properties = new Properties();
		InputStream inStream;
		try {
			inStream = new FileInputStream(new File("src/test/java/jdbc.properties"));
			properties.load(inStream);
			url = properties.getProperty("jdbc.url");
			Username = properties.getProperty("jdbc.username");
			Password = properties.getProperty("jdbc.password");
			driver = properties.getProperty("jdbc.driver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取连接
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	private static Connection ConnectDataBase(String url,String username, String password){
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
//			System.out.println("连接成功");
			logger.info("连接成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	public static List<Map<String, String>> Query(String sql){
		Connection con = ConnectDataBase(url,Username,Password);
		return QueryData(con,sql);
	}
	
	private static List<Map<String,String>> QueryData(Connection con,String sql){
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {	
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet set =  pre.executeQuery();
			ResultSetMetaData  metadata = set.getMetaData();
			
			while(set.next()){
				int counts = metadata.getColumnCount();
				Map<String,String> map = new HashMap<String, String>();
				System.out.println(counts);
				for (int i = 1; i <= counts; i++) {
					System.out.println(metadata.getColumnLabel(i)+":"+set.getString(i));
					System.out.println(set.getObject(i));
					map.put(metadata.getColumnLabel(i), set.getObject(i).toString());
				}
				list.add(map);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	public static void main(String[] args) {
//		Connection con = ConnectDataBase(url,Username,Password);
//		String sql = "select * from students where studentNo=\"001\"";
		String sql = "select * from students";
		List<Map<String,String>> list = Query(sql);
		Object json =  JSONObject.toJSON(list);
		
		System.out.println("条数："+list.toString());
//		for (int i = 0; i < list.size(); i++) {
//			Map<String, String> map = list.get(i);
//			Set<String> set =map.keySet();
//			for (String string : set) {
//				System.out.println(string);
//				System.out.println(map.get(string));
//			}
//		}
	}
}
