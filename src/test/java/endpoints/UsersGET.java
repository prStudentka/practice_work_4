package endpoints;


import core.Specification;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import java.io.IOException;
import java.util.Map;

import static core.Config.USERS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UsersGET extends StatusCodeBase {

    private static final String FIRST_NAME = "data.first_name";
    private static final String LAST_NAME = "data.last_name";

    @Description("Checked one user by name and last_name")
    private void checked_user(
            String id,
            RequestSpecification reqSpecification,
            ResponseSpecification resSpecification,
            String name,
            String last_name)
    {
        given()
                .log().all()
                .pathParam("id", id)
                .spec(reqSpecification)
                .when()
                //---> Endpoint для выполнения запроса GET
                .request("GET", USERS)
                .then()
                .spec(resSpecification)
                .assertThat()
                .body(FIRST_NAME, equalTo(name))
                .body(LAST_NAME, equalTo(last_name));
    }

    @Description("Checked one user by status_code")
    private void checked_user(
            String id,
            RequestSpecification reqSpecification,
            ResponseSpecification resSpecification
    ) {

        given()
                .log().all()
                .pathParam("id", id)
                .spec(reqSpecification)
                .when()
                //---> Endpoint для выполнения запроса GET
                .request("GET", USERS)
                .then()
                .spec(resSpecification)
                .log().ifValidationFails()
                .assertThat()
                .log().body()
                .body(Matchers.anything())
                .log().ifStatusCodeMatches(Matchers.greaterThan(200));
    }

    @Step("GET user by id")
    @Description("Checked one user by map and status_code 200")
    public void checked_one_user(Map<String, String> data, RequestSpecification specification) throws IOException {
        checked_user(data.get("id"), specification, Specification.responseSpecification(), data.get("name"), data.get("last_name"));
    }

    @Step("GET user by id")
    @Description("Checked one user by id and status_code 200")
    public void checked_one_user(String id, RequestSpecification specification, String name, String last_name) throws IOException {
        checked_user(id, specification, Specification.responseSpecification(), name, last_name);
    }

    @Step("GET user by id")
    @Description("Checked one user by id and status_code greater then 200")
    public void checked_one_user(String id, RequestSpecification specification, ResponseSpecification resSpecification) throws IOException {
        checked_user(id, specification, resSpecification);
    }

}
