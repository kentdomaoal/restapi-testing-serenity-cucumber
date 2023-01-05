Feature: Pet Records Management

  Background:
#    Given a pet dog is available in the store
    Given pets are available in the store

  @pets-api @get
  Scenario: Searching for an available pet
    When I search for available pets
    Then following pets should be available
      | petName  | petID |
      | Blackie  | 1     |
      | Kitty    | 3     |
      | Goldie-2 | 6     |


  @pets-api @put
  Scenario Outline: Updating record of a pet via PUT method
    When I add the tag '<tag>' on <petName>'s record
    Then update process should be successful

    When I search for a pet with id <petID>
    Then <petName>'s record contains the tag '<tag>'
    Examples:
      | petName | petID | tag      |
      | Blackie | 1     | cute dog |

  @pets-api @post
  Scenario Outline: Updating record of a pet via POST method
    When I update <petName>'s status to <status>
    Then update process should be successful

    When I search for a pet with id <petID>
    Then <petName>'s status is now <status>
    Examples:
      | petName | petID | status |
      | Blackie | 1     | sold   |

  @pets-api @delete
  Scenario Outline: Deleting record of a pet
    When I delete <petName>'s record
    Then delete process should be successful

    When I search for a pet with id <petID>
    Then <petName>'s record should no longer be found
    Examples:
      | petName | petID |
      | Blackie | 1     |