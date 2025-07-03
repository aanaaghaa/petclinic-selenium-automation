package com.anagha.petclinic.testrunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/resources/features",
		glue="com.anagha.petclinic.stepdefinitions",
		tags="not @skip",
		plugin= {"pretty", "html:target/cucumber-reports"},
		monochrome=true
		)

public class RunCucumberTest {
}






