package com.llr.Inteface;

public class ParamInfo {
	private String key;
	private String value;
	private String mark;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public ParamInfo(){
		
	}
	
	public ParamInfo(String key,String value){
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "key:"+this.key+",value:"+this.value;
	}
}
