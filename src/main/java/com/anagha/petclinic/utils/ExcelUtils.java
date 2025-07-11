package com.anagha.petclinic.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Utility class to read and parse Excel data into a list of maps.
 * Supports reading owner and pet-related test data from .xlsx files.
 */

public class ExcelUtils {

	/**
     * Reads Excel data for Owners from the specified file and sheet.
     * Converts each row into a Map<String, String> using header row as keys.**/
	public static List<Map<String, String>> getExcelData(String filePath, String sheetName) {
	    List<Map<String, String>> dataList = new ArrayList<>();
	    try (FileInputStream fis = new FileInputStream(filePath);
	         Workbook workbook = new XSSFWorkbook(fis)) {

	        Sheet sheet = workbook.getSheet(sheetName);
	        Row headerRow = sheet.getRow(0);

	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	        	
	            Map<String, String> dataMap = new HashMap<>();
	            Row currentRow = sheet.getRow(i);

	            for (int j = 0; j < currentRow.getLastCellNum(); j++) {
	            	String key = headerRow.getCell(j).getStringCellValue().trim().toLowerCase();
	                Cell cell = currentRow.getCell(j);
	                
	                String value = "";
	                if (cell != null) {
	                    switch (cell.getCellType()) {
	                        case STRING:
	                            value = cell.getStringCellValue();
	                            break;
	                        case NUMERIC:
	                            value = String.valueOf((long) cell.getNumericCellValue());
	                            break;
	                        case BOOLEAN:
	                            value = String.valueOf(cell.getBooleanCellValue());
	                            break;
	                        default:
	                            value = "";
	                    }
	                }
	                dataMap.put(key, value);
	            }
	            dataList.add(dataMap);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return dataList;
	}
	
	/**
     * Reads Excel data for Pets, including special handling for date fields.
     * Converts each row into a Map<String, String> using header row as keys.
     * Formats date fields as dd-MM-yyyy.**/
	public static List<Map<String, String>> getPetExcelData(String filePath, String sheetName) {
	    List<Map<String, String>> dataList = new ArrayList<>();
	    try (FileInputStream fis = new FileInputStream(filePath);
	         Workbook workbook = new XSSFWorkbook(fis)) {

	        Sheet sheet = workbook.getSheet(sheetName);
	        Row headerRow = sheet.getRow(0);
	        int totalColumns = headerRow.getPhysicalNumberOfCells(); 

	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	            Row currentRow = sheet.getRow(i);
	            if (currentRow == null) continue;

	            Map<String, String> dataMap = new HashMap<>();

	            for (int j = 0; j < totalColumns; j++) {
	                Cell headerCell = headerRow.getCell(j);
	                if (headerCell == null) continue;

	                headerCell.setCellType(CellType.STRING);
	                String key = headerCell.getStringCellValue().trim().toLowerCase();

	                Cell cell = currentRow.getCell(j);
	                String value = "";
	                if (cell != null) {
	                    switch (cell.getCellType()) {
	                        case STRING:
	                            value = cell.getStringCellValue();
	                            break;
	                        case NUMERIC:
	                            if (DateUtil.isCellDateFormatted(cell)) {
	                                // Your expected format:
	                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	                                value = sdf.format(cell.getDateCellValue());
	                            } else {
	                                value = String.valueOf((long) cell.getNumericCellValue());
	                            }
	                            break;
	                        case BOOLEAN:
	                            value = String.valueOf(cell.getBooleanCellValue());
	                            break;
	                        default:
	                            value = "";
	                    }
	                }
	                dataMap.put(key, value);
	            }

	            dataList.add(dataMap);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return dataList;
	}
}