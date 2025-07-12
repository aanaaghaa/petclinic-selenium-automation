package com.anagha.petclinic.pages;

import java.sql.SQLException;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.utils.ConfigReader;
import com.anagha.petclinic.utils.DBUtils;

import junit.framework.Assert;

import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Model class for the "Add Owner" functionality of the PetClinic application.
 * Navigates to the Add Owner page.
 * Interacts with the Add Owner form fields using PageFactory.
 * Fills out the form and submits data via UI.
 * Inserts and verifies owner data in the database for consistency.**/

public class AddOwnersPage extends BasePage{
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddOwnersPage.class);	
	WebDriver driver;
	
	 // Web elements on the Add Owner form, initialized using PageFactory
	@FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "address")
    private WebElement addressField;

    @FindBy(id = "city")
    private WebElement cityField;

    @FindBy(id = "telephone")
    private WebElement telephoneField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;
	
	public AddOwnersPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
		  
	}
	
	//Re-usable method to navigate to Add owner page
	public void navigateToAddOwnerPage() {
		
	    driver.get(ConfigReader.get("url") + "/owners/new");
	    waitForElement(By.cssSelector("div.container.xd-container"));
	}
	
	// Reads one row of test data from Excel and uses it to add a new owner in the UI.
	// Also inserts the same data into the database for validation and consistency across layers.
	public String addOwnerFromExcelRow(Map<String, String> data) throws InterruptedException, SQLException {
	    String firstname = data.get("firstname");
	    String lastname = data.get("lastname");
	    String address = data.get("address");
	    String city = data.get("city");
	    String telephone = data.get("telephone");

	    addOwnerDetails(firstname, lastname, address, city, telephone);
	    waitForElementDisappear(By.id("success-message"));
	    String successMsg = driver.findElement(By.id("success-message")).getText();
	    logger.info("Success message displayed: {}", successMsg);
	    Assert.assertTrue(successMsg.contains("New Owner Created"));

	    DBUtils.insertOwnerToDB(firstname, lastname, address, city, telephone);
	    logger.info("Inserted into your DB as well.");

	    return firstname + " " + lastname;
	}
	//Fill the owner details in Add Owner Page
	public void addOwnerDetails(String firstName, String lastName, String address, String city, String telephone) throws InterruptedException
	{
		waitForElement(By.id("add-owner-form"));
		   firstNameField.sendKeys(firstName != null ? firstName : "");
		    lastNameField.sendKeys(lastName != null ? lastName : "");
		    addressField.sendKeys(address != null ? address : "");
		    cityField.sendKeys(city != null ? city : "");
		    telephoneField.sendKeys(telephone != null ? telephone : "");
		    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
		    waitForElementToBeClickable(By.xpath("//button[@type='submit']"));
		    submitButton.click(); 
	}
}
