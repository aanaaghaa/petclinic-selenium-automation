@skip
Feature: PetClinic Home Page Navigation

Scenario: Verify the user lands on home page
Given the user launches the petclinic website 
When user views the home page
Then the title should be "PetClinic :: a Spring Framework demonstration"
And the logo should be displayed
And the menus should contain "Home, Find Owners, Veterinarians, Error"