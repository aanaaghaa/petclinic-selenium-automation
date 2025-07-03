@skip
@positive
Feature: Adding a visit to pet and check if its reflecting in the history
Scenario: Verify if pet can be added to visit and it is reflecting in the history
Given the user is on the owners page
When the user clicks on Add visit link
Then the user should be redirected to the visiting page
And the user should be able to add all the details and save it
And user should get a success message
And the user should be able to view the visit in the owners page
And the user should be able to view the visit in the visit page

@negative

Scenario: Verify if the user can submit empty description visit
Given the user is on the owners page
When the user clicks on Add visit link 
And the user provides the valid date and empty description
Then the user will get a field validation error

@edge

Scenario: Verify if the user can submit a visit with long description
Given the user is on the owners page
When the user clicks on Add visit link 
And the user provides a valid date and five hundred character long description
Then the user should be directed to error page
