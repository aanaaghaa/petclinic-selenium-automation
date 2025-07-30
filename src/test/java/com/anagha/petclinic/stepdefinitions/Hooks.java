package com.anagha.petclinic.stepdefinitions;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * This class contains Cucumber hooks that manage WebDriver setup and teardown.
 * This ensures test isolation and prevents resource leaks between test executions.**/
 
public class Hooks {

	//Initiates the Driver before every test scenario
	@Before
	public void setup(Scenario scenario) {
	    DriverFactory.initDriver();
	}

	//Closes and Quits the driver after running every test scenario
	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
	        final byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
	                .getScreenshotAs(OutputType.BYTES);
	        scenario.attach(screenshot, "image/png", "Failure Screenshot");
	     // Saves file locally for Jenkins
	        try {
	            File srcFile = ((TakesScreenshot) DriverFactory.getDriver())
	                    .getScreenshotAs(OutputType.FILE);
	            FileUtils.copyFile(srcFile, new File("target/screenshots/" + scenario.getName() + ".png"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    DriverFactory.getDriver();
	    DriverFactory.quitDriver(); 
	}
}
