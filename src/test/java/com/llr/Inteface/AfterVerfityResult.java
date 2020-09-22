package com.llr.Inteface;

public class AfterVerfityResult {
	private String no;
	
	private String vaildResultValue;
	
//	private String cellPostion;
//	
//	private String rowPotion;
//
//	private String sheetName;
//	public String getCellPostion() {
//		return this.cellPostion;
//	}
//
//	public void setCellPostion(String cellPostion) {
//		this.cellPostion = cellPostion;
//	}
//
//	public String getRowPotion() {
//		return this.rowPotion;
//	}
//
//	public void setRowPotion(String rowPotion) {
//		this.rowPotion = rowPotion;
//	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getVaildResultValue() {
		return vaildResultValue;
	}

	public void setVaildResultValue(String vaildResultValue) {
		this.vaildResultValue = vaildResultValue;
	}
	
	
	
	public AfterVerfityResult(){
		
	}
	
	public AfterVerfityResult(String no,String vaildResultValue){
		this.no = no;
		this.vaildResultValue = vaildResultValue;
	}
//	public String getSheetName() {
//		return sheetName;
//	}
//
//	public void setSheetName(String sheetName) {
//		this.sheetName = sheetName;
//	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return "no:"+this.no+",vaildResultValue"+this.vaildResultValue;
	}
}
