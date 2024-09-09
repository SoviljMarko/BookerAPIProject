
Feature: Validating Booker APIs

Scenario: Gather all Booker IDs from same person
	Given Author First Name "Josh"
	When User calls "/booking" with GET http request
	Then The API call is success with status code 200
	And Count number of Bookings with this name
	And Catch random Bookings ID
	

Scenario: Check if searched ID contains same First Name
	Given Previously caught Booking ID
	When User calls "/booking/" and ID with GET http request
	Then The API call is success with status code 200
	And "firstname" is equal to "Josh"
	