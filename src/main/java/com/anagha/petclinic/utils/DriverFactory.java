package com.anagha.petclinic.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

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
        System.out.println("🚀 WebDriver initialized in Thread: " + Thread.currentThread().getId());
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
                System.out.println("✅ Browser closed for thread " + Thread.currentThread().getId());
            } catch (Exception e) {
                System.err.println("❌ Error while quitting browser: " + e.getMessage());
            } finally {
                driver.remove(); // Always clean up
            }
        } else {
            System.err.println("⚠️ No driver found to quit for thread " + Thread.currentThread().getId());
        }
    }
}
