Feature: basic calculator functions
  Scenario: adding two numbers
    Given I have calculator
    When I add 2 and 3
    Then I should get 5
  Scenario: subtraction two numbers
    Given I have calculator
    When I subtraction 7 by 1
    Then I should get 6
  Scenario: division two numbers
    Given I have calculator
    When I division 8 by 4
    Then I should get 2
  Scenario: multiplication two numbers
    Given I have calculator
    When I multiplication 3 and 4
    Then I should get 12