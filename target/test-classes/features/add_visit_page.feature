
Feature: Adding a visit to pet and check if its reflecting in the history
This feature validates the Add Pet functionality under various scenarios.
  It includes positive flow, form validations, edge-case handling, and database verification.
  
# ------------------------ POSITIVE SCENARIO ------------------------
@positive  
Scenario Outline: Add visit to pet for multiple owners
Given the user is on the owners page owner with "<ownerId>"
When the user clicks on Add visit link for the pet "<petName>"
Then the user should be redirected to the visiting page
And the user provides visit date "<date>" and description "<description>"
And user should get a success message
And the user should be able to view the visit in the visit page
And the user should be able to view the visit in the owners page
And the visit details should be added to the database for owner "<ownerId>" and pet "<petName>"

Examples:
    | ownerId | petName			| date        | description     |
    | 3	      |	Ginger			| TODAY				| Grooming Visit  |
    | 1 	    |	Leo					| TODAY			  |  Health Checkup	|
    | 5	      |	Bella				| TODAY				|  Vaccination		|

# ------------------------ NEGATIVE SCENARIO ------------------------
@negative

Scenario Outline: Verify if the user can submit empty description visit
Given the user is on the owners page owner with "<ownerId>"
When the user clicks on Add visit link
And the user provides the valid date and empty description
Then the user will get a field validation error

Examples:
	|ownerId | 
	| 9 		 |

# ------------------------ EDGE SCENARIO ------------------------
@edge

Scenario Outline: Verify if the user can submit a visit with long description
Given the user is on the owners page owner with "<ownerId>"
When the user clicks on Add visit link 
And the user provides a valid date and five hundred character long description
Then the user should be directed to error page

Examples:
	|ownerId | 
	| 9   	 | 

  
