package com.anagha.petclinic.stepdefinitions;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.pages.VeterinariansListPage;
import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/** Step definition class for Veterinarians List functionality
* Covers positive test cases for navigating to the Veterinarians page, verifying URL,
* checking table visibility, reading headers, and printing paginated list of veterinarians
* Uses POM class VeterinariansListPage, SLF4J logging, and WebDriver for browser interaction**/

public class VeterinariansListPageSteps {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VeterinariansListPageSteps.class);
	WebDriver driver;
	VeterinariansListPage vlistPage;
	BasePage basePage;
	
	// ------------------------ POSITIVE TEST CASES ------------------------
	
	// Launch the PetClinic home page and verify the application URL
	@Given("the user is on the Home page")
	public void the_user_is_on_the_Home_page()
	{
		driver=DriverFactory.getDriver();
		driver.get(ConfigReader.get("url"));
		logger.info("Navigating to Home page");
		vlistPage=new VeterinariansListPage(driver);
		basePage=new BasePage(driver);
	}
	
	// Click on the "Veterinarians" menu item to view the list
	@When("the user clicks on Veterinarians option")
	public void the_user_clicks_on_Veterinarians_option()
	{
		driver.findElement(By.xpath("//a[@title='veterinarians']")).click(); 
		logger.info("Clicked on Veterinarians menu");
	}
	
	// Validate that the user is redirected to the Veterinarians page
	@Then("the user should be redirected to Veterinarians page")
	public void the_user_should_be_redirected_to_Veterinarians_page()
	{
		logger.info("Current URL: " + driver.getCurrentUrl());
		Assert.assertTrue(driver.getCurrentUrl().contains("vets"));
	}
	
	// Verify that the full list of Veterinarians is displayed correctly across all pages
	@Then("the user should be able to view the list of all Veterinarians")
	public void the_user_should_be_able_to_view_the_list_of_all_Veterinarians() throws InterruptedException {
	    Assert.assertTrue("Veterinarians table not found", vlistPage.isVeterinariansTablePresent());
	    List<WebElement> headers = vlistPage.getTableHeaders();
	    Assert.assertFalse("Table headers are missing", headers.isEmpty());
	    for (WebElement header : headers) {
	        logger.info("Table Header: {}", header.getText());
	    }

	    vlistPage.printVeterinariansListAcrossPages();
	}
}
