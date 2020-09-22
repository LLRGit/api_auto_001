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
	//�������excel�������Ĳ�������
	public static List<Case> list;
	//�������excel�������Ľӿ���Ϣ
	public static List<InterfaceInfo> infos;
	//����������ݿ�����������
	public static List<AfterVerfityResult> resultList;
	//�������excel�������Ĳ�����Ϣ
	public static List<ParamInfo> paramList;
	//��Ԫ���Ӧ���е�λ��
	public static Map<String,Integer> cellToRowPosition = new HashMap<String, Integer>();
	//��Ԫ���Ӧ�е�λ��
	public static Map<String,Integer> cellToCellPosition = new HashMap<String, Integer>();
	
	public static Logger logger = Logger.getLogger(ExcelUtil.class);
	
	public static List<WriteData> writeDataList = new ArrayList<WriteData>();
	static{
//		load(FilePath,"��������",Case.class);
		list = load(FilePath,"��������",Case.class);
		infos = load(FilePath,"�ӿ���Ϣ",InterfaceInfo.class);
		paramList = load(FilePath,"����",ParamInfo.class);
	}
	public static void main(String[] args) {
		List<Case> list = load(FilePath,"��������",Case.class);
		for (Case case1 : list) {
			System.out.println(case1);
		}
//		
//		List<InterfaceInfo> infos = load(FilePath,"�ӿ���Ϣ",InterfaceInfo.class);
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
	 * ����ָ�����ļ��ͱ���ȡָ����ļ��϶���
	 * @param <T>
	 * @param FilePath
	 * @param sheetName
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> load(String FilePath,String sheetName,Class<T> clazz){
		File file = new File(FilePath);
		if(!file.exists()){
			logger.info("�ļ�������");
			System.out.println("�ļ�������");
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
			//��ȡһ���м���
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
			
			//��ȡһ���м���
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
