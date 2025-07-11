package com.anagha.petclinic.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.anagha.petclinic.utils.ConfigReader;

/**
 * BasePage class provides common wait utilities for synchronizing WebDriver actions.
 * All page classes should extend this for access to consistent wait behaviors.
 */
public class BasePage {
	protected WebDriver driver;
    public BasePage(WebDriver driver) {
        this.driver = driver;        
    }
    private final int defaultTimeoutInSeconds = ConfigReader.getInt("timeout");

    // Waits for the visibility of a single element
    public void waitForElement(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
            .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    // Waits for all elements matching the locator to be visible
    public void waitForAllElements(By locator) {
    	 new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
            .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    
    // Waits until the element becomes invisible 
    public void waitForElementDisappear(By locator) {
    	
        new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
            .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    //Wait for the element to contain the required text
    public void waitForElementTextToContain(By locator, String textToBePresent) {
    	
        new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
            .until(ExpectedConditions.textToBePresentInElementLocated(locator, textToBePresent));
    }
    
    // Waits until the element is clickable
    public void waitForElementToBeClickable(By locator)
    {
    	new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
        .until(ExpectedConditions.elementToBeClickable(locator));	
    }
    
    // Waits until the element is clickable and returns the WebElement
	public WebElement waitForElementToBeClickableReturnElt(By locator) {
		return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
		        .until(ExpectedConditions.elementToBeClickable(locator));	
	}
}
