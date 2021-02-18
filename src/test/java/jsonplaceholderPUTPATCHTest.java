import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class jsonplaceholderPUTPATCHTest {

    private static Faker faker;
    private String fakeEmail;
    private String fakeName;
    private String fakeUserName;
    private String fakerPhone;
    private String fakeWWW;
    // jeden raz przed wszystkimi testami
    @BeforeAll
    public static void beforeAll(){
        faker = new Faker();
    }
    // przed każdym testem
    @BeforeEach
    public void beforeEach(){
        fakeEmail = faker.internet().emailAddress();
        fakeName = faker.name().name();
        fakeUserName = faker.name().username();
        fakerPhone = faker.phoneNumber().phoneNumber();
        fakeWWW = faker.internet().url();
    }

    @Test
    public void jsonplaceholderUpdateUserPUTTest() {

        JSONObject user = new JSONObject();
        user.put("name", fakeName);
        user.put("username", fakeUserName);
        user.put("email", fakeEmail);
        user.put("phone", fakerPhone);
        user.put("website", fakeWWW);

        JSONObject geo = new JSONObject();
        geo.put("lat", "-37.3159");
        geo.put("lng", "81.1496");

        JSONObject address = new JSONObject();
        address.put("street", "Sezamkowa");
        address.put("suite", "13/7");
        address.put("city", "Sezamkowo");
        address.put("zipcode", "12-3456");
        address.put("geo", geo);

        user.put("address", address);

        JSONObject company = new JSONObject();
        company.put("name", "Firma Sezamowa");
        company.put("catchPhrase", "The best Sezams in the city");
        company.put("bs", "Super Seazam Heros");

        user.put("company", company);

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .put("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakeName, json.get("name"));
        assertEquals(fakeUserName, json.get("username"));
        assertEquals(fakeEmail, json.get("email"));
    }

    @Test
    public void jsonplaceholderUpdateUserPATCHTest() {

        JSONObject userEmail = new JSONObject();
        userEmail.put("email", fakeEmail);

        Response response = given()
                .contentType("application/json")
                .body(userEmail.toString())
                .when()
                .patch("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakeEmail, json.get("email"));
    }
}
