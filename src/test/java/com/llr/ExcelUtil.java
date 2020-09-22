package com.llr;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import com.llr.Inteface.AfterVerfityResult;
import com.llr.Inteface.Case;
import com.llr.Inteface.InterfaceInfo;
import com.llr.Inteface.ParamInfo;
import com.llr.Inteface.WriteData;

public class ExcelUtil {
	public static String FilePath = "src/test/java/com/llr/lib/login_v5.xlsx";
	//用来存放excel读出来的测试用例
	public static List<Case> list;
	//用来存放excel读出来的接口信息
	public static List<InterfaceInfo> infos;
	//用来存放数据库查出来的数据
	public static List<AfterVerfityResult> resultList;
	//用来存放excel读出来的参数信息
	public static List<ParamInfo> paramList;
	//单元格对应的行的位置
	public static Map<String,Integer> cellToRowPosition = new HashMap<String, Integer>();
	//单元格对应列的位置
	public static Map<String,Integer> cellToCellPosition = new HashMap<String, Integer>();
	
	public static Logger logger = Logger.getLogger(ExcelUtil.class);
	
	public static List<WriteData> writeDataList = new ArrayList<WriteData>();
	static{
//		load(FilePath,"测试用例",Case.class);
		list = load(FilePath,"测试用例",Case.class);
		infos = load(FilePath,"接口信息",InterfaceInfo.class);
		paramList = load(FilePath,"参数",ParamInfo.class);
	}
	public static void main(String[] args) {
		List<Case> list = load(FilePath,"测试用例",Case.class);
		for (Case case1 : list) {
			System.out.println(case1);
		}
//		
//		List<InterfaceInfo> infos = load(FilePath,"接口信息",InterfaceInfo.class);
//		for (InterfaceInfo case1 : infos) {
//			System.out.println(case1);
//		}
//		Set<String> setCells = cellToCellPosition.keySet();
//		for (String string : setCells) {
//			System.out.println("key:"+string+",value:"+cellToCellPosition.get(string));
//		}
//		
//		Set<String> setRows = cellToRowPosition.keySet();
//		for (String string : setRows) {
//			System.out.println("key:"+string+",value:"+cellToRowPosition.get(string));
//		}
	}
	
	/**
	 * 根据指定的文件和表格获取指定类的集合对象
	 * @param <T>
	 * @param FilePath
	 * @param sheetName
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> load(String FilePath,String sheetName,Class<T> clazz){
		File file = new File(FilePath);
		if(!file.exists()){
			logger.info("文件不存在");
			System.out.println("文件不存在");
			return null;
		}
		InputStream inp=null;
		Workbook workbook=null;
		List<T> list = new ArrayList<T>();
		try {
			inp = new FileInputStream(file);
			workbook = WorkbookFactory.create(inp);
			Sheet sheet = workbook.getSheet(sheetName);					
			Row row = sheet.getRow(0);	
			//获取一共有几列
			int cellNum = row.getLastCellNum();
			logger.debug("sheetName:"+sheetName);
			String [] title = new String[cellNum];
			for (int i = 0; i < cellNum; i++) {
				Cell cell= row.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				String cell_value =cell.getStringCellValue();
				cell_value = cell_value.substring(0,1).toUpperCase()+cell_value.substring(1);
				if(cell_value.contains("(")){
					cell_value = cell_value.split("(")[0];
				}
				cellToCellPosition.put(cell_value, cell.getAddress().getColumn());
				title[i] = "set" + cell_value;
//				System.out.println(cell_value);
			}
			
			//获取一共有几行
			int rowNum = sheet.getLastRowNum();
			for (int i = 1; i <= rowNum; i++) {
				Row rowBody = sheet.getRow(i);
				T t = clazz.newInstance();
				for (int j = 0; j < cellNum; j++) {
					Cell cell= rowBody.getCell(j,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);
					String cell_value =cell.getStringCellValue();
					//setAfterVerifySql
					//setAfterVerifySql
//					System.out.println(title[j]);
//					System.out.println(cell_value);
					if(j==0){
//						System.out.println("==================");
//						System.out.println(cell.getAddress().getRow());
						cellToRowPosition.put(cell_value, cell.getAddress().getRow());
					}
					Method method = clazz.getMethod(title[j],String.class);
					method.invoke(t, cell_value);
				}	
				list.add(t);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(inp!=null)
					inp.close();
				if(workbook!=null)
					workbook.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
		return list;
		
	}
}
