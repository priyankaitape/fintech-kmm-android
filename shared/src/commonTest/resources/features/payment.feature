Feature: Payment processing

  Scenario: Valid payment is sent successfully
    Given the user has entered a valid email and amount
    When the user submits the payment
    Then the payment should be processed and stored

  Scenario: Invalid email fails validation
    Given the user has entered an invalid email
    When the user submits the payment
    Then the system should show an error
