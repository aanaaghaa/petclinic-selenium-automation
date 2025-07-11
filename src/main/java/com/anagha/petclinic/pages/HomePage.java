package com.anagha.petclinic.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;

/**
 * Represents the Home Page of the PetClinic web application.
 * Provides reusable methods for interacting with core elements on the home screen.
 * Supports validation of UI components like title, logo visibility, and navigation menu items.
 **/

public class HomePage extends BasePage{
	
	WebDriver driver;

	public HomePage(WebDriver driver) {
	    super(driver);
	}
	
public String getHomePageTitle()
{
	return driver.getTitle();
	
}
//Reusable method to verify if the logo is visible in the home screen
public boolean isLogoVisible() { return driver.findElement(By.xpath("//img[@class='logo']")).isDisplayed(); }

// Reusable method to fetch menu items displayed on the home page
public List<String> getMenuItems() {
    List<WebElement> items = driver.findElements(By.cssSelector("nav li a"));
    return items.stream().map(WebElement::getText).collect(Collectors.toList());
}
}
