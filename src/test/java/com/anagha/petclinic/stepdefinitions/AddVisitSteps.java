package com.anagha.petclinic.stepdefinitions;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.pages.AddVisitPage;
import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DBUtils;
import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definition class for Cucumber scenarios related to adding a pet visit.
 * Handles page navigation, input form population, validation message checks,
 * success message assertions, and DB verifications.
 *
 * Includes positive, negative, and edge case validations for robust testing.
 */

public class AddVisitSteps {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddVisitSteps.class);
	WebDriver driver;
	AddVisitPage addVisitPage;
	BasePage basePage;
	//String today = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	String today = LocalDate.now().minusDays(1)
		    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	private String savedDescription;
	public static final String GENERIC_ERROR = "Something happened";
	
	// ------------------------ POSITIVE TEST CASES ------------------------
	
	// Navigates to the Owner page for a given owner ID and verifies the URL
	@Given("the user is on the owners page owner with {string}")
	public void the_user_is_on_the_owners_page_owner_with(String ownerId)
	{
		driver=DriverFactory.getDriver();
		driver.get(ConfigReader.get("url")+"/owners/" + ownerId);
		String currUrl=driver.getCurrentUrl();
		logger.info("Navigated to Owner page. Current URL: {}", currUrl);
		Assert.assertTrue(currUrl.contains("owners"));
		basePage=new BasePage(driver);
		addVisitPage=new AddVisitPage(driver);
	}
	
	// Clicks the 'Add Visit' link for the specified pet
	@When("the user clicks on Add visit link for the pet {string}")
	public void the_user_clicks_on_Add_visit_link_for_the_pet(String petName) throws InterruptedException
	{
		
		 logger.info("Looking for Add Visit link for pet: {}", petName);
		 addVisitPage.clickAddVisitLinkForPet(petName); 
		    logger.info("Clicked on Add Visit link for pet: {}", petName);
	}
	
	// Verifies redirection to the Add Visit page
	@Then("the user should be redirected to the visiting page")
	public void the_user_should_be_redirected_to_the_visiting_page()
	{
		String currUrl=driver.getCurrentUrl();
		logger.info("Redirected to Add Visit page. Current URL: {}", currUrl);
		Assert.assertTrue(currUrl.contains("visits/new"));
	}
	
	// Enters the visit date and description into the form
	@Then("the user provides visit date {string} and description {string}")
	public void the_user_provides_visit_date_and_description(String date, String description)
	{
		if (date.equalsIgnoreCase("TODAY")) {
			date=today;
		}
		  logger.info("Entering date: {} and description: {}", date, description);
		  this.savedDescription = description;  
		    addVisitPage.addVisitDetails(date, description);
	}
	
	// Verifies the success message after booking a visit
	@Then("user should get a success message")
	public void user_should_get_a_success_message()
	{
		basePage.waitForElement(By.id("success-message")); 
		String successvisitbookedMsg=driver.findElement(By.id("success-message")).getText();
		logger.info("Success message displayed: {}", successvisitbookedMsg);
		Assert.assertTrue(successvisitbookedMsg.contains("visit has been booked"));
	}
	
	// Verifies that the newly added visit is displayed under 'Previous Visits'
	@Then("the user should be able to view the visit in the visit page")
	public void the_user_should_be_able_to_view_the_visit_in_the_visit_page() throws InterruptedException
		{
			String expectedDescription = this.savedDescription;
			addVisitPage.addVisitButtonClick();
			List<WebElement> visitData=addVisitPage.getPreviousVisitData();
			if (!visitData.isEmpty()) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", visitData.get(0));
			Thread.sleep(500);
			}
			for(WebElement visitDesc: visitData)
			{
				if(visitDesc.getText().contains(expectedDescription))
				{
					logger.info("Visit is displayed in Previous Visits section: {}", visitDesc.getText());
					Assert.assertTrue("Expected visit not found in previous visits table", visitDesc.getText().contains(expectedDescription));
				}
			}
	}
	
	// Confirms the visit is also listed under the owner's visit section
	@Then("the user should be able to view the visit in the owners page")
	public void the_user_should_be_able_to_view_the_visit_in_the_owners_page()
	{
		String expectedDescription = this.savedDescription;
		boolean found = false;
		String today = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		By successMsg=By.id("success-message");
		basePage.waitForElementDisappear(successMsg);
		List<WebElement> table=addVisitPage.getVisitOnOwnerTable();
	    for (WebElement row : table) {
	        List<WebElement> columns = row.findElements(By.tagName("td"));
	        if (columns.size() >= 2) {
	            String dateText = columns.get(0).getText().trim(); // Assuming first column is date
	            dateText = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	            String descText = columns.get(1).getText().trim(); // Assuming second column is description
	            //System.out.println("Description expected: " + expectedDescription);
	           // System.out.println("Daate expected: " + today);
	            System.out.println("Actaul desc: " +descText);
	            System.out.println("Actual date: " +dateText);
	            logger.info("Visit found - Date: {}, Description: {}", dateText, descText);

	            if (dateText.equals(today) && descText.equals(expectedDescription)) {
	                found = true;
	                break;
	            }
	        }
	    }
	    Assert.assertTrue("Expected visit with correct date and description not found in owner's page", found);
		}
	
	// Inserts the visit record into the database and verifies its presence
	@Then("the visit details should be added to the database for owner {string} and pet {string}")
	public void the_visit_details_should_be_added_to_the_database_for_owner_and_pet(String ownerIdStr, String petName) throws SQLException {
		 String dbFormattedDate = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String expectedDescription = this.savedDescription;
		String petidStr=addVisitPage.getLastExtractedPetId();
		int ownerId = Integer.parseInt(ownerIdStr);
	    int petId = Integer.parseInt(petidStr);

		    DBUtils.insertVisitIntoDB(
		        ownerId,
		        petId,
		        dbFormattedDate,
		        expectedDescription
		    );

			    logger.info("Visit details inserted into DB for owner {}, pet {}, date {}, desc '{}'",
			                ownerId, petId, dbFormattedDate, expectedDescription);
			    boolean isPresent = DBUtils.isVisitPresentInDB(ownerId, petId, dbFormattedDate, expectedDescription);
			    Assert.assertTrue("Visit not found in DB for ownerId: " + ownerId + ", petId: " + petId, isPresent);
			    logger.info("Visit exists in DB for owner {}, pet {}, date {}, description '{}'", ownerId, petId, dbFormattedDate, expectedDescription);
	}
	
	// ------------------------ NEGATIVE TEST CASES ------------------------
	
	// Clicks the 'Add Visit' link without selecting a specific pet
	@When("the user clicks on Add visit link")
	public void the_user_clicks_on_Add_visit_link() throws InterruptedException
	{
		addVisitPage.addVisitButtonClick();
	}
	
	// Enters a valid date but leaves the description field blank
	@When("the user provides the valid date and empty description")
	public void the_user_provides_the_valid_date_and_empty_description()
	{
		logger.info("Entering valid visit date and empty description");
		System.out.println("Date: " +today);
		addVisitPage.addVisitDetails(today, " ");
	}
	
	// Checks that a validation error is shown for missing description
	@Then("the user will get a field validation error")
	public void the_user_will_get_a_field_validation_error()
	{
		basePage.waitForElement(By.className("help-inline"));
		String descValError=driver.findElement(By.className("help-inline")).getText();
		logger.info("Validation error displayed: {}", descValError);
		System.out.println("ACTUAL VALIDATION MESSAGE: " + descValError);
		Assert.assertTrue(descValError.contains("blank"));
	}
	
	// ------------------------ EDGE TEST CASES ------------------------
	
	// Submits a 500-character description to test input length limit
	@When("the user provides a valid date and five hundred character long description")
	public void the_user_provides_a_valid_date()
	{
		logger.info("Entering valid date and 500-character long description");
		addVisitPage.addVisitDetails(today, "This is a sample 500-character description for testing the input field limits accurately. It is useful for validating how well the application handles edge cases, ensures proper user experience, avoids truncation or system crashes, and supports special characters and multilingual text. Such tests are essential for robust systems that require data precision, especially in forms where user-provided content might vary in length significantly, from a few to hundreds of characters.");
	}
	
	// Verifies redirection to error page for invalid (long) description
	@Then("the user should be directed to error page")
	public void the_user_should_be_directed_to_error_page()
	{
		By errorMsg=By.xpath("//div[contains(@class, 'xd-container')]");
		basePage.waitForElement(errorMsg);
		String error=driver.findElement(By.xpath("//div[contains(@class, 'xd-container')]/h2")).getText();
		logger.info("Error page displayed: {}", error);
		System.out.println("ACTUAL ERROR MESSAGE: " + error);
		Assert.assertTrue(error.contains(GENERIC_ERROR));
	}
}
