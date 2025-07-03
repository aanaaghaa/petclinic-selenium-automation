package com.anagha.petclinic.stepdefinitions;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.pages.AddVisitPage;
import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddVisitSteps {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddVisitSteps.class);
	WebDriver driver;
	AddVisitPage addVisitPage;
	BasePage basePage;
	String today = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	public static final String REGULAR_VISIT = "Reg check up";
	public static final String GENERIC_ERROR = "Something happened";

	
	/*-------------------------------------------------------------------------------------------------------------
	POSITIVE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/
	
	@Given("the user is on the owners page")
	public void the_user_is_on_the_owners_page()
	{
		driver = DriverFactory.initDriver();
		driver.get(ConfigReader.get("url")+"/owners/9");
		String currUrl=driver.getCurrentUrl();
		logger.info("Navigated to Owner page. Current URL: {}", currUrl);
		Assert.assertTrue(currUrl.contains("owners"));
		basePage=new BasePage(driver);
		addVisitPage=new AddVisitPage(driver);
	}
	@When("the user clicks on Add visit link")
	public void the_user_clicks_on_Add_visit_link() throws InterruptedException
	{
		logger.info("Clicking on Add Visit link.");
		addVisitPage.addVisitButtonClick();
	}
	@Then("the user should be redirected to the visiting page")
	public void the_user_should_be_redirected_to_the_visiting_page()
	{
		String currUrl=driver.getCurrentUrl();
		logger.info("Redirected to Add Visit page. Current URL: {}", currUrl);
		Assert.assertTrue(currUrl.contains("visits/new"));
	}
	@Then("the user should be able to add all the details and save it")
	public void the_user_should_be_able_to_add_all_the_details_and_save_it()
	{
		logger.info("Entered description: Reg check up and submitting Add Visit form.");
		addVisitPage.addVisitDetails(today, REGULAR_VISIT);
	}
	@Then("user should get a success message")
	public void user_should_get_a_success_message()
	{
		By successMessageLocator = By.id("success-message");
		basePage.waitForElement(successMessageLocator); // Add this before fetching the text
		String successvisitbookedMsg=driver.findElement(By.id("success-message")).getText();
		logger.info("Success message displayed: {}", successvisitbookedMsg);
		Assert.assertTrue(successvisitbookedMsg.contains("visit has been booked"));
	}
	@Then("the user should be able to view the visit in the owners page")
	public void the_user_should_be_able_to_view_the_visit_in_the_owners_page()
	{
		boolean found = false;

		By successMsg=By.id("success-message");
		basePage.waitForElementDisappear(successMsg);
		List<WebElement> table=addVisitPage.getOwnerVisitTableData();
		for (WebElement tabledata : table) {
		    List<WebElement> columns = tabledata.findElements(By.tagName("td"));
		    if (columns.size() > 1) {
		        String desc = columns.get(1).getText(); // 1 = second column (index is 0-based)
		        logger.info("Visit is listed in Owner's page: {}", desc);
		        if (desc.contains("Reg")) {
		            found = true;
		            break;
		        }
		        
		    }
		}
		basePage.waitForElementTextToContain(By.xpath("//table[@class='table-condensed']//td[2]"), REGULAR_VISIT);
		logger.info("Visit added successfully");
		Assert.assertTrue("Expected visit description not found in owner's page",found );
		}
		
	@Then("the user should be able to view the visit in the visit page")
	public void the_user_should_be_able_to_view_the_visit_in_the_visit_page() throws InterruptedException
	{
		addVisitPage.addVisitButtonClick();
	List<WebElement> visitData=addVisitPage.getPreviousVisitData();
	if (!visitData.isEmpty()) {
	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", visitData.get(0));
	Thread.sleep(500);
	}
	for(WebElement visitDesc: visitData)
	{
		if(visitDesc.getText().contains("Reg"))
		{
			logger.info("Visit is displayed in Previous Visits section: {}", visitDesc.getText());
			Assert.assertTrue("Expected visit not found in previous visits table", visitDesc.getText().contains("Reg"));

		}
	}
	}
	
	/*-------------------------------------------------------------------------------------------------------------
	NEGATIVE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/
	@When("the user provides the valid date and empty description")
	public void the_user_provides_the_valid_date_and_empty_description()
	{
		logger.info("Entering valid visit date: 01-07-2025 and empty description");
		addVisitPage.addVisitDetails(today, " ");
	}
	@Then("the user will get a field validation error")
	public void the_user_will_get_a_field_validation_error()
	{
		String descValError=driver.findElement(By.className("help-inline")).getText();
		logger.info("Validation error displayed: {}", descValError);
		Assert.assertTrue(descValError.contains("not be blank"));
	}
	

	/*-------------------------------------------------------------------------------------------------------------
	EDGE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/
	
	@When("the user provides a valid date and five hundred character long description")
	public void the_user_provides_a_valid_date()
	{
		logger.info("Entering valid date: 01-07-2025 and 500-character long description");
		addVisitPage.addVisitDetails(today, "This is a sample 500-character description for testing the input field limits accurately. It is useful for validating how well the application handles edge cases, ensures proper user experience, avoids truncation or system crashes, and supports special characters and multilingual text. Such tests are essential for robust systems that require data precision, especially in forms where user-provided content might vary in length significantly, from a few to hundreds of characters.");
	}
	
	@Then("the user should be directed to error page")
	public void the_user_should_be_directed_to_error_page()
	{
		By errorMsg=By.xpath("//div[contains(@class, 'xd-container')]");
				basePage.waitForElement(errorMsg);
		String error=driver.findElement(errorMsg).getText();
		logger.info("Error page displayed: {}", error);
		Assert.assertTrue(error.contains(GENERIC_ERROR));
	}
}
