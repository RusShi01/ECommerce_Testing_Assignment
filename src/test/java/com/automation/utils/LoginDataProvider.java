package com.automation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {
	
	@DataProvider(name = "loginData")
	public static Object[][] getLoginData() throws IOException {
		 String excelPath = "C:\\Users\\bhagw\\Downloads\\LoginData.xlsx";
	     String sheetName = "LoginData"; 
	     
	     FileInputStream fis = new FileInputStream(excelPath);
	     Workbook workbook = new XSSFWorkbook(fis);
	        Sheet sheet = workbook.getSheet(sheetName);

	        if (sheet == null) {
	            throw new IOException("Sheet '" + sheetName + "' not found in the Excel file.");
	        }

	        int rowCount = sheet.getPhysicalNumberOfRows();
	        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

	        List<Object[]> data = new ArrayList<>();

	        Iterator<Row> rowIterator = sheet.iterator();
	        rowIterator.next(); // Skip header row

	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	            String username = row.getCell(0).getStringCellValue();
	            String password = row.getCell(1).getStringCellValue();
	            String expectedResult = row.getCell(2).getStringCellValue();
	            data.add(new Object[]{username, password, expectedResult});
	        }

	        workbook.close();
	        fis.close();

	        return data.toArray(new Object[0][]);
	    }
		
	}
