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


public class AddOwnersPage extends BasePage{
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddOwnersPage.class);	
	WebDriver driver;
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
	
	public void navigateToAddOwnerPage() {
		
	    driver.get(ConfigReader.get("url") + "/owners/new");
	    waitForElement(By.cssSelector("div.container.xd-container"));
	}
	
	public String addOwnerFromExcelRow(Map<String, String> data) throws InterruptedException, SQLException {
	    String firstname = data.get("firstname");
	    String lastname = data.get("lastname");
	    String address = data.get("address");
	    String city = data.get("city");
	    String telephone = data.get("telephone");

	    addOwnerDetails(firstname, lastname, address, city, telephone);

	    String successMsg = driver.findElement(By.id("success-message")).getText();
	    logger.info("Success message displayed: {}", successMsg);
	    Assert.assertTrue(successMsg.contains("New Owner Created"));

	    DBUtils.insertOwnerToDB(firstname, lastname, address, city, telephone);
	    logger.info("✔ Inserted into your DB as well.");

	    return firstname + " " + lastname;
	}

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
