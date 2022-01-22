Feature: Check out of a stay
  In order to manage my work more efficiently
  As a Front Office employee
  I want to check-out the guest, so that I can terminate their stay.

  Scenario: Check out a charged stay
    Given a category with name "Business Casual EZ" and max persons 1 and half board price 90 full board price 120 and currency EUR
    And a second category with name "Business Casual DZ" and max persons 2 and half board price 120 full board price 140 and currency EUR
    And a stay with stayId S100000 and arrivalDate today and departureDate 2022-04-24 and number of persons 4 and payment information with cardHolderName Hans-Peter-Mayer and cardNumber "5432 9876 5678 1234" and cardValidThru 12/21 and cardCvc 123 and paymentType INVOICE and selected categories room count
      | Business Casual EZ  | 2 |
      | Business Casual DZ  | 1 |
    And a guest with guestId G100090 and salutation MR and first name Hans and last name Mayr and date of birth 1999-12-24 and street Musterstrasse and zipcode 8888 and city Musterhausen and country DE
    When Stay with stayId S100000 is charged to recipient with first name Bruno and last name Winkler and street Blumenstrasse and zipcode 7777 and city Blumenhausen and country DE with all selected line items
      | Business Casual EZ  | 2 |
      | Business Casual DZ  | 1 |
    And Stay with stayId S100000 is checked out
    Then Stay with stayId S100000 should be CHECKED_OUT