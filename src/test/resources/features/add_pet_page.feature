@skip
Feature: PetClinic Add Pet
This feature validates the Add Pet functionality under various scenarios.
It includes positive flow, form validations, edge-case handling, and database verification.
  
# ------------------------ POSITIVE SCENARIO ------------------------
@positive
Scenario: Add a new pet to an existing owner

    Given the user is on Add new pet page for owner with id "<ownerId>"
    When the user provides valid pet details from Excel and confirm success
    Then all the pets added through UI should be present in the database 
    And the owner should be able to edit pet details
    And the Add Visit button should be enabled for the pet
    
 Examples:
 | ownerId |
 | 9			 |

# ------------------------ NEGATIVE SCENARIO ------------------------  
@negative
Scenario Outline: Add a pet without pet name

Given the user is on Add new pet page for owner with id "<ownerId>"
When the user does not provide "<petName>" and valid "<dob>" and "<petType>"
Then the user should get a field validation error

Examples:
| ownerId | petName | dob 			 | petType |
| 9				| 				| 01-10-2024 | dog		 |

 # ------------------------ EDGE SCENARIO ------------------------
@edge
Scenario Outline: Set the birth date in future date

Given the user is on Add new pet page for owner with id "<ownerId>"
When the user provides all valid "<petName>" and future "<dob>" and valid "<petType>"
Then the user should get a validation error

Examples:
| ownerId | petName | dob 			 | petType |
| 9				| dooog		| 11-01-2026 | dog		 |