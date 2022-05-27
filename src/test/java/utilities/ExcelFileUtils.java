package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtils {
Workbook wb;
//constructor for reading excel path
public ExcelFileUtils(String excelpath) throws Throwable
{
	FileInputStream fi = new FileInputStream(excelpath);
	wb =WorkbookFactory.create(fi);
}
//counting no of rows in a sheet
public int rowCount(String sheetName)
{
	return wb.getSheet(sheetName).getLastRowNum();
}
//count no of cells in a row
public int cellCount(String sheetName)
{
	return wb.getSheet(sheetName).getRow(0).getLastCellNum();
}
//reading cell data from sheet
public String getCellData(String sheetName,int row,int column)
{
	String data ="";
	if(wb.getSheet(sheetName).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
	{
		int celldata = (int) wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
		//convert cell data into string
		data = String.valueOf(celldata);
	}
	else
	{
		data =wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();
	}
     return data;
}
//method for set cell data
public void setCellData(String sheetName,int row,int column,String status,String writeExcelpath)throws Throwable
{
	//get sheet from wb
	Sheet ws = wb.getSheet(sheetName);
	//get row from sheet
	Row rowNum = ws.getRow(row);
	//create cell in a row
	Cell cell = rowNum.createCell(column);
	//write status in a cell
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("Pass"))
	{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		//colour text into green
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("Fail"))
	{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		//colour text into red
		font.setColor(IndexedColors.RED.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("Blocked"))
	{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		//colour text into blue
		font.setColor(IndexedColors.BLUE.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
	}
FileOutputStream fo = new FileOutputStream(writeExcelpath);
wb.write(fo);
}
}
