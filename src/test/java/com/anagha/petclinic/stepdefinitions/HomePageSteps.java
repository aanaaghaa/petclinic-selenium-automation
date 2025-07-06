package com.anagha.petclinic.stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.pages.HomePage;
import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

public class HomePageSteps {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HomePageSteps.class);
	
	WebDriver driver;
	HomePage homePage;
	BasePage basePage;
	
	@Given("the user launches the petclinic website")
	public void the_user_launches_the_petclinic_website()
	{
		driver=DriverFactory.getDriver();
		String url = ConfigReader.get("url");
		logger.info("Launching PetClinic website at URL: " + url);
		if (driver == null || url == null) {
		    logger.error("Driver is null or URL is missing.");
		    Assert.fail("Driver or URL is null.");
		}
		driver.get(url);
		homePage=new HomePage(driver);
		basePage=new BasePage(driver);
	}
	@When("user views the home page")
	public void user_views_the_home_page()
	{
		logger.info("Viewing Home page. Current URL: " + driver.getCurrentUrl());
		driver.getCurrentUrl();
	}
	@Then("the title should be {string}")
	public void the_title_should_be(String expectedTitle)
	{
			logger.info("Verifying title. Expected: " + expectedTitle + ", Actual: " + driver.getTitle());

		if (!driver.getTitle().equals(expectedTitle)) {
		    logger.error("Title mismatch! Actual: " + driver.getTitle());
		}
		Assert.assertEquals("Homepage title mismatch", expectedTitle, driver.getTitle());
	}
	@Then("the logo should be displayed")
	public void the_logo_should_be_displayed()
	{
		
		 WebElement logo = DriverFactory.getDriver().findElement(By.xpath("//img[@class='logo']"));
		 logger.info("Checking if logo is visible on the homepage.");

			if (!logo.isDisplayed()) {
			    logger.error("Logo not displayed on the homepage.");
			}
		    Assert.assertTrue("Logo is not displayed", logo.isDisplayed());
	}
	@Then("the menus should contain {string}")
	public void the_menus_should_contain(String expectedMenus) {
		
		logger.info("Waiting for visibility of menu items.");
		By menuLocator = By.xpath("//div[@id='main-navbar']//li/a/span[not(@class)]");
	    basePage.waitForAllElements(menuLocator); 
	    List<WebElement> menuElements = driver.findElements(menuLocator);
	    List<String> actualMenus = new ArrayList<>();
	    for (WebElement menu : menuElements) {
	        actualMenus.add(menu.getText().trim());
	    }

	    List<String> expectedMenuList = Arrays.asList(expectedMenus.split(",\\s*"));
	    logger.info("Expected Menus: " + expectedMenuList);
	    logger.info("Actual Menus: " + actualMenus);
	    
	    for (String expected : expectedMenuList) {
	    	boolean found = actualMenus.stream().anyMatch(actual -> actual.equalsIgnoreCase(expected));

	        if (!found) {
	            logger.error("Menu not found (case-insensitive): " + expected);
	        }
	        Assert.assertTrue("Menu not found (case-insensitive): " + expected, found);
	    }
	}


}
