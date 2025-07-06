@skip
Feature: PetClinic Add Owner

@positive

Scenario: Add a new owner with valid details
Given the user is on the Add Owner page
When the user enters all valid owner details from Excel and confirm success
Then all the owners added through UI should be present in the database
And the added information should be visible on the Owner Information page
And the user should be able to add a new pet for the owner
And the user should be able to edit the owner
  
    
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

