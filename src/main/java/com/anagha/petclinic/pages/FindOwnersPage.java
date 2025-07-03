package com.anagha.petclinic.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;


public class FindOwnersPage extends BasePage{
	WebDriver driver;
	
	public FindOwnersPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
	}
	
	public void ownerDetails(String lastName)
	{
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		driver.findElement(By.xpath("//button[text()='Find Owner']")).click();
	}

	public String getOwnerInfoHeader() {
		return driver.findElement(By.xpath("//h2[contains(text(),'Owner')]")).getText();
	}
	public boolean isOwnerNamePresent(String name) {
	    List<WebElement> data = driver.findElements(By.xpath("//th[text()='Name']/ancestor::thead/following-sibling::tbody/tr/td"));
	    return data.stream().anyMatch(el -> el.getText().contains(name));
	}
	
	public String getFieldError()
	{
		return driver.findElement(By.xpath("//span[@class='help-inline']//p")).getText();
	}


}
