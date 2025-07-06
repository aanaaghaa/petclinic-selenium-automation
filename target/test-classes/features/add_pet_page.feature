@skip
Feature: PetClinic Add Pet

@positive
Scenario: Add a new pet to an existing owner
    Given the user is on Add new pet page for owner with id "9"
    When the user provides valid pet details from Excel and confirm success
    Then all the pets added through UI should be present in the database 
    And the owner should be able to edit pet details
    And the Add Visit button should be enabled for the pet
  
@negative

Scenario: Add a pet without pet name

Given the user is on Add new pet page for owner with id "9"
When the user provides all the valid details except pet name
Then the user should get a field validation error

@edge

Scenario: Set the birth date in future date

Given the user is on Add new pet page for owner with id "9"
When the user provides all the valid details and date of birth is in future
Then the user should get a validation error


