package com.anagha.petclinic.stepdefinitions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.pages.AddOwnersPage;
import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class AddOwnerPageSteps {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddOwnerPageSteps.class);
	
	WebDriver driver;
	AddOwnersPage addOwnersPage;
	BasePage basePage;
	
	/*-------------------------------------------------------------------------------------------------------------
	POSITIVE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/
	@Given("the user is on the Add Owner page")
	public void the_user_is_on_the_Add_Owner_page()
	{
		driver=DriverFactory.initDriver();
		driver.get(ConfigReader.get("url")+"/owners/new");
		addOwnersPage=new AddOwnersPage(driver);
		basePage=new BasePage(driver);
		String currUrl=driver.getCurrentUrl();
		logger.info("Navigated to Add Owner page. Current URL: {}", currUrl);
		Assert.assertTrue(currUrl.contains("/new"));
	}
	@When("the user enters all valid owner details")
	public void the_user_enters_all_valid_owner_details()
	{
		addOwnersPage=new AddOwnersPage(driver);
		logger.info("Entering valid owner details and submitting form.");
		addOwnersPage.addOwnerDetails("Anagha", "SriRam", "Peepal Tree Apt", "BLR", "1234567890");
	}
	@Then("the new owner should be added successfully")
	public void the_new_owner_should_be_added_successfully()
	{
		logger.info("Verifying that new owner has been successfully added.");
		driver.findElement(By.xpath("//div[contains(@class, 'xd-container')]"));
	}
	@Then("the user should be able to edit the owner")
	public void the_user_should_be_able_to_edit_the_owner()
	{
		WebElement addOwnerBtn=driver.findElement(By.xpath("//a[text()='Edit Owner']"));
		logger.info("Checking if Edit Owner button is enabled.");
		if(!addOwnerBtn.isEnabled())
		{
			logger.warn("Edit Owner button is disabled.");
		}else 
		{
			logger.info("Edit Owner button is enabled.");

		}
	}
	@Then("the added information should be visible on the Owner Information page")
	public void the_added_information_should_be_visible_on_the_Owner_Information_page()
	{
		logger.info("Fetching and displaying Owner Information table data.");
		List<WebElement>headers=driver.findElements(By.xpath("//table/tbody/tr/th"));
		for(WebElement th: headers)
		{
			System.out.println(th.getText());
		}
		List<WebElement>ownerData=driver.findElements(By.xpath("//table/tbody/tr/td"));
		for(WebElement td: ownerData)
		{
			System.out.println(td.getText());
		}
	}
	@Then("a confirmation or success message should be displayed")
	public void a_confirmation_or_success_message_should_be_displayed()
	{
		String successMsg=driver.findElement(By.id("success-message")).getText();
		logger.info("Success message displayed: {}", successMsg);
		Assert.assertTrue(successMsg.contains("New Owner Created"));
	}
	@Then("the user should be able to add a new pet for the owner")
	public void the_user_should_be_able_to_add_a_new_pet_for_the_owner()
	{
		WebElement newPetBtn=driver.findElement(By.xpath("//a[text()='Add New Pet']"));
		logger.info("Checking if Add New Pet button is available.");
		if(!newPetBtn.isEnabled())
		{
			logger.warn("Add New Pet button is disabled.");
		}
		else
		{
			logger.info("Add New Pet button is enabled.");

		}
	}
	
	/*-------------------------------------------------------------------------------------------------------------
	NEGATIVE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/

	@When("the user keeps all the fields empty")
	public void the_user_keeps_all_the_fields_empty()
	{
		addOwnersPage=new AddOwnersPage(driver);
		logger.info("Submitting the Add Owner form with all fields empty.");
		addOwnersPage.addOwnerDetails(" ", " ", " ", " ", " ");
	}
	@Then("the user should get field validation error for all the required fields")
	public void the_user_should_get_field_validation_error_for_all_the_required_fields() 
	{
		logger.info("Checking for required field validation errors.");
		int count=0;
		List<WebElement> validationErrors=driver.findElements(By.xpath("//span[@class='help-inline']"));
		for(WebElement validationError: validationErrors)
			{
			String error=validationError.getText();
			logger.info("Validation error found: {}", error);
			Assert.assertTrue(error.contains("must not be blank"));
			count++;
			}
		if(count==5)
			{
			logger.info("All 5 required fields showed validation errors.");
			}
	}
	
	/*-------------------------------------------------------------------------------------------------------------
	EDGE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/
	
	@When("the user will add Name of five hundred characters and all other valid details")
	public void the_user_will_add_Name_of_five_hundred_characters_and_provide_all_other_valid_details()
	{
		logger.info("Entered Entering a 500-character long first name and remaining valid details and submitted form.");
		addOwnersPage.addOwnerDetails("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"SriRam", "Peepal Tree Apt", "BLR", "1234567890");
	}
	@Then("the user should get a error message")
	public void the_user_should_get_a_error_message()
	{
		String errorMsg=driver.findElement(By.xpath("//div[contains(@class,'xd-container')]")).getText();
		logger.info("Error message received: {}", errorMsg);
		Assert.assertTrue(errorMsg.contains("Something happened"));
	}

}
