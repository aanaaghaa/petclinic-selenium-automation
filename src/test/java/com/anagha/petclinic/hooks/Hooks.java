package com.anagha.petclinic.hooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {

	  @Before
	    public void setup() {
	        String browser = System.getProperty("browser", "chrome"); // default to chrome
	        WebDriver driver = null;

	        if (browser.equalsIgnoreCase("chrome")) {
	            WebDriverManager.chromedriver().setup();
	            driver = new ChromeDriver();
	        } else if (browser.equalsIgnoreCase("firefox")) {
	            WebDriverManager.firefoxdriver().setup();
	            driver = new FirefoxDriver();
	        }

	        DriverFactory.setDriver(driver);
	        DriverFactory.getDriver().manage().window().maximize();
	        
	    }


	@After
	public void tearDown(Scenario scenario) {
	    System.out.println("Thread " + Thread.currentThread().getId() + " - Executing @After for: " + scenario.getName());
	    WebDriver driver = DriverFactory.getDriver();
	    if (scenario.isFailed() && driver != null) {
	        // Screenshot logic (if needed)
	    }
	    DriverFactory.quitDriver(); // Must be called in same thread
	}

}
