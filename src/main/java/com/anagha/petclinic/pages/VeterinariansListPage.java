package com.anagha.petclinic.pages;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;

/** Page Object Model (POM) class for the Veterinarians listing page
* Contains reusable methods to fetch table headers, check table visibility, and
* iterate across paginated vet list for data extraction
* Includes SLF4J logging and error handling for reliable test execution*/

public class VeterinariansListPage extends BasePage{
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VeterinariansListPage.class);
	WebDriver driver;
	BasePage basePage;

	
	public VeterinariansListPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		basePage=new BasePage(driver);
	}
	
	// Reusable method to fetch header elements from the Veterinarians table
	    public List<WebElement> getTableHeaders() {
	        return driver.findElements(By.xpath("//table[@id='vets']/thead/tr/th"));
	    }
	    
	 // Reusable method to verify the presence of the Veterinarians table on the page
	    public boolean isVeterinariansTablePresent() {
	        try {
	            boolean isDisplayed = driver.findElement(By.id("vets")).isDisplayed();
	            logger.info("Veterinarians table display status: {}", isDisplayed);
	            return isDisplayed;
	        } catch (NoSuchElementException e) {
	            logger.error("Veterinarians table not found!", e);
	            return false;
	        }
	    }

	 // Reusable method to print all Veterinarians listed across paginated table pages
	public void printVeterinariansListAcrossPages() throws InterruptedException { 
        boolean hasNextPage = true;

        while (hasNextPage) {
            List<WebElement> rows = driver.findElements(By.xpath("//table[@id='vets']/tbody/tr"));
            logger.info("Found {} rows in this page.", rows.size());

            for (WebElement row : rows) {
                logger.info("Veterinarian: {}", row.getText());
            }
            List<WebElement> nextButtons = driver.findElements(By.xpath("//a[@title='Next']"));
            if (!nextButtons.isEmpty()) {
                WebElement nextBtn = nextButtons.get(0);
                if (nextBtn.isDisplayed() && nextBtn.isEnabled()) {
                    nextBtn.click();
                    basePage.waitForElement(By.xpath("//table[@id='vets']/tbody/tr[1]"));
                } else {
                    hasNextPage = false;
                }
            } else {
                hasNextPage = false;
                logger.info("No more pages to process.");
            }
        }
    }
}



