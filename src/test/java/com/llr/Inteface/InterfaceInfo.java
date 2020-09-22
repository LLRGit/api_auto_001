package com.llr.Inteface;

public class InterfaceInfo {
	private String name;
	private String url;
	private String type;
	private String apiId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Name:"+this.name+",Url:"+this.url+",Type:"+this.type +",ApiId:"+this.apiId;

	}
}
