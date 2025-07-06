package com.anagha.petclinic.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.anagha.petclinic.utils.ConfigReader;


public class BasePage {
	protected WebDriver driver;
    public BasePage(WebDriver driver) {
        this.driver = driver;        
    }
    private final int defaultTimeoutInSeconds = ConfigReader.getInt("timeout");

    public void waitForElement(By locator) {
    	
        new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
            .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitForAllElements(By locator) {
    	
        new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
            .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    public void waitForElementDisappear(By locator) {
    	
        new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
            .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public void waitForElementTextToContain(By locator, String textToBePresent) {
    	
        new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
            .until(ExpectedConditions.textToBePresentInElementLocated(locator, textToBePresent));
    }
    public void waitForElementToBeClickable(By locator)
    {
    	new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
        .until(ExpectedConditions.elementToBeClickable(locator));	
    }
	public WebElement waitForElementToBeClickableReturnElt(By locator) {
		
		return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutInSeconds))
		        .until(ExpectedConditions.elementToBeClickable(locator));	
	}
    
}
