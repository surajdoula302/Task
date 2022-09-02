package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	public FileInputStream fis;
	public FileOutputStream fos;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	String reportfileName;
	
	public static HashMap<String,String> readExcel(String filePath,String fileName,String sheetName,HashMap<String,String> mapdetails) throws Exception
	{
		File file=new File(filePath+"\\"+fileName);
		FileInputStream inputStream=new FileInputStream(file);
		Object MyWorkbook=null;
		String fileExtensionName=fileName.substring(fileName.indexOf("."));
		if(fileExtensionName.equals(".xlsx"))
		{
			MyWorkbook =new XSSFWorkbook(inputStream);
		}
		
		Sheet mySheet=( (XSSFWorkbook) MyWorkbook).getSheet(sheetName);
		int rowCount=mySheet.getLastRowNum();
		for(int i=0;i<rowCount;i++)
		{
			Row row=mySheet.getRow(i);
			mapdetails.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
		}
		return mapdetails;	
	}
	
	public ReadExcel(String reportfileName)
	{
		this.reportfileName=reportfileName;
	}
	
	
	
	public void setCellData(String sheetName,int rownum,int colnum,String data) throws IOException
	{
		File xlfile=new File(reportfileName);
		if(!xlfile.exists())    // If file not exists then create new file
		{
			XSSFWorkbook workbook=new XSSFWorkbook();
			FileOutputStream fos=new FileOutputStream(reportfileName);
			workbook.write(fos);
		}
				
		FileInputStream fis=new FileInputStream(reportfileName);
		workbook=new XSSFWorkbook(fis);
			
		if(workbook.getSheetIndex(sheetName)==-1) // If sheet not exists then create new Sheet
			workbook.createSheet(sheetName);
		
		XSSFSheet sheet=workbook.getSheet(sheetName);
					
		if(sheet.getRow(rownum)==null)   // If row not exists then create new Row
				sheet.createRow(rownum);
		XSSFRow row=sheet.getRow(rownum);
		
		XSSFCell cell=row.createCell(colnum);
		cell.setCellValue(data);
		FileOutputStream fos=new FileOutputStream(reportfileName);
		workbook.write(fos);		
		workbook.close();
		fis.close();
		fos.close();
	
}

}


