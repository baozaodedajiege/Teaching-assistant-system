package com.ctgu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ctgu.model.Account;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 读取Excel文件的方法
 * @author lmb
 * @date 2017-3-15
 *
 */
public class ReadExcel {
 
	private static String xls2003 = "D:\\student.xls";
	private static String xlsx2007 = "D:\\student.xlsx";
	
	/** 
     * 读取Excel2003的主表数据 （单个sheet）
     * @param is
     * @return 
     */  
	private static List<Account> readFromXLS2003(InputStream is) {
        File excelFile = null;// Excel文件对象  
        String cellStr = null;// 单元格，最终按字符串处理
        List<Account> studentList = new ArrayList<Account>();// 返回封装数据的List  
        Account student = null;// 每一个学生信息对象  
        try {  
            HSSFWorkbook workbook2003 = new HSSFWorkbook(is);// 创建Excel2003文件对象
            HSSFSheet sheet = workbook2003.getSheetAt(0);// 取出第一个工作表，索引是0  
            // 学号和姓名所在列编号
            int studentId = -1;
            int studentName = -1;
            // 获取表头
            HSSFRow head = sheet.getRow(0);
            for (int j = 0; j < head.getLastCellNum(); j++) {
                if("学号".equals(head.getCell(j).getStringCellValue())) {
                    studentId = j;
                }
                if("姓名".equals(head.getCell(j).getStringCellValue())) {
                    studentName = j;
                }
                if(studentId != -1 && studentName != -1) {
                    break;
                }
            }
            if(studentId == -1 || studentName == -1) {
                return null;
            }
            // 开始循环遍历行，表头不处理，从1开始
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {  
            	HSSFRow row = sheet.getRow(i);// 获取行对象 
            	student = new Account();// 实例化Student对象  
                if (row == null) {// 如果为空，不处理  
                    continue;  
                }
                HSSFCell studentIdCell = row.getCell(studentId);// 获取学号
                HSSFCell studentNameCell = row.getCell(studentName);// 获取学生姓名
                if(studentIdCell != null && studentNameCell != null) {
                    student.setQq(HSSFCell2String(studentIdCell));
                    student.setName(HSSFCell2String(studentNameCell));
                    studentList.add(student);
                }
            }  
		} catch (IOException e) {  
            e.printStackTrace();  
        } finally {// 关闭文件流  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return studentList;  
    }  

	/**
	 * 读取Excel2007的示例方法 （单个sheet）
	 * @param is
	 * @return
	 */
	public static List<Account> readFromXLSX2007(InputStream is) {
        String cellStr = null;// 单元格，最终按字符串处理
        List<Account> studentList = new ArrayList<Account>();// 返回封装数据的List  
        Account student = null;// 每一个学生信息对象  
        try {  
//            XSSFWorkbook workbook2007 = new XSSFWorkbook(is);// 创建Excel2007文件对象
            org.apache.poi.ss.usermodel.Workbook workbook2007 = WorkbookFactory.create(is);
//            XSSFSheet sheet = workbook2007.getSheetAt(0);// 取出第一个工作表，索引是0 
            org.apache.poi.ss.usermodel.Sheet sheet = workbook2007.getSheetAt(0);

            // 学号和姓名所在列编号
            int studentId = -1;
            int studentName = -1;
            // 获取表头
            Row head = sheet.getRow(0);
            for (int j = 0; j < head.getLastCellNum(); j++) {
                if("学号".equals(head.getCell(j).getStringCellValue())) {
                    studentId = j;
                }
                if("姓名".equals(head.getCell(j).getStringCellValue())) {
                    studentName = j;
                }
                if(studentId != -1 && studentName != -1) {
                    break;
                }
            }
            if(studentId == -1 || studentName == -1) {
                return null;
            }
            // 开始循环遍历行，表头不处理，从1开始
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);// 获取行对象
                student = new Account();// 实例化Student对象
                if (row == null) {// 如果为空，不处理
                    continue;
                }
                Cell studentIdCell = row.getCell(studentId);// 获取学号
                Cell studentNameCell = row.getCell(studentName);// 获取学生姓名
                if(studentIdCell != null && studentNameCell != null) {
                    student.setQq(Cell2String(studentIdCell));
                    student.setName(Cell2String(studentNameCell));
                    studentList.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  
        } catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
		}finally {// 关闭文件流  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return studentList;  
    }

    private static String HSSFCell2String(HSSFCell cell) {
        if(cell == null) {
            return "";
        }else {// 其余按照字符串处理
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            return cell.getStringCellValue();
        }
    }
    private static String Cell2String(Cell cell) {
        if(cell == null) {
            return "";
        }else {// 其余按照字符串处理
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return cell.getStringCellValue();
        }
    }
	/**
	 * 读取Excel的示例方法
	 * @param excelFile
	 * @return
	 */
	public static List<Account> readMoreSheetFromXLS(File excelFile){
		List<Account> studentList = new ArrayList<>();
		try {
		    String fileName = excelFile.getName();
            FileInputStream is = new FileInputStream(excelFile);// 获取文件输入流
            if (fileName.toLowerCase().endsWith("xls")) {//2003
                studentList = readFromXLS2003(is);
			}else if(fileName.toLowerCase().endsWith("xlsx")){//2007
			    studentList = readFromXLSX2007(is);
			}
		} catch (IOException e) {
            e.printStackTrace();
        }
        return studentList;
	}
	
	/** 
     * 主函数 
     *  
     * @param args 
     */  
    public static void main(String[] args) {
//        String filePath = "G:/data/student.xlsx";
//        List<Account> accountList = ReadExcel.readMoreSheetFromXLS(filePath);
//        for(Account account : accountList) {
//            System.out.println(account);
//        }
    }
}  