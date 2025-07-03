package com.anagha.petclinic.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.anagha.petclinic.base.BasePage;

public class AddPetPage extends BasePage{
	WebDriver driver;
	
	
	public AddPetPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
	}
	
	public void addPetDetails(String name, String birthDate, String dropdownText)
	{
		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("birthDate")).sendKeys(birthDate);
		WebElement dropdown=driver.findElement(By.id("type"));
		Select petType=new Select(dropdown);
		petType.selectByVisibleText(dropdownText);
	}
	public void addPetButtonClick()
	{
		By addPet=By.xpath("//button[text()='Add Pet']");
		waitForElement(addPet);
		driver.findElement(addPet).click();
	}

}
