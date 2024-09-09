import io.restassured.path.json.JsonPath;

public class BaseThings {
	
	
	public static JsonPath extractingStringFromJson(String response) {
		JsonPath js = new JsonPath(response);
		return js;
	}
}
