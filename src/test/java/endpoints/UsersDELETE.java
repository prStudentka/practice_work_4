package endpoints;


import core.Specification;
import groovy.json.JsonException;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

import io.restassured.specification.RequestSpecification;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import java.io.IOException;

import static core.Config.USERS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;


public class UsersDELETE {

    @Description("Checked delete user")
    private void checked_user(
            String id,
            RequestSpecification reqSpecification
    ) throws JsonException
    {
        given()
                .log().all()
                .pathParam("id", id)
                .spec(reqSpecification)
                .when()
                //---> Endpoint для выполнения запроса POST
                .request("DELETE", USERS)
                .then()
                .log().ifValidationFails().statusCode(HttpStatus.SC_NO_CONTENT)
                .log().ifStatusCodeMatches(Matchers.greaterThan(HttpStatus.SC_NO_CONTENT))
                .log().ifStatusCodeMatches(Matchers.lessThan(HttpStatus.SC_NO_CONTENT))
                .log().ifError()
                .assertThat()
                //---> Проверка Body по key и value в json
                .body(Matchers.anything())
                .log().body()
                .body(Matchers.emptyString());
    }


    @Step("DELETE user by id")
    @Description("Checked one user by id and status_code 204")
    public void checked_change_user(String id) throws IOException {
        checked_user(id, Specification.requestSpecification());
    }

}
