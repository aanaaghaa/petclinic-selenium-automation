@skip
Feature: PetClinic Find Owner
This feature validates the "Find Owners" functionality of the PetClinic application,
including positive, negative, and edge scenarios such as case-insensitive searches.

# ------------------------ POSITIVE SCENARIO ------------------------
@positive

Scenario Outline: Search existing owner by last name and verify their profile
Given the user is on Find Owners page
When user provides valid last name and clicks Find Owner button
Then the user details should be displayed on the Owner Information page


 # ------------------------ NEGATIVE SCENARIO ------------------------
@negative

Scenario Outline: Verify the handling of the system for non existent users
Given the user is on Find Owners page 
When the user enters non existent user name with "<LastName>" and clicks on Find Owner button
Then the user will get field error

Examples:
	| LastName |
	| X				 |

# ------------------------ EDGE SCENARIO ------------------------
@edge
Scenario Outline: Verify if the owner name field is case sensitive
  Given the user is on Find Owners page
  When the user finds the same Owner name with different cases of "<LastName>"
  Then the user should be able to get the owners

Examples:
  | LastName |
  | S        |
  | s        |
