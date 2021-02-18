import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderGETTwoTest {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String USERS = "users";
    // GIVEN - konfiguracja
    // WHEN - wysłanie requestu
    // THEN - asercje
    @Test
    public void jsonplaceholderReadAllUsers() {
        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(response.asString());
        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        names.stream()
                .forEach(System.out::println);
        assertEquals(10, names.size());

    }
    @Test
    public void jsonplaceholderReadOneUser() {
        given()
                .when()
                .get(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email", equalTo("Sincere@april.biz"))
                .body("address.street", equalTo("Kulas Light"));

//        Assertions.assertEquals(200, response.statusCode());
//
//        JsonPath json = response.jsonPath();
//
//        Assertions.assertEquals("Leanne Graham", json.get("name"));
//        Assertions.assertEquals("Bret", json.get("username"));
//        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
//        Assertions.assertEquals("Kulas Light", json.get("address.street"));
//
//        System.out.println(response.asString());
    }

    // PATH Variable
    @Test
    public void jsonplaceholderReadOneUserWithPathVariable() {
        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get(BASE_URL + "/" + USERS + "/{userId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Sincere@april.biz", json.get("email"));
        assertEquals("Kulas Light", json.get("address.street"));
    }
    // QUERY PARAMS
    @Test
    public void jsonplaceholderReadUsersWithQueryParams() {
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Leanne Graham", json.getList("name").get(0));
        assertEquals("Bret", json.getList("username").get(0));
        assertEquals("Sincere@april.biz", json.getList("email").get(0));
        assertEquals("Kulas Light", json.getList("address.street").get(0));
    }
}
