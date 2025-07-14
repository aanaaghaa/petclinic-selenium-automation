PetClinic Automation Test Framework

This project demonstrates a robust full-stack test automation framework developed for the Spring PetClinic web application. Built with a modular, scalable, and industry-grade architecture, it integrates Selenium WebDriver, Cucumber BDD, JUnit, Excel-driven test data, and Database validation to ensure end-to-end quality assurance.

In Progress:
Jenkins CI Pipeline
REST API Automation using Rest Assured
TestNG Parallel Execution support
Cloud BrowserGrid support (e.g., BrowserStack, Selenium Grid)

| Tool/Library              | Purpose                                             |
| ------------------------- | --------------------------------------------------- |
| **Java 17**               | Core programming language for automation            |
| **Maven**                 | Build automation & dependency management            |
| **RestAssured**           | REST API testing framework                          |
| **TestNG**                | Test execution, data-driven testing, and assertions |
| **ExtentReports**         | Rich and customizable HTML reporting                |
| **Jackson**               | JSON serialization/deserialization                  |
| **RetryAnalyzer**         | Automatically re-run flaky tests                    |
| **JavaMail API**          | Send automated email reports after test execution   |
| **Log4j**                 | Logging framework for debugging and traceability    |
| **JSON Schema Validator** | Contract testing and schema validation              |

Project Structure

src/
├── main/
│ └── java/
│ └── com.anagha.petclinic/
│ ├── base/  BasePage class with common reusable methods
│ ├── pages/  Page Object classes (e.g., AddVisitPage, HomePage)
│ └── utils/  DriverFactory, ConfigReader, DBUtils, ExcelUtils
├── test/
│ └── java/
│ └── com.anagha.petclinic.stepdefinitions/
│ └── .java  Step Definitions for BDD
└── resources/
├── features/  All feature files (BDD scenarios)
└── config.properties  Central config (URL, browser)
└──testdata/ All testdata from Excel
1. Home Page
•	Title and menu validation
•	Logo visibility
2. Find Owners Page
•	Valid and invalid owner search
•	Case-insensitive name search using Scenario Outline
3. Add Pet Page
•	Add new pet with positive, negative, and edge case data
•	Read test data from Excel and store valid entries in DB
•	Validation for missing name or future birth date (negative/edge)
4. Add Visit Page
•	Add visit with valid and invalid data
•	Read data from Scenario Outline for different users and pets
•	View and assert the added visit on UI
5. Veterinarians Page
•	Navigate and validate veterinarian listings across paginated tables
6. Add Owner Page
•	Add new owner with positive, negative, and edge data
•	Read test data from Excel and store valid entries in DB
•	Validation for empty fields and overly long names (negative/edge)

Jenkins Integration (In Progress)

This project is configured with Jenkins CI/CD to automate test runs and generate reports.

Jenkins Job Includes:
 Git pull on build
 Maven goal: `clean test`
 HTML/Cucumber test report publishing
 Runs on push or manual trigger

Jenkins Plugins Used:
 Maven Integration Plugin
 Git Plugin
 HTML Publisher Plugin
 Cucumber Reports Plugin

Jenkins can be accessed locally at:  
http://localhost:8090

How to Run
 1. Clone the Repo

git clone url=http://localhost:8080
2. Update Config
Update config.properties: properties
url=http://localhost:8080
browser=chrome
3. Setup MySQL Database
This project interacts with MySQL for validation. Please ensure:
MySQL is installed and running

CREATE DATABASE IF NOT EXISTS petclinic;
USE petclinic;

CREATE TABLE IF NOT EXISTS owners (
  id INT AUTO_INCREMENT PRIMARY KEY,
  firstName VARCHAR(50),
  lastName VARCHAR(50),
  address VARCHAR(100),
  city VARCHAR(50),
  telephone VARCHAR(15)
);

CREATE TABLE pets(
id int INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50), 
birth_date DATE, 
type VARCHAR(30),

CREATE TABLE visit (
    visit_id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    pet_id INT NOT NULL,
    visit_date DATE NOT NULL,
    description VARCHAR(500) NOT NULL
);

3. Run with Maven
bash
mvn clean test
4. Run Specific Feature (Optional)
Use tags or feature file path with IDE or CLI.

Author
Anagha S — QA Engineer


