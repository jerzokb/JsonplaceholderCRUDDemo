import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class jsonplaceholderCheckEmailAddressTest {

    // Odczytaj wszystkich użytkowników z serwisu JSONPlaceholder
    // Sprawdź, cz nie ma żadnego użytkownika, którego adres email kończy się na .pl
    @Test
    public void jsonplaceholderCheckIfEmailsNotEndWithPL(){
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> emails = json.getList("email");
        Assertions.assertEquals(10, emails.size());

        List<String> emailsEndsWithPL = emails.stream()
                .filter(email -> email.endsWith(".pl"))
                .collect(Collectors.toList());
        Assertions.assertTrue(emailsEndsWithPL.isEmpty());
    }
}
