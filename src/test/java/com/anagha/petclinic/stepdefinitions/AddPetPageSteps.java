package com.anagha.petclinic.stepdefinitions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.pages.AddPetPage;
import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DriverFactory;
import com.anagha.petclinic.utils.ExcelUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

/**
 * Step definition class for Cucumber scenarios related to adding a pet.
 * Implements both positive test flows using data from Excel and DB.
 * Implements negative and edge cases.
 * Verifies UI validations, DB consistency, and element visibility after form submission.
 */

public class AddPetPageSteps {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddPetPageSteps.class);
	WebDriver driver;
	AddPetPage addPetPage;
	WebDriverWait wait;
	BasePage basePage;
	List<String> submittedPets = new ArrayList<>();

	// ------------------------ POSITIVE TEST CASES ------------------------
	
	// Launches the Add New Pet page for a given owner ID
	@Given("the user is on Add new pet page for owner with id {string}")
public void the_user_is_on_Add_new_pet_page_for_owner_with_id(String ownerId)
{
		driver=DriverFactory.getDriver();
		addPetPage=new AddPetPage(driver);
		basePage=new BasePage(driver);
		driver.get(ConfigReader.get("url")+"/owners/" + ownerId + "/pets/new");
		logger.info("Navigated to Add New Pet page. Current URL: {}", driver.getCurrentUrl());
		}
	
	// Reads pet data from Excel and adds each pet for its corresponding owner
	@When("the user provides valid pet details from Excel and confirm success")
	public void the_user_provides_valid_pet_details_from_Excel_and_confirm_success() throws SQLException
	{
		
		String filePath = ConfigReader.get("testdata_path_pet");
	    List<Map<String, String>> petList = ExcelUtils.getPetExcelData(filePath, "Sheet1");

	    for (Map<String, String> pet : petList) {
			 String ownerid = pet.get("ownerid");
			    driver.get(ConfigReader.get("url") + "/owners/" + ownerid + "/pets/new");
			    addPetPage.addPetFromExcel(pet);
			    addPetPage.addPetToDB(pet);
	        logger.info("Pet added: " + pet.get("petname"));
	        String petName = pet.get("petname");
		    String ownerId = pet.get("ownerid");
		    submittedPets.add(petName.toLowerCase() + "_" + ownerId);
	    }
	}
	
	// Validates that the pet is visible on the UI and part of the Owner Information page
	@Then("all the pets added through UI should be present in the database")
	public void all_the_pets_added_through_UI_should_be_present_in_the_database()
	{
		
		 String lastPetIdentifier = submittedPets.get(submittedPets.size() - 1);
		 String petName = lastPetIdentifier.split("_")[0];

		    basePage.waitForElement(By.cssSelector("table.table"));
		    WebElement table = driver.findElement(By.xpath("//h2[contains(text(), 'Pets and Visits')]/following-sibling::table"));
		    String tableText = table.getText().toLowerCase();

		    Assert.assertTrue("Pet not found on page: " + petName, tableText.contains(petName));
		    logger.info("Pet '{}' is visible on the Owner Information page.", petName);

		    basePage.waitForElement(By.cssSelector("h2"));
		    String headerText = driver.findElement(By.cssSelector("h2")).getText();
		    Assert.assertTrue("Owner Information page header missing!", headerText.contains("Owner Information"));
	}
	
	// Verifies that the "Edit Pet" button is enabled after adding a pet
	@Then("the owner should be able to edit pet details")
	public void the_owner_should_be_able_to_edit_pet_details()
	{
		WebElement editPetBtn=driver.findElement(By.xpath("//a[text()='Edit Pet']"));
		logger.info("Checking if Edit Pet button is enabled.");
		if(!editPetBtn.isEnabled())
		{
			logger.warn("Edit Pet button is disabled.");
			Assert.assertTrue("Expected 'Edit Pet' to be enabled, but it is disabled", editPetBtn.isEnabled());
		}else
		{
			logger.info("Edit Pet button is enabled.");
			Assert.assertTrue("Edit Pet button is not disabled", editPetBtn.isEnabled());
		}
	}
	
	// Verifies that the "Add Visit" button is enabled for the added pet
	@Then("the Add Visit button should be enabled for the pet")
	public void the_Add_Visit_button_should_be_enabled_for_the_pet()
	{
		WebElement addVisitBtn=driver.findElement(By.xpath("//a[text()='Add Visit']"));
		basePage.waitForElement(By.xpath("//a[text()='Add Visit']"));
		logger.info("Checking if Add Visit button is enabled.");
		if(!addVisitBtn.isEnabled())
		{
			 logger.warn("Add Visit button is not clickable.");
			 Assert.assertTrue("Expected 'Add Visit' to be enabled, but it is disabled", addVisitBtn.isDisplayed());
		}else
		{
			  logger.info("Add Visit button is enabled.");
			  Assert.assertTrue("Add Visit button is not disabled", addVisitBtn.isDisplayed());
		}
		
	}
	
	// ------------------------ NEGATIVE TEST CASES ------------------------
	
	// Tries submitting the Add Pet form without a pet name
	@When("the user does not provide {string} and valid {string} and {string}")
	public void the_user_does_not_provide_and_valid_and(String petName, String dob, String petType)
	{
		//System.out.println(petName + " " + dob + " "+ petType);
		logger.info("Submitting Pet form without pet name.");
		addPetPage.addPetDetails(petName, dob, petType);
		addPetPage.clickAddPetButton();
	}
	
	// Checks for validation error when pet name is missing
	@Then("the user should get a field validation error")
	public void the_user_should_get_a_field_validation_error()
	{
		basePage.waitForElement(By.className("help-inline"));
		String validationError=driver.findElement(By.className("help-inline")).getText();
		logger.info("Validation error displayed: {}", validationError);
		Assert.assertTrue(validationError.contains("is required"));
	}
		
	// ------------------------ EDGE TEST CASES ------------------------

	// Submits the Add Pet form with a future date of birth
	@When("the user provides all valid {string} and future {string} and valid {string}")
	public void the_user_provides_all_valid_and_future_and_valid(String petName, String dob, String petType)
	{
		logger.info("Entering valid pet name and type with DOB in future");
		System.out.println(petName + " " + dob + " "+ petType);
		addPetPage.addPetDetails(petName, dob, petType);
		addPetPage.clickAddPetButton();
	}
	
	// Checks for validation error when future date of birth is used
	@Then("the user should get a validation error")
	public void the_user_should_get_a_validation_error()
	{
		basePage.waitForElement(By.className("help-inline"));
		String validationError=driver.findElement(By.className("help-inline")).getText();
		System.out.println("Error Message in Actual: " + validationError);
		logger.info("Validation error received for future DOB: {}", validationError);
		Assert.assertTrue(validationError.contains("invalid date"));
	}
}