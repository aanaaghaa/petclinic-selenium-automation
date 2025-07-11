package com.anagha.petclinic.testrunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

//This class runs the Cucumber BDD tests using JUnit 4
@RunWith(Cucumber.class)

/** Specifies Cucumber options:
* 'features' points to the feature files location
* 'glue' links step definitions
* 'tags' lets you include or exclude scenarios
* 'plugin' generates readable reports
* 'monochrome=true' ensures clean console output **/

@CucumberOptions(
		features="src/test/resources/features",
		glue="com.anagha.petclinic.stepdefinitions",
		tags="not @positive and not @skip",
		plugin= {"pretty", "html:target/cucumber-reports"},
		monochrome=true
		)

public class RunCucumberTest {
	 // No methods needed, Cucumber will scan and execute based on annotations
}






