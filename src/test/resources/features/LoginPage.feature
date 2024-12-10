Feature: Login feature
  
Background: 
	Given user is on app login page

  @UI @Test
  Scenario: Signing UP to the system scenario
    Then user navigates to page "Create an Account"
    Then user fills the sign up form with given details
    Then user validates the successful sign up
    Then user logs out
    Then user navigates to page "Sign In"
    Then user log in with existing username and password
