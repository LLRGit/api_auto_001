package com.llr.Cases;

import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.llr.ExcelUtil;
import com.llr.GetDataUtil;
import com.llr.HttpUtil;
import com.llr.Inteface.WriteData;

public class GetChannelsTest extends NewTest{

  
  @DataProvider
  public Object[][] datas(){
			  return GetDataUtil.getDataByApiId("2",params);
  	}
}
