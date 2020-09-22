package com.llr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import com.llr.Inteface.WriteData;

/**
 * ����Ӧ�����д��excel
 * @author lll
 *
 */
public class WriteDataToExcel {
	public static void main(String[] args) {
		WriteToPotion("��������","1","ActuallResponseData","1111");
	}
	
	/**
	 * ����ָ��λ�û�д����
	 * @param sheetName
	 * @param ApiId
	 * @param CellName
	 * @param result
	 */
	public static void WriteToPotion(String sheetName,String ApiId, String CellName,String result){
		InputStream inp=null;
		Workbook wokeBook = null;
		OutputStream stream = null;
		try {
			inp = new FileInputStream(ExcelUtil.FilePath);
			wokeBook = WorkbookFactory.create(inp);
			 Sheet sheet = wokeBook.getSheet(sheetName);
			 int rownum = ExcelUtil.cellToRowPosition.get(ApiId);
			 Row row = sheet.getRow(rownum);
			 int cellnum = ExcelUtil.cellToCellPosition.get(CellName);
			 Cell cell = row.getCell(cellnum,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			 cell.setCellType(CellType.STRING);
			 cell.setCellValue(result);
			 stream = new FileOutputStream(ExcelUtil.FilePath);
			 wokeBook.write(stream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(inp!=null)
					inp.close();
				if(wokeBook!=null)
					wokeBook.close();
				if(stream!=null)
					stream.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
		
	}

	/**
	 * �����еĲ��Խ��һ���Ի�д��excelָ����λ��
	 */
	public static void WriteDataList(){
		InputStream inp = null;
		Workbook workBook= null;
		OutputStream stream = null;
		try {
			inp = new FileInputStream(new File(ExcelUtil.FilePath));
			workBook = WorkbookFactory.create(inp);
			for (WriteData data : ExcelUtil.writeDataList) {
				Sheet sheet = workBook.getSheet(data.getSheetName());
				int rownum = ExcelUtil.cellToRowPosition.get(data.getRowNum());
				Row row =  sheet.getRow(rownum);
				int cellnum = ExcelUtil.cellToCellPosition.get(data.getCellName());
				Cell cell = row.getCell(cellnum,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(data.getResult());
			}
			stream = new FileOutputStream(new File(ExcelUtil.FilePath));
			workBook.write(stream);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(inp!=null)
					inp.close();
				if(stream!=null)
					stream.close();
				if(workBook!=null)
					workBook.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
			
		}
		
	}
}
