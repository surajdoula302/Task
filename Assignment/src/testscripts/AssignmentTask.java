package testscripts;

import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Pages.UICode;
import utilities.BaseClass;



public class AssignmentTask {
	
	@Test
	public void Tasktest()
	{
		Reporter.log("Test case Started.....");
		UICode code=PageFactory.initElements(BaseClass.driver, UICode.class);
		code.mainTask();
		Reporter.log("Test case Run Successfully.....");
	}
	

}
