package com.anagha.petclinic.pages;

import java.sql.SQLException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.anagha.petclinic.base.BasePage;
import com.anagha.petclinic.utils.DBUtils;

import org.junit.Assert;

import org.openqa.selenium.support.ui.Select;

/**
 * Page Object Model class for the "Add Pet" page in the PetClinic application.
 * Encapsulates UI interactions such as entering pet details, submitting the form,
 * and verifying success messages.
 *
 * Also supports integration with Excel and Database for data-driven testing.
 */

public class AddPetPage extends BasePage {
    WebDriver driver;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddPetPage.class);
    public AddPetPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

 // Web elements on the Add Pet form, initialized using PageFactory
    @FindBy(id = "name")
    private WebElement petNameField;

    @FindBy(id = "birthDate")
    private WebElement birthDateField;

    @FindBy(id = "type")
    private WebElement petTypeDropdown;

    @FindBy(xpath = "//button[text()='Add Pet']")
    private WebElement addPetButton;

    // Reusable method to fill pet details in the Add Pet form
    public void addPetDetails(String petname, String dob, String pettype) {
        waitForElement(By.id("name"));
        //petNameField.clear();
        petNameField.sendKeys(petname);
        //birthDateField.clear();
        birthDateField.sendKeys(dob);
        Select petType = new Select(petTypeDropdown);
        petType.selectByVisibleText(pettype);
    }

    // Clicks the "Add Pet" button after filling the form
    public void clickAddPetButton() {
        waitForElement(By.xpath("//button[text()='Add Pet']"));
        addPetButton.click();
    }

    // Reads pet data from Excel and submits it via the UI
    public void addPetFromExcel(Map<String, String> petData) throws SQLException {
    	 waitForElement(By.id("name"));
        addPetDetails(
            petData.get("petname"),
            petData.get("dob"),
            petData.get("pettype")
        );
        clickAddPetButton();
        successMsgOfNewPetAdded();    
    }
    
    // Inserts pet details into the database
    public void addPetToDB(Map<String, String> petData) throws SQLException
    {
    	int ownerId = Integer.parseInt(petData.get("ownerid"));
        String petname = petData.get("petname");
        String dob = petData.get("dob"); 
        String pettype = petData.get("pettype");
    	 DBUtils.insertPetToDB(petname, dob, pettype, ownerId);
    }
    
    // Verifies the success message after pet is added
    public void successMsgOfNewPetAdded()
    {
    	  By successMsg=By.id("success-message");
    		waitForElement(successMsg);
    		String popUpMsg=driver.findElement(successMsg).getText();
    		waitForElementDisappear(successMsg);
    		logger.info("Success message displayed: {}", popUpMsg);
    		Assert.assertTrue("Expected success message not found!", popUpMsg.contains("New Pet has been Added"));
    }
}
