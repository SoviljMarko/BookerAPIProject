package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class Utility {
	
	
	public JsonPath extractingTextFromJsonFormat(String response) {
		JsonPath jp = new JsonPath(response);
		return jp;
	}
	
	
	public RequestSpecification requestSpecification() {
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com")
				.addHeader("Content-Type", "application/json").addHeader("Accept", "application/json").build();
		return requestSpec;
	}
}
