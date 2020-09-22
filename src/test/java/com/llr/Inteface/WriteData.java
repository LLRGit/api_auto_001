package com.llr.Inteface;

public class WriteData {
	private String CellName;
	
	/*
	 * ––Œª÷√
	 */
	private String rowNum;
	
	private String result;
	
	private String sheetName;

	public String getCellName() {
		return CellName;
	}

	public void setCellName(String cellName) {
		CellName = cellName;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String apiId) {
		rowNum = apiId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public WriteData(){
		
	}
	public WriteData(String CellName,String ApiId,String result,String sheetName){
			this.CellName = CellName;
			this.rowNum =ApiId;
			this.result = result;
			this.sheetName = sheetName;
	}
	
}