package endpoints;

import groovy.json.JsonException;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.PostRegisterSchema;
import model.UpdateUserSchema;
import org.apache.http.HttpStatus;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;

public class StatusCodeBase {
    /* Основной класс для проверки статус кода

     **/

    @DisplayName("Тестирование status_code")
    public static void checked_status_code(
            String id,
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            String request_name,
            String path
    ) throws JsonException
    {
        given()
                .pathParam("id", id)
                //---> Указание RequestSpecification для формирования request
                .spec(requestSpecification)
                .request(request_name, path)
                .then()
                .spec(responseSpecification)
                .log().ifValidationFails()
                .assertThat()
                .log().body()
                .body(Matchers.anything());
    }

    @DisplayName("Тестирование status_code со схемой user")
    public static void checked_status_code(
            String id,
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            String request_name,
            String path,
            String first_param,
            String second_param
    ) throws JsonException
    {
        given()
                .log().all()
                .pathParam("id", id)
                //---> Указание RequestSpecification для формирования request
                .spec(requestSpecification)
                .relaxedHTTPSValidation()
                //---> body для запроса с методом POST
                .body(new UpdateUserSchema(first_param, second_param))
                .request(request_name, path)
                .then()
                .spec(responseSpecification)
                .log().ifValidationFails()
                .assertThat()
                .log().ifStatusCodeMatches(Matchers.greaterThan(HttpStatus.SC_OK))
                .log().body()
                .extract()
                .response();
    }

    @DisplayName("Тестирование status_code со схемой без id")
    public static void checked_status_code(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            String request_name,
            String path
    ) throws JsonException
    {
        given()
                .log().all()
                //---> Указание RequestSpecification для формирования request
                .spec(requestSpecification)
                .relaxedHTTPSValidation()
                .request(request_name, path)
                .then()
                .spec(responseSpecification)
                .log().ifValidationFails().statusCode(HttpStatus.SC_OK)
                .assertThat()
                .log().ifStatusCodeMatches(Matchers.greaterThan(HttpStatus.SC_OK))
                .body(Matchers.anything())
                .log().body()
                .extract()
                .response();
    }

    @DisplayName("Тестирование status_code со схемой register")
    public static void checked_status_code(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            String request_name,
            String path,
            String first_param,
            String second_param
    ) throws JsonException
    {
        given()
                .log().all()
                //---> Указание RequestSpecification для формирования request
                .spec(requestSpecification)
                .relaxedHTTPSValidation()
                //---> body для запроса с методом POST
                .body(new PostRegisterSchema(first_param, second_param))
                .request(request_name, path)
                .then()
                .spec(responseSpecification)
                .log().ifValidationFails().statusCode(HttpStatus.SC_OK)
                .assertThat()
                .log().ifStatusCodeMatches(Matchers.greaterThan(HttpStatus.SC_OK))
                .body(Matchers.anything())
                .log().body()
                .extract()
                .response();
    }
}
