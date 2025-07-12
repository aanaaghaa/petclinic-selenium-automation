
Feature: PetClinic Add Owner
  This feature tests the functionality of adding a new Owner in the PetClinic application.
  Scenarios include positive, negative, and edge cases to validate form behavior, DB integration, and UI validations.

# ------------------------ POSITIVE SCENARIO ------------------------
 


Scenario: Add a new owner with valid details
Given the user is on the Add Owner page
When the user enters all valid owner details from Excel and confirm success
Then all the owners added through UI should be present in the database
And the added information should be visible on the Owner Information page
And the user should be able to add a new pet for the owner
And the user should be able to edit the owner
  
# ------------------------ NEGATIVE SCENARIO ------------------------   
@negative

Scenario Outline: Add a new owner with empty fields

Given the user is on the Add Owner page 
When the user keeps "<firstName>" "<lastName>" "<address>" "<city>" "<telephone>" fields empty
Then the user should get field validation error for all the required fields

Examples:
| firstName | lastName | address | city | telephone |
| 					|					 | 				 | 			|						|

 # ------------------------ EDGE SCENARIO ------------------------
@edge

Scenario Outline: Add a new owner with long name 

 Given the user is on the Add Owner page
 When the user will add "<firstName>" of five hundred characters and all other valid details like "<lastName>" "<address>" "<city>" "<telephone>"
 Then the user should get a error message
 
 Examples:
 | firstName 																																																																																																																																																																																												| lastName | address 							 | city      | telephone 	| 	
 | Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa | SriRam	 | Peepal Tree Apartment | bangalore | 9876543210 |
 

