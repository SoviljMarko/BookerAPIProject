package step_definitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.runner.Request;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.Utility;

public class StepDefinitions extends Utility{
	Response response;
	RequestSpecification wholeRequest;
	List<Integer> allBookingIds;
	
	static String bookId;
	static String token;
	static int randomIdNumber;
	
	RequestSpecification requestSpec = requestSpecification();
	
	
	@Given("Providing username and password")
	public void providing_username_and_password() {
		wholeRequest =  given().spec(requestSpec).body("{\n"
	    		+ "    \"username\" : \"admin\",\n"
	    		+ "    \"password\" : \"password123\"\n"
	    		+ "}");
	}

	@When("User calls \\/auth with POST http request")
	public void user_calls_auth_with_POST_http_request() {
		response = wholeRequest.when().post("/auth");
	}

	@Then("Response contains Token")
	public void response_contains_Token() {
		token = extractingTextFromJsonFormat(response.asString()).getString("token");
	}
	
	
	
	@Given("Providing all necessary information for new Booking from Json File")
	public void providin_all_necessary_information_for_new_Booking() throws IOException {
		
		wholeRequest = given().spec(requestSpec)
				.body(new String(Files.readAllBytes(Paths.get("/Users/markosovilj/Desktop/Brandon.json"))));
	}
	
	@When("User calls {string} with POST http request")
	public void user_calls_booking_with_post_http_request(String path) {
		response = wholeRequest.when().post(path);
	}
	
	@Then("The API call is success with status code {int}")
	public void the_API_call_is_success_with_status_code(int code) {
		assertEquals(response.getStatusCode(), code);
	}
	
	@Then("Response contains Booking Id")
	public void response_contains_booking_id() {
		bookId = extractingTextFromJsonFormat(response.asString()).getString("bookingid");
		Assert.assertTrue(bookId != null);
	}
	
	
	
	
	@Given("New {string}, {string} and auth Token")
	public void new_and(String firstName, String lastName) {
		wholeRequest = given().spec(requestSpec).cookie("token", token)
				.body("{\n"
				+ "    \"firstname\": \""+ firstName +"\",\n"
				+ "    \"lastname\":\""+lastName+"\"\n"
				+ "}");
	}

	@When("User calls {string} and ID with PATCH http request")
	public void user_calls_and_ID_with_PATCH_http_request(String path) {
		response = wholeRequest.when().patch(path + bookId);
				
	}

	@Then("Response contains changed {string} and {string}")
	public void response_contains_changed_and(String firstName, String lastName) {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(firstName, extractingTextFromJsonFormat(response.asString()).getString("firstname"));
		assertEquals(lastName, extractingTextFromJsonFormat(response.asString()).getString("lastname"));
		
	}

	
	
	@Given("Author First Name {string}")
	public void author_First_Name(String firstName) {
		wholeRequest =  given().spec(requestSpec).queryParam("firstname", "Josh");
		
	}

	@When("User calls {string} with GET http request")
	public void user_calls_with_GET_http_request(String path) {
	    response = wholeRequest.when().get(path);
	}

	@Then("Count number of Bookings with this name")
	public void count_number_of_Bookings_with_this_name() {
		JsonPath js = new JsonPath(response.asString());
		allBookingIds = new ArrayList<Integer>(extractingTextFromJsonFormat(response.asString())
				.get("bookingid"));
		System.out.println(allBookingIds.size());
	}
	
	@Then("Catch random Bookings ID")
	public void catch_random_bookings_ID() {
		Random random = new Random();
		randomIdNumber = allBookingIds.get(random.nextInt(allBookingIds.size()));	
		
	}
	
	
	
	@Given("Previously caught Booking ID")
	public void previously_caught_Booking_ID() {
		wholeRequest =  given().spec(requestSpec); 
	}

	@When("User calls {string} and ID with GET http request")
	public void user_calls_and_ID_with_GET_http_request(String path) {
	    response = wholeRequest.when().get(path + randomIdNumber);
	}

	@Then("{string} is equal to {string}")
	public void is_equal_to(String firstName, String expectedName) {
		JsonPath js = new JsonPath(response.asString());
		extractingTextFromJsonFormat(response.asString()).get(firstName);
		assertEquals(extractingTextFromJsonFormat(response.asString())
				.get(firstName), expectedName);
	}
	
	
}
