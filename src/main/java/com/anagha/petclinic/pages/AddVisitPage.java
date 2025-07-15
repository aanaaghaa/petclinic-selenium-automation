package com.anagha.petclinic.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;

/**
 * Page Object Model class for the Add Visit functionality in PetClinic.
 * Provides reusable methods to interact with the visit form,
 * extract visit details, and verify visit history on both
 * pet and owner pages.
 */

public class AddVisitPage extends BasePage{
	
	WebDriver driver;
	private String lastExtractedPetId;
	
	public AddVisitPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
	}
	
	// Scrolls to and clicks the 'Add Visit' button
	public void addVisitButtonClick() throws InterruptedException
	{
		WebElement addVisitLink=driver.findElement(By.xpath("//a[text()='Add Visit']"));
		waitForElement(By.xpath("//a[text()='Add Visit']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addVisitLink);
		 Thread.sleep(500);
		addVisitLink.click();
	}
	
	// Fills the visit form with date and description, and submits it
	public void addVisitDetails(String date, String desc)
	{
		driver.findElement(By.id("date")).sendKeys(date);
		driver.findElement(By.id("description")).sendKeys(desc);
		driver.findElement(By.xpath("//button[text()='Add Visit']")).click();
	}
	
	
	//public List<WebElement> getOwnerVisitTableData() {
	 //waitForElement(By.id("visitTable"));
	  //WebElement table=driver.findElement(By.id("visitTable"));
	    //return table.findElements(By.tagName("tr"));*/
		//return driver.findElements(By.xpath("//table[@class='table-condensed']/tbody/tr"));
	//}

	
	// Fetches all cells from the 'Previous Visits' section
	public List<WebElement> getPreviousVisitData() {
		return driver.findElements(By.xpath("//b[text()='Previous Visits']/following-sibling::table//td"));
		}
	
	// Returns the most recently extracted pet ID
	public void clickAddVisitLinkForPet(String petName) throws InterruptedException {
		waitForElement(By.className("table-condensed"));
	    String xpath = "//tr//dd[contains(text(),'" + petName + "')]//ancestor::tr//td//a[contains(text(),'Add Visit')]";
	    WebElement addVisitLink = driver.findElement(By.xpath(xpath));
	    String href = addVisitLink.getAttribute("href");
	    String[] parts = href.split("/");
	    for (int i = 0; i < parts.length; i++) {
	        if (parts[i].equalsIgnoreCase("pets") && i + 1 < parts.length) {
	            lastExtractedPetId = parts[i + 1];
	            break;
	        }
	    }
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addVisitLink);
	    Thread.sleep(500);
	    addVisitLink.click();
	}
	
	//Returns all rows from the Owner's visit table
	public List<WebElement> getVisitOnOwnerTable()
	{
		return driver.findElements(By.xpath("//table[contains (@class, table-striped)][2]//tr"));
	}

	// Returns the most recently extracted pet ID
	public String getLastExtractedPetId() {
	    return lastExtractedPetId;
	}
}
