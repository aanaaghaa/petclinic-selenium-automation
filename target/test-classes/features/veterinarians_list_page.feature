@skip
Feature: View list of Veterinarians

Scenario: Verify if Veterinarians list is available
Given the user is on the Home page
When the user clicks on Veterinarians option
Then the user should be redirected to Veterinarians page 
And the user should be able to view the list of all Veterinarians
