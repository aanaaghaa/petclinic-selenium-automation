package com.anagha.petclinic.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;

	/**Page Object Model (POM) class for the Find Owners page
	* Includes reusable methods to search by last name, validate owner info, and check field errors
	* Uses XPath locators and integrates with BasePage for synchronization**/

public class FindOwnersPage extends BasePage{
	WebDriver driver;
	
	public FindOwnersPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
	}
	
	//Reusable method to get the owner details
	public void ownerDetails(String lastName)
	{
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		driver.findElement(By.xpath("//button[text()='Find Owner']")).click();
	}
	
	//Reusable method to get the Owner Page Header for validatioon
	public String getOwnerInfoHeader() {
		By headerLocator = By.xpath("//h2[contains(text(),'Owner')]");
		waitForElement(headerLocator); 
		return driver.findElement(headerLocator).getText();
	}
	
	//Verify if the owner information is matching with the data provided by the user by checking its name field 
	public boolean isOwnerNamePresent(String name) {
		boolean nameFetched = false;
	    List<WebElement> data = driver.findElements(By.xpath("//th//ancestor::thead/following-sibling::tbody//a"));
	    
	    return data.stream().anyMatch(el -> el.getText().contains(name));
	    
	}
	
	//Veirfy if there is a field validation for negative scenario
	public String getFieldError()
	{
		return driver.findElement(By.xpath("//span[@class='help-inline']//p")).getText();
	}


}
