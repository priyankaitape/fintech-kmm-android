Feature: Transaction History

  Scenario: Display transaction history
    Given transactions exist in the Firestore
    When the user opens the transaction history screen
    Then the user should see a list of past transactions
