Feature: Booking Airfare

  Scenario: Search search field
    Given user is on the Kayak homepage
    When user clicks "Flights" button
    Then user should be on the "https://www.kayak.com/flights"

  Scenario: