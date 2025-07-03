package com.anagha.petclinic.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.anagha.petclinic.base.BasePage;

public class AddVisitPage extends BasePage{
	WebDriver driver;
	
	public AddVisitPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
	}
	public void addVisitButtonClick() throws InterruptedException
	{
		WebElement addVisitLink=driver.findElement(By.xpath("//a[text()='Add Visit']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addVisitLink);
		 Thread.sleep(500);
		addVisitLink.click();
	}
	public void addVisitDetails(String date, String desc)
	{
		driver.findElement(By.id("date")).sendKeys(date);
		driver.findElement(By.id("description")).sendKeys(desc);
		driver.findElement(By.xpath("//button[text()='Add Visit']")).click();
	}
	public List<WebElement> getOwnerVisitTableData() {
		return driver.findElements(By.xpath("//table[@class='table-condensed']/tbody/tr"));
	}
	public List<WebElement> getPreviousVisitData() {
		return driver.findElements(By.xpath("//b[text()='Previous Visits']/following-sibling::table//td"));
		}

}
