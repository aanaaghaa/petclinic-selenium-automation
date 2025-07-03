package com.anagha.petclinic.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;

public class HomePage extends BasePage{
	
	WebDriver driver;

	public HomePage(WebDriver driver) {
	    super(driver);
	}
	
public String getHomePageTitle()
{
	return driver.getTitle();
	
}
public boolean isLogoVisible() { return driver.findElement(By.xpath("//img[@class='logo']")).isDisplayed(); }

public List<String> getMenuItems() {
    List<WebElement> items = driver.findElements(By.cssSelector("nav li a"));
    return items.stream().map(WebElement::getText).collect(Collectors.toList());
}
}
