package com.anagha.petclinic.stepdefinitions;

import org.openqa.selenium.WebDriver;

import com.anagha.petclinic.utils.DriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	@Before
	public void setup(Scenario scenario) {
	    WebDriver driver = DriverFactory.initDriver();
	}



	@After
	public void tearDown(Scenario scenario) {
	    System.out.println("Thread " + Thread.currentThread().getId() + " - Executing @After for: " + scenario.getName());
	    WebDriver driver = DriverFactory.getDriver();
	    DriverFactory.quitDriver(); 
	}

}
