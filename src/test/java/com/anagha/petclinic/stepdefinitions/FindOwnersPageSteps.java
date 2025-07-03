package com.anagha.petclinic.stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.pages.FindOwnersPage;
import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class FindOwnersPageSteps {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FindOwnersPageSteps.class);
	WebDriver driver;
	FindOwnersPage findOwnerPage;
	BasePage basePage;
	
	/*-------------------------------------------------------------------------------------------------------------
	POSITIVE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/
	
	@Given("the user is on Find Owners page")
	public void the_user_is_on_Find_Owners_page()
	{
		driver=DriverFactory.initDriver();
		driver.get(ConfigReader.get("url")+"/owners/find");
		findOwnerPage=new FindOwnersPage(driver);
		String currUrl=driver.getCurrentUrl();
		logger.info("Navigated to Find Owners page. Current URL: {}", currUrl);
		Assert.assertTrue(currUrl.contains("/find"));
		basePage=new BasePage(driver);
	}
	@When("user provides valid last name and clicks Find Owner button")
	public void user_provides_valid_last_name_and_clicks_Find_Owner_button()
	{
		findOwnerPage.ownerDetails("SriRam");
		logger.info("Entered valid last name and clicked Find Owner button ");
	}
	@Then("the user details should be displayed on the Owner Information page")
	public void the_user_details_should_be_displayed_on_the_Owner_Information_page()
	{
		
		String ownerInfo = findOwnerPage.getOwnerInfoHeader();
		logger.info("Owner Information header text found: {}", ownerInfo);
		Assert.assertTrue(ownerInfo.contains("Owner"));
		
		Assert.assertTrue(findOwnerPage.isOwnerNamePresent("Anagha"));

	}
	
	/*-------------------------------------------------------------------------------------------------------------
	NEGATIVE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/
	@When("the user enters non existent user name")
	public void the_user_enters_non_existent_user_name()
	{
		findOwnerPage.ownerDetails("X");
		logger.info("Entered invalid last name and clicked Find Owner.");
	}
	@Then("the user will get field error")
	public void the_user_will_get_field_error()
	{
		String lastNameValError=driver.findElement(By.xpath("//span[@class='help-inline']//p")).getText();
		logger.info("Validation error displayed: {}", lastNameValError);
		Assert.assertTrue(lastNameValError.contains("has not been found"));
	}

	/*-------------------------------------------------------------------------------------------------------------
	EDGE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/
	@When("the user finds the same Owner name with different cases of {string}")
	public void the_user_finds_the_same_Owner_name_with_different_cases_of(String lastName) throws InterruptedException {
		Thread.sleep(100);
	    if (lastName == null || lastName.trim().isEmpty()) {
	        throw new IllegalArgumentException("Last name was not passed from feature file!");
	    }

	    logger.info("Entered case-variant last name", lastName);
	    findOwnerPage.ownerDetails(lastName);
	}

	@Then("the user should be able to get the owners")
	public void the_user_should_be_able_to_get_the_owners()
	{
		String ownerInfo = findOwnerPage.getOwnerInfoHeader();
		logger.info("Owner Information found for case-variant search: {}", ownerInfo);
		Assert.assertTrue(ownerInfo.contains("Owner"));
	}
}
