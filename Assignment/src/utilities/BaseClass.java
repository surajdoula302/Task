package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;


public class BaseClass {
	public static WebDriver driver;
	

	public static ChromeDriver launchBrowsers()
	{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
		ChromeOptions options=new ChromeOptions();
		DesiredCapabilities caps=DesiredCapabilities.chrome();
		options.addArguments(new String[] {"disable-popup-blocking"});
		options.setAcceptInsecureCerts(true);
		options.addArguments(new String[] {"--disable-gpu"});
		options.addArguments(new String[] {"--disable-extensions"});
		options.addArguments(new String[] {"--no-sandbox"});
		options.addArguments(new String[] {"--disable-dev-shm-usage"});
		options.addArguments(new String[] {"--window-size=1920,1080"});
		caps.setCapability("goog:chromeOptions", options);
		ChromeDriver driver=new ChromeDriver(options);
	
		return driver;
	}
}
