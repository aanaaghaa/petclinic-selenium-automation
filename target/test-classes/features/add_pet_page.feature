@skip
Feature: PetClinic Add Pet

@positive
Scenario: Add a new pet to an existing owner
    Given the user is on Add new pet page
    When the user provides valid pet details
    And clicks on the Add Pet button
    Then the pet should be added under the owner
    And the owner should be able to edit pet details
    And the Add Visit button should be enabled for the pet
    And the success message New Pet has been Added should be displayed
    
@negative

Scenario: Add a pet without pet name

Given the user is on Add new pet page
When the user provides all the valid details except pet name
Then the user should get a field validation error

@edge

Scenario: Set the birth date in future date

Given the user is on Add new pet page
When the user provides all the valid details and date of birth is in future
Then the user should get a validation error


