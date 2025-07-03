package com.anagha.petclinic.stepdefinitions;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.pages.HomePage;
import com.anagha.petclinic.pages.VeterinariansListPage;
import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class VeterinariansListPageSteps {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VeterinariansListPageSteps.class);
	WebDriver driver;
	VeterinariansListPage vlistPage;
	BasePage basePage;
	
	@Given("the user is on the Home page")
	public void the_user_is_on_the_Home_page()
	{
		driver=DriverFactory.initDriver();
		driver.get(ConfigReader.get("url"));
		if (ConfigReader.get("url") == null) {
		    logger.error("Base URL is not configured in config.properties.");
		}
		driver = DriverFactory.getDriver();
		if (driver == null) {
		    logger.error("WebDriver initialization failed.");
		}

		logger.info("Navigating to Home page");
		vlistPage=new VeterinariansListPage(driver);
		basePage=new BasePage(driver);
	}
	@When("the user clicks on Veterinarians option")
	public void the_user_clicks_on_Veterinarians_option()
	{
		driver.findElement(By.xpath("//a[@title='veterinarians']")).click();
		logger.info("Clicked on Veterinarians menu");
	}
	@Then("the user should be redirected to Veterinarians page")
	public void the_user_should_be_redirected_to_Veterinarians_page()
	{
		logger.info("Current URL: " + driver.getCurrentUrl());
		Assert.assertTrue(driver.getCurrentUrl().contains("vets"));
	}
	@Then("the user should be able to view the list of all Veterinarians")
	public void the_user_should_be_able_to_view_the_list_of_all_Veterinarians() throws InterruptedException {
	    // Print table headers
		logger.info("Fetching table headers");
	    List<WebElement> headers = driver.findElements(By.xpath("//table[@id='vets']/thead/tr/th"));
	    for (WebElement header : headers) {
	        System.out.print(header.getText() + " | ");
	    }
	    if (headers.isEmpty()) {
	        logger.error("No table headers found on Veterinarians page.");
	    }
	    
	    // Loop through all pages
	    boolean hasNextPage = true;
	    while (hasNextPage) {
	        // Print current page's rows
	        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='vets']/tbody/tr"));
	        logger.info("Found " + rows.size() + " rows in this page.");
	        for (WebElement row : rows) {
	        	
	            System.out.println(row.getText());
	        }
	        logger.info("Processing next page of Veterinarians list");
	        if (rows.isEmpty()) {
	            logger.error("No veterinarian rows found on the current page.");
	        }

	        // Try to click "Next" if available
	        List<WebElement> nextButtons = driver.findElements(By.xpath("//a[@title='Next']"));
	        if (!nextButtons.isEmpty()) {
	            WebElement nextBtn = nextButtons.get(0);
	            if (nextBtn.isDisplayed() && nextBtn.isEnabled()) {
	                nextBtn.click();
	                Thread.sleep(1000); // Let page load
	            } else {
	                hasNextPage = false;
	            }
	        } else {
	            hasNextPage = false;
	            logger.info("No more pages to process");
	        }
	    }
	}
}
