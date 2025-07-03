package com.anagha.petclinic.stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.pages.AddPetPage;
import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class AddPetPageSteps {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddPetPageSteps.class);
	WebDriver driver;
	AddPetPage addPetPage;
	WebDriverWait wait;
	BasePage basePage;

	/*-------------------------------------------------------------------------------------------------------------
	POSITIVE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/
	
	@Given("the user is on Add new pet page")
public void the_user_is_on_Add_new_pet_page()
{
		driver = DriverFactory.initDriver();
		addPetPage=new AddPetPage(driver);
		basePage=new BasePage(driver);
		driver.get(ConfigReader.get("url")+"/owners/9/pets/new");
		logger.info("Navigated to Add New Pet page. Current URL: {}", driver.getCurrentUrl());
		}
	
	@When("the user provides valid pet details")
	public void the_user_provides_valid_pet_details()
	{
		logger.info("Entering valid pet details: name='doog', birthDate='11-01-2025', type='dog'.");
		addPetPage.addPetDetails("doog", "11-01-2025", "dog");
	}
	@When("clicks on the Add Pet button")
	public void clicks_on_the_Add_Pet_button()
	{
		
		logger.info("Clicked on Add Pet button.");
		addPetPage.addPetButtonClick();
	}
	
	@Then("the pet should be added under the owner")
	public void the_pet_should_be_added_under_the_owner()
	{
		driver.findElement(By.xpath("//h2[text()='Pets and Visits']"));
		logger.info("Verified that pet has been added under Pets and Visits section.");
	}
	@Then("the owner should be able to edit pet details")
	public void the_owner_should_be_able_to_edit_pet_details()
	{
		WebElement editPetBtn=driver.findElement(By.xpath("//a[text()='Edit Pet']"));
		logger.info("Checking if Edit Pet button is enabled.");
		if(!editPetBtn.isEnabled())
		{
			System.out.println("Edit Pet is not clickable");
		}else
		{
			logger.info("Edit Pet button is enabled.");
		}
	}
	@Then("the Add Visit button should be enabled for the pet")
	public void the_Add_Visit_button_should_be_enabled_for_the_pet()
	{
		WebElement addVisitBtn=driver.findElement(By.xpath("//a[text()='Add Visit']"));
		logger.info("Checking if Add Visit button is enabled.");
		if(!addVisitBtn.isEnabled())
		{
			 logger.warn("Add Visit button is not clickable.");
		}else
		{
			  logger.info("Add Visit button is enabled.");
		}
		
	}
	@Then("the success message New Pet has been Added should be displayed")
	public void the_success_message_New_Pet_has_been_Added_should_be_displayed()
	{
		By successMsg=By.id("success-message");
		basePage.waitForElement(successMsg);
		String popUpMsg=driver.findElement(successMsg).getText();
		logger.info("Success message displayed: {}", popUpMsg);
		Assert.assertTrue("Expected success message not found!", popUpMsg.contains("New Pet has been Added"));
	}
	
	/*-------------------------------------------------------------------------------------------------------------
	NEGATIVE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/
	
	@When("the user provides all the valid details except pet name")
	public void the_user_provides_all_the_valid_details_except_pet_name()
	{
		logger.info("Submitting Add Pet form without pet name.");
		addPetPage.addPetDetails(" ","11-01-2025", "dog");
		addPetPage.addPetButtonClick();
	}
	@Then("the user should get a field validation error")
	public void the_user_should_get_a_field_validation_error()
	{
		String validationError=driver.findElement(By.className("help-inline")).getText();
		logger.info("Validation error displayed: {}", validationError);
		Assert.assertTrue(validationError.contains("is required"));
	}
	
	/*-------------------------------------------------------------------------------------------------------------
	EDGE TEST CASE
	--------------------------------------------------------------------------------------------------------------*/

	@When("the user provides all the valid details and date of birth is in future")
	public void the_user_provides_all_the_valid_details_and_date_of_birth_is_in_future()
	{
		logger.info("Entering valid pet name and type. Name: 'dogg', Type: 'dog', future birth date: 11-01-2026 and submitting form.");
		addPetPage.addPetDetails("dooog", "11-01-2026", "dog");
		addPetPage.addPetButtonClick();
	}
	@Then("the user should get a validation error")
	public void the_user_should_get_a_validation_error()
	{
		String validationError=driver.findElement(By.className("help-inline")).getText();
		logger.info("Validation error received for future DOB: {}", validationError);
		Assert.assertTrue(validationError.contains("invalid date"));
	}
}
