Feature: PetClinic Home Page Navigation
This feature verifies the proper loading and UI elements on the PetClinic home page,
including title, logo visibility, and top menu items.

# ------------------------ POSITIVE SCENARIO ------------------------
Scenario: Verify the user lands on home page
Given the user launches the petclinic website 
When user views the home page
Then the title should be "PetClinic :: a Spring Framework demonstration"
And the logo should be displayed
And the menus should contain "Home, Find Owners, Veterinarians, Error"