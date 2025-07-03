package com.anagha.petclinic.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.anagha.petclinic.base.BasePage;

public class AddOwnersPage extends BasePage{
	
	WebDriver driver;
	
	public AddOwnersPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
	}
	
	public void addOwnerDetails(String firstName, String lastName, String address, String city, String telephone)
	{
		driver.findElement(By.id("firstName")).sendKeys(firstName);
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		driver.findElement(By.id("address")).sendKeys(address);
		driver.findElement(By.id("city")).sendKeys(city);
		driver.findElement(By.id("telephone")).sendKeys(telephone);
		driver.findElement(By.xpath("//button[text()='Add Owner']")).click();
	}

}
