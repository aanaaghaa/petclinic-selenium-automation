package com.anagha.petclinic.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

   private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
   
    public static WebDriver initDriver() {
        String browser = ConfigReader.get("browser").toUpperCase();
       // System.out.println("[INIT] Thread: " + Thread.currentThread().getId() + " - Initializing " + browser + " driver");

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
        return getDriver();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
        System.out.println("[QUIT] Thread: " + Thread.currentThread().getId() + " - Quitting driver");

        if (currentDriver != null) {
            try {
                currentDriver.quit();
                System.out.println("Browser closed successfully for thread " + Thread.currentThread().getId());
            } catch (Exception e) {
                System.err.println("Error while quitting browser: " + e.getMessage());
            } finally {
                driver.remove(); // Important for memory cleanup
            }
        } else {
            System.err.println("No driver found to quit for thread " + Thread.currentThread().getId());
        }
    }

	public static void setDriver(WebDriver driver2) {
		// TODO Auto-generated method stub
		
	}
}



