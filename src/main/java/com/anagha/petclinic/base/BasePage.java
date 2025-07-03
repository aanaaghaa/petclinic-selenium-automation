package com.anagha.petclinic.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.anagha.petclinic.utils.ConfigReader;


public class BasePage {
	protected WebDriver driver;
    public BasePage(WebDriver driver) {
        this.driver = driver;        
    }
    
    public void waitForElement(By locator) {
    	int timeout=ConfigReader.getInt("timeout");
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
            .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitForAllElements(By locator) {
    	int timeout=ConfigReader.getInt("timeout");
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
            .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    public void waitForElementDisappear(By locator) {
    	int timeout=ConfigReader.getInt("timeout");
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
            .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public void waitForElementTextToContain(By locator, String textToBePresent) {
    	int timeout=ConfigReader.getInt("timeout");
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
            .until(ExpectedConditions.textToBePresentInElementLocated(locator, textToBePresent));
    }
    
}
