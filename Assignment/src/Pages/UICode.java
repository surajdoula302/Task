package Pages;

	import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
	import java.util.ArrayList;
	import java.util.Date;
	import java.util.HashMap;
	import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.remote.DesiredCapabilities;

import utilities.BaseClass;
import utilities.ReadExcel;

	public class UICode {
		
		static String url;
		static String cities;
		static String reportfilePath;
		static String reportfileName;
		static List<String> cities_1=new ArrayList<>();
		static HashMap<String,Object> temp=new HashMap<>();

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public  void mainTask() {
			try
			{
				String filepath=System.getProperty("user.dir")+"\\InputDetails.xlsx";
				File file=new File(filepath);
				String strParentDirectory=file.getParent(); 
				reportfilePath=strParentDirectory+"\\Report";
				reportfileName="Temparature" + (new SimpleDateFormat("YYYY_MM_dd_HH_mm")).format(new Date())+".xlsx";
				HashMap<Object, Object> loginHashMap = new HashMap<> ();
				loginHashMap=utilities.ReadExcel.readExcel(strParentDirectory,"InputDetails.xlsx","Sample",(HashMap) loginHashMap);
				System.out.println("Login"+ loginHashMap);
				url=(String) loginHashMap.get("URL");
				cities=(String) loginHashMap.get("Cities");
				
				if(cities.contains(","))
				{
					String[] s=cities.split(",");
					for(String city:s)
					{
						cities_1.add(city);
					}
				}
				else
				{
					cities_1.add(cities);
				}
				ChromeDriver driver=BaseClass.launchBrowsers();
				List<String> tempList=getTempOfCity(driver,url,cities_1);
				ReadExcel xlutil=new ReadExcel(reportfileName);
				
				xlutil.setCellData("sheet1", 0, 0, "City");
				xlutil.setCellData("sheet1", 0, 1, "Temperature");
				
				int rownum=0;
				 XSSFWorkbook workbook=new XSSFWorkbook();
				 XSSFSheet sheet=workbook.createSheet(reportfileName);
				 for(Map.Entry<String, Object>  entry:temp.entrySet())
				 {
					 XSSFRow row=sheet.createRow(rownum++);
					 row.createCell(0).setCellValue(entry.getKey());
					 //row.createCell(1).setCellValue(entry.getValue());
					 
				 }
				
				 FileOutputStream fos=new FileOutputStream(reportfileName);
				 workbook.write(fos);
				 fos.close();
				 workbook.close();
				driver.quit();
			}
			catch(Exception var21)
			{
				var21.printStackTrace();
			}
		}
		
		public static List<String> getTempOfCity(ChromeDriver driver,String url,List<String> cities)
		{
			WebElement webElement=null;
			try
			{
				driver.get(url);
				Thread.sleep(3000);
				for(String city:cities)
				{
					webElement=driver.findElement(By.id("searchBox"));
					webElement.sendKeys(Keys.HOME,Keys.chord(new CharSequence[] {(CharSequence) Keys.SHIFT,(CharSequence)Keys.END}),city);
					webElement=driver.findElement(By.xpath("//input[@id='"+city+"' and @type='checkbox']"));
					Actions action=new Actions(driver);
					action.moveToElement(webElement).click().build().perform();
					
					List<WebElement> l=driver.findElements(By.xpath("//*[text()=\""+city+"\"]/preceding-sibling::*[1]/descendant::*"));
					
					List<Double> t=new ArrayList<>();
					for(WebElement e:l)
					{
						String celcius_cityTemp=e.getText().replaceAll("[^0-9.]", "");
						double cityTemp=Double.parseDouble(celcius_cityTemp.replace("?", ""));
						t.add(cityTemp);
						
					}
					temp.put(city, t);
				}
				System.out.println(temp);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return new ArrayList<>();
		}
		
	}


