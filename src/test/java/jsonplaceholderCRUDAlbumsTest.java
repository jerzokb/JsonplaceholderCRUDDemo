import com.github.javafaker.Faker;
import groovy.transform.ToString;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class jsonplaceholderCRUDAlbumsTest {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String ALBUMS = "albums";
    private static Faker faker;
    private String fakeTitle;
    private Integer fakeUserId;

    @BeforeAll
    public static void beforeAll(){
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach(){
        fakeTitle = faker.lorem().sentence();
        fakeUserId = faker.number().randomDigit();
    }

    // Read all albums - GET
    @Test
    public void jsonplaceholderGETAllAlbums() {
        Response response = given()
                .when()
                .get(BASE_URL + "/" + ALBUMS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        JsonPath json = response.jsonPath();
        List<String> posts = json.getList("title");
        Assertions.assertEquals(100, posts.size());
    }

    //Read one album - GET
    @Test
    public void jsonplaceholderGETOneAlbum() {
        Response response = given()
                .pathParam("id", 1)
                .when()
                .get(BASE_URL + "/" + ALBUMS + "/" + "{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        JsonPath json = response.jsonPath();
        Assertions.assertEquals("1", json.get("userId").toString());
        Assertions.assertEquals("quidem molestiae enim",
                json.get("title"));
    }

    // Create album
    @Test
    public void jsonplaceholderPOSTAlbum() {

        JSONObject new_album = new JSONObject();
        new_album.put("userId", fakeUserId);
        new_album.put("title", fakeTitle);

        Response response = given()
                .contentType("application/json")
                .body(new_album.toString())
                .when()
                .post(BASE_URL + "/" + ALBUMS)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(fakeUserId, json.get("userId"));
        Assertions.assertEquals(fakeTitle, json.get("title"));
    }

    // Update album - PUT
    @Test
    public void jsnoplaceholderPUTAlbumTests() {
        JSONObject new_album = new JSONObject();
        new_album.put("userId", fakeUserId);
        new_album.put("title", fakeTitle);

        Response response = given()
                .pathParam("id", 1)
                .contentType("application/json")
                .body(new_album.toString())
                .when()
                .put(BASE_URL + "/" + ALBUMS + "/" + "{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(fakeUserId, json.get("userId"));
        Assertions.assertEquals(fakeTitle, json.get("title"));
    }

    // Update Album - PATCH
    @Test
    public void jsnoplaceholderPATCHAlbumTests() {
        JSONObject new_album = new JSONObject();
        new_album.put("title", fakeTitle);

        Response response = given()
                .pathParam("id", 1)
                .contentType("application/json")
                .body(new_album.toString())
                .when()
                .patch(BASE_URL + "/" + ALBUMS + "/" + "{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(fakeTitle, json.get("title"));
    }

    // Delete Album
    @Test
    public void jsnoplaceholderDELETElbumTests() {
        Response response = given()
                .pathParam("id", 1)
                .when()
                .delete(BASE_URL + "/" + ALBUMS + "/" + "{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }
}
