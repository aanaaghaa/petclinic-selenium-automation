@skip
Feature: PetClinic Add Owner

@positive

Scenario: Add a new owner with valid details
    Given the user is on the Add Owner page
    When the user enters all valid owner details
    Then the new owner should be added successfully
    And the user should be able to edit the owner
    And the added information should be visible on the Owner Information page
    And a confirmation or success message should be displayed
    And the user should be able to add a new pet for the owner
    
@negative

Scenario: Add a new owner with empty fileds

Given the user is on the Add Owner page 
When the user keeps all the fields empty
Then the user should get field validation error for all the required fields

@edge

Scenario: Add a new owner with long name 

 Given the user is on the Add Owner page
 When the user will add Name of five hundred characters and all other valid details
 Then the user should get a error message

