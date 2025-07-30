package com.anagha.petclinic.utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * DriverFactory is a utility class responsible for managing the WebDriver lifecycle.
 * It supports multi-threaded execution using ThreadLocal.
 * Initializes browser drivers based on the configuration.
 * Ensures proper teardown of driver sessions post execution.
 * Supported browsers: Chrome, Firefox, Edge (configurable via config.properties). **/

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    //Initializes the WebDriver by fetching the browser value from config file and maximizes the screen
    public static WebDriver initDriver() {
        String browser = ConfigReader.get("browser").toUpperCase();

        switch (BrowserType.valueOf(browser)) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
                break;
            case CHROME:
            default:
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
                break;
        }

        getDriver().manage().window().maximize();
        System.out.println("WebDriver initialized in Thread: " + Thread.currentThread().getId());
        return getDriver();
    }


	 // Returns the WebDriver instance for the current thread and Ensures thread-safe driver access in parallel test execution.
    public static WebDriver getDriver() {
        return driver.get();
    }

    //Quits the driver if the driver is not null
    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
         if (currentDriver != null) {
            try {
                currentDriver.quit();
            } catch (Exception e) {
               
            } finally {
                driver.remove(); 
            }
        } else {
            
        }
    }
}
