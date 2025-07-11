PetClinic Automation Test Framework

This project automates UI testing for the [Spring PetClinic](https://spring-petclinic.github.io/) web application using Selenium WebDriver, Cucumber BDD, JUnit, and Java.

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

 Tech Stack
| Tool        | Purpose                      |
|-------------|------------------------------|
| Java        | Main programming language    |
| Selenium    | UI automation framework      |
| Cucumber    | BDD with Gherkin             |
| JUnit       | Test runner                  |
| Maven       | Dependency management        |
| SLF4J       | Logging                      |
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

How to Run
 1. Clone the Repo
```bash
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


