
Feature: Validating Booker APIs

Scenario: Create a new auth Token
	Given Providing username and password
	When User calls /auth with POST http request
	Then The API call is success with status code 200
	And Response contains Token
	
Scenario: Verify if the Booking is Successfully added
	Given Providing all necessary information for new Booking from Json File
	When User calls "/booking" with POST http request
	Then The API call is success with status code 200
	And Response contains Booking Id


Scenario Outline: Verify if changing Booking information is Successful
	Given New "<firstname>", "<lastname>" and auth Token
	When User calls "/booking/" and ID with PATCH http request
	Then The API call is success with status code 200
	And Response contains changed "<firstname>" and "<lastname>"
	
Examples:	
	| firstname | lastname |
	| Patrick   | Rothfuss |
	| John R.R. | Tolkien  |