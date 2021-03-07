Feature: DevTp basic features
  Scenario: Open first seeing blog
    Given I go to devto main page
    And I click on first blog displayed
    Then I should be redirected to blog site
  Scenario: Podcast test
    Given I go to devto main page
    When I click text podcast in main page
    When I click on first cast displayed
    Then I should be redirected to cast site
  Scenario: Search the phrase
    Given I go to devto main page
    When I search for "python" phrase
    Then Top 3 blogs found should have correct phrase in title
