import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SingleDevelopment {
	
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		FistBookDataPractice data = new FistBookDataPractice();
		
		
//		System.out.print(data.addBookPayload());
		
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com")
				.addHeader("Content-Type", "application/json").addHeader("Accept", "application/json").build();
		
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
		
		String newBookInformations = given().spec(requestSpec)
				.body(new String(Files.readAllBytes(Paths.get("/Users/markosovilj/Desktop/Brandon.json"))))
//		.body("{\n"
//				+ "        \"firstname\": \"Brandon\",\n"
//				+ "        \"lastname\": \"Sanderson\",\n"
//				+ "        \"totalprice\" : 1200,\n"
//				+ "        \"depositpaid\" : false,\n"
//				+ "        \"bookingdates\" :{\n"
//				+ "            \"checkin\": \"2018-01-01\",\n"
//				+ "            \"checkout\": \"2019-01-01\"\n"
//				+ "        },\n"
//				+ "        \"additionalneeds\": \"book one\"\n"
//				+ "}")
		.when().post("/booking")
		.then().spec(responseSpec).log().all().extract().response().asString();
		
		
		
		System.out.println(newBookInformations);
		
	}

}
