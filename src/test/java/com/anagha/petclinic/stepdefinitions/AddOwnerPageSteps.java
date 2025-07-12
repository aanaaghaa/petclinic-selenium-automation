package com.anagha.petclinic.stepdefinitions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.pages.AddOwnersPage;
import com.anagha.petclinic.utils.DriverFactory;
import com.anagha.petclinic.utils.ExcelUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DBUtils;

/**
 * Step Definition class for Add Owner feature of the PetClinic application.
 * Handles the navigation and interaction with the Add Owner page using Cucumber steps.
 * Submits valid and invalid data from Excel and UI to validate positive, negative, and edge scenarios.
 * Verifies that data submitted through the UI is reflected in the database.
 * Ensures proper navigation and UI behavior like error handling, button state validation, etc.
 **/

public class AddOwnerPageSteps {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddOwnerPageSteps.class);
	
	WebDriver driver;
	AddOwnersPage addOwnersPage;
	BasePage basePage;
	List<String> submittedOwners = new ArrayList<>();
	
	// ------------------------ POSITIVE TEST CASES ------------------------
	
	// Navigate to Add Owner page and verify the current URL
	@Given("the user is on the Add Owner page")
	public void the_user_is_on_the_Add_Owner_page()
	{
		driver=DriverFactory.getDriver();
		addOwnersPage=new AddOwnersPage(driver);
		basePage=new BasePage(driver);
		addOwnersPage.navigateToAddOwnerPage();
		String currUrl=driver.getCurrentUrl();
		logger.info("Navigated to Add Owner page. Current URL: {}", currUrl);
		Assert.assertTrue(currUrl.contains("/new"));
	}
	
	// Read test data from Excel and submit each owner via UI
	@When("the user enters all valid owner details from Excel and confirm success")
	public void the_user_enters_all_valid_owner_details_from_Excel_and_confirm_success() throws SQLException, InterruptedException
	{
		logger.info("Entering valid owner details and submitting form.");

		String filePath = ConfigReader.get("testdata_path_owner");
	    List<Map<String, String>> owners = ExcelUtils.getExcelData(filePath, "Sheet1");

	    for (Map<String, String> data : owners) {
	        addOwnersPage.navigateToAddOwnerPage();   
	        String fullName = addOwnersPage.addOwnerFromExcelRow(data);
	        submittedOwners.add(fullName);
	    }
 	}
	
	// Verify added owners exist in the database
	@Then("all the owners added through UI should be present in the database")
	public void all_owners_added_through_UI_should_be_present_in_the_database() throws SQLException {
		 List<String> dbOwners = DBUtils.getAllOwnerNames();

		    for (String fullName : submittedOwners) {
		        Assert.assertTrue("Owner missing in DB: " + fullName, dbOwners.contains(fullName));
		        logger.info("Owner '{}' exists in DB.", fullName);
		    }
	}
	
	//Once the owner is successfully added, user will be redirected to Owner Info page and this method checks for the same
	@Then("the added information should be visible on the Owner Information page")
	public void the_added_information_should_be_visible_on_the_Owner_Information_page() throws InterruptedException
	{
		  String lastOwnerFullName  = submittedOwners.get(submittedOwners.size() - 1);
		  basePage.waitForElement(By.cssSelector("table.table"));
		  WebElement ownerTable=driver.findElement(By.cssSelector("table.table"));
		  String tableText = ownerTable.getText().toLowerCase();
		  Assert.assertTrue("Owner name not found on Owner Information page!",
		            tableText.contains(lastOwnerFullName.toLowerCase()));
		    logger.info("Owner '{}' is visible on the Owner Information page.", lastOwnerFullName);
		    basePage.waitForElement(By.cssSelector("h2"));
		    driver.findElement(By.cssSelector("h2")).getText().contains("Owner Information");
	}
	
	//The successfully added owner should be able to edit his/her information for which the Edit Owner button should be Enabled
	@Then("the user should be able to edit the owner")
	public void the_user_should_be_able_to_edit_the_owner()
	{
		WebElement editOwnerBtn=driver.findElement(By.xpath("//a[text()='Edit Owner']"));
		logger.info("Checking if Edit Owner button is enabled.");
		if(!editOwnerBtn.isEnabled())
		{
			
			logger.warn("Edit Owner button is disabled.");
			Assert.assertTrue("Expected Edit Owner button to be enabled, but it was disabled", editOwnerBtn.isEnabled());
		}else 
		{
			logger.info("Edit Owner button is enabled.");
			Assert.assertTrue("Edit button enabled", editOwnerBtn.isEnabled());
		}
	}
	
	// Verify 'Add New Pet' button is enabled for the newly added owner
	@Then("the user should be able to add a new pet for the owner")
	public void the_user_should_be_able_to_add_a_new_pet_for_the_owner()
	{
		WebElement newPetBtn=driver.findElement(By.xpath("//a[text()='Add New Pet']"));
		logger.info("Checking if Add New Pet button is available.");
		if(!newPetBtn.isEnabled())
		{
			logger.warn("Add New Pet button is disabled.");
			Assert.assertTrue("Expected 'Add New Pet' button to be enabled, but it was disabled", newPetBtn.isEnabled());
		}
		else
		{
			logger.info("Add New Pet button is enabled.");
			Assert.assertTrue("Add New button is not disabled", newPetBtn.isEnabled());
		}
		}
	
	// ------------------------ NEGATIVE TEST CASES ------------------------

	//Owner is clicking on the Add Owner without filling mandatory fields 
	@When("the user keeps {string} {string} {string} {string} {string} fields empty")
	public void the_user_keeps_fields_empty(String firstName, String lastName, String address, String city, String telephone) throws InterruptedException
	{
		addOwnersPage=new AddOwnersPage(driver);
		logger.info("Submitting the Add Owner form with empty fields");
		addOwnersPage.addOwnerDetails(firstName, lastName, address, city, telephone);
	}
	
	
	//Verify validation error for all the fields
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
	
	// ------------------------ EDGE TEST CASES ------------------------
	
	//Owner adds a very huge name of 500 character
	@When("the user will add {string} of five hundred characters and all other valid details like {string} {string} {string} {string}")
	public void the_user_will_add_of_five_hundred_characters_and_all_other_valid_details_like(String firstName, String lastName, String address, String city, String telephone) throws InterruptedException
	{
		logger.info("Entered Entering a 500-character long first name and remaining valid details and submitted form.");
		addOwnersPage.addOwnerDetails(firstName, lastName, address, city, telephone);
	}
	
	//User will be navigated to the error page
	@Then("the user should get a error message")
	public void the_user_should_get_a_error_message() throws InterruptedException
	{
		basePage.waitForElement(By.xpath("//div[contains(@class,'xd-container')]"));
		Thread.sleep(1000);
		String errorMsg=driver.findElement(By.xpath("//div[contains(@class,'xd-container')]")).getText();
		logger.info("Error message received: {}", errorMsg);
		Assert.assertTrue(errorMsg.contains("Something happened"));
	}
}
