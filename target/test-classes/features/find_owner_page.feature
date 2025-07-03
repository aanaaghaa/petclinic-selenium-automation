
Feature: PetClinic Find Owner

@positive

Scenario: Search existing owner by last name and verify their profile
Given the user is on Find Owners page
When user provides valid last name and clicks Find Owner button
Then the user details should be displayed on the Owner Information page

@negative

Scenario: Verify the handling of the system for non existent users
Given the user is on Find Owners page
When the user enters non existent user name
Then the user will get field error

@edge
Scenario Outline: Verify if the owner name field is case sensitive
  Given the user is on Find Owners page
  When the user finds the same Owner name with different cases of <LastName>
  Then the user should be able to get the owners

Examples:
  | LastName |
  | S        |
  | s        |
