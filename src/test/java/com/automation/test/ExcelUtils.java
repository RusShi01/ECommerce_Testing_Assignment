package com.automation.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	

	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static CellStyle style;
	
	public static int getRowCount(String Xlfile, String XlSheet ) throws IOException {
		fi = new FileInputStream(Xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(XlSheet);
		int rowcount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount + 1;  // +1 to include header or first row
		
	}
	
	public static int getCellCount(String Xlfile, String XlSheet, int rownum, int colnum ) throws IOException {
		fi = new FileInputStream(Xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(XlSheet);
		row = ws.getRow(rownum);
		int cellcount = 0;
	    if(row != null){
	        cellcount = row.getLastCellNum();
	    }
		wb.close();
		fi.close();
		return cellcount;
		
	}
	
	public static String getCellData (String Xlfile, String XlSheet, int rownum, int colnum ) throws IOException {
		fi = new FileInputStream(Xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(XlSheet);
		row = ws.getRow(rownum);
		cell = row != null ? row.getCell(colnum) : null;
		
		String data;
		
		try {
		data = cell.toString();
		}
		catch(Exception e){
			data = " ";
		}
		wb.close();
		fi.close();
		return data;
		
	}
	
	public static void setCellData (String Xlfile, String XlSheet, int rownum, int colnum, String data ) throws IOException {
		fi = new FileInputStream(Xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(XlSheet);
		row = ws.getRow(rownum);
		cell = row.createCell(colnum);
	    cell.setCellValue(data);  
	    
	    fo = new FileOutputStream(Xlfile);
	    wb.write(fo);
	    wb.close();
	    
	    fi.close();
	    fo.close();
	
    }
}
