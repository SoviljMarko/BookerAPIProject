import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Development extends BaseThings {
	
	static ArrayList<Integer> bookingId;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com")
				.addHeader("Content-Type", "application/json").addHeader("Accept", "application/json").build();
		
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
		
		//////////////////////////////////////////////////////////////////////
		
		String tokenResponse = given().spec(requestSpec)
				.body("{\n"
						+ "    \"username\" : \"admin\",\n"
						+ "    \"password\" : \"password123\"\n"
						+ "}")
		.when().post("/auth")
		.then().spec(responseSpec).extract().response().asString();
		
		
		String token = extractingStringFromJson(tokenResponse).getString("token");
//		System.out.println(token);

		////////////////////////////////////////////////////////////////////// All IDs
		
		String allBookingIds = given()
				.when().get("/booking")
				.then().spec(responseSpec).extract().response().asString();
		
		try {
			bookingId = new ArrayList<Integer>(extractingStringFromJson(allBookingIds).get("bookingid"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(bookingId.get(0));
		
		//////////////////////////////////////////////////////////////////////
		
		String bookInformations = given().spec(requestSpec).pathParam("id", bookingId.get(0))
		.when().get("/booking/{id}")
		.then().spec(responseSpec).extract().response().asString();
		
//		System.out.println(bookInformations);
		
		//////////////////////////////////////////////////////////////////////
		
		FistBookDataPractice data = new FistBookDataPractice();
		
		String newBookInformations = given().spec(requestSpec)
				.body(new String(Files.readAllBytes(Paths.get("/Users/markosovilj/Desktop/Brandon.json"))))
		.when().post("/booking")
		.then().spec(responseSpec).log().all().extract().response().asString();
		
		String newBookingId = extractingStringFromJson(newBookInformations).getString("bookingid");
		
		System.out.println(newBookingId);
		
		//////////////////////////////////////////////////////////////////////
		
		given().log().all().spec(requestSpec).cookie("token", token)
		.pathParam("id", bookingId.get(0)).body("{\n"
				+ "    \"firstname\": \"Brandon\",\n"
				+ "    \"lastname\": \"Sanderson\",\n"
				+ "    \"totalprice\": 1200,\n"
				+ "    \"depositpaid\": false,\n"
				+ "    \"bookingdates\": {\n"
				+ "        \"checkin\": \"2018-01-01\",\n"
				+ "        \"checkout\": \"2019-01-01\"\n"
				+ "    },\n"
				+ "    \"additionalneeds\": \"The Way of Kings\"\n"
				+ "}")
		.when().put("/booking/{id}");
		
		//////////////////////////////////////////////////////////////////////
		
		given().spec(requestSpec).cookie("token", token)
		.pathParam("id", bookingId.get(0)).body("{\n"
				+ "    \"firstname\": \"Marko\",\n"
				+ "    \"lastname\":\"Sovilj\"\n"
				+ "}")
		.when().patch("/booking/{id}")
		.then().spec(responseSpec).log().all();
		
		//////////////////////////////////////////////////////////////////////
		
		given().cookie("token", token).pathParam("id", bookingId.get(0))
		.when().delete("/booking/{id}")
		.then().log().all().assertThat().statusCode(201);
		
		
	}

}
