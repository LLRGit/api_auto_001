package com.llr.Inteface;

public class Case {
	private String caseId;
	
	private String mobile;
	
	private String apiId;
	
	private String expctedResponseData;
	
	private String actuallResponseData;
	
	private String verifySql;
	
	private String verifySqlResult;
	
	private String afterVerifySql;
	
	private String afterVerifySqlResult;
	
	public String getVerifySql() {
		return verifySql;
	}

	public void setVerifySql(String verifySql) {
		this.verifySql = verifySql;
	}

	public String getVerifySqlResult() {
		return verifySqlResult;
	}

	public void setVerifySqlResult(String verifySqlResult) {
		this.verifySqlResult = verifySqlResult;
	}

	public String getAfterVerifySql() {
		return afterVerifySql;
	}

	public void setAfterVerifySql(String afterVerifySql) {
		this.afterVerifySql = afterVerifySql;
	}

	public String getAfterVerifySqlResult() {
		return afterVerifySqlResult;
	}

	public void setAfterVerifySqlResult(String afterVerifySqlResult) {
		this.afterVerifySqlResult = afterVerifySqlResult;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getCaseId() {
		return caseId;
	}
	
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExpctedResponseData() {
		return expctedResponseData;
	}

	public void setExpctedResponseData(String expctedResponseData) {
		this.expctedResponseData = expctedResponseData;
	}

	public String getActuallResponseData() {
		return actuallResponseData;
	}

	public void setActuallResponseData(String actuallResponseData) {
		this.actuallResponseData = actuallResponseData;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "caseId:"+this.caseId + ";mobile:"+this.mobile + ",expctedResponseData:"+this.expctedResponseData+",actuallResponseData:"+this.actuallResponseData;
	}
}
