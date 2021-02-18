import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class jsonplaceholderPOSTTest {
    @Test
    public void jsonplaceholderCreateUser() {

        String jsonBody = "{\n" +
                "    \"name\": \"Beata Testowa\",\n" +
                "    \"username\": \"Beata\",\n" +
                "    \"email\": \"beata@aqa.com\",\n" +
                "    \"address\": {\n" +
                "      \"street\": \"Sezamkowa\",\n" +
                "      \"suite\": \"13/7\",\n" +
                "      \"city\": \"Sezamkowo\",\n" +
                "      \"zipcode\": \"12-3456\",\n" +
                "      \"geo\": {\n" +
                "        \"lat\": \"-37.3159\",\n" +
                "        \"lng\": \"81.1496\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"phone\": \"111-222-333\",\n" +
                "    \"website\": \"aqa.com\",\n" +
                "    \"company\": {\n" +
                "      \"name\": \"Firam Sezamowa\",\n" +
                "      \"catchPhrase\": \"The best Sezams in the city\",\n" +
                "      \"bs\": \"Super Seazam Heros\"\n" +
                "    }\n" +
                "  }";

        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Beata Testowa", json.get("name"));
        assertEquals("Beata", json.get("username"));
        assertEquals("beata@aqa.com", json.get("email"));
    }
}
